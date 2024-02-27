package com.blueboxmc.database.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public abstract class AsyncCache<K, V> {

    protected final AsyncLoadingCache<K, V> cache;

    public AsyncCache() {
        cache = Caffeine.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .buildAsync((key, executor) -> createCacheEntry(key));
    }

    public CompletableFuture<V> get(K key) {
        return cache.get(key);
    }

    public V awaitGet(K key) {
        try {
            return get(key).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to await cache result", e);
        }
    }

    public void remove(K key) {
        cache.synchronous().invalidate(key);
    }

    public void removeAll() {
        cache.synchronous().invalidateAll();
    }

    abstract CompletableFuture<V> createCacheEntry(K key);

}
