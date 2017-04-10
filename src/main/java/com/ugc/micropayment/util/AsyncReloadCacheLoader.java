package com.ugc.micropayment.util;

//import com.fiveonechain.digitasset.service.LocalCacheRefreshExecutor;
import com.google.common.cache.CacheLoader;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

/**
 * Created by yuanshichao on 2016/12/14.
 */
public abstract class AsyncReloadCacheLoader<K, V> extends CacheLoader<K, V> {
/*
    @Override
    public ListenableFuture<V> reload(K key, V oldValue) throws Exception {
        ListenableFutureTask task = ListenableFutureTask.create(() -> load(key));
        LocalCacheRefreshExecutor.execute(task);
        return task;
    }*/
}
