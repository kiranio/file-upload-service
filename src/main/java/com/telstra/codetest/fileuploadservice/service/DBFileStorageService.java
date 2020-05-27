package com.telstra.codetest.fileuploadservice.service;

import com.telstra.codetest.fileuploadservice.exception.FileStorageException;
import com.telstra.codetest.fileuploadservice.exception.InvalidFileTypeException;
import com.telstra.codetest.fileuploadservice.model.ObjectStorageFile;
import com.telstra.codetest.fileuploadservice.repository.ObjectStorageFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DBFileStorageService {

    @Autowired
    private ObjectStorageFileRepository dbFileRepository;

    @Value("${fileExtentions.types}")
    String fileExtentions;

    public ObjectStorageFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            int lastIndex = fileName.lastIndexOf('.');
            String currentFileExtn = fileName.substring(lastIndex);

            if (!fileExtentions.contains(currentFileExtn)) {
                throw new InvalidFileTypeException("Sorry! Invalid file type: " + fileName);
            }

            ObjectStorageFile dbFile = new ObjectStorageFile(fileName, file.getContentType(), file.getBytes(), "PENDING");

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

}
