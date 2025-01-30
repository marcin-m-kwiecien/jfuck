import org.junit.jupiter.api.Test;
import xyz.kwiecien.jfuck.operation.Initialization;
import xyz.kwiecien.jfuck.operation.ModifyStackValueOperation;
import xyz.kwiecien.jfuck.operation.WriteOperation;

import java.io.IOException;
import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

import static java.lang.constant.ConstantDescs.*;

public class OperationsTest {
    @Test
    void testGen() throws IOException {
        Consumer<CodeBuilder> initCode = cb -> cb
                .aload(0)
                .invokespecial(CD_Object, INIT_NAME, MTD_void).return_();

                Consumer<CodeBuilder> mainCode = cb -> {
                    new Initialization().appendBytecode().accept(cb);
                    new ModifyStackValueOperation((byte) 0x30).appendBytecode().accept(cb);
                    new WriteOperation().appendBytecode().accept(cb);
                    cb.return_();
                };

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
