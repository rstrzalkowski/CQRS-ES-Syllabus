package pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserDto {
    private String email;
    private boolean emailVerified = true;
    private boolean enabled = true;
    private String firstName;
    private String lastName;
    private List<String> groups = new ArrayList<>();
    private List<String> requiredActions = new ArrayList<>();
    private String username;

    @JsonIgnore
    private String password;

    public CreateUserDto(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = email;
        this.password = password;
    }
}
