package com.psajd.banks.core.clients;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.psajd.banks.core.notifications.IObserver;
import com.psajd.banks.core.notifications.Notification;
import lombok.*;

/**
 * bank client with collection of notification
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return getId().equals(client.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
