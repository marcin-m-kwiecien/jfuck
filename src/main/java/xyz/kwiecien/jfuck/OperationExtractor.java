package xyz.kwiecien.jfuck;

import xyz.kwiecien.jfuck.operation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class OperationExtractor {
    public List<Operation> extract(String code) {
        Operation currentOperation = new Initialization();
        var operationLists = new Stack<List<Operation>>();
        operationLists.push(new ArrayList<>());

        for (int currentIndex = 0; currentIndex < code.length(); currentIndex++) {
            char c = code.charAt(currentIndex);
            switch (c) {
                case '+':
                case '-':
                    byte valueModification = (byte) (c == '+' ? 1 : -1);
                    if (currentOperation instanceof ModifyStackValueOperation(byte value)) {
                        currentOperation = new ModifyStackValueOperation((byte) (value + valueModification));
                    } else {
                        if (currentOperation != null) {
                            operationLists.peek().add(currentOperation);
                        }
                        currentOperation = new ModifyStackValueOperation(valueModification);
                    }
                    break;
                case '<':
                case '>':
                    int addressModification = c == '>' ? 1 : -1;
                    if (currentOperation instanceof ModifyPointerOperation(int value)) {
                        currentOperation = new ModifyPointerOperation(value + addressModification);
                    } else {
                        if (currentOperation != null) {
                            operationLists.peek().add(currentOperation);
                        }
                        currentOperation = new ModifyPointerOperation(addressModification);
                    }
                    break;
                case '.':
                    if (currentOperation != null) {
                        operationLists.peek().add(currentOperation);
                    }
                    currentOperation = new WriteOperation();
                    break;
                case ',':
                    if (currentOperation != null) {
                        operationLists.peek().add(currentOperation);
                    }
                    currentOperation = new ReadOperation();
                    break;
                case '[':
                    if (currentOperation != null) {
                        operationLists.peek().add(currentOperation);
                    }
                    operationLists.push(new ArrayList<>());
                    currentOperation = null;
                    break;
                case ']':
                    if (currentOperation != null) {
                        operationLists.peek().add(currentOperation);
                    }
                    currentOperation = new Loop(operationLists.pop());
                    break;
            }
        }
        operationLists.peek().add(currentOperation);
        return operationLists.pop();
    }
}
