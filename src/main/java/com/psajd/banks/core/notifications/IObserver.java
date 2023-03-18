package com.psajd.banks.core.notifications;

/**
 * observer interface for realisation observer pattern
 */
public interface IObserver<T> {

    /**
     * do some updates by observer
     *
     * @param t update information
     */
    void update(T t);
}
