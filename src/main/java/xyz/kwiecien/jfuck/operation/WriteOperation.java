package xyz.kwiecien.jfuck.operation;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.TypeKind;
import java.lang.constant.MethodTypeDesc;

import static java.lang.constant.ConstantDescs.*;
import static xyz.kwiecien.jfuck.operation.BytecodeConstants.DATA_VAR_INDEX;
import static xyz.kwiecien.jfuck.operation.BytecodeConstants.PTR_VAR_INDEX;

public record WriteOperation() implements Operation {
    @Override
    public void appendBytecode(CodeBuilder cb) {
        cb.getstatic(BytecodeConstants.SYSTEM_DESC, "out", BytecodeConstants.PRINT_STREAM_DESC)
                .new_(BytecodeConstants.STRING_CLASS_DESC).dup() // dup, because we will be initializing it and then using as an argument for print
                .iconst_1().newarray(TypeKind.BYTE).dup() // dup, because we will be filling it with data and then passing to String.<init>
                .iconst_0() // index of byte array to set
                .aload(DATA_VAR_INDEX).iload(PTR_VAR_INDEX).baload() //Load data at pointer
                .bastore() // store loaded data at index zero
                .invokespecial(BytecodeConstants.STRING_CLASS_DESC, INIT_NAME, MethodTypeDesc.of(CD_void, CD_byte.arrayType()))
                .invokevirtual(BytecodeConstants.PRINT_STREAM_DESC, "print", MethodTypeDesc.of(CD_void, CD_String));
    }
}
