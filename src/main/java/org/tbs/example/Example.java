package org.tbs.example;

import org.tbs.*;

import java.util.List;

/**
 * @author Yury Litvinov
 */
public class Example {

    private static final Symbol symbol = Symbol.EURJPY;
    private static final long QUOTES = 1000000l;
    private static final int CHECK_POINTS = 10;

    public static void main(String[] args) throws InterruptedException {
        ITrendBarService service = TrendBarServiceFactory.create(symbol);

        RandomQuotesProducer producer = new RandomQuotesProducer(service, QUOTES, symbol);
        producer.start();

        producer.join();
        List<TrendBar> history = service.getHistory(symbol, Period.minutes(1), 0l, null);
        for (TrendBar trendBar : history) {
            System.out.println(trendBar);
        }
    }
}
