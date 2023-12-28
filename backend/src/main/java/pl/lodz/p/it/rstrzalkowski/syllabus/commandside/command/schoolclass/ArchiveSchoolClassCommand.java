package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.schoolclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArchiveSchoolClassCommand {

    private UUID id;
}
