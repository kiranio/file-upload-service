package com.telstra.codetest.fileuploadservice.service;

import com.telstra.codetest.fileuploadservice.exception.FileStorageException;
import com.telstra.codetest.fileuploadservice.model.ObjectStorageFile;
import com.telstra.codetest.fileuploadservice.model.TrasnferredObjectLogFile;
import com.telstra.codetest.fileuploadservice.repository.ObjectStorageFileRepository;
import com.telstra.codetest.fileuploadservice.repository.TransferredObjectFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class S3FileUploadService {

    @Autowired
    private ObjectStorageFileRepository dbFileRepository;

    @Autowired
    private TransferredObjectFileRepository transferredObjectFileRepository;

    @Value("${upload.s3bucket.url}")
    String serverUrl;

    @Transactional
    public boolean getPendingFiles() {

        try {

            List<ObjectStorageFile> dbPendingFiles = dbFileRepository.findByStatus("PENDING");

            for (ObjectStorageFile dbFile : dbPendingFiles) {

                if (dbFile.getId() != null) {

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                    MultiValueMap<String, Object> body
                            = new LinkedMultiValueMap<>();
                    body.add("file", getUserFileResource(dbFile));

                    HttpEntity<MultiValueMap<String, Object>> requestEntity
                            = new HttpEntity<>(body, headers);

                    RestTemplate restTemplate = new RestTemplate();

                    ResponseEntity<String> response = restTemplate
                            .postForEntity(serverUrl, requestEntity, String.class);

                    TrasnferredObjectLogFile trasnferredObjectLogFile = new TrasnferredObjectLogFile(dbFile.getFileName(), dbFile.getFileType(), "test_transfereed to");
                    transferredObjectFileRepository.save(trasnferredObjectLogFile);

                    dbFileRepository.setStatusForObjectStorageFile("UPLOADED", dbFile.getId());

                }
            }
            return true;
        } catch( Exception e){
            throw new FileStorageException("Exception while loading into S3 bucket "+e);
        }
    }

    public static Resource getUserFileResource(ObjectStorageFile dbFile) throws IOException {
        Path tempFile = null;
        if (dbFile.getId() != null) {
            String[] originalFileName = dbFile.getFileName().split("[.]");
            String filename = originalFileName[0];
            String extension = originalFileName[1];
            tempFile = Files.createTempFile(filename, "."+extension);
            Files.write(tempFile, dbFile.getFile());
        }
        return new FileSystemResource(tempFile.toFile());
    }
}
