package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.grade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArchiveGradeCommand {

    private UUID id;
}
