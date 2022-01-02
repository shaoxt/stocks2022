package shao.sheldon.util;


import org.slf4j.LoggerFactory;
import shao.sheldon.model.Stock;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import org.femtoframework.text.csv.CsvReader;
import org.slf4j.Logger;

public class AllStocks {

    private static Logger log = LoggerFactory.getLogger(AllStocks.class);

    private static Map<String, Stock> all = new HashMap<>();

    static {
        load();
    }

    private static void load() {
        InputStream inputStream = AllStocks.class.getClassLoader().getResourceAsStream("META-INF/stocks/all/all.csv");
        if (inputStream != null) {
            InputStreamReader reader = new InputStreamReader(inputStream);
            CsvReader csvReader = new CsvReader(new BufferedReader(reader));
            String[] line = null;
            try {
                while((line = csvReader.readLine()) != null) {
                    if (line.length == 2) {
                        String item1 = line[0];
                        String item2 = line[1];
                        String symbol, name, alias;
                        int dashIndex = item1.indexOf('-');
                        if (dashIndex > 0) {
                            symbol = item1.substring(0, dashIndex).trim();
                            alias = name = item1.substring(dashIndex +1).trim();

                            int slashIndex = item2.indexOf('/');
                            if (slashIndex > 0) {
                                if (symbol.equals(item2.substring(0, slashIndex).trim())) {
                                    alias = item2.substring(slashIndex+1).trim();
                                }
                            }

                            Stock stock = new Stock(name, alias, symbol);
                            all.put(symbol, stock);
                        }
                        else {
                            log.info("Invalid line:" + line[0] + ", " + line[1]);
                        }
                    }
                }
            } catch (IOException e) {
                log.warn("Loading stock info exception", e);
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
