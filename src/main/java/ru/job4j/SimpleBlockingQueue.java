package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author dl
 * @date 26.06.2024 20:28
 * 1. Реализовать методы poll() и offer().
 * 2. Написать тесты. В тестах должны быть две нити: одна производитель, другая потребитель.
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public void offer(T value) {
    }

    public T poll() throws InterruptedException {
        return null;
    }
}
