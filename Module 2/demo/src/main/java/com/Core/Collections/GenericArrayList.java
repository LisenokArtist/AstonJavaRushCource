package com.Core.Collections;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class GenericArrayList<T> {
    private T[] array;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public GenericArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public GenericArrayList(int capacity) {
        array = (T[]) new Object[capacity];
        size = 0;
    }

    public GenericArrayList(T[] array){
        this.array = array;
        size = this.array.length;
    }

    public void add(T element) {
        if (size == array.length) {
            resize();
        }
        array[size++] = element;
    }

    public void addAll(T[] elements){
        for (T t : elements) {
            this.add(t);
        }
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return array[index];
    }

    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        array[index] = element;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T removedElement = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[--size] = null;
        return removedElement;
    }

    private void resize() {
        int newCapacity = array.length * 2;
        T[] newArray = (T[]) new Object[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    public String toString(){
        String[] e = Arrays.stream(this.array).filter(x -> x != null).map(x -> x.toString()).toArray(String[]::new);
        return String.join("\n", e);
    }

    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    private Spliterator<T> spliterator() {
        return Spliterators.spliterator(array, 0);
    }
}
