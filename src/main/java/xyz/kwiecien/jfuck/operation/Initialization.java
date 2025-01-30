package xyz.kwiecien.jfuck.operation;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.TypeKind;
import java.util.function.Consumer;

import static xyz.kwiecien.jfuck.operation.BytecodeConstants.DATA_VAR_INDEX;
import static xyz.kwiecien.jfuck.operation.BytecodeConstants.PTR_VAR_INDEX;

public class Initialization implements Operation {
    @Override
    public Consumer<CodeBuilder> appendBytecode() {
        return c -> c
                .iconst_0().istore(PTR_VAR_INDEX) // Pointer init
                .sipush(30000).newarray(TypeKind.BYTE).astore(DATA_VAR_INDEX); // Initialize array of 30k bytes
    }
}
