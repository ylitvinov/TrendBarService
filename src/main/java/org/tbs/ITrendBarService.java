package org.tbs;

import java.util.List;

/**
 * @author Yury Litvinov
 */
public interface ITrendBarService {

    void registerQuote(Quote quote);

    List<TrendBar> getHistory(Symbol symbol, Period period, Long from, Long to);
}
