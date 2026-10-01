package com.devnexus.repository;

import com.devnexus.entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InviteRepository extends JpaRepository<Invite, String> {
    Optional<Invite> findByToken(String token);
}
