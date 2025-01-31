package xyz.kwiecien.jfuck.operation;

import java.lang.classfile.CodeBuilder;

import static xyz.kwiecien.jfuck.operation.BytecodeConstants.DATA_SIZE;
import static xyz.kwiecien.jfuck.operation.BytecodeConstants.PTR_VAR_INDEX;

public record ModifyPointerOperation(int value) implements Operation {
    @Override
    public void appendBytecode(CodeBuilder cb) {
        cb.iload(PTR_VAR_INDEX)
                .ldc(value).iadd() // ptr = ptr + value
                .ldc(DATA_SIZE).iadd().ldc(DATA_SIZE).irem() // ptr = (ptr + DATA_SIZE) % DATA_SIZE
                .istore(PTR_VAR_INDEX);
    }
}
