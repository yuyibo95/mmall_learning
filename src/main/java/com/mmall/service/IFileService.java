package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by yyb on 11/13/18.
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
