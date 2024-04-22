package ru.job4j.linked;

/**
 * @author dl
 * @date 22.04.2024 21:43
 */
public final class Node<T> {
    final private Node<T> next;
    final private T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }
}
