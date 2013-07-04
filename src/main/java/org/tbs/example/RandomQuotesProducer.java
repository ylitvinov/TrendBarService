package org.tbs.example;

import org.tbs.ITrendBarService;
import org.tbs.Quote;
import org.tbs.Symbol;

import java.util.Random;

/**
 * @author Yury Litvinov
 */
public class RandomQuotesProducer extends Thread {
    private final ITrendBarService trendBarService;
    private final long quotes;
    private final Symbol symbol;

    public RandomQuotesProducer(ITrendBarService trendBarService, long quotes, Symbol symbol) {
        this.trendBarService = trendBarService;
        this.quotes = quotes;
        this.symbol = symbol;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (long i = 0; i < quotes; i++) {
            // lets assume our time moves 100 times faster
            long timestamp = System.currentTimeMillis() * 1000;

            // replace, if slow ;)
            int price = random.nextInt(1000);

            trendBarService.registerQuote(new Quote(symbol, timestamp, price));
        }
    }
}
