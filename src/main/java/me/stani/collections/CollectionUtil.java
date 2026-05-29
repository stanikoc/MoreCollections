package me.stani.collections;

import java.util.Queue;
import java.util.function.Consumer;

public final class CollectionUtil {
    private CollectionUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void emptyQueue(Queue<Runnable> queue) {
        emptyQueue(queue, Runnable::run);
    }

    public static <T> void emptyQueue(Queue<T> queue, Consumer<T> onPoll) {
        while (!queue.isEmpty()) {
            T polled = queue.poll();
            if (polled != null) {
                onPoll.accept(polled);
            }
        }
    }

}
