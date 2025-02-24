package ru.spshop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class InputRequestService {

    /**
     * @return xlsx
     */
    public String separateString(String pathFile) {
        if (pathFile != null) {
            return pathFile.substring(pathFile.lastIndexOf('.') + 1);
        }
        return null;
    }

    /**
     * @return Счет_ИП_Кашлева
     */
    public String getFileNameWithoutExtension(MultipartFile file) throws IOException {
        if (file != null) {
            String originName = getOriginalFilename(file);
            String temp = originName.split("\\.")[0];

            return temp.replace(" ", "_");
        }
        return null;
    }

    public InputStream getInputStreamFromRequest(MultipartFile file) throws IOException {
        return file.getInputStream();
    }

    /**
     * @return Счет ИП Кашлева.xlsx
     */
    public String getOriginalFilename(MultipartFile file) throws IOException {
        return file.getOriginalFilename();
    }

    /**
     * @return application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
     */
    public String getContentType(MultipartFile file) throws IOException {
        return file.getContentType();
    }

}
