public class ArrayDeque<T> implements Deque<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    public ArrayDeque(ArrayDeque other) {
        size = other.size;
        nextFirst = other.items.length - 1;
        nextLast = other.size;
        items = (T[]) new Object[other.items.length];
        for (int i = 0; i < other.size; i++) {
            items[i] = (T) other.get(i);
        }
    }

    private void resize(double shift) {
        T[] newItems = (T[]) new Object[(int) (items.length * shift)];
        for (int i = 0; i < size; i++) {
            newItems[i] = get(i);
        }
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(2);
        }
        items[nextFirst] = item;
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(2);
        }
        items[nextLast] = item;
        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int index = getCurrentFirst();

        for (int i = 0; i < size; i++) {
            System.out.println(items[index]);
            if (index == items.length - 1) {
                index = 0;
            } else {
                index += 1;
            }
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        double usageFactor = (double) size / items.length;
        if (items.length >= 16 && usageFactor < 0.25) {
            resize(0.5);
        }

        T first = get(0);

        int currentFirst = getCurrentFirst();
        items[currentFirst] = null;
        nextFirst = currentFirst;
        size -= 1;

        if (isEmpty()) {
            nextFirst = 0;
            nextLast = 1;
        }

        return first;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        double usageFactor = (double) size / items.length;
        if (items.length >= 16 && usageFactor < 0.25) {
            resize(0.5);
        }

        T last = get(size - 1);
        int currentLast;

        if (nextLast == 0) {
            currentLast = items.length - 1;
        } else {
            currentLast = nextLast - 1;
        }

        items[currentLast] = null;
        nextLast = currentLast;
        size -= 1;

        if (isEmpty()) {
            nextFirst = 0;
            nextLast = 1;
        }

        return last;
    }

    private int getCurrentFirst() {
        int currentFirst;

        if (nextFirst == items.length - 1) {
            currentFirst = 0;
        } else {
            currentFirst = nextFirst + 1;
        }
        return currentFirst;
    }

    @Override
    public T get(int index) {
        int currentFirst = getCurrentFirst();
        return items[(currentFirst + index) % items.length];
    }
}
