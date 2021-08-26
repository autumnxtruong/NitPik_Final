package com.photoarchive.repositories;

import com.photoarchive.domain.Photo;
import com.photoarchive.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Set<Tag> findByPhotos(Photo photo);
}
