package shao.sheldon.implement;

import org.femtoframework.bean.annotation.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shao.sheldon.service.StockFetcher;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * CALL Action
 *
 * method: PATCH
 * URL:    http://127.0.0.1:9169/coin/api/v1/namespace/coin-examples/bean/helloworld?_action=action&_name=sayHello&1=Sheldon
 *
 * Configuration:
 * method: PATCH
 * URL:   http://127.0.0.1:9169/coin/api/v1/namespace/coin-examples/bean/helloworld?_action=set&_property=response&_value=Hello
 */
public class StockFetcherImpl implements StockFetcher {


    private static Logger log = LoggerFactory.getLogger(StockFetcherImpl.class);


    private static final String HEADER = "date,open,high,low,close,volume";

    private static final char[] CRLF = "\r\n".toCharArray();

    @Override
    @Action
    public String fetch(String symbol) {

        //https://www.macrotrends.net/assets/php/stock_data_download.php?s=61cfe35676c05&t=MSFT

        try {
            URL url = new URL("https://www.macrotrends.net/assets/php/stock_data_download.php?s=61cfe35676c05&t=" + symbol);

            InputStream input = url.openStream();
            InputStreamReader reader = new InputStreamReader(new BufferedInputStream(input));
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;
            boolean start = false;
            StringWriter writer = new StringWriter();
            while ((line = bufferedReader.readLine())!= null) {
                if (start) {
                    writer.write(line);
                    writer.write(CRLF);
                }
                else if (HEADER.equals(line.trim())) {
                    writer = new StringWriter();
                    writer.write(line);
                    writer.write(CRLF);
                    start = true;
                }
            }
            return writer.toString();
        } catch (MalformedURLException e) {
            log.warn("URL ex", e);
        } catch (IOException e) {
            log.warn("IO ex", e);
        }
        return null;
    }

    public static void main(String[] args) {
        StockFetcherImpl fetcher = new StockFetcherImpl();
        String result = fetcher.fetch("NVDA");
        System.out.println(result);
    }

}
