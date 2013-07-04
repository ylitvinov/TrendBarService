package org.tbs;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Integration test.
 *
 * @author Yury Litvinov
 */
public class TrendBarServiceIntTest {

    @Test
    public void testNormalUseCase() throws Exception {
        ITrendBarService service = TrendBarServiceFactory.create();

        service.registerQuote(new Quote(Symbol.EURJPY, 0, 10));
        service.registerQuote(new Quote(Symbol.EURUSD, 100, 5));
        service.registerQuote(new Quote(Symbol.EURUSD, 200, 2));
        service.registerQuote(new Quote(Symbol.EURUSD, 3000, 15));
        service.registerQuote(new Quote(Symbol.EURUSD, 4000, 19));
        service.registerQuote(new Quote(Symbol.EURJPY, 4000, 9));
        service.registerQuote(new Quote(Symbol.EURJPY, 4200, 12));
        service.registerQuote(new Quote(Symbol.EURJPY, 4300, 11));

        List<TrendBar> history = service.getHistory(Symbol.EURUSD, Period.hours(1), 0l, null);
        assertThat(history).hasSize(1);
        TrendBar trendBar = history.get(0);
        assertThat(trendBar.getStartTime()).isEqualTo(100);
        assertThat(trendBar.getOpenPrice()).isEqualTo(5);
        assertThat(trendBar.getClosePrice()).isEqualTo(15);
        assertThat(trendBar.getLowPrice()).isEqualTo(2);
        assertThat(trendBar.getHighPrice()).isEqualTo(15);
    }
}
