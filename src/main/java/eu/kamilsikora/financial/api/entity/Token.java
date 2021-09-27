package eu.kamilsikora.financial.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Token {

    @Id
    @SequenceGenerator(
            name = "token_seq",
            sequenceName = "token_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_seq")
    private Long tokenId;
    private String token;
    @JoinColumn(name = "user_id")
    private User user;

    public Token(final User user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
    }
}
