package com.maypink.tinkoff.cache;

import org.apache.commons.lang3.NotImplementedException;

public class DummyNode<T> implements DoubleLinkedNode<T> {
    private DoubleLinkedList<T> list;

    public DummyNode(DoubleLinkedList<T> list) {
        this.list = list;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public T getElement() throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public void detach() throws NotImplementedException{
        throw new NotImplementedException();
    }

    @Override
    public DoubleLinkedList<T> getListReference() {
        return list;
    }

    @Override
    public DoubleLinkedNode<T> setPrev(DoubleLinkedNode<T> next) {
        return next;
    }

    @Override
    public DoubleLinkedNode<T> setNext(DoubleLinkedNode<T> prev) {
        return prev;
    }

    @Override
    public DoubleLinkedNode<T> getPrev() {
        return this;
    }

    @Override
    public DoubleLinkedNode<T> getNext() {
        return this;
    }

    @Override
    public DoubleLinkedNode<T> search(T value) {
        return this;
    }
}
