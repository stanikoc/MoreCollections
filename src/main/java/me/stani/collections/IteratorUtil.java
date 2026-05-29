package me.stani.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class IteratorUtil {
    @SuppressWarnings("rawtypes")
    private static final Iterator EMPTY_ITERATOR = new Iterator<>() {
        public boolean hasNext() {
            return false;
        }

        public Object next() {
            throw new NoSuchElementException();
        }
    };

    private IteratorUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    @SuppressWarnings("unchecked")
    public static <T> @NotNull Iterator<T> forArray(@NotNull T[] array) {
        if (array.length == 0) {
            return EMPTY_ITERATOR;
        }

        return new Iterator<>() {
            private int i;

            @Override
            public boolean hasNext() {
                return i < array.length;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return array[i++];
            }
        };
    }

}
