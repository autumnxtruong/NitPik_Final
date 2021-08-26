package com.photoarchive.services;

import com.photoarchive.domain.Photo;
import com.photoarchive.domain.User;
import com.photoarchive.exceptions.UploadFileFailureException;
import com.photoarchive.models.PhotoWithFileDTO;
import com.photoarchive.models.PhotoWithUrlDTO;
import com.photoarchive.repositories.PhotoRepository;
import com.photoarchive.repositories.TagRepository;
import com.photoarchive.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UploadService {
    @Autowired
    private UserRepository userRepository;
    private PhotoRepository photoRepository;
    private TagRepository tagRepository;
    private PhotoSetUpService photoSetUpService;

    @Autowired
    public UploadService(PhotoRepository photoRepository,
                         TagRepository tagRepository,
                         PhotoSetUpService photoSetUpService) {
        this.photoRepository = photoRepository;
        this.tagRepository = tagRepository;
        this.photoSetUpService = photoSetUpService;
    }

//    public Photo addPhoto(final PhotoWithUrlDTO photoWithUrlDTO) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        User user=userRepository.findByUsername(auth.getName()).get();
//        Photo photo = new Photo(user);
//
//        photo.setUrl(photoWithUrlDTO.getUrl());
//        photoSetUpService.setCorrectTags(photo, photoWithUrlDTO.getTagsAsString());
//
//        return saveToDB(photo);
//    }

    public Set <Photo> addPhoto(final PhotoWithFileDTO photoWithFileDTO) throws UploadFileFailureException {
        int i=0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userr=userRepository.findByUsername(auth.getName()).get();
       System.out.println(photoWithFileDTO.getMultipartFile().length);
        Set <Photo> photos =new HashSet<>();
        for (int ii=0;ii<photoWithFileDTO.getMultipartFile().length;ii++){
            Photo p=new Photo(userr);
            photos.add(p);
        }



        System.out.println("t"+photos.size());
        System.out.println("m"+photoWithFileDTO.getMultipartFile().length);
          for(Photo p :photos){

              photoSetUpService.setCorrectUrl(p, photoWithFileDTO.getMultipartFile()[i]);
              photoSetUpService.setCorrectTags(p, photoWithFileDTO.getTagsAsString());
              saveToDB(p);
              i++;

         };



        return photos;
    }
//    public Photo adddPhoto( Photo photos,Set<User> usersh) throws UploadFileFailureException {
//
//
//        Photo photo = new Photo( usersh);
//
//        photo.setUrl(photos.getUrl());
//        photo.setTags(photos.getTags());
//
//
//
//        return saveToDB(photo);
//    }


    @Transactional
    public Photo saveToDB(Photo photo){
        tagRepository.saveAll(photo.getTags());
        tagRepository.flush();
        log.info("Photo added to database: " + photo);
        return photoRepository.saveAndFlush(photo);
    }
}
