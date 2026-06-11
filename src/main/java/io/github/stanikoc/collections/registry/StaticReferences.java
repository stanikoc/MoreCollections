package io.github.stanikoc.collections.registry;

final class StaticReferences {
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private static final int[] EMPTY_INT_ARRAY = new int[0];

    private StaticReferences() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Object[] emptyObjects() {
        return EMPTY_OBJECT_ARRAY;
    }

    public static int[] emptyInts() {
        return EMPTY_INT_ARRAY;
    }

}
