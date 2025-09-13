package com.Core.Collections;

import java.util.Arrays;
import java.util.Objects;

public class GenericHashMap<K, V> {
    private static final int DEFAULT_LENGHT = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] table;
    private int capacity;
    private int size;
    private final float loadFactor;

    public GenericHashMap(){
        this(DEFAULT_LENGHT, DEFAULT_LOAD_FACTOR);
    }

    public GenericHashMap(int capacity, float loadFactor){
        this.capacity = capacity;
        this.table = new Entry[this.capacity];
        this.loadFactor = loadFactor;
        this.size = 0;
    }

    public void put(K key, V value){
        if ((float) size / capacity >= loadFactor){
            resize();
        }

        int index = indexOf(key);
        var newEntry = new Entry<>(key, value, null);
        if (table[index] == null){
            table[index] = newEntry;
        } else{
            var current = table[index];
            while (current.next != null){
                if (Objects.equals(current.key, key)){
                    current.value = value;
                    return;
                }
                current = current.next;
            }
            if (Objects.equals(current.key, key)){
                current.value = value;
            } else {
                current.next = newEntry;
            }
        }

        size ++;
    }

    public V get(K key) {
        int index = indexOf(key);
        Entry<K, V> current = table[index];
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        int index = indexOf(key);
        Entry<K, V> current = table[index];
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    public V remove(K key) {
        int index = indexOf(key);
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    private int indexOf(K key){
        return hash(key) & (capacity - 1);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public String toString(){
        String[] e = Arrays.stream(table).filter(x -> x != null).map(x -> x.value.toString()).toArray(String[]::new);
        return String.join("\n", e);
    }

    private void resize() {
        int newCapacity = capacity * 2;
        Entry<K, V>[] oldTable = table;
        table = new Entry[newCapacity];
        capacity = newCapacity;
        size = 0;
        for (Entry<K, V> entry : oldTable) {
            Entry<K, V> current = entry;
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }

    private int hash(K key){
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }



    private static class Entry<K,V> {
        K key;
        V value;
        Entry<K,V> next;

        public Entry(K key, V value, Entry<K,V> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return this.value.toString();
        }
    }
}
