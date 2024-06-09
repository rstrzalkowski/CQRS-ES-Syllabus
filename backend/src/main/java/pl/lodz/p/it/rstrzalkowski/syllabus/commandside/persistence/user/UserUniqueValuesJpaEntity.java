package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.AbstractJpaEntity;

@Entity
@NoArgsConstructor
@Data
public class UserUniqueValuesJpaEntity extends AbstractJpaEntity {

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String personalId;

    public UserUniqueValuesJpaEntity(String email, String personalId) {
        this.email = email;
        this.personalId = personalId;
    }
}
