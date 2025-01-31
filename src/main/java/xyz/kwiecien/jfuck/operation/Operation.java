package xyz.kwiecien.jfuck.operation;

import java.lang.classfile.CodeBuilder;

public interface Operation {
    void appendBytecode(CodeBuilder cb);
}
