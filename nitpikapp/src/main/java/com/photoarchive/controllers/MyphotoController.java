package com.photoarchive.controllers;

import com.photoarchive.domain.Photo;
import com.photoarchive.domain.Tag;
import com.photoarchive.domain.User;
import com.photoarchive.exceptions.UploadFileFailureException;
import com.photoarchive.exceptions.UserAlreadyExistsException;
import com.photoarchive.models.PhotoWithFileDTO;
import com.photoarchive.models.UserDTO;
import com.photoarchive.models.UserInfo;
import com.photoarchive.repositories.PhotoRepository;
import com.photoarchive.repositories.TagRepository;
import com.photoarchive.repositories.UserRepository;
import com.photoarchive.services.SearchService;
import com.photoarchive.services.TagParsingService;
import com.photoarchive.services.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/Myphoto")
@SessionAttributes({"userInfo", "foundPhotos"})
public class MyphotoController {
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SearchService searchService;
    @Autowired
    private TagParsingService tagParsingService;
    @Autowired
    private UploadService uploadService;
    @PersistenceContext
    private EntityManager em;
    private boolean b;
    @Autowired
    public MyphotoController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public String showSearchPage(@AuthenticationPrincipal User user, Model model){
        UserInfo userInfo = new UserInfo(user);

        Set<Photo> foundPhotos = searchService.getPhotosByUser(user.getUsername());

        model.addAttribute("foundPhotos", foundPhotos);
        model.addAttribute("userInfo", userInfo);
        return "Myphoto";
    }
    @GetMapping("/shared")
    public String showSharedPhoto(@AuthenticationPrincipal User user, Model model){
        UserInfo userInfo = new UserInfo(user);

       User test=userRepository.findByUsername(user.getUsername()).get();

        Set<Photo> foundPhotos = photoRepository.findByUsersShare(test);

        model.addAttribute("foundPhotos", foundPhotos);


        model.addAttribute("userInfo", userInfo);
        return "share";
    }
    @RequestMapping(value = "/Edit-Tags", method=RequestMethod.POST)
    public String ChangeTag(@AuthenticationPrincipal User user,  @RequestParam("photoId") String photoId,@RequestParam("tagString") String tags, Model model) {
        final Set<Tag> tagsFromInput = tagParsingService.parseTagSet(tags);
        Photo photo=photoRepository.findById(Long.parseLong(photoId)).get();
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

        photoRepository.save(photo);

        Set<Photo> foundPhotos = searchService.getPhotosByUser(user.getUsername());

        model.addAttribute("foundPhotos", foundPhotos);
        System.out.println("tag"+tags);
        System.out.println("id"+photoId);
        return "Myphoto";
    }
    @RequestMapping(value = "/Remove", method=RequestMethod.POST)
    public String Rmove(@AuthenticationPrincipal User user,  @RequestParam("Idphoto") String photoId, Model model) {

        Photo photo=photoRepository.findById(Long.parseLong(photoId)).get();

   photoRepository.delete(photo);

        Set<Photo> foundPhotos = searchService.getPhotosByUser(user.getUsername());

        model.addAttribute("foundPhotos", foundPhotos);

        System.out.println("id"+photoId);
        return "Myphoto";
    }
    @RequestMapping(value = "/share", method=RequestMethod.POST)
    public String share( @AuthenticationPrincipal User user,  @RequestParam("share") String photoId,@RequestParam("usershare") String usershare, Model model) throws UploadFileFailureException {
        Photo photo=photoRepository.findById(Long.parseLong(photoId)).get();
        User userSharee =userRepository.findByEmail(usershare).get();
        Set <User> usershared=new HashSet<>();
        photo.getUsersShare().forEach(u->usershared.add(u));

        Set<Photo> shared=photoRepository.findByUsersShare(userSharee);
        shared.forEach(p->
        {
            if(p.getPhoto_id()==photo.getPhoto_id()){
                usershared.remove(userSharee);
                photo.setUsersShare(usershared);
                photoRepository.saveAndFlush(photo);
                b=true;
            }

        });
        if(b==false){
            usershared.add(userSharee);
            photo.setUsersShare(usershared);
            photoRepository.saveAndFlush(photo);

        }





        b=false;







        return "Myphoto";
    }



}
