package com.telstra.codetest.fileuploadservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transferred_object_log")
public class TrasnferredObjectLogFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;

    private String fileType;


    @Temporal(TemporalType.DATE)
    private Date transferredAt;

    private String transferredTo;

    public TrasnferredObjectLogFile() {

    }

    public TrasnferredObjectLogFile(String fileName, String fileType, String transferredTo) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.transferredTo = transferredTo;
    }

    @PrePersist
    void transferredAt() {
        this.transferredAt = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getTransferredAt() {
        return transferredAt;
    }

    public void setTransferredAt(Date transferredAt) {
        this.transferredAt = transferredAt;
    }

    public String getTransferredTo() {
        return transferredTo;
    }

    public void setTransferredTo(String transferredTo) {
        this.transferredTo = transferredTo;
    }
}
