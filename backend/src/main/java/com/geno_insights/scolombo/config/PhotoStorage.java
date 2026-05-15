package com.geno_insights.scolombo.config;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoStorage {
    String uploadVisitorPhoto(MultipartFile photo);
}