package xyz.kwiecien.jfuck;

import java.io.IOException;
import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

import static java.lang.constant.ConstantDescs.*;

public class SimpleReturnGenerator {
    public static void main(String[] args) throws IOException {
        Consumer<CodeBuilder> initCode = cb -> cb
                .aload(0)
                .invokespecial(CD_Object, INIT_NAME, MTD_void).return_();
        Consumer<CodeBuilder> mainCode = cb -> cb
                .iconst_5()
                .istore(1)
                .iload(1)
                .invokestatic(ClassDesc.of("java.lang", "System"), "exit", MethodTypeDesc.of(CD_void, CD_int))
                .return_();

        var bytes = ClassFile.of().build(ClassDesc.of("GenClass"), clb ->
                clb.withFlags(ClassFile.ACC_PUBLIC)
                        .withMethod(
                                INIT_NAME,
                                MTD_void,
                                ClassFile.ACC_PUBLIC,
                                mb -> mb.withCode(initCode))
                        .withMethod(
                                "main",
                                MethodTypeDesc.of(CD_void, CD_String.arrayType()),
                                ClassFile.ACC_PUBLIC + ClassFile.ACC_STATIC,
                                mb -> mb.withCode(mainCode)));
        Files.write(Path.of("/tmp/GenClass.class"), bytes);
    }
}
