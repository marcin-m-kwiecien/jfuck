package xyz.kwiecien.jfuck;

import java.io.IOException;
import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

import static java.lang.classfile.ClassFile.*;
import static java.lang.constant.ConstantDescs.*;

public class BFCompiler {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: GenMain <filename>");
            System.exit(1);
        }
        var path = Paths.get(args[0]);
        if (!path.toFile().exists()) {
            System.err.println("Unable to find file: " + args[0]);
            System.exit(1);
        }
        var code = Files.readString(path);
        var operations = new OperationExtractor().extract(code);

        Consumer<CodeBuilder> mainCode = cb -> {
            for (var operation : operations) {
                operation.appendBytecode(cb);
            }
            cb.return_();
        };

        var bytes = ClassFile.of().build(ClassDesc.of("GenClass"), clb -> {
            clb.withFlags(ACC_PUBLIC);
            clb.withMethod(
                            "main",
                            MethodTypeDesc.of(CD_void, CD_String.arrayType()),
                            ACC_PUBLIC + ACC_STATIC,
                            mb -> mb.withCode(mainCode));
        });
        Files.write(Path.of("/tmp/GenClass.class"), bytes);
    }
}
