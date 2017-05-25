package me.davidsargent.brain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String... args) throws IOException {
        Registers registers = new Registers(8);
        CharacterBuffer sourceBuffer = new CharacterBuffer(8);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        System.out.print(">> ");
        String currentLine = input.readLine();
        while (currentLine != null && !Thread.currentThread().isInterrupted()) {
            char[] line = currentLine.toCharArray();
            for (char current : line) {
                sourceBuffer.append(current);
            }
            sourceBuffer.position(sourceBuffer.position() - line.length);
            char current = sourceBuffer.get();
            while (current != 0) {
                interpretCharacter(registers, sourceBuffer, input, current);
                current = sourceBuffer.get();
            }
            System.out.print("\n>> ");
            currentLine = input.readLine();
        }
    }

    static int operationCount = -1;
    private static void interpretCharacter(Registers registers, CharacterBuffer sourceBuffer, BufferedReader input, char current) throws IOException {
        if (Character.isDigit(current)) {
            if (operationCount < 0) operationCount = 0;
            operationCount *= 10;
            operationCount += Character.getNumericValue(current);
            return;
        }

        if (operationCount < 0) operationCount = 1;
        for (; operationCount > 0; operationCount--) {
            switch (current) {
                case '+':
                    registers.add();
                    break;
                case '-':
                    registers.subtract();
                    break;
                case '<':
                    registers.previous();
                    break;
                case '>':
                    registers.next();
                    break;
                case '.':
                    System.out.print((char) registers.get());
                    break;
                case ',':
                    registers.set(input.read());
                    break;
                case '[':
                    if (registers.get() != 0) {
                        sourceBuffer.mark();
                    } else {
                        sourceBuffer.positionAt(']');
                        sourceBuffer.get(); // Character after
                    }
                    break;
                case ']':
                    sourceBuffer.reset();
                    break;
            }
        }
        operationCount = -1;
    }
}