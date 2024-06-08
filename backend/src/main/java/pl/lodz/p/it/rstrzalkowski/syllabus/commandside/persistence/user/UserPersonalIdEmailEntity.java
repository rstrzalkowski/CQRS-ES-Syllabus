package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPersonalIdEmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String personalId;

    private UUID aggregateId;

    public UserPersonalIdEmailEntity(String email, String personalId) {
        this.email = email;
        this.personalId = personalId;
    }
}
