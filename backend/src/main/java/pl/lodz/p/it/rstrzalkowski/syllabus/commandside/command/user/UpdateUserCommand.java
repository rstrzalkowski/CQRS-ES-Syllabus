package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserCommand {

    @Length(max = 1024)
    private String description;
}
