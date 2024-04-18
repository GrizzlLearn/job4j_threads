package ru.job4j;

/**
 * @author dl
 * @date 18.04.2024 22:55
 */
public final class Cache {
    /**
     * volatile используется для того что бы любые изменения, внесенные в переменную одним потоком,
     * будут видны всем другим потокам.
     */
    private static volatile Cache cache;

    /**
     * synchronized + Блокировка с двойной проверкой
     * @return
     */
    public static Cache getInstance() {
        if (cache == null) {
            synchronized (Cache.class) {
                if (cache == null) {
                    cache = new Cache();
                }
            }
        }
        return cache;
    }
}
