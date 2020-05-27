package com.telstra.codetest.fileuploadservice.repository;

import com.telstra.codetest.fileuploadservice.model.TrasnferredObjectLogFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransferredObjectFileRepository extends JpaRepository<TrasnferredObjectLogFile, String> {

}
