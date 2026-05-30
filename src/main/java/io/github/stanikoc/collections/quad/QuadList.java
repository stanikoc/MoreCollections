package io.github.stanikoc.collections.quad;

import io.github.stanikoc.collections.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * An immutable {@link List} consisting of 1, 2, 3, or 4 elements.
 * <p>
 * <b>Performance Characteristics &amp; Memory Layout:</b><br>
 * Standard {@link List#of()} implementations utilize dedicated, flat-field
 * classes for 1 or 2 elements ({@code List12}), but immediately degrade to an array-backed
 * wrapper ({@code ListN}) for 3 or more elements.
 * <p>
 * {@code QuadList} bypasses this limitation by explicitly backing up to 4 elements
 * with direct object fields ({@code e0}, {@code e1}, {@code e2}, {@code e3}).
 * This memory layout completely eliminates array allocation, removes array bounds
 * checking overhead during iteration, and guarantees that the elements are kept
 * tightly packed in the CPU cache for {@code get()} and {@code indexOf()} operations.
 * @param <E> the type of elements maintained by this list
 */
public interface QuadList<E> extends ImmutableList<E> {
    static @NotNull <E> QuadList<E> of(@NotNull E e1) {
        return new QuadListImpl<>(e1, null, null, null);
    }

    static @NotNull <E> QuadList<E> of(@NotNull E e1, @NotNull E e2) {
        return new QuadListImpl<>(e1, e2, null, null);
    }

    static @NotNull <E> QuadList<E> of(@NotNull E e1, @NotNull E e2, @NotNull E e3) {
        return new QuadListImpl<>(e1, e2, e3, null);
    }

    static @NotNull <E> QuadList<E> of(@NotNull E e1, @NotNull E e2, @NotNull E e3, @NotNull E e4) {
        return new QuadListImpl<>(e1, e2, e3, e4);
    }

}
