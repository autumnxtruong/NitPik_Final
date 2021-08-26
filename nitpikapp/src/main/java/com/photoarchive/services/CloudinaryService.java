package com.photoarchive.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.photoarchive.exceptions.UploadFileFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    private Cloudinary cloudinary;
    private Map<String, String> configMap = new HashMap<>();

    public CloudinaryService() {
        configMap.put("cloud_name", "dndt9lmqd");
        configMap.put("api_key", "674989767353521");
        configMap.put("api_secret", "Vi5BHfElTB5nJTi8a-7tjd76vSI");
        cloudinary = new Cloudinary(configMap);
    }

    public Map upload(MultipartFile  multipartFile) throws UploadFileFailureException {
        File fileToUpload = null;
        Map result = null;
        try {
            fileToUpload = convert(multipartFile);
            result = cloudinary.uploader().upload(fileToUpload, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new UploadFileFailureException();
        }finally {
            fileToUpload.delete();
        }
        return result;
    }

    public Map delete(String id){
        Map result = null;
        try {
            result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File fileTemplate =  new File(multipartFile.getOriginalFilename());
        FileOutputStream outputStream = null;
        outputStream = new FileOutputStream(fileTemplate);
        outputStream.write(multipartFile.getBytes());
        outputStream.close();
        return fileTemplate;
    }
}
