package com.intbyte.wizbuddy.exception.schedule;

import com.intbyte.wizbuddy.exception.StatusEnum;

public class ScheduleNotFoundException extends IllegalArgumentException {

    private final StatusEnum status;

    private static final String message = "해당 스케줄이 존재하지 않습니다.";

    public ScheduleNotFoundException() {
        super(message);
        this.status = StatusEnum.SCHEDULE_NOT_FOUND;
    }
}
