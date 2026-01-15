package com.reseatvation.config;

import java.time.LocalTime;

public final class CinemaRules {

    public static final int BUFFER_MINUTES = 10;
    public static final int SLOT_MINUTES = 5;
    public static final LocalTime OPEN_TIME = LocalTime.of(10, 0);
    public static final LocalTime CLOSE_TIME = LocalTime.of(23, 0);
    private CinemaRules() {
    }
}
