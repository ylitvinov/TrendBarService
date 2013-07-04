package org.tbs.impl;

import org.junit.Test;
import org.tbs.Period;
import org.tbs.Quote;
import org.tbs.Symbol;
import org.tbs.common.CollectionUtils;

import static org.mockito.Mockito.*;

/**
 * @author Yury Litvinov
 */
public class TrendBarServiceTest {

    MockTrendBarProvider mockTrendBarProvider = new MockTrendBarProvider();

    @Test(expected = NullPointerException.class)
    public void testNullSymbols() throws Exception {
        new TrendBarService(null, CollectionUtils.asSet(Period.days(1)));
    }

    @Test(expected = NullPointerException.class)
    public void testNullPeriods() throws Exception {
        new TrendBarService(CollectionUtils.asSet(Symbol.EURJPY), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHistoryNullSymbol() throws Exception {
        Period period = Period.minutes(1);
        TrendBarService service = new TrendBarService(CollectionUtils.asSet(Symbol.EURJPY), CollectionUtils.asSet(period));

        service.getHistory(null, period, 0l, Long.MAX_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHistoryWrongSymbol() throws Exception {
        Period period = Period.minutes(1);
        TrendBarService service = new TrendBarService(CollectionUtils.asSet(Symbol.EURJPY), CollectionUtils.asSet(period));

        service.getHistory(Symbol.EURUSD, period, 0l, Long.MAX_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHistoryNullPeriod() throws Exception {
        Period period = Period.minutes(1);
        TrendBarService service = new TrendBarService(CollectionUtils.asSet(Symbol.EURJPY), CollectionUtils.asSet(period));

        service.getHistory(Symbol.EURJPY, null, 0l, Long.MAX_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHistoryWrongPeriod() throws Exception {
        Period period = Period.minutes(1);
        TrendBarService service = new TrendBarService(CollectionUtils.asSet(Symbol.EURJPY), CollectionUtils.asSet(period));

        service.getHistory(Symbol.EURJPY, Period.minutes(2), 0l, Long.MAX_VALUE);
    }

    @Test()
    public void testGetHistory() throws Exception {
        Period period = Period.minutes(1);
        TrendBarService service = new TrendBarService(CollectionUtils.asSet(Symbol.EURJPY), CollectionUtils.asSet(period), mockTrendBarProvider);

        service.getHistory(Symbol.EURJPY, period, null, Long.MAX_VALUE);

        verify(mockTrendBarProvider.mock, times(1)).getHistory(0l, Long.MAX_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterQuoteWrongQuoteSymbol() throws Exception {
        Period period = Period.minutes(1);
        TrendBarService service = new TrendBarService(CollectionUtils.asSet(Symbol.EURJPY), CollectionUtils.asSet(period));

        service.registerQuote(new Quote(Symbol.EURUSD, 1, 1));
    }

    @Test()
    public void testRegisterQuote() throws Exception {
        Period period = Period.minutes(1);
        TrendBarService service = new TrendBarService(CollectionUtils.asSet(Symbol.EURJPY), CollectionUtils.asSet(period), mockTrendBarProvider);

        Quote quote = new Quote(Symbol.EURJPY, 1, 1);
        service.registerQuote(quote);

        verify(mockTrendBarProvider.mock, times(1)).register(quote);
    }

    class MockTrendBarProvider implements ITrendBarsHolderProvider {

        TrendBarsHolder mock = mock(TrendBarsHolder.class);

        @Override
        public TrendBarsHolder create(Period trendBarInterval) {
            return mock;
        }
    }
}
