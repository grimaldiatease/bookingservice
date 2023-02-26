package com.atease.booking.domain.enumeration;

/**
 * The ReservationStatus enumeration.
 */
public enum ReservationStatus {
    WAITING,
    WAITING_RESCHEDULE,
    ACTIVE,
    EXECUTED,
    CANCELLED,
    ARCHIVED,
    REJECTED_BY_CHANNEL,
}
