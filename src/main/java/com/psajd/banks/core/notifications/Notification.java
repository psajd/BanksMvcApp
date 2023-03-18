package com.psajd.banks.core.notifications;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * notification, it is used for transmission to the observer method
 */
@Getter
@AllArgsConstructor
@ToString
public class Notification {

    private Date creationDate;
    private String notificationText;
}
