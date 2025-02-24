package ru.spshop.projects.ural_toys.find_articul.check;

public class CheckArticular {

    public boolean checkArticular(String articularEXL, String articularCSV) {
        articularCSV = articularCSV.trim();
        //log.info("articularCSV " + articularCSV);
        if (articularCSV.contains(" ")) {
            String[] divided = articularCSV.split(" ");
            String csvCode = divided[1];
            //if (divided[1].contains("("))
            //csvCode = csvCode.substring(1, csvCode.length() - 1);  // отсекаем скобки
            if (articularEXL.equals(divided[0])) {
                return true;
            }
            String up = divided[0].toUpperCase();
            if (up.equals(articularEXL)) {
                return true;
            }
            String down = divided[0].toLowerCase();
            if (down.equals(articularEXL)) {
                return true;
            }
        } else if (articularEXL.equals(articularCSV)) {
            return true;
        }

        return false;
    }

}
