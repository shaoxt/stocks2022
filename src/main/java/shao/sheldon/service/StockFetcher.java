package shao.sheldon.service;

//GET
//	https://www.macrotrends.net/assets/php/stock_data_download.php?s=61cfe35676c05&t=MSFT
public interface StockFetcher {

    String fetch(String symbol);
}
