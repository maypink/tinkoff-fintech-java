package com.maypink.tinkoff.cache;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WeatherCache<K, V> implements BaseCache<K, V> {
    private int size;
    private Map<K, LinkedListNode<CacheElement<K, V>>> linkedListNodeMap;
    private DoubleLinkedList<CacheElement<K, V>> doubleLinkedList;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public WeatherCache(int size) {
        this.size = size;
        this.linkedListNodeMap = new ConcurrentHashMap<>(size);
        this.doubleLinkedList = new DoubleLinkedList<>();
    }

    @Override
    public boolean put(K key, V value) {
        this.lock.writeLock().lock();
        try {
            CacheElement<K, V> item = new CacheElement<K, V>(key, value);
            LinkedListNode<CacheElement<K, V>> newNode;
            if (this.linkedListNodeMap.containsKey(key)) {
                LinkedListNode<CacheElement<K, V>> node = this.linkedListNodeMap.get(key);
                newNode = doubleLinkedList.updateAndMoveToFront(node, item);
            } else {
                if (this.size() >= this.size) {
                    this.evictElement();
                }
                newNode = this.doubleLinkedList.add(item);
            }
            if (newNode.isEmpty()) {
                return false;
            }
            this.linkedListNodeMap.put(key, newNode);
            return true;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public Optional<V> get(K key) {
        this.lock.readLock().lock();
        try {
            LinkedListNode<CacheElement<K, V>> linkedListNode = this.linkedListNodeMap.get(key);
            if (linkedListNode != null && !linkedListNode.isEmpty()) {
                linkedListNodeMap.put(key, this.doubleLinkedList.moveToFront(linkedListNode));
                return Optional.of(linkedListNode.getElement().getValue());
            }
            return Optional.empty();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public int size() {
        this.lock.readLock().lock();
        try {
            return doubleLinkedList.size();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        this.lock.writeLock().lock();
        try {
            linkedListNodeMap.clear();
            doubleLinkedList.clear();
        } finally {
            this.lock.writeLock().unlock();
        }
    }


    private boolean evictElement() {
        this.lock.writeLock().lock();
        try {
            LinkedListNode<CacheElement<K, V>> linkedListNode = doubleLinkedList.removeTail();
            if (linkedListNode.isEmpty()) {
                return false;
            }
            linkedListNodeMap.remove(linkedListNode.getElement().getKey());
            return true;
        } finally {
            this.lock.writeLock().unlock();
        }
    }
}