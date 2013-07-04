package org.tbs;

/**
 * @author Yury Litvinov
 */
public class Quote {
    private final Symbol symbol;
    private final long timestamp; // in milliseconds
    private final float price;

    public Quote(Symbol symbol, long timestamp, float price) {
        if (symbol == null) {
            throw new IllegalArgumentException();
        }
        if (timestamp < 0) {
            throw new IllegalArgumentException("Timestamp should be non negative");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price should be non negative");
        }
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.price = price;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quote)) return false;

        Quote quote = (Quote) o;

        if (Float.compare(quote.price, price) != 0) return false;
        if (timestamp != quote.timestamp) return false;
        if (symbol != quote.symbol) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        return result;
    }
}
