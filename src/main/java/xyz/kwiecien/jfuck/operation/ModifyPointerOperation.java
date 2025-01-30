package xyz.kwiecien.jfuck.operation;

import java.lang.classfile.CodeBuilder;
import java.util.function.Consumer;

public record ModifyPointerOperation(int value) implements Operation {
    @Override
    public Consumer<CodeBuilder> appendBytecode() {
        return c -> {};
    }
}
