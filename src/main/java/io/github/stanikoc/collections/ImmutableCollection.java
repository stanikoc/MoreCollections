package io.github.stanikoc.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * A root interface for collections that cannot be mutated after creation.
 * <p>
 * This interface intercepts all standard mutator methods defined in the
 * {@link Collection} interface (such as {@code add}, {@code remove}, and {@code clear})
 * and provides default implementations that unconditionally throw an
 * {@link UnsupportedOperationException}.
 * <p>
 * Implementing this interface ensures a strict, compile-time and runtime guarantee
 * that the underlying data structure remains strictly read-only.
 * @param <E> the type of elements in this collection
 */
public interface ImmutableCollection<E> extends Collection<E> {
    @Override
    default boolean add(E e) {
        throw new UnsupportedOperationException("add");
    }

    @Override
    default boolean remove(Object o) {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    default void clear() {
        throw new UnsupportedOperationException("clear");
    }

    @Override
    default boolean addAll(@NotNull Collection<? extends E> c) {
        throw new UnsupportedOperationException("addAll");
    }

    @Override
    default boolean retainAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException("retainAll");
    }

    @Override
    default boolean removeAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException("removeAll");
    }

}