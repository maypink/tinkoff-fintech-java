package com.maypink.tinkoff.cache;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DoubleLinkedList<T> {

    private DummyNode<T> dummyNode;
    private DoubleLinkedNode<T> head;
    private DoubleLinkedNode<T> tail;
    private AtomicInteger size;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public DoubleLinkedList() {
        this.dummyNode = new DummyNode<T>(this);
        clear();
    }

    public void clear() {
        this.lock.writeLock().lock();
        try {
            head = dummyNode;
            tail = dummyNode;
            size = new AtomicInteger(0);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public int size() {
        this.lock.readLock().lock();
        try {
            return size.get();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public boolean isEmpty() {
        this.lock.readLock().lock();
        try {
            return head.isEmpty();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public boolean contains(T value) {
        this.lock.readLock().lock();
        try {
            return !search(value).isEmpty();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public DoubleLinkedNode<T> search(T value) {
        this.lock.readLock().lock();
        try {
            return head.search(value);
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public DoubleLinkedNode<T> add(T value) {
        this.lock.writeLock().lock();
        try {
            head = new Node<T>(value, head, this);
            if (tail.isEmpty()) {
                tail = head;
            }
            size.incrementAndGet();
            return head;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public boolean addAll(Collection<T> values) {
        this.lock.writeLock().lock();
        try {
            for (T value : values) {
                if (add(value).isEmpty()) {
                    return false;
                }
            }
            return true;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public DoubleLinkedNode<T> remove(T value) {
        this.lock.writeLock().lock();
        try {
            DoubleLinkedNode<T> doubleLinkedNode = head.search(value);
            if (!doubleLinkedNode.isEmpty()) {
                if (doubleLinkedNode == tail) {
                    tail = tail.getPrev();
                }
                if (doubleLinkedNode == head) {
                    head = head.getNext();
                }
                doubleLinkedNode.detach();
                size.decrementAndGet();
            }
            return doubleLinkedNode;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public DoubleLinkedNode<T> removeTail() {
        this.lock.writeLock().lock();
        try {
            DoubleLinkedNode<T> oldTail = tail;
            if (oldTail == head) {
                tail = head = dummyNode;
            } else {
                tail = tail.getPrev();
                oldTail.detach();
            }
            if (!oldTail.isEmpty()) {
                size.decrementAndGet();
            }
            return oldTail;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public DoubleLinkedNode<T> moveToFront(DoubleLinkedNode<T> node) {
        return node.isEmpty() ? dummyNode : updateAndMoveToFront(node, node.getElement());
    }

    public DoubleLinkedNode<T> updateAndMoveToFront(DoubleLinkedNode<T> node, T newValue) {
        this.lock.writeLock().lock();
        try {
            if (node.isEmpty() || (this != (node.getListReference()))) {
                return dummyNode;
            }
            detach(node);
            add(newValue);
            return head;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    private void detach(DoubleLinkedNode<T> node) {
        if (node != tail) {
            node.detach();
            if (node == head) {
                head = head.getNext();
            }
            size.decrementAndGet();
        } else {
            removeTail();
        }
    }
}
