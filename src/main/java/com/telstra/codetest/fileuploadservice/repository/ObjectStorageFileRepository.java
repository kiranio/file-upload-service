package com.telstra.codetest.fileuploadservice.repository;

import com.telstra.codetest.fileuploadservice.model.ObjectStorageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ObjectStorageFileRepository extends JpaRepository<ObjectStorageFile, String> {

    @Query("select c from ObjectStorageFile c where c.status like %?1")
    List<ObjectStorageFile> findByStatus(String chars);

    @Modifying
    @Query("update ObjectStorageFile c set c.status = :status where c.id = :id")
    int setStatusForObjectStorageFile(@Param("status") String status, @Param("id") String id);

}
