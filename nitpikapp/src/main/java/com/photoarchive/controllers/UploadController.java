package com.photoarchive.controllers;

import com.photoarchive.domain.User;
import com.photoarchive.exceptions.UploadFileFailureException;
import com.photoarchive.models.PhotoWithFileDTO;
import com.photoarchive.models.PhotoWithUrlDTO;
import com.photoarchive.services.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/upload")
@SessionAttributes("userInfo")
public class UploadController {

    private UploadService uploadService;

    @ModelAttribute(name = "photoWithUrlDTO")
    private PhotoWithUrlDTO photoWithUrlDTO(){
        return new PhotoWithUrlDTO();
    }

    @ModelAttribute(name = "photoWithFileDTO")
    private PhotoWithFileDTO photoWithFileDTO(){
        return new PhotoWithFileDTO();
    }

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping
    public String show(){
        return "upload";
    }

    @PostMapping("/photo-with-url")
    public String processPostWithUrl(@Valid PhotoWithUrlDTO photoWithUrlDTO, Errors errors){
        if (errors.hasErrors()){
            return "upload";
        }
//        uploadService.addPhoto(photoWithUrlDTO);
        return "upload";
    }

    @PostMapping("/photo-with-file")
    public String processPostWithFile(PhotoWithFileDTO photoWithFileDTO, Errors errors, Model model){
        if (errors.hasErrors()){
            return "upload";
        }
        try {
            uploadService.addPhoto(photoWithFileDTO);
        } catch (UploadFileFailureException e) {
            log.warn(e.getMessage());
            model.addAttribute("message", e.getMessage());
        }
        return "upload";
    }
}
