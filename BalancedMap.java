package edu.sdsu.cs.datastructures;
import java.util.TreeMap;
import java.util.ArrayList;

public class BalancedMap<K extends Comparable<K>, V> implements IMap<K,V>
{
    private int currentSize;
    TreeMap<K,V> map;

    public BalancedMap()
    {
        map = new TreeMap<>();
    }

    public BalancedMap(IMap<K,V> source)
    {
        map = new TreeMap<>();
        for(K key: source.keyset())
        {
            add(key,source.getValue(key));
        }
    }

    private class Node<K, V>
    {
        public Node left;
        public Node right;
        public K key;
        public V value;

    }

    /**
     * Indicates if the map contains the object identified by the
     key inside.
     *
     * @param key The object to compare against
     * @return true if the parameter object appears in the structure
     */

    public boolean contains(K key)
    {
        boolean b = false;
        if(map.containsKey(key)){
            b = true;
        }
        return b;
    }


    /**
     * Adds the given key/value pair to the dictionary.
     *
     * @param key
     * @param value
     * @return false if the dictionary is full, or if the key is a
    duplicate.
     * Returns true if addition succeeded.
     */
    public boolean add(K key, V value)
    {
        boolean b = true;
        if(map.containsKey(key)){
            b = false;
        }
        else{
            map.put(key, value);
        }
        return b;
    }


    /**
     * Deletes the key/value pair identified by the key parameter.
     *
     * @param key
     * @return The previous value associated with the deleted key or
    null if not
     * present.
     */
    public V delete(K key)
    {
        V val = map.get(key);
        map.remove(key);
        return val;
    }

    /**
     * Retreives, but does not remove, the value associated with the
     provided
     * key.
     *
     * @param key The key to identify within the map.
     * @return The value associated with the indicated key.
     */
    public V getValue(K key)
    {
        return map.get(key);
    }
    /**
     * Returns a key in the map associated with the provided value.
     *
     * @param value The value to find within the map.
     * @return The first key found associated with the indicated
    value.
     */

    public K getKey(V value)
    {
        K key = null;
        for(K k: map.keySet()){
            if(map.get(k).equals(value)) {
                key = k;
                break;
            }

        }
        //dont get why theres an error under key
        return key;
    }

    /**
     * Returns all keys associated with the indicated value
     contained within the
     * map.
     *
     * @param value The value to locate within the map.
     * @return An iterable object containing all keys associated
    with the
     * provided value.
     */
    public Iterable<K> getKeys(V value)
    {
        ArrayList<K> list = new ArrayList();
        for(K k: map.keySet()){
            if(map.get(k).equals(value)) {
                list.add(k);
            }
        }

        Iterable<K> keys = list;
        return keys;
    }

    /**
     * Indicates the count of key/value entries stored inside the
     map.
     *
     * @return A non-negative number representing the number of
    entries.
     */

    public int size()
    {
        return map.size();
    }

    /**
     * Indicates if the dictionary contains any items.
     *
     * @return true if the dictionary is empty, false otherwise.
     */

    public boolean isEmpty()
    {
        boolean b = true;
        if(map.size() > 0){
            b = false;
        }
        return b;
    }

    public void clear()
    {
        map.clear();
    }

    /**
     * Provides an Iterable object of the keys in the dictionary.
     * <p>
     * The keys provided by this method must appear in their
     natural, ascending,
     * order.
     *
     * @return An iterable set of keys.
     */

    public Iterable<K> keyset()
    {
        Iterable<K> keys = map.keySet();
        return keys;
    }
    /**
     * Provides an Iterable object of the keys in the dictionary.
     * <p>
     * The values provided by this method must appear in an order
     matching the
     * keyset() method. This object may include duplicates if the
     data structure
     * includes duplicate values.
     *
     * @return An iterable object of all the dictionary's values.
     */

    public Iterable<V> values()
    {
        //  Collection<V> vals = map.values();
        Iterable<V> values = map.values();
        return values;
    }

}


