package io.github.stanikoc.collections.registry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;

/**
 * An implementation of {@link Registry} backed by parallel arrays.
 * <p>
 * Instead of wrapping key-value pairs in entry objects, this registry maintains two
 * parallel arrays ({@code keys} and {@code values}) alongside a flat {@code int[]} hash table.
 * The hash table stores the corresponding array index for each key. This structure
 * provides O(1) lookup performance comparable to a {@link java.util.HashMap}, while maintaining
 * sequential iteration performance comparable to an {@link java.util.ArrayList}. Iteration occurs
 * directly over the contiguous values array, avoiding node allocation and pointer chasing.
 * <p>
 * Because the internal arrays must remain packed without gaps, registration and
 * unregistration operations require array copying and are inherently slower than
 * those in standard collections.
 * <p>
 * <strong>Note: This implementation is not thread-safe.</strong>
 *
 * @param <K> the type of keys
 * @param <V> the type of mapped values
 */
public class HashRegistry<K, V> implements Registry<K, V> {
    @SuppressWarnings("unchecked")
    private @NotNull K @NotNull [] keys = (K[]) StaticReferences.emptyObjects();
    @SuppressWarnings("unchecked")
    private @NotNull V @NotNull [] values = (V[]) StaticReferences.emptyObjects();
    private int[] table = StaticReferences.emptyInts();

    @Override
    public final int size() {
        return values.length;
    }

    @Override
    public final boolean contains(@NotNull K key) {
        return indexOf(key) != -1;
    }

    @Override
    public final @NotNull Iterator<V> iterator() {
        return IteratorUtil.forArray(values);
    }

    @Override
    public @Nullable V get(@NotNull K key) {
        int i = indexOf(key);
        return i == -1 ? null : values[i];
    }

    @Override
    public V register(@NotNull K key, @NotNull V value) {
        int existing = indexOf(key);
        if (existing != -1) {
            return values[existing];
        }

        int last = values.length;
        keys = Arrays.copyOf(keys, last + 1);
        values = Arrays.copyOf(values, last + 1);
        keys[last] = key;
        values[last] = value;
        if (table.length == 0 || values.length > (table.length >> 1) + (table.length >> 2)) {
            rebuildTable();
        } else {
            insert(key, last);
        }

        return null;
    }

    @Override
    public V unregister(@NotNull K key) {
        int i = indexOf(key);
        if (i != -1) {
            V removedValue = values[i];
            int i0 = values.length - 1;
            if (i != i0) {
                keys[i] = keys[i0];
                values[i] = values[i0];
            }

            keys = Arrays.copyOf(keys, i0);
            values = Arrays.copyOf(values, i0);
            rebuildTable();
            return removedValue;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        keys = (K[]) StaticReferences.emptyObjects();
        values = (V[]) StaticReferences.emptyObjects();
        table = StaticReferences.emptyInts();
    }

    private int indexOf(K key) {
        if (table.length == 0) {
            return -1;
        }

        int mask = table.length - 1;
        int slot = hash(key) & mask;
        int i;
        while ((i = table[slot]) != -1) {
            K o = keys[i];
            if (key == o || key.equals(o)) {
                return i;
            }

            slot = (slot + 1) & mask;
        }

        return -1;
    }

    private void rebuildTable() {
        int capacity = table.length == 0 ? 8 : table.length;
        while (values.length > (capacity >> 1) + (capacity >> 2)) {
            capacity <<= 1;
        }

        if (table.length != capacity) {
            table = new int[capacity];
        }

        Arrays.fill(table, -1);
        for (int i = 0; i < values.length; i++) {
            insert(keys[i], i);
        }
    }

    private void insert(K key, int index) {
        int mask = table.length - 1;
        int slot = hash(key) & mask;
        while (table[slot] != -1) {
            slot = (slot + 1) & mask;
        }

        table[slot] = index;
    }

    private static <K> int hash(K key) {
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

}
