package org.tbs;

import org.junit.Test;

/**
 * @author Yury Litvinov
 */
public class TrendBarTest {
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeStartTime() throws Exception {
        new TrendBar(-1, 10, 10, 10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeOpenPrice() throws Exception {
        new TrendBar(10, -1, 10, 10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeClosePrice() throws Exception {
        new TrendBar(10, 10, -1, 10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeHighPrice() throws Exception {
        new TrendBar(10, 10, 10, -1, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroLowPrice() throws Exception {
        new TrendBar(10, 10, 10, 10, -1);
    }

    @Test()
    public void testNormalUseCase() throws Exception {
        new TrendBar(10, 10, 10, 10, 10);
    }
}
