package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity extends AbstractEntity {
    @Column(unique = true)
    private String email;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String personalId;

    private String description = "";

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private SchoolClassEntity schoolClass;

    private boolean archived = false;

    public UserEntity(UUID id, String email, String firstName, String lastName, String personalId, String description) {
        super(id);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalId = personalId;
        this.description = description;
    }
}
