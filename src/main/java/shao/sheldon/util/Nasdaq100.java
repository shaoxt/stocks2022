package shao.sheldon.util;

import org.femtoframework.text.csv.CsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Load Nasdaq 100
 */
public class Nasdaq100 {


    private static Logger log = LoggerFactory.getLogger(Nasdaq100.class);

    private static HashSet<String> all = new HashSet<>();

    static {
        load();
    }

    private static void load() {
        InputStream inputStream = Nasdaq100.class.getClassLoader().getResourceAsStream("META-INF/stocks/nasdaq/100/all.csv");
        if (inputStream != null) {
            InputStreamReader reader = new InputStreamReader(inputStream);
            CsvReader csvReader = new CsvReader(new BufferedReader(reader));
            String[] line = null;
            try {
                while((line = csvReader.readLine()) != null) {
                    if (line.length == 1) {
                        String item1 = line[0];
                        all.add(item1.trim());
                    }
                }
            } catch (IOException e) {
                log.warn("Loading nasdaq stock info exception", e);
            }
        }
    }

    /**
     * Return all Stocks
     *
     * @return  all Stocks <Symbol>
     */
    public static Set<String> getAll() {
        return all;
    }

    public static void main(String[] args) {
        System.out.println(all);
    }


}
