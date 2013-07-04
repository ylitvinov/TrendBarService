package org.tbs.impl;

import org.tbs.Period;
import org.tbs.Quote;
import org.tbs.TrendBar;
import org.tbs.common.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yury Litvinov
 */
@ThreadSafe
public class TrendBarsHolder {

    private final List<TrendBar> completedTrendBars = new ArrayList<TrendBar>();
    private final Period period;

    // current trend bar data
    private Long periodStartTime;
    private Float openPrice;
    private Float closePrice;
    private Float highPrice;
    private Float lowPrice;

    public TrendBarsHolder(Period period) {
        if (period == null) {
            throw new IllegalArgumentException();
        }
        this.period = period;
    }

    public synchronized void register(Quote quote) {
        if (periodStartTime == null) {
            processFirstQuote(quote);
        } else if (periodStartTime + period.getSeconds() <= quote.getTimestamp()) {
            closeTrendBar(quote);
            addToExistingTrendBar(quote);
        } else {
            addToExistingTrendBar(quote);
        }
    }

    public synchronized List<TrendBar> getHistory(Long from, Long to) {
        List<TrendBar> result = new ArrayList<TrendBar>();
        int currentPeriodIdx = getTrendBarIdx(from);

        TrendBar previousTrendBar = null;
        while (currentPeriodIdx < completedTrendBars.size()
                && (to == null || completedTrendBars.get(currentPeriodIdx).getStartTime() < to)) {

            TrendBar currentTrendBar = completedTrendBars.get(currentPeriodIdx);

            if (previousTrendBar != null) {
                long endTime = currentTrendBar.getStartTime();
                addEmptyTrendBars(result, previousTrendBar, endTime);
            }

            result.add(currentTrendBar);

            previousTrendBar = currentTrendBar;
            currentPeriodIdx++;
        }
        if (!result.isEmpty()) {
            addEmptyTrendBars(result, result.get(result.size() - 1), periodStartTime);
        }
        return result;
    }

    private void addEmptyTrendBars(List<TrendBar> result, TrendBar trendBar, long toTime) {
        long missedTrendBars = ((toTime - trendBar.getStartTime()) / period.getSeconds()) - 1;
        for (int i = 1; i <= missedTrendBars; i++) {
            long startTime = trendBar.getStartTime() + i * period.getSeconds();
            result.add(createNoQuotesTrendBar(startTime, trendBar.getClosePrice()));
        }
    }

    private TrendBar createNoQuotesTrendBar(long startTime, float price) {
        return new TrendBar(startTime, price, price, price, price);
    }

    private int getTrendBarIdx(Long from) {
        int index = 0;
        while (completedTrendBars.size() < index
                && completedTrendBars.get(index).getStartTime() + period.getSeconds() < from) {
            index++;
        }
        return index == 0 ? 0 : index - 1;
    }

    private void addToExistingTrendBar(Quote quote) {
        float quotePrice = quote.getPrice();
        if (quotePrice > highPrice) {
            highPrice = quotePrice;
        }
        if (quotePrice < lowPrice) {
            lowPrice = quotePrice;
        }
        closePrice = quotePrice;
    }

    private void closeTrendBar(Quote quote) {
        completedTrendBars.add(new TrendBar(periodStartTime, openPrice, closePrice, highPrice, lowPrice));

        long skippedPeriods = (quote.getTimestamp() - periodStartTime) / period.getSeconds();
        periodStartTime += skippedPeriods * period.getSeconds();
        openPrice = closePrice;
        highPrice = closePrice;
        lowPrice = closePrice;
    }

    private void processFirstQuote(Quote quote) {
        periodStartTime = quote.getTimestamp();
        openPrice = quote.getPrice();
        closePrice = quote.getPrice();
        highPrice = quote.getPrice();
        lowPrice = quote.getPrice();
    }
}
