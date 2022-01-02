package shao.sheldon;

import shao.sheldon.implement.StockFetcherImpl;
import shao.sheldon.model.Stock;
import shao.sheldon.util.StandardAndPoor500;

import java.io.FileWriter;
import java.util.Map;

public class LoadAllStandardAndPoorPrices {

    public static void main(String[] args) throws Exception {
        Map<String, Stock> all = StandardAndPoor500.getAll();
        StockFetcherImpl fetcher = new StockFetcherImpl();

        for(String symbol: all.keySet()) {
            System.out.println("Loading price for :" + symbol);

            String result = fetcher.fetch(symbol);

            FileWriter writer = new FileWriter("data/S&P/500/" + symbol + ".csv");
            writer.write(result);
            writer.flush();
            writer.close();
        }
    }
}
