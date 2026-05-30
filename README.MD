# MoreCollections

MoreCollections is a small library providing specialized, low-overhead collection types for Java.

*(Note: This library is currently a work in progress!)*

---

## Installation
Gradle
```groovy
implementation 'io.github.stanikoc:morecollections:0.0.1'
```
Maven
```maven
<dependency>
    <groupId>io.github.stanikoc</groupId>
    <artifactId>morecollections</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Current Collections

### QuadSet & QuadList
These are immutable sets and lists designed to hold exactly 1 to 4 elements. Under the hood, they store elements using flat object fields rather than relying on underlying arrays or node objects.

**When to use them:** They are useful when you need to allocate a collection for a very small, fixed number of items. By using these instead of standard Java collections like `HashSet` or `ArrayList`, you can completely avoid the standard memory and instantiation overhead (like array resizing or hash bucket allocation).

### Registry & HashRegistry
A unified collection that provides the O(1) key lookup performance of a standard hash table alongside the sequential iteration speed of an `ArrayList`.

**When to use it:** Perfect for Manager classes that hold references (loaded plugins, system configurations, services...). It is designed for read-heavy scenarios where you frequently need to retrieve items by a specific key and loop over all elements, but rarely modify the collection after the initial setup.

```java
Registry<String, Service> services = new HashRegistry<>();

// Registration
services.register("database", new DatabaseService());
services.register("network", new NetworkService());

// O(1) Lookup
Service db = services.get("database");

// sequential iteration
for (Service service : services) {
    service.start();
}
```

TODO: ConcurrentHashRegistry, StringRegistry; Benchmarks