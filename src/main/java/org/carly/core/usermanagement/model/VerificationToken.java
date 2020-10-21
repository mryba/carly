package org.carly.core.usermanagement.model;

import lombok.Getter;
import org.carly.core.usermanagement.model.User;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "tokens")
@Getter
public class VerificationToken {
    private String token;
    private User user;
    private LocalDateTime expiryDate;

    public VerificationToken(User user, String token, LocalDateTime expiryDate) {
        this.user = user;
        this.token = token;
        this.expiryDate=expiryDate;
    }
}