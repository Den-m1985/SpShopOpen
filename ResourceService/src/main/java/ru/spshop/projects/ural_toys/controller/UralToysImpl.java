package ru.spshop.projects.ural_toys.controller;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.spshop.projects.compare2files.controller.ChoiceDirection;
import ru.spshop.projects.projects_services.excel.newExel.AddNoUseName;
import ru.spshop.projects.projects_services.csvFilter.CsvFilter;
import ru.spshop.projects.projects_services.csvFilter.csv.StructureCSV;
import ru.spshop.projects.ural_toys.UralToys;
import ru.spshop.projects.ural_toys.find_articul.IterateXlsxImpl;
import ru.spshop.service.InputRequestService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class UralToysImpl implements UralToys {
    private final String extensionCSV = "csv";
    private final String extensionOldExel = "xls";
    private final String extensionExel = "xlsx";
    private int numberSheet = 0;  // номер страницы в файле.
    private String extension;
    private List<StructureCSV> data;
    private final Logger log = LoggerFactory.getLogger(UralToysImpl.class);
    //@Autowired
    private CsvFilter csvFilter;
    //@Autowired
    private InputRequestService inputService;

    @Override
    public Workbook getWorkbook(String filename1, InputStream file1InputStream,
                                String filename2, InputStream file2InputStream) throws IOException {
        String extensionFile1 = inputService.separateString(filename1);
        String extensionFile2 = inputService.separateString(filename2);
        //log.info("UralToysImpl/extensionFile2 " + extensionFile2);

        if (extensionFile1.equals(extensionCSV)) {
            data = csvFilter.csvFilter(file1InputStream);
        }
        log.info("StructureCSV data.size() " + data.size());

        if (extensionFile2.equals(extensionOldExel) || extensionFile2.equals(extensionExel)) {
            ChoiceDirection choiceDirection = new ChoiceDirection(filename2);  // выбираем между старым и новым Exel
            Workbook workbook = choiceDirection.choiceOldNewExel(file2InputStream);
            extension = choiceDirection.getExtension();

            workbook = new IterateXlsxImpl(data, workbook).iterateEXEL();

            new AddNoUseName(workbook, data, numberSheet, filename1, filename2);

            return workbook;
        } else {
            throw new RuntimeException("Controller UralToysImpl error");
        }
    }

    @Override
    public String getExtension() {
        return extension;
    }

    public UralToysImpl(CsvFilter csvFilter, InputRequestService inputService) {
        this.csvFilter = csvFilter;
        this.inputService = inputService;
    }

}
