package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        fillCount = 0;
        first = 0;
        last = -1;
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */

    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        fillCount += 1;

        if (last == rb.length - 1) {
            last = 0;
        } else {
            last += 1;
        }
        rb[last] = x;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        T firstItem = rb[first];
        rb[first] = null;
        if (first == rb.length - 1) {
            first = 0;
        } else {
            first += 1;
        }
        fillCount -= 1;
        return firstItem;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        return rb[first];
    }

    private class ArrayRingBufferIterator<T> implements Iterator<T> {
        int pos;
        private ArrayRingBufferIterator() {
            pos = 0;
        }

        @Override
        public boolean hasNext() {
            return pos < fillCount();
        }

        @Override
        public T next() {
            T next = (T) rb[(first + pos) % rb.length];
            pos += 1;
            return next;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    public boolean equals(Object o) {
        return true;
    }
}
