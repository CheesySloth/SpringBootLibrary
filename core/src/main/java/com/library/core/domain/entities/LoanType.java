package com.library.core.domain.entities;

public enum LoanType {
    SHORT_TERM(7),
    NORMAL(14),
    EXTENDED(30);

    private final int durationInDays;

    LoanType(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    public int getDuration() {
        return durationInDays;
    }
}
