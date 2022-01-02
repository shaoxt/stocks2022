package shao.sheldon.model;

public class Stock {

    private String name;
    private String alias;
    private String symbol;
    private String sector;

    public Stock(String name, String alias, String symbol) {
        this.name = name;
        this.alias = alias;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    public String toString() {
        return "{'name':'" + getName() + "', 'alias':'" + getAlias() + "}";
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}

