import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] RandQ;

    // construct an empty randomized queue
    public RandomizedQueue() {
        RandQ = (Item[]) new Object[1];
        size = 0;
    }

    private class RandQIterator implements Iterator<Item> {
        private Item[] duplicate = (Item[]) new Object[RandQ.length];
        private int duplicateSize = size;

        public RandQIterator() {
            for (int i = 0; i < RandQ.length; i++) {
                duplicate[i] = RandQ[i];
            }
        }

        public boolean hasNext() {
            return duplicateSize != 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int rand = StdRandom.uniformInt(duplicateSize);
            Item item = duplicate[rand];
            if (rand != duplicateSize - 1) {
                duplicate[rand] = duplicate[duplicateSize - 1];
            }
            duplicate[duplicateSize - 1] = null;
            duplicateSize--;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int newSize) {
        assert newSize >= size;
        Item[] temp = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            temp[i] = RandQ[i];
        }
        RandQ = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == RandQ.length) {
            resize(RandQ.length * 2);
        }
        RandQ[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int rand = StdRandom.uniformInt(size);
        Item item = RandQ[rand];
        if (rand != size - 1) {
            RandQ[rand] = RandQ[size - 1];
        }
        RandQ[--size]=null;
        if(size>0 && size<=RandQ.length/4){
            resize(RandQ.length/2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if(isEmpty()){throw new NoSuchElementException();}
        int rand = StdRandom.uniformInt(size);
        Item item = RandQ[rand];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandQIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}