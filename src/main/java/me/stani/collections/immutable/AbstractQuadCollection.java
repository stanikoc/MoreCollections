package me.stani.collections.immutable;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

abstract class AbstractQuadCollection<E> implements ImmutableCollection<E> {
    protected final E e0;
    protected final E e1;
    protected final E e2;
    protected final E e3;
    protected final int size;

    AbstractQuadCollection(E e0, E e1, E e2, E e3) {
        this(e0, e1, e2, e3, (e3 != null) ? 4 : (e2 != null) ? 3 : (e1 != null) ? 2 : 1);
    }

    AbstractQuadCollection(E e0, E e1, E e2, E e3, int size) {
        this.e0 = e0;
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
        this.size = size;
    }

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final boolean isEmpty() {
        return false;
    }

    @Override
    public final boolean contains(Object o) {
        if (o == null) {
            return false;
        }

        return switch (size) {
            case 1 -> (o == e0 || e0.equals(o));
            case 2 -> (o == e0 || e0.equals(o)) || (o == e1 || e1.equals(o));
            case 3 -> (o == e0 || e0.equals(o)) || (o == e1 || e1.equals(o)) || (o == e2 || e2.equals(o));
            case 4 -> (o == e0 || e0.equals(o)) || (o == e1 || e1.equals(o)) || (o == e2 || e2.equals(o)) || (o == e3 || e3.equals(o));
            default -> false;
        };
    }

    @Override
    public final @NotNull Object @NotNull [] toArray() {
        return switch (size) {
            case 1 -> new Object[]{e0};
            case 2 -> new Object[]{e0, e1};
            case 3 -> new Object[]{e0, e1, e2};
            default -> new Object[]{e0, e1, e2, e3};
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    public final @NotNull <T> T @NotNull [] toArray(T @NotNull [] a) {
        if (a.length < size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        }

        switch (size) {
            case 1:
                a[0] = (T) e0;
                break;
            case 2:
                a[0] = (T) e0;
                a[1] = (T) e1;
                break;
            case 3:
                a[0] = (T) e0;
                a[1] = (T) e1;
                a[2] = (T) e2;
                break;
            case 4:
                a[0] = (T) e0;
                a[1] = (T) e1;
                a[2] = (T) e2;
                a[3] = (T) e3;
                break;
        }

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public final boolean containsAll(@NotNull Collection<?> c) {
        if (c == this) {
            return true;
        }

        if (this instanceof Set<?> && c instanceof Set<?> && c.size() > this.size) {
            return false;
        }

        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public final String toString() {
        return switch (size) {
            case 1 -> "[" + e0 + "]";
            case 2 -> "[" + e0 + ", " + e1 + "]";
            case 3 -> "[" + e0 + ", " + e1 + ", " + e2 + "]";
            case 4 -> "[" + e0 + ", " + e1 + ", " + e2 + ", " + e3 + "]";
            default -> "[]";
        };
    }

    @Override
    public final @NotNull Iterator<E>  iterator() {
        return new Iterator<>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public E next() {
                if (i >= size) {
                    throw new NoSuchElementException();
                }

                return switch (i++) {
                    case 0 -> e0;
                    case 1 -> e1;
                    case 2 -> e2;
                    case 3 -> e3;
                    default -> throw new NoSuchElementException();
                };
            }
        };
    }

    protected static boolean eq(Object e, Object o) {
        return o == e || e.equals(o);
    }

}
