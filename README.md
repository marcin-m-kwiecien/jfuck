# Brainfuck to JVM bytecode compiler
## because why not...

Requires JDK24+ to build.

Usage:
```shell
path/to/jdk/bin/java src/main/java/xyz/kwiecien/jfuck/BFCompiler.java source.bf Target.class
```
It will generate `Target.class` file with `Target` class containing compiled BF code.

To run the generated code:
```shell
path/to/jdk/bin/java Target
```