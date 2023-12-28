package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArchiveActivityCommand {

    private UUID id;
}
