public class LinkedListDeque<T> implements Deque<T> {

    private int size;
    private Node sentinel;
    private Node sentinelLast;

    private class Node {
        private Node previous;
        private T item;
        private Node next;

        private Node(Node p, T i, Node n) {
            previous = p;
            item = i;
            next = n;
        }

        private T getRecursiveT(int index) {
            if (index == 0) {
                return item;
            }
            return next.getRecursiveT(index - 1);
        }
    }

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinelLast = new Node(null, null, null);
        sentinel.next = sentinelLast;
        sentinelLast.previous = sentinel;
        size = 0;
    }

    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new Node(null, null, null);
        sentinelLast = new Node(null, null, null);
        sentinel.next = sentinelLast;
        sentinelLast.previous = sentinel;
        for (int i = 0; i < other.size; i++) {
            addLast((T) other.get(i));
        }
        size = other.size;
    }

    public T getRecursive(int index) {
        return sentinel.next.getRecursiveT(index);
    }

    @Override
    public void addFirst(T item) {
        size += 1;
        Node second;
        second = sentinel.next;
        sentinel.next = new Node(sentinel, item, second);
        second.previous = sentinel.next;
    }

    @Override
    public void addLast(T item) {
        size += 1;
        Node secondLast;
        secondLast = sentinelLast.previous;
        sentinelLast.previous = new Node(secondLast, item, sentinelLast);
        secondLast.next = sentinelLast.previous;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node p = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.println(p.item);
            System.out.println(" ");
        }
        System.out.println("\n");
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node first, second;
        first = sentinel.next;
        second = sentinel.next.next;
        second.previous = sentinel;
        sentinel.next = second;
        size -= 1;
        return first.item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node last, secondLast;
        last = sentinelLast.previous;
        secondLast = sentinelLast.previous.previous;
        secondLast.next = sentinelLast;
        sentinelLast.previous = secondLast;
        size -= 1;
        return last.item;
    }

    @Override
    public T get(int index) {
        Node p = sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }
}
