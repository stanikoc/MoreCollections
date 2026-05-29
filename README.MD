# MoreCollections

MoreCollections is a small library providing specialized, low-overhead collection types for Java.

*(Note: This library is currently a work in progress!)*

## Current Collections

### QuadSet & QuadList
These are immutable sets and lists designed to hold exactly 1 to 4 elements. Under the hood, they store elements using flat object fields rather than relying on underlying arrays or node objects.

**When to use them:** They are useful when you need to allocate a collection for a very small, fixed number of items. By using these instead of standard Java collections like `HashSet` or `ArrayList`, you can completely avoid the standard memory and instantiation overhead (like array resizing or hash bucket allocation).