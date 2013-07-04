package org.tbs;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Yury Litvinov
 */
public class PeriodTest {

    @Test(expected = IllegalArgumentException.class)
    public void testMinutesMinus() throws Exception {
        Period.minutes(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinutesZero() throws Exception {
        Period.minutes(0);
    }

    @Test
    public void testMinutesPositive() throws Exception {
        assertThat(Period.minutes(2).getSeconds()).isEqualTo(60 * 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHoursMinus() throws Exception {
        Period.hours(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHoursZero() throws Exception {
        Period.hours(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDaysMinus() throws Exception {
        Period.days(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDaysZero() throws Exception {
        Period.days(0);
    }

    @Test
    public void testDaysPositive() throws Exception {
        assertThat(Period.days(2).getSeconds()).isEqualTo(24 * 60 * 60 * 2);
    }
}
