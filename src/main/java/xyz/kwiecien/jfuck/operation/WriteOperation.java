package xyz.kwiecien.jfuck.operation;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.TypeKind;
import java.lang.classfile.constantpool.MethodRefEntry;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.MethodTypeDesc;
import java.util.function.Consumer;

import static java.lang.constant.ConstantDescs.*;
import static xyz.kwiecien.jfuck.operation.BytecodeConstants.DATA_VAR_INDEX;
import static xyz.kwiecien.jfuck.operation.BytecodeConstants.PTR_VAR_INDEX;

public class WriteOperation implements Operation {
    private static final ClassDesc SYSTEM_DESC = ClassDesc.of("java.lang.System");
    private static final ClassDesc PRINT_STREAM_DESC = ClassDesc.of("java.io.PrintStream");
    private static final ClassDesc STRING_CLASS_DESC = ClassDesc.of("java.lang.String");
    @Override
    public Consumer<CodeBuilder> appendBytecode() {
        return c -> c.getstatic(SYSTEM_DESC, "out", PRINT_STREAM_DESC)
                .new_(STRING_CLASS_DESC).dup() // dup, because we will be initializing it and then using as an argument for print
                .iconst_1().newarray(TypeKind.BYTE).dup() // dup, because we will be filling it with data and then passing to String.<init>
                .iconst_0() // index of byte array to set
                .aload(DATA_VAR_INDEX).iload(PTR_VAR_INDEX).baload() //Load data at pointer
                .bastore() // store loaded data at index zero
                .invokespecial(STRING_CLASS_DESC, INIT_NAME, MethodTypeDesc.of(CD_void, CD_byte.arrayType()))
                .invokevirtual(PRINT_STREAM_DESC, "print", MethodTypeDesc.of(CD_void, CD_String));
    }
}
