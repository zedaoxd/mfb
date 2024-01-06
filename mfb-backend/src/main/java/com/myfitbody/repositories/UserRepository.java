package com.myfitbody.repositories;

import com.myfitbody.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsUserByEmailIgnoreCase(String email);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByTokenVerifyEmail(UUID tokenVerifyEmail);

    Page<User> findAllByFirstNameIgnoreCaseStartingWithOrLastNameIgnoreCaseStartingWith(Pageable pageable, String firstName, String lastName);
}
