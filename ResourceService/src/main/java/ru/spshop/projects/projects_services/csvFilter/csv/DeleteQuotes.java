package ru.spshop.projects.projects_services.csvFilter.csv;

import java.util.List;

public class DeleteQuotes {
    public DeleteQuotes(List<String[]> rows) {
        for (String[] row : rows) {
            for (int j = 0; j < row.length; j++) {
                row[j] = deleteQuotes(row[j]);
            }
        }
    }


    private String deleteQuotes(String str) {
        if (str.contains("\"\"")) {
            str = str.replace("\"\"", "\"");
        }
        if (str.startsWith("\"") && str.endsWith("\"")) {
            return str.substring(1, str.length() - 1);

        }
        return str;
    }

}
