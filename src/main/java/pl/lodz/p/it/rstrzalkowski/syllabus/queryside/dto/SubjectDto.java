package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectDto extends AbstractDto {
    private String name;
    private String abbreviation;

    public SubjectDto(UUID uuid, String name, String abbreviation) {
        super(uuid);
        this.name = name;
        this.abbreviation = abbreviation;
    }
}
