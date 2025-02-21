package ru.spshop.progects.csvFilter.csv;

import org.junit.jupiter.api.Test;
import ru.spshop.projects.csvFilter.csv.DeleteQuotes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest
public class DeleteQuotesTest {

    @Test
    public void deleteQuotesTest() {
        String[] array = {"\"Веточка \"\"Ель\"\" с ягодками в мешочке 17 см (SF-3539)\"", "677-236", "41,5", "5"};
        List<String[]> rows = new ArrayList<>();
        rows.add(array);

        new DeleteQuotes(rows);

        String result = rows.get(0)[0];
        String[] expect = {"Веточка \"Ель\" с ягодками в мешочке 17 см (SF-3539)", "677-236", "41,5", "5"};
        assertEquals(expect[0], result);
    }

}
