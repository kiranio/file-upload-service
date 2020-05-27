package com.telstra.codetest.fileuploadservice.controller;

import com.telstra.codetest.fileuploadservice.model.ObjectStorageFile;
import com.telstra.codetest.fileuploadservice.payload.UploadFileResponse;
import com.telstra.codetest.fileuploadservice.service.DBFileStorageService;
import com.telstra.codetest.fileuploadservice.service.S3FileUploadService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.telstra.codetest.fileuploadservice.exception.InvalidFileTypeException;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private DBFileStorageService dbFileStorageService;

    @Autowired
    private S3FileUploadService s3FileUploadService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/uploadFile")
    public ResponseEntity<UploadFileResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            ObjectStorageFile objectStorageFile = dbFileStorageService.storeFile(file);

            return ResponseEntity.ok().body(new UploadFileResponse(objectStorageFile.getFileName(),
                    file.getContentType(), file.getSize(),"Success",""));
        }catch(InvalidFileTypeException ife){
            return ResponseEntity.status(400).body(new UploadFileResponse("",
                    "", 0, "Failed", ife.getMessage()));
        }
    }

    //@GetMapping("/getPendingObjects")
    @Scheduled(fixedDelay = 2000)
    public Boolean getPendingObjects() {
        logger.info("Fixed Delay Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        return s3FileUploadService.getPendingFiles();
    }

}
