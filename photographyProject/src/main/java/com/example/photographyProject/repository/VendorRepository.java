package com.example.photographyProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.photographyProject.model.Vendor;


@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
