package com.maypink.tinkoff.cache;

public interface LinkedListNode<V> {
    boolean hasElement();

    boolean isEmpty();

    V getElement() throws NullPointerException;

    void detach();

    DoubleLinkedList<V> getListReference();

    LinkedListNode<V> setPrev(LinkedListNode<V> prev);

    LinkedListNode<V> setNext(LinkedListNode<V> next);

    LinkedListNode<V> getPrev();

    LinkedListNode<V> getNext();

    LinkedListNode<V> search(V value);
}