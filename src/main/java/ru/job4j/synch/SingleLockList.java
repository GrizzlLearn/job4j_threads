package ru.job4j.synch;

/**
 * @author dl
 * @date 03.06.2024 22:16
 */

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.List;

public class SingleLockList<T> implements Iterable<T> {

    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public void add(T value) {
    }

    public T get(int index) {
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    private List<T> copy(List<T> origin) {
        return null;
    }
}

