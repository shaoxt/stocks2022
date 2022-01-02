package shao.sheldon;

import shao.sheldon.implement.StockFetcherImpl;
import shao.sheldon.util.Nasdaq100;

import java.io.FileWriter;
import java.util.Set;

public class LoadAllNasdaq100Prices {

    public static void main(String[] args) throws Exception {
        Set<String> all = Nasdaq100.getAll();
        StockFetcherImpl fetcher = new StockFetcherImpl();

        for(String symbol: all) {
            System.out.println("Loading price for :" + symbol);

            String result = fetcher.fetch(symbol);

            FileWriter writer = new FileWriter("data/nasdaq/100/" + symbol + ".csv");
            writer.write(result);
            writer.flush();
            writer.close();
        }
    }
}
