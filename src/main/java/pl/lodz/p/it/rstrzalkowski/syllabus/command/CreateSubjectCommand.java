package pl.lodz.p.it.rstrzalkowski.syllabus.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubjectCommand {
    private String name;
    private String abbreviation;
}
