package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.apache.log4j.Logger;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author dl
 * @date 03.06.2024 22:16
 */

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    private static final Logger LOGGER = Logger.getLogger(SingleLockList.class);
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public synchronized void add(T value) {
        this.list.add(value);
    }

    public synchronized T get(int index) {
        return this.list.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }

    private synchronized List<T> copy(List<T> origin) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(origin);
            oos.flush();
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (ArrayList<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error("Error during deep copy", e);
            return null;
        }
    }
}

