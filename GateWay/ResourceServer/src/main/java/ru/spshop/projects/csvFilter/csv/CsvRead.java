package ru.spshop.projects.csvFilter.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CsvRead {

    public List<String[]> readCSV2(InputStream file1InputStream, String encoding) throws IOException {

        List<String[]> rows = new ArrayList<>();

        Charset charset = Charset.forName(encoding);
        BufferedReader reader = new BufferedReader(new InputStreamReader(file1InputStream, charset));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] divideStr = line.split(";");
            rows.add(divideStr);
        }
        return rows;
    }

}
