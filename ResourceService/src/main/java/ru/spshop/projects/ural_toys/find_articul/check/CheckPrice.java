package ru.spshop.projects.ural_toys.find_articul.check;

public class CheckPrice {
    int procent;


    public boolean checkPrice(String cellPriceXlsDiscount, int priceCSV) {
        int priceXLX;
        if (isInteger(cellPriceXlsDiscount)) {
            priceXLX = Integer.parseInt(cellPriceXlsDiscount);
        } else return false;


        procent = (priceXLX * 100) / priceCSV;
        if (procent < 101) {
            return true;
        }
        return false;
    }


    private boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public String[] getErrorPrice(String goodsName) {
        return new String[]{goodsName, "цена у поставщика больше на " + procent + "%"};
    }

}
