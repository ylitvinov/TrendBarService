package org.tbs;

/**
 * @author Yury Litvinov
 */
public class TrendBar {

    private final long startTime;
    private final float openPrice;
    private final float closePrice;
    private final float highPrice;
    private final float lowPrice;

    public TrendBar(long startTime, float openPrice, float closePrice, float highPrice, float lowPrice) {
        if (startTime < 0) {
            throw new IllegalArgumentException("startTime should be non negative");
        }
        if (openPrice < 0) {
            throw new IllegalArgumentException("openPrice should be non negative");
        }
        if (closePrice < 0) {
            throw new IllegalArgumentException("closePrice should be non negative");
        }
        if (highPrice < 0) {
            throw new IllegalArgumentException("highPrice should be non negative");
        }
        if (lowPrice < 0) {
            throw new IllegalArgumentException("lowPrice should be non negative");
        }
        this.startTime = startTime;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
    }

    public long getStartTime() {
        return startTime;
    }

    public float getOpenPrice() {
        return openPrice;
    }

    public float getClosePrice() {
        return closePrice;
    }

    public float getHighPrice() {
        return highPrice;
    }

    public float getLowPrice() {
        return lowPrice;
    }
}
