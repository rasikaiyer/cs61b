package bearmaps;

import java.util.HashMap;
import java.util.ArrayList;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ {
    private ArrayList<PriorityNode> items;

    public ArrayHeapMinPQ() {
        items = new ArrayList<PriorityNode>();
    }

    @Override
    public void add(T item, double priority) {
        items.add(new PriorityNode(item, priority));
    }

    @Override
    public boolean contains(T item) {

    }

    @Override
    public T getSmallest() {

    }

    @Override
    public T removeSmallest() {

    }

    @Override
    public void changePriority(T item, double priority) {

    }

    @Override
    public int size() {
        return items.size();
    }


    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
