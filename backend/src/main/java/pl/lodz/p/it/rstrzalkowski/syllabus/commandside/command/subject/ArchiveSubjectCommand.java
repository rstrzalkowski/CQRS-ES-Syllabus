package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArchiveSubjectCommand {

    private UUID id;
}