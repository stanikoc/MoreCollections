package io.github.stanikoc.collections.quad;

import org.jetbrains.annotations.NotNull;

import java.util.*;

final class QuadListImpl<E> extends AbstractQuadCollection<E> implements QuadList<E> {
    QuadListImpl(E e0, E e1, E e2, E e3) {
        super(e0, e1, e2, e3);
    }

    @Override
    public E get(int index) {
        return switch (index) {
            case 0 -> e0;
            case 1 -> e1;
            case 2 -> e2;
            case 3 -> e3;
            default -> throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        };
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            return -1;
        }

        return switch (size) {
            case 1 -> eq(e0, o) ? 0 : -1;
            case 2 -> eq(e0, o) ? 0 : eq(e1, o) ? 1 : -1;
            case 3 -> eq(e0, o) ? 0 : eq(e1, o) ? 1 : eq(e2, o) ? 2 : -1;
            case 4 -> eq(e0, o) ? 0 : eq(e1, o) ? 1 : eq(e2, o) ? 2 : eq(e3, o) ? 3 : -1;
            default -> -1;
        };
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            return -1;
        }

        return switch (size) {
            case 1 -> eq(e0, o) ? 0 : -1;
            case 2 -> eq(e1, o) ? 1 : eq(e0, o) ? 0 : -1;
            case 3 -> eq(e2, o) ? 2 : eq(e1, o) ? 1 : eq(e0, o) ? 0 : -1;
            case 4 -> eq(e3, o) ? 3 : eq(e2, o) ? 2 : eq(e1, o) ? 1 : eq(e0, o) ? 0 : -1;
            default -> -1;
        };
    }

    @Override
    public @NotNull ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public @NotNull ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        return new ListIterator<>() {
            private int i = index;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public E next() {
                if (i >= size) {
                    throw new NoSuchElementException();
                }

                return get(i++);
            }

            @Override
            public boolean hasPrevious() {
                return i > 0;
            }

            @Override
            public E previous() {
                if (i <= 0) {
                    throw new NoSuchElementException();
                }

                return get(--i);
            }

            @Override
            public int nextIndex() {
                return i;
            }

            @Override
            public int previousIndex() {
                return i - 1;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(E e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add(E e) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public @NotNull List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }

        int subSize = toIndex - fromIndex;
        if (subSize == size) {
            return this;
        }

        return switch (subSize) {
            case 1 -> QuadList.of(get(fromIndex));
            case 2 -> QuadList.of(get(fromIndex), get(fromIndex + 1));
            case 3 -> QuadList.of(get(fromIndex), get(fromIndex + 1), get(fromIndex + 2));
            default -> List.of();
        };
    }

    @Override
    public int hashCode() {
        return switch (size) {
            case 1 -> 31 + e0.hashCode();
            case 2 -> 31 * (31 + e0.hashCode()) + e1.hashCode();
            case 3 -> 31 * (31 * (31 + e0.hashCode()) + e1.hashCode()) + e2.hashCode();
            case 4 -> 31 * (31 * (31 * (31 + e0.hashCode()) + e1.hashCode()) + e2.hashCode()) + e3.hashCode();
            default -> 1;
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof List<?> l) || l.size() != this.size) {
            return false;
        }

        try {
            return switch (size) {
                case 1 -> e0.equals(l.get(0));
                case 2 -> e0.equals(l.get(0)) && e1.equals(l.get(1));
                case 3 -> e0.equals(l.get(0)) && e1.equals(l.get(1)) && e2.equals(l.get(2));
                case 4 -> e0.equals(l.get(0)) && e1.equals(l.get(1)) && e2.equals(l.get(2)) && e3.equals(l.get(3));
                default -> false;
            };
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

}
