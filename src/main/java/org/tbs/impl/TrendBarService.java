package org.tbs.impl;

import org.tbs.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Yury Litvinov
 */
public class TrendBarService implements ITrendBarService {

    private final Map<Symbol, Map<Period, TrendBarsHolder>> trendBarsSetMap = new HashMap<Symbol, Map<Period, TrendBarsHolder>>();

    public TrendBarService(Set<Symbol> symbols, Set<Period> trendBarIntervals) {
        this(symbols, trendBarIntervals, new ITrendBarsHolderProvider() {
            @Override
            public TrendBarsHolder create(Period trendBarInterval) {
                return new TrendBarsHolder(trendBarInterval);
            }
        });
    }

    TrendBarService(Set<Symbol> symbols, Set<Period> trendBarIntervals,
                    ITrendBarsHolderProvider trendBarsHolderProvider) {
        for (Symbol symbol : symbols) {
            HashMap<Period, TrendBarsHolder> barsHolderMap = new HashMap<Period, TrendBarsHolder>();
            trendBarsSetMap.put(symbol, barsHolderMap);
            for (Period trendBarInterval : trendBarIntervals) {
                barsHolderMap.put(trendBarInterval, trendBarsHolderProvider.create(trendBarInterval));
            }
        }
    }

    @Override
    public void registerQuote(Quote quote) {
        Map<Period, TrendBarsHolder> barsHolderMap = getTrendBarsForSymbol(quote.getSymbol());
        for (TrendBarsHolder trendBarsHolder : barsHolderMap.values()) {
            trendBarsHolder.register(quote);
        }
    }

    @Override
    public List<TrendBar> getHistory(Symbol symbol, Period period, Long from, Long to) {
        Map<Period, TrendBarsHolder> barsHolderMap = getTrendBarsForSymbol(symbol);
        TrendBarsHolder trendBarsHolder = barsHolderMap.get(period);
        if (trendBarsHolder == null) {
            throw new IllegalArgumentException("No trend bars for the '" + symbol + "' and period '" + period + "'");
        }
        if (from == null) {
            from = 0l;
        }
        return trendBarsHolder.getHistory(from, to);
    }

    private Map<Period, TrendBarsHolder> getTrendBarsForSymbol(Symbol symbol) {
        Map<Period, TrendBarsHolder> barsHolderMap = trendBarsSetMap.get(symbol);
        if (barsHolderMap == null) {
            throw new IllegalArgumentException("Unknown symbol '" + symbol + "'");
        }
        return barsHolderMap;
    }

}
