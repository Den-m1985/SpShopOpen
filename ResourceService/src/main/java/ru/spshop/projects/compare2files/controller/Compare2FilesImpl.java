package ru.spshop.projects.compare2files.controller;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spshop.projects.projects_services.csvFilter.CsvFilter;
import ru.spshop.projects.projects_services.csvFilter.csv.StructureCSV;
import ru.spshop.projects.compare2files.Compare2Files;
import ru.spshop.projects.projects_services.excel.newExel.AddNoUseName;
import ru.spshop.projects.compare2files.modifyPrice.ModifyPrice;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class Compare2FilesImpl implements Compare2Files {
    private String extensionCSV = "csv";
    private String extensionOldExel = "xls";
    private String extensionExel = "xlsx";
    private String extension;
    private int numberSheet = 0;  // номер страницы в файле.
    private List<StructureCSV> data;
    //private final Logger log = LoggerFactory.getLogger(Compare2FilesImpl.class);
    //@Autowired
    private CsvFilter csvFilter;


    @Override
    public Workbook getWorkbook(String filename1, InputStream file1InputStream,
                                String filename2, InputStream file2InputStream) throws IOException {

        String extensionFile1 = separateString(filename1);
        String extensionFile2 = separateString(filename2);

        if (extensionFile1.equals(extensionCSV)) {
            //data = new CsvFilter().csvFilter(file1InputStream);  // читаем csv
            data = csvFilter.csvFilter(file1InputStream);
        }

        if (extensionFile2.equals(extensionOldExel) || extensionFile2.equals(extensionExel)) {
            ChoiceDirection choiceDirection = new ChoiceDirection(filename2);  // выбираем между старым и новым Exel
            Workbook workbook = choiceDirection.choiceOldNewExel(file2InputStream);
            extension = choiceDirection.getExtension();

            workbook = new ModifyPrice(data, workbook, numberSheet).findCellEXEL();
            //workbook = addCell.findCellEXEL();   // сравниваем
            //List<StructureCSV> noFindData = addCell.getNotUseArticle();  // лишние строки с данными

            new AddNoUseName(workbook, data, numberSheet, filename1, filename2);

            return workbook;
        } else {
            throw new RuntimeException("Ошибка в контроллере");
        }
    }

    private String separateString(String pathFile) {
        if (pathFile != null) {
            return pathFile.substring(pathFile.lastIndexOf('.') + 1);
        }
        return null;
    }


    @Override
    public String getExtension() {
        return extension;
    }

    public Compare2FilesImpl(CsvFilter csvFilter) {
        this.csvFilter = csvFilter;
        data = new ArrayList<>();
    }

}
