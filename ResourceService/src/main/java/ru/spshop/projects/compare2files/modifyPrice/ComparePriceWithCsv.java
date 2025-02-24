package ru.spshop.projects.compare2files.modifyPrice;


import ru.spshop.projects.projects_services.csvFilter.csv.StructureCSV;
import ru.spshop.projects.projects_services.report_data.ColorExcelCell;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ComparePriceWithCsv {
    private List<StructureCSV> data;
    private final String nameEXL;
    private final int agreePercent = 40;
     private ColorExcelCell colorExcelCell;

    public ComparePriceWithCsv(List<StructureCSV> data, String nameEXL) {
        this.data = data;
        this.nameEXL = nameEXL;

    }


    public ReportData compareWithCSV() {
        String[] tokens2 = divideString(nameEXL);

        for (StructureCSV csv : data) {
            String nameCSV = csv.getName();
            String[] tokens1 = divideString(nameCSV);
            int matchPercent = calculateMatchPercentage(tokens1, tokens2);
            if (matchPercent == 100) {
                ReportData reportData = createReportArray(matchPercent, csv);
                data.remove(csv);
                return reportData;
            }
        }
        for (StructureCSV csv : data) {
            String nameCSV = csv.getName();
            String[] tokens1 = divideString(nameCSV);
            int matchPercent = calculateMatchPercentage(tokens1, tokens2);
            if (matchPercent >= agreePercent) {
                ReportData reportData = createReportArray(matchPercent, csv);
                data.remove(csv);
                return reportData;
            }
        }
        // переделать возвращаемый null
        return null;
    }

    private ReportData createReportArray(int matchPercent, StructureCSV csv) {
        String strMatchPercent = String.valueOf(matchPercent);
        String nameCSV = csv.getName();
        String priceCSV = String.valueOf(csv.getPrice());
        String itemCSV = String.valueOf(csv.getItem());
        return new ReportData(strMatchPercent,priceCSV, itemCSV, nameCSV);
    }

    private String[] divideString(String input) {
        String str = input.trim();
        String[] strSplit = str.split(" ");
        for (int i = 0; i < strSplit.length; i++) {
            strSplit[i] = deleteQuote(strSplit[i]);
        }
        return strSplit;
    }


    private int calculateMatchPercentage(String[] tokens1, String[] tokens2) {
        Set<String> set1 = new HashSet<>(Arrays.asList(tokens1));
        Set<String> set2 = new HashSet<>(Arrays.asList(tokens2));

        /*
         * is used to find the intersection of two sets (set1 and set2).
         * It creates a new HashSet called intersection and initializes
         * it with the elements of set1. Then, it uses the retainAll method
         * to retain only the elements that are common between intersection and set2.
         * After this operation, intersection contains the elements that are
         * present in both set1 and set2.
         *
         * In the context of the previous code I provided, this is used to find
         * the common substrings between the two input strings str1 and str2.
         * The Jaccard similarity coefficient is calculated by dividing the size
         * of the intersection of the sets by the size of the union of the sets,
         * which gives you the proportion of common elements to the total unique
         * elements in both strings. This is a common way to measure
         * the similarity between sets of elements.
         */
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        /*
         *is used to find the union of two sets (set1 and set2).
         * It creates a new HashSet called union and initializes
         * it with the elements of set1. Then, it uses the addAll
         * method to add all the elements from set2 to union.
         * After this operation, union contains all the unique
         * elements from both set1 and set2.
         */
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        double jaccardSimilarity = (double) intersection.size() / union.size();
        return (int) (jaccardSimilarity * 100.0);
    }

    private String deleteQuote(String str) {
        return str.replace("\"", "");
    }

}
