package xyz.kwiecien.jfuck;

import org.junit.jupiter.api.Test;
import xyz.kwiecien.jfuck.operation.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationExtractorTest {
    @Test
    void extractManyValueIncrements() {
        var code = "+++++";
        var operations = new OperationExtractor().extract(code);
        assertEquals(List.of(
                new Initialization(),
                new ModifyStackValueOperation((byte) 5)
        ), operations);
    }

    @Test
    void extractManyValueDecrements() {
        var code = "-----";
        var operations = new OperationExtractor().extract(code);
        assertEquals(List.of(
                new Initialization(),
                new ModifyStackValueOperation((byte) -5)
        ), operations);
    }

    @Test
    void extractMixedValueModifications() {
        var code = "++--+-++"; // 2 more "+" than "-"
        var operations = new OperationExtractor().extract(code);
        assertEquals(List.of(
                new Initialization(),
                new ModifyStackValueOperation((byte) 2)
        ), operations);
    }

    @Test
    void extractManyPointerIncrements() {
        var code = ">>>>>";
        var operations = new OperationExtractor().extract(code);
        assertEquals(List.of(
                new Initialization(),
                new ModifyPointerOperation((byte) 5)
        ), operations);
    }

    @Test
    void extractManyPointerDecrements() {
        var code = "<<<<<";
        var operations = new OperationExtractor().extract(code);
        assertEquals(List.of(
                new Initialization(),
                new ModifyPointerOperation((byte) -5)
        ), operations);
    }

    @Test
    void extractMixePointerModifications() {
        var code = "<<>><><<"; // 2 more "<" than ">"
        var operations = new OperationExtractor().extract(code);
        assertEquals(List.of(
                new Initialization(),
                new ModifyPointerOperation((byte) -2)
        ), operations);
    }

    @Test
    void extractMixedOperationsWithoutLoop() {
        var code = ",+>-<.";
        var operations = new OperationExtractor().extract(code);
        assertEquals(List.of(
                new Initialization(),
                new ReadOperation(),
                new ModifyStackValueOperation((byte) 1),
                new ModifyPointerOperation(1),
                new ModifyStackValueOperation((byte) -1),
                new ModifyPointerOperation(-1),
                new WriteOperation()
        ), operations);
    }

    @Test
    void extractSimpleLoop() {
        var code = "[,.]";
        var operations = new OperationExtractor().extract(code);
        assertEquals(List.of(
                new Initialization(),
                new Loop(List.of(
                        new ReadOperation(),
                        new WriteOperation()
                ))
        ), operations);
    }

    @Test
    void extractEmbeddedLoops() {
        var code = ",[,[,.].].";
        var operations = new OperationExtractor().extract(code);
        assertEquals(List.of(
                new Initialization(),
                new ReadOperation(),
                new Loop(List.of(
                        new ReadOperation(),
                        new Loop(List.of(
                                new ReadOperation(),
                                new WriteOperation()
                        )),
                        new WriteOperation()
                )),
                new WriteOperation()
        ), operations);
    }
}