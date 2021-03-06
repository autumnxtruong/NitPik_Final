package com.photoarchive.repositories;

import com.photoarchive.domain.Photo;
import com.photoarchive.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
    @Query("select u from users u join token t on u.id=t.user.id where t.value = ?1")
    Optional<User> findByToken(String tokenValue);

    Set<User> findByPhotosShare (Photo photo);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
