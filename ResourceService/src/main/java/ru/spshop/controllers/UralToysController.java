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
import ru.spshop.projects.ural_toys.controller.UralToysImpl;
import ru.spshop.service.InputRequestService;
import ru.spshop.service.SendFileService;

import java.io.IOException;

@RestController
@RequestMapping("/uraltoys")
public class UralToysController {
    private final Logger log = LoggerFactory.getLogger(UralToysController.class);
    @Autowired
    private SendFileService sendFileService;
    @Autowired
    private InputRequestService inputRequestService;
    @Autowired
    UralToysImpl uralToys;


    @PostMapping()
    @ResponseBody
    public ResponseEntity<Resource> mergeFiles(@RequestParam("file1") MultipartFile file1,
                                               @RequestParam("file2") MultipartFile file2) {
        try {
            ByteArrayResource resource = sendFileService.getFileArray2(file1, file2, uralToys);
            int arrayLength = sendFileService.getArrayLength();
            String contentType = inputRequestService.getContentType(file2);

            String fileName = inputRequestService.getFileNameWithoutExtension(file2);
            fileName = sendFileService.transliterate(fileName);
            log.info("uralToys fileName " + fileName);

            ContentDisposition contentDisposition = ContentDisposition.inline().filename(fileName).build();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.parseMediaType(contentType));
            httpHeaders.setContentDisposition(contentDisposition);
            httpHeaders.setContentLength(arrayLength);
            httpHeaders.set("Access-Control-Expose-Headers", "Content-Disposition");

            return ResponseEntity.ok()
                    .headers(httpHeaders)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }


}
