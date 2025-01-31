import org.junit.jupiter.api.Test;
import xyz.kwiecien.jfuck.operation.*;

import java.io.IOException;
import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

import static java.lang.classfile.ClassFile.*;
import static java.lang.constant.ConstantDescs.*;
import static xyz.kwiecien.jfuck.operation.BytecodeConstants.DATA_SIZE_CONSTANT_NAME;

public class OperationsTest {
    @Test
    void testGen() throws IOException {
        Consumer<CodeBuilder> initCode = cb -> cb
                .aload(0)
                .invokespecial(CD_Object, INIT_NAME, MTD_void).return_();

        Consumer<CodeBuilder> addCode = cb -> {
            new Initialization().appendBytecode().accept(cb);
            new ReadOperation().appendBytecode().accept(cb);
            new ModifyPointerOperation(1).appendBytecode().accept(cb);
            new ModifyStackValueOperation((byte) 6).appendBytecode().accept(cb);
            new Loop(List.of(
                    new ModifyPointerOperation(-1),
                    new ModifyStackValueOperation((byte) -8),
                    new ModifyPointerOperation(1),
                    new ModifyStackValueOperation((byte) -1)
            )).appendBytecode().accept(cb);
            new ReadOperation().appendBytecode().accept(cb);
            new Loop(List.of(
                    new ModifyPointerOperation(-1),
                    new ModifyStackValueOperation((byte) 1),
                    new ModifyPointerOperation(1),
                    new ModifyStackValueOperation((byte) -1)
            )).appendBytecode().accept(cb);
            new ModifyPointerOperation(-1).appendBytecode().accept(cb);
            new WriteOperation().appendBytecode().accept(cb);
            cb.return_();
        };

        var bytes = ClassFile.of().build(ClassDesc.of("GenClass"), clb -> {
            clb.withFlags(ACC_PUBLIC);
            clb.withField(DATA_SIZE_CONSTANT_NAME, CD_int, ACC_STATIC | ACC_FINAL);
            clb.withMethod(
                            INIT_NAME,
                            MTD_void,
                            ACC_PUBLIC,
                            mb -> mb.withCode(initCode))
                    .withMethod(
                            "main",
                            MethodTypeDesc.of(CD_void, CD_String.arrayType()),
                            ACC_PUBLIC + ACC_STATIC,
                            mb -> mb.withCode(addCode));
        });
        Files.write(Path.of("/tmp/GenClass.class"), bytes);
    }
}
