package ru.spshop.progects.csvFilter.csv;

import org.junit.jupiter.api.Test;
import ru.spshop.projects.projects_services.csvFilter.csv.OnlyGoods;
import ru.spshop.projects.projects_services.csvFilter.csv.StructureCSV;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OnlyGoodsTest {

    @Test
    public void onlyGoodsTest() {

        String[] array1 = {"\"Веточка \"\"Ель\"\" с ягодками в мешочке 17 см (SF-3539)\"", "677-236", "41,5", "5"};
        String[] array2 = {"ex", "123", "123.2", "20"};
        String[] array3 = {"", "", "", ""};
        List<String[]> rows = new ArrayList<>();
        rows.add(array1);
        rows.add(array2);
        rows.add(array3);

        List<StructureCSV> dataWithItem = new OnlyGoods().onlyGoods(rows);

        int result = dataWithItem.size();

        assertEquals(2, result);
    }

}
