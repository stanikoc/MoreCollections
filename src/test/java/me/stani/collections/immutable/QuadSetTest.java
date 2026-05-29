package me.stani.collections.immutable;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class QuadSetTest {
    @Test
    void testCreationAndSize() {
        QuadSet<String> s1 = QuadSet.of("A");
        assertEquals(1, s1.size());

        QuadSet<String> s2 = QuadSet.of("A", "B");
        assertEquals(2, s2.size());

        QuadSet<String> s3 = QuadSet.of("A", "B", "C");
        assertEquals(3, s3.size());

        QuadSet<String> s4 = QuadSet.of("A", "B", "C", "D");
        assertEquals(4, s4.size());
    }

    @Test
    void testDeduplication() {
        // Size 1 from duplicates
        assertEquals(1, QuadSet.of("A", "A").size());
        assertEquals(1, QuadSet.of("A", "A", "A").size());
        assertEquals(1, QuadSet.of("A", "A", "A", "A").size());

        // Size 2 from duplicates
        assertEquals(2, QuadSet.of("A", "B", "A").size());
        assertEquals(2, QuadSet.of("A", "A", "B", "B").size());

        // Size 3 from duplicates
        QuadSet<String> s3 = QuadSet.of("A", "B", "A", "C");
        assertEquals(3, s3.size());
        assertTrue(s3.containsAll(Set.of("A", "B", "C")));
    }

    @Test
    void testContainsAndContainsAll() {
        QuadSet<Integer> set = QuadSet.of(1, 2, 3);

        assertTrue(set.contains(1));
        assertTrue(set.contains(2));
        assertTrue(set.contains(3));
        assertFalse(set.contains(4));
        assertFalse(set.contains(null)); // Safe to test since contains(Object) lacks @NotNull

        assertTrue(set.containsAll(List.of(1, 2)));
        assertTrue(set.containsAll(List.of(3, 1, 2)));
        assertFalse(set.containsAll(List.of(1, 2, 3, 4)));

        // Fast-path Set rejection test
        assertFalse(set.containsAll(Set.of(1, 2, 3, 4)));
    }

    @Test
    void testImmutability() {
        QuadSet<String> set = QuadSet.of("A", "B");

        assertThrows(UnsupportedOperationException.class, () -> set.add("C"));
        assertThrows(UnsupportedOperationException.class, () -> set.remove("A"));
        assertThrows(UnsupportedOperationException.class, set::clear);
        assertThrows(UnsupportedOperationException.class, () -> set.addAll(Set.of("C")));
        assertThrows(UnsupportedOperationException.class, () -> set.retainAll(Set.of("A")));
        assertThrows(UnsupportedOperationException.class, () -> set.removeAll(Set.of("B")));
    }

    @Test
    void testEqualsAndHashCode() {
        QuadSet<String> quadSet = QuadSet.of("A", "B", "C", "D");
        Set<String> standardSet = Set.of("D", "B", "C", "A"); // Out of order on purpose

        assertEquals(standardSet, quadSet, "QuadSet should equal a standard Set with the same elements");
        assertEquals(quadSet, standardSet, "Standard Set should equal a QuadSet with the same elements");
        assertEquals(standardSet.hashCode(), quadSet.hashCode(), "HashCodes must match exactly");
    }

    @Test
    void testIteratorAndToArray() {
        QuadSet<String> set = QuadSet.of("X", "Y");

        // Iterator
        Iterator<String> it = set.iterator();
        assertTrue(it.hasNext());
        assertEquals("X", it.next());
        assertTrue(it.hasNext());
        assertEquals("Y", it.next());
        assertFalse(it.hasNext());

        // toArray
        Object[] arr1 = set.toArray();
        assertArrayEquals(new Object[]{"X", "Y"}, arr1);

        String[] arr2 = set.toArray(new String[0]);
        assertArrayEquals(new String[]{"X", "Y"}, arr2);

        String[] arr3 = set.toArray(new String[3]);
        assertArrayEquals(new String[]{"X", "Y", null}, arr3);
    }

}