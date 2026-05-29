package me.stani.collections.immutable;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class QuadListTest {
    @Test
    void testCreationAndSize() {
        QuadList<String> l1 = QuadList.of("A");
        assertEquals(1, l1.size());

        QuadList<String> l2 = QuadList.of("A", "B");
        assertEquals(2, l2.size());

        QuadList<String> l3 = QuadList.of("A", "B", "C");
        assertEquals(3, l3.size());

        QuadList<String> l4 = QuadList.of("A", "B", "C", "D");
        assertEquals(4, l4.size());
    }

    @Test
    void testDuplicatesAllowed() {
        QuadList<String> list = QuadList.of("A", "A", "A", "A");
        assertEquals(4, list.size(), "List must not deduplicate elements");
        assertEquals("A", list.get(0));
        assertEquals("A", list.get(3));
    }

    @Test
    void testIndices() {
        QuadList<String> list = QuadList.of("A", "B", "A", "C");

        // get()
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("A", list.get(2));
        assertEquals("C", list.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(4));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));

        // indexOf() & lastIndexOf()
        assertEquals(0, list.indexOf("A"));
        assertEquals(2, list.lastIndexOf("A"));
        assertEquals(3, list.indexOf("C"));
        assertEquals(3, list.lastIndexOf("C"));
        assertEquals(-1, list.indexOf("Z"));
        assertEquals(-1, list.indexOf(null));
    }

    @Test
    void testImmutability() {
        QuadList<String> list = QuadList.of("A", "B");

        // Collection mutators
        assertThrows(UnsupportedOperationException.class, () -> list.add("C"));
        assertThrows(UnsupportedOperationException.class, () -> list.remove("A"));
        assertThrows(UnsupportedOperationException.class, list::clear);

        // List specific mutators
        assertThrows(UnsupportedOperationException.class, () -> list.add(1, "Z"));
        assertThrows(UnsupportedOperationException.class, () -> list.set(0, "Z"));
        assertThrows(UnsupportedOperationException.class, () -> list.remove(1));
        assertThrows(UnsupportedOperationException.class, () -> list.addAll(1, List.of("X", "Y")));
    }

    @Test
    void testSubList() {
        QuadList<String> list = QuadList.of("A", "B", "C", "D");

        List<String> sub1 = list.subList(0, 2);
        assertEquals(List.of("A", "B"), sub1);
        assertTrue(sub1 instanceof QuadList, "Sublist should return a new QuadList if size > 0");

        List<String> sub2 = list.subList(1, 4);
        assertEquals(List.of("B", "C", "D"), sub2);

        List<String> emptySub = list.subList(1, 1);
        assertTrue(emptySub.isEmpty());

        assertThrows(IndexOutOfBoundsException.class, () -> list.subList(-1, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.subList(2, 5));
        assertThrows(IndexOutOfBoundsException.class, () -> list.subList(3, 1)); // from > to
    }

    @Test
    void testEqualsAndHashCode() {
        QuadList<String> quadList = QuadList.of("A", "B", "C", "D");
        List<String> standardList = List.of("A", "B", "C", "D");

        assertEquals(standardList, quadList, "QuadList should equal a standard List with the same elements");
        assertEquals(quadList, standardList, "Standard List should equal a QuadList with the same elements");
        assertEquals(standardList.hashCode(), quadList.hashCode(), "HashCodes must match exactly");
        
        assertNotEquals(QuadList.of("A", "B", "D", "C"), quadList, "Order matters in List equality");
    }

    @Test
    void testListIterator() {
        QuadList<String> list = QuadList.of("A", "B");
        ListIterator<String> it = list.listIterator();

        assertTrue(it.hasNext());
        assertFalse(it.hasPrevious());
        assertEquals(0, it.nextIndex());
        assertEquals(-1, it.previousIndex());

        assertEquals("A", it.next());
        assertEquals("B", it.next());
        
        assertFalse(it.hasNext());
        assertTrue(it.hasPrevious());
        assertEquals(2, it.nextIndex());
        
        assertEquals("B", it.previous());
        assertEquals("A", it.previous());
        
        assertThrows(NoSuchElementException.class, it::previous);

        // Test iterator immutability
        assertThrows(UnsupportedOperationException.class, it::remove);
        assertThrows(UnsupportedOperationException.class, () -> it.set("Z"));
        assertThrows(UnsupportedOperationException.class, () -> it.add("Z"));
    }

}