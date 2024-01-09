package com.myfitbody.repositories;

import com.myfitbody.domain.email.Email;
import com.myfitbody.domain.email.EmailBodyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmailBodyTypeRepository extends JpaRepository<Email, UUID> {

    Optional<Email> findByEmailBodyType(EmailBodyType emailBodyType);
}
