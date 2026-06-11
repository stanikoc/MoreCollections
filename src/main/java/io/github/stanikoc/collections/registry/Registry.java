package io.github.stanikoc.collections.registry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A specialized storage collection designed to provide both fast O(1) key lookups
 * and highly efficient sequential iteration.
 * <p>
 * Typically, developers try to achieve this by managing both a {@code Map} (for lookups)
 * and a {@code List} (for iteration) simultaneously. This interface provides a unified
 * contract for a collection that handles both, avoiding the memory bloat and synchronization
 * headaches of maintaining two separate data structures.
 * @param <K> the type of keys maintained by this registry
 * @param <V> the type of mapped values
 */
public interface Registry<K, V> extends Iterable<V> {
    /**
     * Retrieves the value associated with the given key.
     * @param key the key whose associated value is to be returned
     * @return the value associated with the key, or {@code null} if the key is not registered
     */
    @Nullable V get(@NotNull K key);

    /**
     * Returns the total number of elements currently in the registry.
     * @return the size of the registry
     */
    int size();

    /**
     * Checks if the registry contains an entry for the specified key.
     * @param key the key to check for
     * @return {@code true} if the key exists, {@code false} otherwise
     */
    boolean contains(@NotNull K key);

    /**
     * Registers a new key-value pair into the storage.
     * <p>
     * If the key is already registered, the new value is ignored, and the existing
     * value is returned. If the key is not registered, the new value is stored and
     * {@code null} is returned.
     * * @param key   the key to associate with the value
     * @param value the value to be stored
     * @return the existing value associated with the key, or {@code null} if it was successfully registered
     */
    @Nullable V register(@NotNull K key, @NotNull V value);

    /**
     * Removes the entry associated with the given key from the registry.
     * @param key the key to unregister
     * @return the value that was removed, or {@code null} if the key was not registered
     */
    @Nullable V unregister(@NotNull K key);

    /**
     * Wipes all keys and values from the registry, resetting it to an empty state.
     */
    void clear();

    /**
     * Checks if the registry has no elements.
     * @return {@code true} if the registry is empty, {@code false} otherwise
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns a sequential {@code Stream} with this collection as its source.
     * @return a sequential {@code Stream} over the elements in this collection
     */
    default Stream<V> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

}
