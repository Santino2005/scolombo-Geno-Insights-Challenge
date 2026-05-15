package com.geno_insights.scolombo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoStorage photoStorage;

    public String upload(MultipartFile photo) {
        return photoStorage.uploadVisitorPhoto(photo);
    }
}
