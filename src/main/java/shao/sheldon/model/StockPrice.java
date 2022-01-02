package shao.sheldon.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Stock Price
 */
public class StockPrice {
    private Date date;
    private String symbol;
    private float open;
    private float close;
    private float high;
    private float low;
    private long volume;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public StockPrice() {

    }

    public StockPrice(String symbol, String[] line) throws ParseException {
        setSymbol(symbol);
        setDate(dateFormat.parse(line[0]));
        setOpen(Float.parseFloat(line[1]));
        setHigh(Float.parseFloat(line[2]));
        setLow(Float.parseFloat(line[3]));
        setClose(Float.parseFloat(line[4]));
        setVolume(Long.parseLong(line[5]));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }
}
