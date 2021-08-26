package com.photoarchive.models;

import com.photoarchive.annotations.PhotoFile;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class PhotoWithFileDTO {

    @PhotoFile
    private MultipartFile [] multipartFile;
    @NotBlank(message = "Please set minimum 1 tag")
    private String tagsAsString;

    public MultipartFile [] getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile[] multipartFile) {
        this.multipartFile = multipartFile;
    }

    public String getTagsAsString() {
        return tagsAsString;
    }

    public void setTagsAsString(String tagsAsString) {
        this.tagsAsString = tagsAsString;
    }
}
