package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.realisations.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractEntity;

import java.util.UUID;


@AllArgsConstructor
@Data
public class SchoolClass extends AbstractEntity {

    private UUID id;
}
