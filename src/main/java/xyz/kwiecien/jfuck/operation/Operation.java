package xyz.kwiecien.jfuck.operation;

import java.lang.classfile.CodeBuilder;
import java.util.function.Consumer;

public interface Operation {
    Consumer<CodeBuilder> appendBytecode();
}
