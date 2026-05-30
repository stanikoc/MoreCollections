package io.github.stanikoc.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * A strictly unmodifiable {@link List}.
 * <p>
 * This interface extends both {@link List} and {@link ImmutableCollection},
 * ensuring that all index-based mutator methods (such as {@code set},
 * {@code add} at index, and {@code remove} at index) unconditionally throw an
 * {@link UnsupportedOperationException}.
 * @param <E> the type of elements in this list
 */
public interface ImmutableList<E> extends List<E>, ImmutableCollection<E> {
    @Override
    default boolean add(E e) {
        return ImmutableCollection.super.add(e);
    }

    @Override
    default boolean remove(Object o) {
        return ImmutableCollection.super.remove(o);
    }

    @Override
    default void clear() {
        ImmutableCollection.super.clear();
    }

    @Override
    default boolean addAll(@NotNull Collection<? extends E> c) {
        return ImmutableCollection.super.addAll(c);
    }

    @Override
    default boolean retainAll(@NotNull Collection<?> c) {
        return ImmutableCollection.super.retainAll(c);
    }

    @Override
    default boolean removeAll(@NotNull Collection<?> c) {
        return ImmutableCollection.super.removeAll(c);
    }

    @Override
    default E remove(int index) {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    default void add(int index, E element) {
        throw new UnsupportedOperationException("add");
    }

    @Override
    default E set(int index, E element) {
        throw new UnsupportedOperationException("set");
    }

    @Override
    default boolean addAll(int index, @NotNull Collection<? extends E> c) {
        throw new UnsupportedOperationException("addAll");
    }

}