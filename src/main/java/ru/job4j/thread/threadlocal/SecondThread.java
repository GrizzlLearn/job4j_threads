package ru.job4j.thread.threadlocal;

/**
 * @author dl
 * @date 12.04.2024 20:00
 */
public class SecondThread extends Thread{
    @Override
    public void run() {
        ThreadLocalDemo.threadLocal.set("Это поток 2.");
        System.out.println(ThreadLocalDemo.threadLocal.get());
    }
}
