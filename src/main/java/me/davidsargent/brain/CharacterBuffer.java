package me.davidsargent.brain;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * Created by David on 11/13/2016.
 */
public class CharacterBuffer implements Comparable<CharBuffer>, Appendable, CharSequence, Readable {
    private CharBuffer backingBuffer;
    private Stack<Integer> marks = new Stack<Integer>();

    public CharacterBuffer(int size) {
        backingBuffer = CharBuffer.allocate(size);
    }

    public CharBuffer slice() {
        return backingBuffer.slice();
    }

    public CharBuffer duplicate() {
        return backingBuffer.duplicate();
    }

    public CharBuffer asReadOnlyBuffer() {
        return backingBuffer.asReadOnlyBuffer();
    }

    public char get() {
        return backingBuffer.get();
    }

    public CharBuffer put(char c) {
        ensureCapacity(1);
        return backingBuffer.put(c);
    }

    public boolean positionAt(int character) {
        for (int i = backingBuffer.position(); i < backingBuffer.array().length; i++) {
            if (backingBuffer.array()[i] == character) {
                backingBuffer.position(i);
                return true;
            }
        }

        return false;
    }

    public int position() {
        return backingBuffer.position();
    }

    public void position(int position) {
        backingBuffer.position(position);
    }

    private void ensureCapacity(int elementsToAdd) {
        if (elementsToAdd + backingBuffer.position() > backingBuffer.capacity()) {
            CharBuffer temp = CharBuffer.allocate(backingBuffer.capacity() * 2);
            System.arraycopy(backingBuffer.array(), 0, temp.array(), 0, backingBuffer.capacity());
            temp.position(backingBuffer.position());
            backingBuffer = temp;
        }
    }

    public char get(int index) {
        return backingBuffer.get(index);
    }

    public CharBuffer put(int index, char c) {
        ensureCapacity(index - backingBuffer.position());
        return backingBuffer.put(index, c);
    }

    public CharBuffer compact() {
        return backingBuffer.compact();
    }

    public boolean isReadOnly() {
        return backingBuffer.isReadOnly();
    }

    public boolean hasArray() {
        return backingBuffer.hasArray();
    }

    public char[] array() {
        return backingBuffer.array();
    }

    public int arrayOffset() {
        return backingBuffer.arrayOffset();
    }

    public boolean isDirect() {
        return backingBuffer.isDirect();
    }

    public int length() {
        return backingBuffer.length();
    }

    public char charAt(int index) {
        return backingBuffer.charAt(index);
    }

    public CharBuffer subSequence(int start, int end) {
        return backingBuffer.subSequence(start, end);
    }

    public IntStream codePoints() {
        return backingBuffer.codePoints();
    }

    public void mark() {
        marks.push(backingBuffer.position() - 1);
    }

    public void reset() {
        backingBuffer.position(marks.pop());
    }

    public ByteOrder order() {
        return backingBuffer.order();
    }

    public Appendable append(CharSequence csq) throws IOException {
        ensureCapacity(csq.length());
        return backingBuffer.append(csq);
    }

    public Appendable append(CharSequence csq, int start, int end) throws IOException {
        ensureCapacity(end - start + 1);
        return backingBuffer.append(csq, start, end);
    }

    public Appendable append(char c) throws IOException {
        ensureCapacity(1);
        return backingBuffer.append(c);
    }

    public int compareTo(CharBuffer o) {
        return backingBuffer.compareTo(o);
    }

    public int read(CharBuffer cb) throws IOException {
        return backingBuffer.read(cb);
    }
}
