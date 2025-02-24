package ru.spshop.projects.projects_services.csvFilter.csv;

import org.springframework.stereotype.Service;
import ru.spshop.projects.projects_services.csvFilter.CsvFilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class CsvFilterImpl implements CsvFilter {
    private final String ENCODING = "windows-1251";
    private List<String[]> error;


    @Override
    public List<StructureCSV> csvFilter(InputStream fileInputStream) throws IOException {

        List<String[]> rowsArray = new CsvRead().readCSV2(fileInputStream, ENCODING);

        List<String[]> rows = new SeparateGoods().separateArray(rowsArray);

        new DeleteQuotes(rows);

        // В этом блоке оставляем только те колонки где есть цена и кол-во
        OnlyGoods onlyGoods = new OnlyGoods();
        List<StructureCSV> dataWithItem = onlyGoods.onlyGoods(rows);
        error = onlyGoods.reportCSV();

        // этот блок возвращает иникальные элементы
        UniqueGoods uniqueGoods = new UniqueGoods();
        List<StructureCSV> uniqueValues = uniqueGoods.uniqueGoods(dataWithItem);
        List<StructureCSV> duplicateNames = uniqueGoods.getDuplicateNames();

        // этот блок работатет с повторяющимися именами.
        List<StructureCSV> resolveDuplicatedNames = new DuplicateGoods().duplicateGoods(duplicateNames);
        uniqueValues.addAll(resolveDuplicatedNames);

        return uniqueValues;
    }

    @Override
    public List<String[]> getError() {
        return error;
    }

}
