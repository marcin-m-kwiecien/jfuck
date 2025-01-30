package xyz.kwiecien.jfuck.operation;

import java.lang.classfile.CodeBuilder;
import java.util.function.Consumer;

import static xyz.kwiecien.jfuck.operation.BytecodeConstants.DATA_VAR_INDEX;
import static xyz.kwiecien.jfuck.operation.BytecodeConstants.PTR_VAR_INDEX;

public record ModifyStackValueOperation(byte value) implements Operation {
    @Override
    public Consumer<CodeBuilder> appendBytecode() {
        return c -> c.aload(DATA_VAR_INDEX).iload(PTR_VAR_INDEX).dup2() // dup2, because will be used to read value and then store incremented
                .baload()
                .bipush(value)
                .iadd()
                .i2b()
                .bastore();
    }
}
