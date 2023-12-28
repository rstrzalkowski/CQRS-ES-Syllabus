package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPersonalIdEntity {

    @Id
    private String email;

    @Column(unique = true)
    private String personalId;
}
