package com.maypink.tinkoff.cache;


public class Node<T> implements DoubleLinkedNode<T> {
    private T value;
    private DoubleLinkedList<T> list;
    private DoubleLinkedNode next;
    private DoubleLinkedNode prev;

    public Node(T value, DoubleLinkedNode<T> next, DoubleLinkedList<T> list) {
        this.value = value;
        this.next = next;
        this.setPrev(next.getPrev());
        this.prev.setNext(this);
        this.next.setPrev(this);
        this.list = list;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public T getElement() {
        return value;
    }

    public void detach() {
        this.prev.setNext(this.getNext());
        this.next.setPrev(this.getPrev());
    }

    @Override
    public DoubleLinkedList<T> getListReference() {
        return this.list;
    }

    @Override
    public DoubleLinkedNode<T> setPrev(DoubleLinkedNode<T> prev) {
        this.prev = prev;
        return this;
    }

    @Override
    public DoubleLinkedNode<T> setNext(DoubleLinkedNode<T> next) {
        this.next = next;
        return this;
    }

    @Override
    public DoubleLinkedNode<T> getPrev() {
        return this.prev;
    }

    @Override
    public DoubleLinkedNode<T> getNext() {
        return this.next;
    }

    @Override
    public DoubleLinkedNode<T> search(T value) {
        return this.getElement() == value ? this : this.getNext().search(value);
    }
}
