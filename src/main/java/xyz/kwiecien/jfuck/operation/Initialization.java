package xyz.kwiecien.jfuck.operation;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.TypeKind;
import java.lang.constant.MethodTypeDesc;
import java.util.function.Consumer;

import static java.lang.constant.ConstantDescs.CD_void;
import static java.lang.constant.ConstantDescs.INIT_NAME;
import static xyz.kwiecien.jfuck.operation.BytecodeConstants.*;

public class Initialization implements Operation {
    @Override
    public Consumer<CodeBuilder> appendBytecode() {
        return c -> c
                .iconst_0().istore(PTR_VAR_INDEX) // Pointer init
                .sipush(DATA_SIZE).newarray(TypeKind.BYTE).astore(DATA_VAR_INDEX) // Initialize array of 30k bytes
                .new_(SCANNER_DESC).dup() //dup, because we will be initializing it and then setting a field
                .getstatic(SYSTEM_DESC, "in", INPUT_STREAM_DESC)
                .invokespecial(SCANNER_DESC, INIT_NAME, MethodTypeDesc.of(CD_void, INPUT_STREAM_DESC))
                .astore(SCANNER_VAR_INDEX);
    }
}
