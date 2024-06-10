package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.classes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractEntity;

import java.util.UUID;


@AllArgsConstructor
@Data
public class Teacher extends AbstractEntity {

    private UUID id;
}
