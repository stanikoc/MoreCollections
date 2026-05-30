package io.github.stanikoc.collections.quad;

import java.util.Set;

final class QuadSetImpl<E> extends AbstractQuadCollection<E> implements QuadSet<E> {
    QuadSetImpl(E e0, E e1, E e2, E e3) {
        super(e0, e1, e2, e3);
    }

    @Override
    public int hashCode() {
        return switch (size) {
            case 1 -> e0.hashCode();
            case 2 -> e0.hashCode() + e1.hashCode();
            case 3 -> e0.hashCode() + e1.hashCode() + e2.hashCode();
            case 4 -> e0.hashCode() + e1.hashCode() + e2.hashCode() + e3.hashCode();
            default -> 0;
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Set<?> s) || s.size() != this.size) {
            return false;
        }

        try {
            return switch (size) {
                case 1 -> s.contains(e0);
                case 2 -> s.contains(e0) && s.contains(e1);
                case 3 -> s.contains(e0) && s.contains(e1) && s.contains(e2);
                case 4 -> s.contains(e0) && s.contains(e1) && s.contains(e2) && s.contains(e3);
                default -> false;
            };
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

}
