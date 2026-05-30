package io.github.stanikoc.collections.quad;

import io.github.stanikoc.collections.ImmutableSet;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * An immutable {@link Set} consisting of up to 4 elements.
 * <p>
 * <b>Deduplication &amp; Safety:</b><br>
 * Standard {@link Set#of()} strictly throws an {@link IllegalArgumentException}
 * if duplicate elements are provided. If a developer needs a set of up to 4 unverified elements,
 * they must typically fall back to {@code new HashSet<>()}. This triggers massive
 * overhead: allocating arrays, instantiating {@code Node} objects, and calculating hash buckets.
 * {@code QuadSet} resolves this by performing safe, inline deduplication before construction,
 * ensuring zero array or node allocations.
 * <p>
 * <b>Performance Characteristics &amp; Memory Layout:</b><br>
 * Even when standard {@link Set#of()} does not fail, it relies on a dedicated field-backed
 * class only for 1 or 2 elements ({@code Set12}). For 3 or 4 elements, it falls back to an
 * array-backed {@code SetN}. {@code SetN} suffers from slower {@code contains()} operations
 * and slower iteration speeds due to pointer chasing and array boundaries.
 * <p>
 * {@code QuadSet} maintains up to 4 elements as flat, direct object fields. Lookups
 * bypass iteration entirely, utilizing instant {@code tableswitch} bytecode jumps for much better performance and cache locality.
 * @param <E> the type of elements maintained by this set
 */
public interface QuadSet<E> extends ImmutableSet<E> {
    static @NotNull <E> QuadSet<E> of(@NotNull E e1) {
        return new QuadSetImpl<>(e1, null, null, null);
    }

    static @NotNull <E> QuadSet<E> of(@NotNull E e1, @NotNull E e2) {
        return e1.equals(e2)
                ? new QuadSetImpl<>(e1, null, null, null)
                : new QuadSetImpl<>(e1, e2, null, null);
    }

    static @NotNull <E> QuadSet<E> of(@NotNull E e1, @NotNull E e2, @NotNull E e3) {
        E t1 = null, t2 = null;
        if (!e2.equals(e1)) {
            t1 = e2;
        }

        if (!e3.equals(e1) && !e3.equals(t1)) {
            if (t1 == null) {
                t1 = e3;
            } else {
                t2 = e3;
            }
        }

        return new QuadSetImpl<>(e1, t1, t2, null);
    }

    static @NotNull <E> QuadSet<E> of(@NotNull E e1, @NotNull E e2, @NotNull E e3, @NotNull E e4) {
        E t1 = null, t2 = null, t3 = null;
        if (!e2.equals(e1)) {
            t1 = e2;
        }

        if (!e3.equals(e1) && !e3.equals(t1)) {
            if (t1 == null) {
                t1 = e3;
            } else {
                t2 = e3;
            }
        }

        if (!e4.equals(e1) && !e4.equals(t1) && !e4.equals(t2)) {
            if (t1 == null) {
                t1 = e4;
            } else if (t2 == null) {
                t2 = e4;
            } else {
                t3 = e4;
            }
        }

        return new QuadSetImpl<>(e1, t1, t2, t3);
    }

}