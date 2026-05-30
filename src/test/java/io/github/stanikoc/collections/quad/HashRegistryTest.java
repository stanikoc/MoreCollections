package io.github.stanikoc.collections.quad;

import io.github.stanikoc.collections.registry.HashRegistry;
import io.github.stanikoc.collections.registry.Registry;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HashRegistryTest {
    @Test
    void testCreationAndSize() {
        Registry<String, Integer> registry = new HashRegistry<>();
        
        assertTrue(registry.isEmpty(), "A new registry should be empty");
        assertEquals(0, registry.size());

        registry.register("A", 1);
        assertFalse(registry.isEmpty());
        assertEquals(1, registry.size());

        registry.register("B", 2);
        assertEquals(2, registry.size());
    }

    @Test
    void testGetAndContains() {
        Registry<String, String> registry = new HashRegistry<>();
        registry.register("user", "Alice");
        registry.register("admin", "Bob");

        assertEquals("Alice", registry.get("user"));
        assertEquals("Bob", registry.get("admin"));
        assertNull(registry.get("guest"), "Getting an unregistered key should return null");

        assertTrue(registry.contains("user"));
        assertTrue(registry.contains("admin"));
        assertFalse(registry.contains("guest"));
    }

    @Test
    void testDuplicateRegistrationIgnored() {
        Registry<String, Integer> registry = new HashRegistry<>();
        registry.register("A", 100);

        registry.register("A", 200);

        assertEquals(1, registry.size(), "Registry should ignore duplicate key registrations");
        assertEquals(100, registry.get("A"), "Registry should retain the initially registered value");
    }

    @Test
    void testUnregisterAndArraySwap() {
        Registry<String, Integer> registry = new HashRegistry<>();
        registry.register("A", 1);
        registry.register("B", 2);
        registry.register("C", 3);
        registry.register("D", 4);

        registry.unregister("B");

        assertEquals(3, registry.size());
        assertNull(registry.get("B"));
        assertFalse(registry.contains("B"));

        assertEquals(1, registry.get("A"));
        assertEquals(3, registry.get("C"));
        assertEquals(4, registry.get("D"));

        registry.unregister("C");
        assertEquals(2, registry.size());
        assertNull(registry.get("C"));

        assertDoesNotThrow(() -> registry.unregister("Z"));
        assertEquals(2, registry.size());
    }

    @Test
    void testClear() {
        Registry<String, Integer> registry = new HashRegistry<>();
        registry.register("A", 1);
        registry.register("B", 2);

        registry.clear();

        assertTrue(registry.isEmpty());
        assertEquals(0, registry.size());
        assertFalse(registry.contains("A"));
        assertNull(registry.get("B"));
    }

    @Test
    void testIteratorAndStream() {
        Registry<String, String> registry = new HashRegistry<>();
        registry.register("A", "Alpha");
        registry.register("B", "Bravo");

        Iterator<String> it = registry.iterator();
        assertTrue(it.hasNext());
        assertEquals("Alpha", it.next());
        assertTrue(it.hasNext());
        assertEquals("Bravo", it.next());
        assertFalse(it.hasNext());

        List<String> streamResult = registry.stream().toList();
        assertEquals(2, streamResult.size());
        assertTrue(streamResult.contains("Alpha"));
        assertTrue(streamResult.contains("Bravo"));
    }

    @Test
    void testTableRebuildAndCapacity() {
        Registry<Integer, String> registry = new HashRegistry<>();
        for (int i = 0; i < 20; i++) {
            registry.register(i, "Value" + i);
        }

        assertEquals(20, registry.size());
        for (int i = 0; i < 20; i++) {
            assertTrue(registry.contains(i));
            assertEquals("Value" + i, registry.get(i));
        }
    }

}