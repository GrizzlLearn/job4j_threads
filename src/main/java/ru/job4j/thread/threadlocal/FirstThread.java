package ru.job4j.thread.threadlocal;

/**
 * @author dl
 * @date 12.04.2024 20:00
 */
public class FirstThread extends Thread{
    @Override
    public void run() {
        ThreadLocalDemo.threadLocal.set("Это поток 1.");
        System.out.println(ThreadLocalDemo.threadLocal.get());
    }
}
