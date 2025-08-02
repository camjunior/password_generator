package com.example.password_generator.repository;

import com.example.password_generator.model.PasswordRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRecordRepository extends JpaRepository<PasswordRecord, Long> {
}