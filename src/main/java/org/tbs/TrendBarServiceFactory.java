package org.tbs;

import org.tbs.common.CollectionUtils;
import org.tbs.impl.TrendBarService;

import java.util.Set;

/**
 * @author Yury Litvinov
 */
public class TrendBarServiceFactory {

    public static ITrendBarService create() {
        return create(CollectionUtils.asSet(Symbol.values()),
                CollectionUtils.asSet(Period.minutes(1), Period.hours(1), Period.days(1)));
    }

    public static ITrendBarService create(Set<Period> trendBarPeriods) {
        return create(CollectionUtils.asSet(Symbol.values()), trendBarPeriods);
    }

    public static ITrendBarService create(Set<Symbol> symbols, Set<Period> trendBarPeriods) {
        return new TrendBarService(symbols, trendBarPeriods);
    }
}
