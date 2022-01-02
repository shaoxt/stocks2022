package shao.sheldon;

import org.femtoframework.text.FloatFormat;
import org.femtoframework.text.csv.CsvReader;
import org.femtoframework.util.SortedList;
import shao.sheldon.model.StockPrice;

import java.io.*;
import java.text.ParseException;
import java.util.Comparator;


/**
 * Stocks
 */
public class Stocks2018And2022 {

    private static StockPricesComparator comparator = new StockPricesComparator();

    private static SortedList<StockPrices> nasdaq100 = new SortedList<>(comparator);

    private static SortedList<StockPrices> standardAndPoor500 = new SortedList<>(comparator);


    public static void main(String[] args) throws Exception {

        scanDir(nasdaq100, new File("data/nasdaq/100"));
        scanDir(standardAndPoor500, new File("data/S&P/500"));

        System.out.println("===============  NASDAQ 100 ===============");

        System.out.println("Symbol,P2018S,P2018E,ROI,P2022");
        for(StockPrices prices : nasdaq100) {
            if (prices.jan022018 != null) {
                System.out.print(prices.symbol);
                System.out.print(",");
                System.out.print(FloatFormat.format(prices.jan022018.getOpen(), 2));
                System.out.print(",");
                System.out.print(FloatFormat.format(prices.dec312018.getClose(), 2));
                System.out.print(",");
                System.out.print(FloatFormat.format(prices.yearROI, 2));
                System.out.print(",");
                System.out.println(FloatFormat.format(prices.dec312021.getClose(), 2));
            }
        }

        System.out.println("===============    S&P 500  ===============");
        System.out.println("Symbol,P2018S,P2018E,ROI,P2022");
        for(StockPrices prices : standardAndPoor500) {
            if (prices.jan022018 != null && prices.dec312018 != null) {
                System.out.print(prices.symbol);
                System.out.print(",");
                System.out.print(FloatFormat.format(prices.jan022018.getOpen(), 2));
                System.out.print(",");
                System.out.print(FloatFormat.format(prices.dec312018.getClose(), 2));
                System.out.print(",");
                System.out.print(FloatFormat.format(prices.yearROI, 2));
                System.out.print(",");
                if (prices.dec312021 != null) {
                    System.out.println(FloatFormat.format(prices.dec312021.getClose(), 2));
                } else {
                    System.out.println("xx");
                }
            }
        }

    }

    private static void scanDir(SortedList<StockPrices> list, File dir) throws IOException, ParseException {
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".csv");
            }
        });
        for(File file: files) {
            String name = file.getName();
            String symbol = name.substring(0, name.length()-4);

            StockPrices prices = new StockPrices();
            prices.symbol = symbol;
            loadPrices(symbol, prices, file);
            list.add(prices);
        }
    }

    public static final String JAN_02_2018 = "2018-01-02";
    public static final String DEC_31_2018 = "2018-12-31";
    public static final String DEC_31_2021 = "2021-12-31";

    private static void loadPrices(String symbol, StockPrices prices, File file) throws IOException, ParseException {
        CsvReader reader = new CsvReader(new BufferedReader(new FileReader(file)));
        String[] line = reader.readLine();
        while((line = reader.readLine()) != null) {
            if (line.length == 6) {
                if (JAN_02_2018.equals(line[0])) {
                    prices.jan022018 = new StockPrice(symbol, line);
                } else if (DEC_31_2018.equals(line[0])) {
                    prices.dec312018 = new StockPrice(symbol, line);
                }
                else if (DEC_31_2021.equals(line[0])) {
                    prices.dec312021 = new StockPrice(symbol, line);
                }
            }
        }

        if (prices.dec312018 != null && prices.jan022018 != null) {
            prices.yearROI = (prices.dec312018.getClose() - prices.jan022018.getOpen()) * 100 / prices.jan022018.getOpen();
        }
    }

    public static class StockPricesComparator implements Comparator<Stocks2018And2022.StockPrices> {
        @Override
        public int compare(StockPrices o1, StockPrices o2) {
            return - o1.compareTo(o2);
        }
    }


    public static class StockPrices implements Comparable<StockPrices> {
        String symbol;

        StockPrice jan022018;
        StockPrice dec312018;
        StockPrice dec312021;

        float yearROI;

        @Override
        public int compareTo(StockPrices o) {
            return Float.compare(yearROI, o.yearROI);
        }
    }
}
