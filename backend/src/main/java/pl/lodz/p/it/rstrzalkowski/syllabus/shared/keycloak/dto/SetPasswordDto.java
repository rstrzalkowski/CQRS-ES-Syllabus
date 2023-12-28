package pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SetPasswordDto {
    private String type = "password";

    private String value;
    private boolean temporary = false;

    public SetPasswordDto(String password) {
        this.value = password;
    }
}
