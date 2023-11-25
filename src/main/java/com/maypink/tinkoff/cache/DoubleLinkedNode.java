package com.maypink.tinkoff.cache;

import org.apache.commons.lang3.NotImplementedException;

public interface DoubleLinkedNode<V> {

    boolean isEmpty() throws NotImplementedException;

    V getElement() throws NotImplementedException;

    void detach() throws NotImplementedException;

    DoubleLinkedList<V> getListReference();

    DoubleLinkedNode<V> setPrev(DoubleLinkedNode<V> prev);

    DoubleLinkedNode<V> setNext(DoubleLinkedNode<V> next);

    DoubleLinkedNode<V> getPrev();

    DoubleLinkedNode<V> getNext();

    DoubleLinkedNode<V> search(V value);
}