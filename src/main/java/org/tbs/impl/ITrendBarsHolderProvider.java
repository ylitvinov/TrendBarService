package org.tbs.impl;

import org.tbs.Period;

/**
 * @author Yury Litvinov
 */
public interface ITrendBarsHolderProvider {

    TrendBarsHolder create(Period trendBarInterval);
}
