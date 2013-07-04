package org.tbs.impl;

import org.junit.Before;
import org.junit.Test;
import org.tbs.Period;
import org.tbs.Quote;
import org.tbs.Symbol;
import org.tbs.TrendBar;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Yury Litvinov
 */
public class TrendBarsHolderTest {

    Period period;
    long periodSec;
    TrendBarsHolder trendBarsHolder;

    @Before
    public void setUp() throws Exception {
        period = Period.minutes(1);
        periodSec = period.getSeconds();
        trendBarsHolder = new TrendBarsHolder(period);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNull() throws Exception {
        new TrendBarsHolder(null);
    }

    @Test(expected = NullPointerException.class)
    public void testRegisterNull() throws Exception {
        trendBarsHolder.register(null);
    }

    @Test
    public void testNoQuotes() throws Exception {
        List<TrendBar> observed = trendBarsHolder.getHistory(0l, Long.MAX_VALUE);

        assertThat(observed).isEmpty();
    }

    @Test
    public void testSingleQuote() throws Exception {
        trendBarsHolder.register(new Quote(Symbol.EURJPY, 1, 1));

        List<TrendBar> observed = trendBarsHolder.getHistory(0l, Long.MAX_VALUE);

        assertThat(observed).isEmpty();
    }

    @Test
    public void testTwoQuotesFromSamePeriod() throws Exception {
        trendBarsHolder.register(new Quote(Symbol.EURJPY, 1, 1)); // first minute
        trendBarsHolder.register(new Quote(Symbol.EURJPY, 2, 1)); // first minute

        List<TrendBar> observed = trendBarsHolder.getHistory(0l, Long.MAX_VALUE);

        assertThat(observed).isEmpty();
    }

    @Test
    public void testTwoQuotesFromDiffPeriods() throws Exception {
        trendBarsHolder.register(new Quote(Symbol.EURJPY, 0, 10));   // first minute
        trendBarsHolder.register(new Quote(Symbol.EURJPY, periodSec, 2)); // second minute

        List<TrendBar> observed = trendBarsHolder.getHistory(0l, Long.MAX_VALUE);

        assertThat(observed).hasSize(1);
        assertTrendBar(observed.get(0), 10, 10, 10, 10, 0);
    }

    @Test
    public void testManyQuotesInSamePeriod() throws Exception {
        TrendBarsHolder trendBarsHolder = new TrendBarsHolder(Period.minutes(1));
        trendBarsHolder.register(new Quote(Symbol.EURJPY, 0, 10)); // first minute
        trendBarsHolder.register(new Quote(Symbol.EURJPY, periodSec + 1, 20)); // second minute
        trendBarsHolder.register(new Quote(Symbol.EURJPY, periodSec + 2, 5)); // second minute
        trendBarsHolder.register(new Quote(Symbol.EURJPY, periodSec + 3, 12)); // second minute
        trendBarsHolder.register(new Quote(Symbol.EURJPY, periodSec * 2 + 1, 30)); // third minute

        List<TrendBar> observed = trendBarsHolder.getHistory(0l, Long.MAX_VALUE);

        assertThat(observed).hasSize(2);
        assertTrendBar(observed.get(0), 10, 10, 10, 10, 0);
        assertTrendBar(observed.get(1), 10, 12, 20, 5, periodSec);
    }

    @Test
    public void testPeriodsWithoutQuotes() throws Exception {

        TrendBarsHolder trendBarsHolder = new TrendBarsHolder(period);
        trendBarsHolder.register(new Quote(Symbol.EURJPY, 1, 10)); // first minute
        trendBarsHolder.register(new Quote(Symbol.EURJPY, periodSec + 1, 20)); // second minute
        trendBarsHolder.register(new Quote(Symbol.EURJPY, periodSec * 4 + 1, 30)); // 5'th minute

        List<TrendBar> observed = trendBarsHolder.getHistory(0l, Long.MAX_VALUE);

        assertThat(observed).hasSize(4);
        assertTrendBar(observed.get(0), 10, 10, 10, 10, 1);
        assertTrendBar(observed.get(1), 10, 20, 20, 10, periodSec + 1);
        assertTrendBar(observed.get(2), 20, 20, 20, 20, periodSec * 2 + 1);
        assertTrendBar(observed.get(3), 20, 20, 20, 20, periodSec * 3 + 1);
    }

    private void assertTrendBar(TrendBar trendBar,
                                int openPrice, int closePrice, int highPrice, int lowPrice, long startTime) {
        assertThat(trendBar.getOpenPrice()).isEqualTo(openPrice);
        assertThat(trendBar.getClosePrice()).isEqualTo(closePrice);
        assertThat(trendBar.getHighPrice()).isEqualTo(highPrice);
        assertThat(trendBar.getLowPrice()).isEqualTo(lowPrice);
        assertThat(trendBar.getStartTime()).isEqualTo(startTime);
    }
}