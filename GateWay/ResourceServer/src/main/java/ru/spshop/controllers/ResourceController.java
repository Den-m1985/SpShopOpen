package ru.spshop.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.spshop.service.SendFileService;

import java.io.*;

@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/resourceservice")
public class ResourceController {
    private static final Logger log = LoggerFactory.getLogger(ResourceController.class);
    @Autowired
    private SendFileService sendFileService;


    @PostMapping("/findSameName")
    @ResponseBody
    public ResponseEntity<Resource> mergeFiles(@RequestParam("file1") MultipartFile file1,
                                               @RequestParam("file2") MultipartFile file2) {
        try {
            String resultFileName = sendFileService.getResultName();
            String extension = sendFileService.getExtension();
            //log.info("ResourceController resultFileName: " + resultFileName);
            log.info("ResourceController extension: " + extension);
            // Создаем ресурс для отправки как поток данных
            ByteArrayResource resource =sendFileService.getFileArray(file1, file2);
            //log.info("ResourceController resource " + resource.exists());
            int arrayLength = sendFileService.getArrayLength();
            //String contentType = sendFileService.getContentType();
            String contentType = file2.getContentType();

            //ContentDisposition contentDisposition = ContentDisposition.attachment().filename("resultFileName").build();
            ContentDisposition contentDisposition = ContentDisposition.inline().filename("resultFileName").build();

            log.info("contentDisposition " + contentDisposition);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.parseMediaType(contentType));
            httpHeaders.setContentDisposition(contentDisposition);
            httpHeaders.setContentLength(arrayLength);

            return ResponseEntity.ok()
                    .headers(httpHeaders)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

}
