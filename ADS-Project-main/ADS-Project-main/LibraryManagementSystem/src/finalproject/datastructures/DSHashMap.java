package finalproject.datastructures;

import finalproject.datastructures.DSArrayList;

import java.util.Iterator;

/*
 * HashMap class written by the 2022 ADS class
 *
 * V is the type parameter, the type of the value we will store.
 * For now, all of our keys will be Strings.
 */

public class DSHashMap<V> implements Iterable<String>{
    // The backing array - contains the values
    DSArrayList<DSArrayList<KVP>> a; // the backing DSArrayList
    int capacity; // Size of the backing array
    int numItems; // Number of KVPs added to the DSHashMap
    int totalCount;

    // A Java constructor is identified by three properties
    // The same name as the class
    // public
    // no return type, not even void
    public DSHashMap(){
        capacity = 2;
        a = new DSArrayList<>(capacity);
        numItems = 0;
        totalCount = 0;
    }


    /**
     * Add a key,value pair to the DSHashMap.
     * If the key is already there, update the value
     *
     * @param key
     * @param value
     */
    public void put(String key, V value){

        // Check to see if our DSHashMap is getting crowded
        if(numItems > 3*capacity){ // Purposely dense to encourage long chain, for the Midterm
            rehash();
        }

        // Give us the location of this key's value in the backing array
        int location = hash(key);
        if(a.get(location) == null){
            a.put(location, new DSArrayList<KVP>(10));
            a.get(location).add(new KVP(key, value));
            numItems++;
        } else { // Already a chain here, with stuff in it
            // search that chain for our key.
            for(int i = 0; i < a.get(location).length(); i++){
                KVP kvp = a.get(location).get(i);
                // If we find the key, replace the value
                if(kvp.key.equals(key)){
                    a.get(location).replace(i, new KVP(key, value));
                    return;
                }
            }
            // If we don't find the key, create a new KVP and add it to the chain
            a.get(location).add(new KVP(key, value));
            numItems++;
        }
    }

    /*
     * Double the size of the backing DSArrayList
     * Move all KVPs to their new locations and chains
     */
    private void rehash() {
        // Save a reference to the current backing array
        DSArrayList<DSArrayList<KVP>> olda = a;

        // Create a new, larger backing array
        int newCapacity = 2 * this.capacity;
        a = new DSArrayList<>(newCapacity);
        this.capacity = newCapacity;
        //System.out.println("New capacity is " + this.capacity);

        // Loop over old backing array
        this.numItems = 0;
        for(int i = 0; i < olda.capacity(); i++){
            DSArrayList<KVP>chain = olda.get(i);
            if(chain == null) continue;

            for(KVP kvp : chain){
                if(kvp == null) break;
                this.put(kvp.key, kvp.value);
            }
        }
    }


    /**
     * Return the value associated with this key
     * If the key is not in the DSHashMap, return an exception
     *
     * @param key
     * @return The value of type V stored with this key
     */
    public V get(String key){
        int location = hash(key);
        if(a.get(location) == null){
            throw new IndexOutOfBoundsException("Key not found: " + key);
        } else { // Already a chain here, with stuff in it
            // search that chain for our key.
            for(int i = 0; i < a.get(location).length(); i++){
                KVP kvp = a.get(location).get(i);
                //System.out.printf("loc = %d, i = %d: Looking for %s, found %s\n",
                //    location, i, key, kvp.key);
                // If we find the key, replace the value
                if(kvp.key.equals(key)){
                    return kvp.value;
                }
            }
            // If we don't find the key, create a new KVP and add it to the chain
            throw new IndexOutOfBoundsException("Key not found: " + key);
        }
    }

    /**
     * Check to see if this DSHashMap contains an entry with key k
     *
     * @param k The key to search for
     * @return True if the key already exists in the hash map
     */
    public Boolean containsKey(String k){
        int location = hash(k);
        if(a.get(location) == null){
            return false;
        } else { // Already a chain here, with stuff in it
            // search that chain for our key.
            for(int i = 0; i < a.get(location).length(); i++){
                KVP kvp = a.get(location).get(i);
                if(kvp.key.equals(k)){
                    return true;
                }
            }
            return false;
        }

    }

    // Return the number of items in the hash map
    public int size(){
        return this.numItems;
    }


    // This is the hash function. Tells where a string's value
    // should be stored in the backing array
    int hash(String s){
        int rv = 0;

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            rv = (rv * 7 + c) % capacity;
        }
        return rv;
    }


    /**
     *
     * @return The length of the longest chain in this DSHashMap
     */
    public int longestChain(){
        int rv = 0;
        for(int i = 0; i < a.capacity(); i++){
            if(a.get(i) != null && a.get(i).length > rv){
                rv = a.get(i).length;
            }
        }
        return rv;
    }


    /**
     * Produces a List of all keys associated with the given value
     * @param value
     * @return
     */
    public DSArrayList<String> keysForValue(V value){
        DSArrayList<String> rv = new DSArrayList<String>();
        for(String k : this){
            if(this.get(k).equals(value)){
                rv.add(k);
            }
        }
        return rv;
    }



    /**
     * Produces a Python-style, String version of this DSHashMap
     *
     * {k1:v1, k2:v2, ...}
     *
     * Note that by overriding the toString() method, when we System.out.println()
     * our hash map, and it will run this method.
     */
    @Override
    public String toString(){
        String rv = "";
        for(String k : this){
            rv = rv + ", " + k + ":" + this.get(k);
            //rv = rv + ", " + k + this.get(k);
        }
        return "{" + rv.substring(2) + "}";
        //return rv.substring(2);
    }


    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>(){
            int backindex = 0;
            int chainindex = 0;

            @Override
            public boolean hasNext() {
                if(a.get(backindex) != null && chainindex < a.get(backindex).length()){
                    return true;
                }
                backindex++;
                while(backindex < capacity && a.get(backindex) == null) backindex++;
                if(backindex == capacity) return false;

                // There is another chain to use!
                chainindex = 0;
                return true;
            }

            @Override
            public String next() {
                String returnValue = a.get(backindex).get(chainindex).key;
                chainindex++;
                return returnValue;
            }

        };
    }


    /**
     * Inner class for Key-Value pairs
     */
    class KVP {
        String key;
        V value;

        public KVP(String s, V v){
            key = s;
            value = v;
        }
    }
}