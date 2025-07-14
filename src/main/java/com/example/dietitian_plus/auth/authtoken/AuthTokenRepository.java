package com.example.dietitian_plus.auth.authtoken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    List<AuthToken> findAllByUser_UserIdAndIsExpiredFalseOrIsRevokedFalse(UUID userId);

    Optional<AuthToken> findByAuthToken(String authToken);

}
