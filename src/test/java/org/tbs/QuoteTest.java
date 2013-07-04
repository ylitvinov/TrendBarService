package org.tbs;

import org.junit.Test;

/**
 * @author Yury Litvinov
 */
public class QuoteTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNegativePrice() throws Exception {
        new Quote(Symbol.EURJPY, 10, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeTimestamp() throws Exception {
        new Quote(Symbol.EURJPY, -1, 10);
    }

    @Test()
    public void testNormalUseCase() throws Exception {
        new Quote(Symbol.EURJPY, 10, 10);
    }
}
