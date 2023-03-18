package com.psajd.banks.core.notifications;

/**
 * observable interface for realisation observer pattern
 */
public interface Observable<T> {

    /**
     * add new observer
     *
     * @param o new observer
     */
    void addObserver(IObserver<T> o);

    /**
     * remove observer
     *
     * @param o observer
     */
    void removeObserver(IObserver<T> o);
}
