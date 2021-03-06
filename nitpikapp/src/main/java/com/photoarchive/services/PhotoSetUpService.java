package com.photoarchive.services;

import com.photoarchive.domain.Photo;
import com.photoarchive.domain.Tag;
import com.photoarchive.domain.User;
import com.photoarchive.exceptions.UploadFileFailureException;
import com.photoarchive.repositories.TagRepository;
import com.photoarchive.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class PhotoSetUpService {
    @Autowired
    private UserRepository userRepository;
    private TagParsingService tagParsingService;
    private TagRepository tagRepository;
    private CloudinaryService cloudinaryService;

    @Autowired
    public PhotoSetUpService(TagParsingService tagParsingService, TagRepository tagRepository, CloudinaryService cloudinaryService) {
        this.tagParsingService = tagParsingService;
        this.tagRepository = tagRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public Photo setCorrectTags(Photo photo, String tags) {
        final Set<Tag> tagsFromInput = tagParsingService.parseTagSet(tags);

        Set<Tag> tagsToAdd = new HashSet<>();

        tagsFromInput.forEach(tag -> {
            Optional<Tag> one = tagRepository.findOne(Example.of(tag));
            if (one.isPresent()) {
                tagsToAdd.add(one.get());
            } else {
                tagsToAdd.add(tag);
            }
        });
        photo.setTags(tagsToAdd);
        return photo;
    }
    public Photo setCorrectUser(Photo photo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("hel"+auth.getName());
        User user=userRepository.findByUsername(auth.getName()).get();
        System.out.println("amine"+user.getUsername());


        return photo;
    }

    public Photo setCorrectUrl(Photo photo, MultipartFile multipartFile) throws UploadFileFailureException {
        Map result = cloudinaryService.upload(multipartFile);
        String urlInCloud = (String) result.get("url");
        log.info("Photo uploaded and available at "+urlInCloud);
        photo.setUrl(urlInCloud);
        return photo;
    }

}
