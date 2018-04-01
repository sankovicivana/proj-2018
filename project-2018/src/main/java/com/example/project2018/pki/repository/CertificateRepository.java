package com.example.project2018.pki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project2018.pki.model.SSCertificate;

@Repository
public interface CertificateRepository extends JpaRepository<SSCertificate, Long> {

	public List<SSCertificate> findByIsCA(boolean bool);
}
