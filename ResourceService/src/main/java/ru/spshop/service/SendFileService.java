package ru.spshop.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.spshop.projects.project_interfaces.MainIntarface;
import ru.spshop.projects.compare2files.Compare2Files;

import java.io.*;

@Service
@RequiredArgsConstructor
public class SendFileService {
    final Logger log = LoggerFactory.getLogger(SendFileService.class);
    private final String contentTypeXLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private final String contentTypeXLS = "application/vnd.ms-excel";
    private int arrayLength;

    @Autowired
    private Compare2Files compare2Files;

    @Autowired
    private InputRequestService inputRequestService;


    public byte[] getExelByteArray(MultipartFile file1, MultipartFile file2) throws IOException {
        InputStream file1InputStream = inputRequestService.getInputStreamFromRequest(file1);
        InputStream file2InputStream = inputRequestService.getInputStreamFromRequest(file2);

        String filename1 = inputRequestService.getOriginalFilename(file1);
        String filename2 = inputRequestService.getOriginalFilename(file2);
        //String con = file2.getContentType();
        //log.info("ContentType " + con);

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

    public byte[] getExelByteArray2(MultipartFile file1, MultipartFile file2, MainIntarface mainIntarface) throws IOException {
        InputStream file1InputStream = inputRequestService.getInputStreamFromRequest(file1);
        InputStream file2InputStream = inputRequestService.getInputStreamFromRequest(file2);

        String filename1 = inputRequestService.getOriginalFilename(file1);
        String filename2 = inputRequestService.getOriginalFilename(file2);

        Workbook workbook = mainIntarface.getWorkbook(filename1, file1InputStream, filename2, file2InputStream);

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

    public ByteArrayResource getFileArray2(MultipartFile file1, MultipartFile file2, MainIntarface mainIntarface) throws IOException {
        byte[] array = getExelByteArray2(file1, file2, mainIntarface);
        return new ByteArrayResource(array);
    }


    public String getExtension() {
        return compare2Files.getExtension();
    }

    public String getResultName() {
        String extension = getExtension();
        return "result." + extension;
    }

    public String getContentType() {
        // Определите Content-Type в зависимости от формата файла
        String contentType = null;
        if (getExtension().equals("xlsx")) {
            contentType = contentTypeXLSX;
        } else if (getExtension().equals("xls")) {
            contentType = contentTypeXLS;
        }
        return contentType;
    }

    public int getArrayLength() {
        return arrayLength;
    }

    public String transliterate(String fileName) {
        // Check if the string contains Cyrillic characters
        boolean isCyrillic = fileName.matches(".*[\\u0400-\\u04FF].*");

        if (isCyrillic) {
            char[] abcCyr = {' ', '_', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            String[] abcLat = {" ", "_", "a", "b", "v", "g", "d", "e", "e", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "ts", "ch", "sh", "sch", "", "i", "", "e", "ju", "ja", "A", "B", "V", "G", "D", "E", "E", "Zh", "Z", "I", "Y", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ts", "Ch", "Sh", "Sch", "", "I", "", "E", "Ju", "Ja", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < fileName.length(); i++) {
                for (int j = 0; j < abcCyr.length; j++) {
                    if (fileName.charAt(i) == abcCyr[j]) {
                        builder.append(abcLat[j]);
                        break;
                    }
                }
            }
            return builder.toString();
        }
        return fileName;
    }

}
