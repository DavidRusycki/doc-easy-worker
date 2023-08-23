package com.doceasy.worker.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doceasy.worker.entity.SubDocument;

@Repository
public interface SubDocumentRepository extends JpaRepository<SubDocument, UUID>{

}
