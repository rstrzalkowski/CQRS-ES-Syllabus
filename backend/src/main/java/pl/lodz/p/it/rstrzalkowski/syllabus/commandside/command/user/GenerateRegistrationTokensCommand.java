package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GenerateRegistrationTokensCommand {

    @NotNull
    @Positive
    private Integer amount;

    @NotNull
    private String role; //TODO check

    private Long schoolClassId;
}
