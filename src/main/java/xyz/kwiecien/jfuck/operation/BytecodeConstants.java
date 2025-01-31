package xyz.kwiecien.jfuck.operation;

import java.lang.constant.ClassDesc;

public interface BytecodeConstants {
    String DATA_SIZE_CONSTANT_NAME = "DATA_SIZE";
    int DATA_SIZE = 30000;

    int PTR_VAR_INDEX = 1;
    int DATA_VAR_INDEX = 2;
    int SCANNER_VAR_INDEX = 3;
    ClassDesc SYSTEM_DESC = ClassDesc.of("java.lang.System");
    ClassDesc PRINT_STREAM_DESC = ClassDesc.of("java.io.PrintStream");
    ClassDesc INPUT_STREAM_DESC = ClassDesc.of("java.io.InputStream");
    ClassDesc SCANNER_DESC = ClassDesc.of("java.util.Scanner");
    ClassDesc STRING_CLASS_DESC = ClassDesc.of("java.lang.String");
}
