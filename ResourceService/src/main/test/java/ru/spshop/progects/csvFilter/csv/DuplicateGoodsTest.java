package ru.spshop.progects.csvFilter.csv;

import org.junit.jupiter.api.Test;
import ru.spshop.projects.projects_services.csvFilter.csv.DuplicateGoods;
import ru.spshop.projects.projects_services.csvFilter.csv.StructureCSV;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DuplicateGoodsTest {

    @Test
    public void duplicateGoodsTest(){
        List<StructureCSV> uniqueValues = new ArrayList<>();
        List<StructureCSV> duplicateNames = new ArrayList<>();
        duplicateNames.add(new StructureCSV("Веточка \"Ель\" с ягодками в мешочке 17 см (SF-3539)", "677-236", 123, 20));
        duplicateNames.add(new StructureCSV("Веточка \"Ель\" с ягодками в мешочке 17 см (SF-3539)", "677-236", 123, 20));
        duplicateNames.add(new StructureCSV("Веточка \"Ель\" с ягодками в мешочке 17 см (SF-3539)", "677-236", 123, 20));
        duplicateNames.add(new StructureCSV("ex1", "677-236", 123, 20));
        duplicateNames.add(new StructureCSV("ex1", "677-236", 123, 20));
        duplicateNames.add(new StructureCSV("ex", "6", 123, 20));
        duplicateNames.add(new StructureCSV("ex", "6", 123, 20));

        List<StructureCSV> resolveDuplicatedNames = new DuplicateGoods().duplicateGoods(duplicateNames);
        uniqueValues.addAll(resolveDuplicatedNames);
        int result = uniqueValues.size();

        assertEquals(3, result);
    }

}
