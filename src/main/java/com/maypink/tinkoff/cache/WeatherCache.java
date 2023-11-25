package com.maypink.tinkoff.cache;

import com.maypink.tinkoff.controllers.resources.WeatherResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class WeatherCache implements BaseCache<String, WeatherResource> {
    @Value("${cache.course.size}")
    private final int size;
    private Map<String, DoubleLinkedNode<CacheElement<String, WeatherResource>>> linkedListNodeMap;
    private DoubleLinkedList<CacheElement<String, WeatherResource>> doubleLinkedList;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public WeatherCache(int size) {
        this.size = size;
        this.linkedListNodeMap = new ConcurrentHashMap<>(size);
        this.doubleLinkedList = new DoubleLinkedList<>();
    }

    @Override
    public boolean put(String key, WeatherResource value) {
        this.lock.writeLock().lock();
        try {
            CacheElement<String, WeatherResource> item = new CacheElement<String, WeatherResource>(key, value);
            DoubleLinkedNode<CacheElement<String, WeatherResource>> newNode;
            if (this.linkedListNodeMap.containsKey(key)) {
                DoubleLinkedNode<CacheElement<String, WeatherResource>> node = this.linkedListNodeMap.get(key);
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
    public Optional<WeatherResource> get(String key) {
        this.lock.readLock().lock();
        try {
            DoubleLinkedNode<CacheElement<String, WeatherResource>> doubleLinkedNode = this.linkedListNodeMap.get(key);
            if (doubleLinkedNode != null && !doubleLinkedNode.isEmpty()) {
                linkedListNodeMap.put(key, this.doubleLinkedList.moveToFront(doubleLinkedNode));
                return Optional.of(doubleLinkedNode.getElement().getValue());
            }
            return Optional.empty();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public boolean isPresent(String key){
        this.lock.readLock().lock();
        try {
            DoubleLinkedNode<CacheElement<String, WeatherResource>> doubleLinkedNode = this.linkedListNodeMap.get(key);
            return doubleLinkedNode != null && !doubleLinkedNode.isEmpty();
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
            DoubleLinkedNode<CacheElement<String, WeatherResource>> doubleLinkedNode = doubleLinkedList.removeTail();
            if (doubleLinkedNode.isEmpty()) {
                return false;
            }
            linkedListNodeMap.remove(doubleLinkedNode.getElement().getKey());
            return true;
        } finally {
            this.lock.writeLock().unlock();
        }
    }
}
