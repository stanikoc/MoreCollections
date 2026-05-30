package me.stani.collections.registry;

import org.jetbrains.annotations.NotNull;

public interface ImmutableRegistry<K, V> extends Registry<K, V> {
    @Override
    default void register(@NotNull K key, @NotNull V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    default void unregister(@NotNull K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    default void clear() {
        throw new UnsupportedOperationException();
    }

}
