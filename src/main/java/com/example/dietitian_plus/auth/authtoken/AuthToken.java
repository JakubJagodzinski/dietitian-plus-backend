package com.example.dietitian_plus.auth.authtoken;

import com.example.dietitian_plus.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "auth_tokens")
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_token_id")
    private Long authTokenId;

    @Column(name = "auth_token", unique = true, nullable = false)
    private String authToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_token_type", nullable = false)
    private AuthTokenType authTokenType;

    @Column(name = "is_revoked", nullable = false)
    @Builder.Default
    private Boolean isRevoked = false;

    @Column(name = "is_expired", nullable = false)
    @Builder.Default
    private Boolean isExpired = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_auth_tokens_user_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

}
