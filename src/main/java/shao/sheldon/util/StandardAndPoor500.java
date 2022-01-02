package shao.sheldon.util;

import org.femtoframework.text.csv.CsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shao.sheldon.model.Stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * S&P 500
 *
 * https://datahub.io/core/s-and-p-500-companies
 */
public class StandardAndPoor500 {

    private static Logger log = LoggerFactory.getLogger(StandardAndPoor500.class);

    private static Map<String, Stock> all = new HashMap<>();

    static {
        load();
    }

    private static void load() {
        InputStream inputStream = AllStocks.class.getClassLoader().getResourceAsStream("META-INF/stocks/S&P/500/all.csv");
        if (inputStream != null) {
            InputStreamReader reader = new InputStreamReader(inputStream);
            CsvReader csvReader = new CsvReader(new BufferedReader(reader));
            String[] line = null;
            try {
                while((line = csvReader.readLine()) != null) {
                    if (line.length == 3) {
                        String symbol = line[0];
                        String name = line[1];
                        String sector = line[2];

                        Stock stock = new Stock(name, name, symbol);
                        stock.setSector(sector);
                        all.put(symbol, stock);
                    }
                    else {
                        log.info("Invalid line:" + line[0] + ", " + line[1]);
                    }
                }
            } catch (IOException e) {
                log.warn("Loading S&P stock info exception", e);
            }
        }
    }

    /**
     * Return all Stocks
     *
     * @return  all Stocks <Symbol --> Stock>
     */
    public static Map<String, Stock> getAll() {
        return all;
    }

    public static void main(String[] args) {
        System.out.println(all);
    }
}
