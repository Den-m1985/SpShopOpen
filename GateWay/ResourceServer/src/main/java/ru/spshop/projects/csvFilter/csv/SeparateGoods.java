package ru.spshop.projects.csvFilter.csv;

import java.util.ArrayList;
import java.util.List;

public class SeparateGoods {
    private final int lengthLine = 4;
    
    public List<String[]> separateArray(List<String[]> rows){
        List<String[]> newArray = new ArrayList<>();
        for (String[] row : rows) {
            if (row.length == lengthLine) {
                newArray.add(row);
            }
        }
        return newArray;
    }
    
}
