import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

  protected LinkedList<Pair>[] table = null;

  @SuppressWarnings("unchecked")
  public HashtableMap(int capacity) {
    table = (LinkedList<Pair>[]) new LinkedList[capacity];
  }

  @SuppressWarnings("unchecked")
  public HashtableMap() {
    // with default capacity = 64
    table = (LinkedList<Pair>[]) new LinkedList[64];
  }

  /**
   * Adds a new key,value pair/mapping to this collection.It is ok that the value is null.
   * 
   * @param key   the key of the key,value pair
   * @param value the value that key maps to
   * @throws IllegalArgumentException if key already maps to a value
   * @throws NullPointerException     if key is null
   */
  @Override
  public void put(KeyType key, ValueType value) throws IllegalArgumentException {

    if (key == null) {
      throw new NullPointerException();
    }

    if (this.containsKey(key)) {
      throw new IllegalArgumentException();
    }

    int index = Math.abs(key.hashCode()) % table.length;

    if (table[index] == null) {
      table[index] = new LinkedList<Pair>();
    }
    table[index].addLast(new Pair(key, value));
    double loadFactor = (double) this.getSize() / this.getCapacity();
    if (loadFactor >= 0.8) {
      rehash();
    }
  }

  /**
   * performs rehashing operations
   */
  @SuppressWarnings("unchecked")
  private void rehash() {
    LinkedList<Pair>[] newTable = new LinkedList[this.getCapacity() * 2];
    LinkedList<Pair> pairs = new LinkedList<>();
    // puts all pairs in list into an arraylist
    for (LinkedList<Pair> lists : table) {
      if (lists != null) {
        for (Pair p : lists) {
          pairs.add(p);
        }
      }
    }
    table = newTable;
    // populates new table with arraylist
    for (Pair p : pairs) {
      this.put(p.key, p.value);
    }
  }

  /**
   * Checks whether a key maps to a value in this collection.
   * 
   * @param key the key to check
   * @return true if the key maps to a value, and false is the key doesn't map to a value
   */
  @Override
  public boolean containsKey(KeyType key) {
    int index = Math.abs(key.hashCode()) % table.length;
    if (table[index] != null) {
      for (Pair pair : table[index]) {
        if (pair.key.equals(key)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Retrieves the specific value that a key maps to.
   * 
   * @param key the key to look up
   * @return the value that key maps to
   * @throws NoSuchElementException when key is not stored in this collection
   */
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    int index = Math.abs(key.hashCode()) % table.length;
    if (table[index] != null) {
      for (Pair pair : table[index]) {
        if (pair.key.equals(key)) {
          return pair.value;
        }
      }
    }
    throw new NoSuchElementException();
  }

  /**
   * Remove the mapping for a key from this collection.
   * 
   * @param key the key whose mapping to remove
   * @return the value that the removed key mapped to
   * @throws NoSuchElementException when key is not stored in this collection
   */
  @Override
  public ValueType remove(KeyType key) throws NoSuchElementException {
    int index = Math.abs(key.hashCode()) % table.length;
    if (table[index] != null) {
      for (int i = 0; i < table[index].size(); i++) {
        if (table[index].get(i).key.equals(key)) {
          ValueType toReturn = table[index].get(i).value;
          table[index].remove(i);
          return toReturn;
        }
      }
    }
    throw new NoSuchElementException();
  }

  /**
   * Removes all key,value pairs from this collection.
   */
  @SuppressWarnings("unchecked")
  @Override
  public void clear() {

    table = (LinkedList<Pair>[]) new LinkedList[table.length];
  }

  /**
   * Retrieves the number of keys stored in this collection.
   * 
   * @return the number of keys stored in this collection
   */
  @Override
  public int getSize() {
    int count = 0;
    for (int i = 0; i < table.length; i++) {
      if (table[i] != null) {
        count += table[i].size();
      }
    }
    return count;
  }

  /**
   * Retrieves this collection's capacity.
   * 
   * @return the size of te underlying array for this collection
   */
  @Override
  public int getCapacity() {
    return table.length;
  }

  protected class Pair {

    public KeyType key;
    public ValueType value;

    public Pair(KeyType key, ValueType value) {
      this.key = key;
      this.value = value;
    }

  }

  /**
   * tests get size and get capacity
   */
  @Test
  public void test1() {
    // Initialize map
    HashtableMap hashmap = new HashtableMap();
    // tests size
    Assertions.assertTrue(hashmap.getSize() == 0);
    Assertions.assertTrue(hashmap.getCapacity() == 64);
    // tests size if new value
    hashmap.put(10, 10);
    Assertions.assertTrue(hashmap.getSize() == 1);
    Assertions.assertTrue(hashmap.getCapacity() == 64);
    // tests setting size
    HashtableMap hashmap2 = new HashtableMap(5);
    Assertions.assertTrue(hashmap2.getSize() == 0);
    Assertions.assertTrue(hashmap2.getCapacity() == 5);
    hashmap2.put(10, 10);
    Assertions.assertTrue(hashmap2.getSize() == 1);
    Assertions.assertTrue(hashmap2.getCapacity() == 5);
  }

  /**
   * tests get and contain
   */
  @Test
  public void test2() {
    // Initialize map
    HashtableMap hashmap = new HashtableMap();
    // inserts values
    hashmap.put(10, 10);
    hashmap.put(5, 20);
    // tests get and contain
    Assertions.assertTrue(hashmap.containsKey(5));
    Assertions.assertTrue(hashmap.containsKey(10));
    Assertions.assertTrue(hashmap.get(5).equals(20));
    Assertions.assertTrue(hashmap.get(10).equals(10));
  }

  /**
   * tests remove and clear
   */
  @Test
  public void test3() {
    // Initialize map
    HashtableMap hashmap = new HashtableMap();
    // inserts values
    hashmap.put(10, 10);
    hashmap.put(5, 20);
    Assertions.assertTrue(hashmap.containsKey(5));
    Assertions.assertTrue(hashmap.getSize() == 2);
    // removes value
    hashmap.remove(5);
    Assertions.assertTrue(!hashmap.containsKey(5));
    Assertions.assertTrue(hashmap.getSize() == 1);

  }

  /**
   * tests put for collisions
   */
  @Test
  public void test4() {
    // Initialize map
    HashtableMap hashmap = new HashtableMap(10);
    // put duplicate hash values
    hashmap.put(5, 20);
    hashmap.put(15, 30);
    // assert that values are in the thing
    Assertions.assertTrue(hashmap.containsKey(5));
    Assertions.assertTrue(hashmap.containsKey(15));
    Assertions.assertTrue(hashmap.get(5).equals(20));
    Assertions.assertTrue(hashmap.get(15).equals(30));

  }

  /**
   * tests rehashing
   */
  @Test
  public void test5() {
    // initialize map
    HashtableMap hashmap = new HashtableMap(5);
    // check map capacity
    Assertions.assertTrue(hashmap.getCapacity() == 5);
    // inserts over the LF threshold
    hashmap.put(5, 20);
    hashmap.put(15, 30);
    hashmap.put(34, 45);
    hashmap.put(27, 376);
    // chceks to see all values are still there and new capacity
    Assertions.assertTrue(hashmap.getCapacity() == 10);
    Assertions.assertTrue(hashmap.containsKey(5));
    Assertions.assertTrue(hashmap.containsKey(15));
    Assertions.assertTrue(hashmap.containsKey(34));
    Assertions.assertTrue(hashmap.containsKey(27));
  }

}
