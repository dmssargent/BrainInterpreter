package me.davidsargent.brain;

public class Registers {
    private int[] registers;
    private int head;

    public Registers(int startSize) {
        this.registers = new int[startSize];
        this.head = 0;
    }

    public void next() {

        verifyNotOutOfBounds(++head);
    }

    public void previous() {
        verifyNotOutOfBounds(--head);
    }

    public void add() {
        registers[head]++;
    }

    public void subtract() {
        registers[head]--;
    }

    public int get() {
        return registers[head];
    }

    public void set(int value) {
        registers[head] = value;
    }

    private void verifyNotOutOfBounds(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Accessing non-existent registers");
        }

        if (position > registers.length - 1) {
            int newArray[] = new int[registers.length * 2];
            System.arraycopy(registers, 0, newArray, 0, registers.length);
            registers = newArray;
        }
    }
}
