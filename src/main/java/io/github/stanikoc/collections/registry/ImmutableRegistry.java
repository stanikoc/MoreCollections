package io.github.stanikoc.collections.registry;

import org.jetbrains.annotations.NotNull;

public interface ImmutableRegistry<K, V> extends Registry<K, V> {
    @Override
    default V register(@NotNull K key, @NotNull V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    default V unregister(@NotNull K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    default void clear() {
        throw new UnsupportedOperationException();
    }

}
