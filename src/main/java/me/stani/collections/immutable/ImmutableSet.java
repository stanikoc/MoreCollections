package me.stani.collections.immutable;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

/**
 * A strictly unmodifiable {@link Set}.
 * <p>
 * This interface extends both {@link Set} and {@link ImmutableCollection},
 * ensuring that all mutator methods throw an {@link UnsupportedOperationException}.
 * The characteristics of this set match those of unmodifiable sets provided
 * by the Java Collections Framework, but act as a unified type hierarchy for
 * specialized internal collections.
 * * @param <E> the type of elements in this set
 */
public interface ImmutableSet<E> extends Set<E>, ImmutableCollection<E> {
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

}