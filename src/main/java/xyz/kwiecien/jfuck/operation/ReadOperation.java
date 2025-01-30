package xyz.kwiecien.jfuck.operation;

import java.lang.classfile.CodeBuilder;
import java.util.function.Consumer;

public class ReadOperation implements Operation {
    @Override
    public Consumer<CodeBuilder> appendBytecode() {
        return c -> {};
    }
}
