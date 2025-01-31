package xyz.kwiecien.jfuck.operation;

import java.lang.classfile.CodeBuilder;
import java.lang.constant.MethodTypeDesc;

import static java.lang.constant.ConstantDescs.*;
import static xyz.kwiecien.jfuck.operation.BytecodeConstants.*;

public class ReadOperation implements Operation {
    @Override
    public void appendBytecode(CodeBuilder cb) {
        cb.aload(DATA_VAR_INDEX).iload(PTR_VAR_INDEX)
                .aload(SCANNER_VAR_INDEX)
                .invokevirtual(SCANNER_DESC, "next", MethodTypeDesc.of(CD_String))
                .iconst_0()
                .invokevirtual(CD_String, "charAt", MethodTypeDesc.of(CD_char, CD_int))
                .i2b()
                .bastore();
    }
}
