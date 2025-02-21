package ru.spshop.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.spshop.projects.compare2files.controller.Compare2FilesImpl;

import java.io.*;

@Service
@RequiredArgsConstructor
public class SendFileService {
    private static final Logger log = LoggerFactory.getLogger(SendFileService.class);
    private final String contentTypeXLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private final String contentTypeXLS = "application/vnd.ms-excel";
    private int arrayLength;

    @Autowired
    Compare2FilesImpl compare2Files;


    public byte[] getExelByteArray(MultipartFile file1, MultipartFile file2) throws IOException {
        InputStream file1InputStream = file1.getInputStream();
        InputStream file2InputStream = file2.getInputStream();

        String filename1 = file1.getOriginalFilename();
        String filename2 = file2.getOriginalFilename();
        String con = file2.getContentType();
        log.info("ContentType " + con);
        //log.info("filename1: " + filename1);
        //log.info("filename2: " + filename2);

        Workbook workbook = compare2Files.getWorkbook(filename1, file1InputStream, filename2, file2InputStream);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            byte[] array = byteArrayOutputStream.toByteArray();
            arrayLength = array.length;
            return array;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ByteArrayResource getFileArray(MultipartFile file1, MultipartFile file2) throws IOException {
        byte[] array = getExelByteArray(file1, file2);
        // Создаем ресурс для отправки как поток данных
        return new ByteArrayResource(array);
    }


    public String getExtension() {
        return compare2Files.getExtension();
    }

    public String getResultName() {
        String extension = getExtension();
        return "result." + extension;
    }

    public String getContentType(){
        // Определите Content-Type в зависимости от формата файла
        String contentType = null;
        if (getExtension().equals("xlsx")) {
            contentType = contentTypeXLSX;
        } else if (getExtension().equals("xls")) {
            contentType = contentTypeXLS;
        }
        return contentType;
    }

    public int getArrayLength(){
        return arrayLength;
    }

}
