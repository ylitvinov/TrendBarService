package org.tbs;

/**
 * @author Yury Litvinov
 */
public class Period {

    public static final int MS_IN_MINUTE = 60000;

    private final int seconds;

    private Period(int seconds) {
        if (seconds <= 0) {
            throw new IllegalArgumentException("Period should be positive");
        }
        this.seconds = seconds;
    }

    public static Period minutes(int minutes) {
        return new Period(minutes * 60);
    }

    public static Period hours(int hours) {
        return new Period(hours * 60 * 60);
    }


    public static Period days(int days) {
        return new Period(days * 60 * 60 * 24);
    }

    public int getSeconds() {
        return seconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Period)) return false;

        Period period = (Period) o;

        if (seconds != period.seconds) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return seconds;
    }
}
