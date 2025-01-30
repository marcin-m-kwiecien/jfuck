package xyz.kwiecien.jfuck;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Stack;

public class BFMain {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: GenMain <filename>");
            System.exit(1);
        }
        var path = Paths.get(args[0]);
        if (!path.toFile().exists()) {
            System.err.println("Unable to find file: " + args[0]);
            System.exit(1);
        }
        var code = Files.readString(path);
        var array = new byte[30000];
        var ptr = 0;
        var loopStartIndex = new Stack<Integer>();
        var scanner = new Scanner(System.in);

        for (int i = 0; i < code.length(); i++) {
            var c = code.charAt(i);
            switch (c) {
                case '>':
                    ptr++;
                    if (ptr == array.length) {
                        ptr = 0;
                    }
                    break;
                case '<':
                    ptr--;
                    if (ptr == -1) {
                        ptr = array.length - 1;
                    }
                    break;
                case '+':
                    array[ptr]++;
                    break;
                case '-':
                    array[ptr]--;
                    break;
                case '.':
                    System.out.print(new String(new byte[] {array[ptr]}));
                    break;
                case ',':
                    array[ptr] = (byte) scanner.next().charAt(0);
                    break;
                case ']':
                    i = loopStartIndex.pop();
                    // pass-through to the '[' handling on purpose
                case '[':
                    if (array[ptr] > 0) {
                        loopStartIndex.push(i);
                        continue;
                    }
                    int nestingLevel = 0;
                    while (true) {
                        i++;
                        char nextChar = code.charAt(i);
                        if (nextChar == '[') {
                            nestingLevel++;
                        } else if (nextChar == ']' && nestingLevel == 0) {
                            break;
                        } else if (nextChar == ']') {
                            nestingLevel--;
                        }
                    }
                    break;
            }
        }

    }
}
