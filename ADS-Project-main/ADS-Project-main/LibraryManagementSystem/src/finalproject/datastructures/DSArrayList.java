package finalproject.datastructures;

import java.util.Arrays;
import java.util.Iterator;

/*
 * ArrayList implementation that resizes itself as needed.
 *
 * Also, note that it is generic!
 * It will hold an array of things of type T
 */

public class DSArrayList<T> implements DSList<T>, Iterable<T> {

    private T [] list;
    public int length = 0;

    // constructor
    // size is the initial size of the backing array
    @SuppressWarnings("unchecked")
    public DSArrayList(int size) {
        list = (T[])(new Object[size]);
    }

    // Constructor Chaining
    public DSArrayList(){
        this(10);
    }

    @SuppressWarnings("unchecked")
    public void add(T x) {
        if ( length == list.length ) {
            //System.out.println("Resizing the backing array to size "  +
            //(int)(1.1*length));
            // List is full
            //throw new IndexOutOfBoundsException("no more room.");
            // Resize the array, to make room for the new element
            T[] newlist = (T[])(new Object[2*length]);

            // Copy from the old list to the new one
            for(int i = 0; i < length; i++){
                newlist[i] = list[i];
            }

            list = newlist; // the list field now points to the new array
        }

        list[length] = x;
        length = length + 1;

    }

    public boolean contains(T x) {
        for ( int i = 0 ; i < length ; i++ ) {
            if ( x.equals(list[i])) {
                return true;
            }
        }
        return false;
    }


    /*
     * Replace the item in location i with item
     */
    public void replace(int i, T item){ // add item at location i
        if(i < length){
            list[i] = item;
        } else {
            throw new IndexOutOfBoundsException("" + i);
        }
    }

    /**
     * Add an item to the backing array in a random-access location.
     * That is, we may be scattering items in the backing array.
     *
     * length will no longer make sense!
     *
     * @param location
     * @param item
     */
    public void put(int location, T item){
        list[location] = item;
    }


    public T get(int i) {
        //if ( i < length ) {
        if(i < list.length){
            return list[i];
        }

        throw new IndexOutOfBoundsException("" + i);
    }

    public void remove(T x) {
        for ( int i = 0 ; i < length ; i++ ) {
            if ( x.equals(list[i]) ) {
                for ( int j = i ; j < length-1 ; j++ ) {
                    list[j] = list[j+1];
                }
                length = length - 1;
                return;
            }
        }

    }

    public void clear() {
        length = 0;
    }

    public int length() {
        return length;
    }

    public int capacity() {
        return list.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>(){
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < length;
            }

            @Override
            public T next() {
                T returnValue = list[index];
                index++;
                return returnValue;
            }

        };
    }


    /**
     * Checks for equality with another DSArrayList.
     *
     * @return true if they have the same length, and the elements at each index are .equals()
     * to one another.
     */
    @Override
    public boolean equals(Object o){
        if(o == this) return true;

        if(!(o instanceof DSArrayList<?>)) return false;

        @SuppressWarnings("unchecked")
        DSArrayList<T> other = (DSArrayList<T>) o;
        if(this.length != other.length()) return false;

        for(int i = 0; i < this.length(); i++){
            if(!this.get(i).equals(other.get(i))) return false;
        }

        return true;
    }

    /**
     * Sort the entries in the backing array.
     */
    @SuppressWarnings("unchecked")
    public void sort() {
        Arrays.sort(this.list, 0, this.length, (a, b) -> ((Comparable) (a)).compareTo(b));
    }


    /**
     * Return a string representation of the object
     */
    public String toString() {
        // return Arrays.toString(this.a);
        String rv = "[";
        for (int i = 0; i < this.length; i++) {
            rv += list[i];
            if (i < this.length - 1)
                rv += ", ";
        }
        rv += "]";
        return rv;
    }

}