package xyz.kwiecien.jfuck.operation;

import java.lang.classfile.CodeBuilder;
import java.util.List;

import static xyz.kwiecien.jfuck.operation.BytecodeConstants.DATA_VAR_INDEX;
import static xyz.kwiecien.jfuck.operation.BytecodeConstants.PTR_VAR_INDEX;

public record Loop(List<Operation> innerOperations) implements Operation {
    @Override
    public void appendBytecode(CodeBuilder cb) {
        cb.block(bc -> {
                    bc.aload(DATA_VAR_INDEX).iload(PTR_VAR_INDEX).baload() //Get data from pointer
                            .ifeq(bc.endLabel()); //Jump out of loop if data == 0
                    for (var innerOperation : innerOperations) {
                        innerOperation.appendBytecode(bc);
                    }
                    bc.goto_(bc.startLabel());
                }
        );
    }
}
