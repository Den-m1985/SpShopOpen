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
        List<StructureCSV> inputData = List.of(
                new StructureCSV("FORZA Кольцо-подставка для смартфона, 6,5x3x0,1см, фэйс", "(12-470-011)", 17, 1),
                new StructureCSV("FORZA Кольцо-подставка для смартфона, 6,5x3x0,1см, фэйс", "(12-470-011)", 17, 1),
                new StructureCSV("FORZA Кольцо-подставка для смартфона, 6,5x3x0,1см, фэйс", "(12-470-011)", 17, 1),
                new StructureCSV("INBLOOM  Заборчик декоративный Цветок 10шт, 15x33 см", "(44-172-003)", 87, 4),
                new StructureCSV("INBLOOM  Заборчик декоративный Цветок 10шт, 15x33 см", "(44-172-003)", 87, 4)
        );
        List<StructureCSV> resolveDuplicatedNames = new DuplicateGoods().findDuplicateGoods(inputData);
        List<StructureCSV> expected = List.of(
                new StructureCSV("FORZA Кольцо-подставка для смартфона, 6,5x3x0,1см, фэйс", "(12-470-011)", 17, 3),
                new StructureCSV("INBLOOM  Заборчик декоративный Цветок 10шт, 15x33 см", "(44-172-003)", 87, 8)
        );
        assertEquals(resolveDuplicatedNames.size(), expected.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(resolveDuplicatedNames.get(i), expected.get(i));
        }
    }

}
