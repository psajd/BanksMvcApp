package com.psajd.banks.core.clients;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.psajd.banks.core.notifications.IObserver;
import com.psajd.banks.core.notifications.Notification;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * bank client with collection of notification
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
@EqualsAndHashCode
public class Client implements IObserver<Notification> {

    private UUID id;
    private String address;
    private String clientName;
    private String passport;
    private String phoneNumber;
    private final List<Notification> notifications = new ArrayList<>();

    /**
     * check for full profile
     *
     * @return true if profile is full, otherwise false
     */
    public boolean IsProfileFull() {
        return address != null && passport != null && phoneNumber != null;
    }

    /**
     * override IObserver update method, add notification from bank
     *
     * @param notification new notification from bank
     */
    @Override
    public void update(Notification notification) {
        notifications.add(notification);
    }
}
