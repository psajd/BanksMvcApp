package com.psajd.banks.core.time;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.psajd.banks.core.notifications.IObserver;
import com.psajd.banks.core.notifications.Observable;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * class for working with time in bank
 */
@Component
public class TimeManager implements Observable<TimeManager> {

    private final List<IObserver<TimeManager>> _observers = new ArrayList<>();
    @Getter
    private LocalDate now;

    public TimeManager() {
        this.now = LocalDate.now();
    }

    /**
     * plus custom amount of time
     *
     * @param years add years
     * @param month add month
     * @param days  add days
     */
    public void Add(int years, int month, int days) {
        now = now.plusYears(years);
        now = now.plusMonths(month);
        now = now.plusDays(days);

        _observers.forEach(x -> x.update(this));
    }

    /**
     * add new observer
     *
     * @param o new observer
     */
    @Override
    public void addObserver(IObserver<TimeManager> o) {
        if (!_observers.contains(o)) {
            _observers.add(o);
        }
    }

    /**
     * remove observer
     *
     * @param o observer
     */
    @Override
    public void removeObserver(IObserver<TimeManager> o) {
        _observers.remove(o);
    }
}
