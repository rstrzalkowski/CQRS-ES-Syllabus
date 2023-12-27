package pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.ResponseBadRequestException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.SyllabusCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user.UserAlreadyRegisteredCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.CreateUserDto;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.JwtResponse;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.KeycloakRoleDto;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.SetPasswordDto;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final KeycloakHttpClient keycloakHttpClient;

    @Value("${keycloak.master.admin.username}")
    private String adminUsername;

    @Value("${keycloak.master.admin.password}")
    private String adminPassword;

    public JwtResponse login(String email, String password, String realm) {
        if (realm.equals("master")) {
            return keycloakHttpClient.loginAsAdmin(
                email,
                password,
                null,
                null,
                null);
        }

        return keycloakHttpClient.login(
            email,
            password,
            null,
            null,
            null);
    }

    public String createUser(CreateUserDto dto) {
        String authorization = getKeycloakAdminAuthorization();

        try {
            HttpEntity<Void> response = keycloakHttpClient.createUser(dto, authorization);
            URI location = response.getHeaders().getLocation();

            if (location == null) {
                throw new UserAlreadyRegisteredCommandExecutionException();
            }

            List<String> parts = List.of(location.getPath().split("/"));
            String userId = parts.get(parts.size() - 1);

            keycloakHttpClient.setPassword(new SetPasswordDto(dto.getPassword()), userId, authorization);

            return userId;
        } catch (WebClientResponseException wcre) {
            throw new SyllabusCommandExecutionException(wcre.getStatusCode());
        }
    }

    public void assignRole(String userId, String role) {
        String authorization = getKeycloakAdminAuthorization();

        try {
            List<KeycloakRoleDto> availableRoles = keycloakHttpClient.getAllRoles(authorization);

            KeycloakRoleDto roleToBeAssigned = availableRoles.stream()
                .filter(keycloakRole -> Objects.equals(keycloakRole.getName().toUpperCase(), role.toUpperCase()))
                .findFirst()
                .orElseThrow(ResponseBadRequestException::new);

            keycloakHttpClient.assignRole(List.of(roleToBeAssigned), userId, authorization);
        } catch (WebClientResponseException wcre) {
            throw new SyllabusCommandExecutionException(wcre.getStatusCode());
        }
    }

    public void unassignRole(String userId, String role) {
        String authorization = getKeycloakAdminAuthorization();

        try {
            List<KeycloakRoleDto> userRoles = keycloakHttpClient.getUserRoles(userId, authorization).getRoles();

            KeycloakRoleDto roleToBeUnassigned = userRoles.stream()
                .filter(keycloakRole -> Objects.equals(keycloakRole.getName().toUpperCase(), role.toUpperCase()))
                .findFirst()
                .orElseThrow(ResponseBadRequestException::new);

            keycloakHttpClient.unassignRole(List.of(roleToBeUnassigned), userId, authorization);
        } catch (WebClientResponseException wcre) {
            throw new SyllabusCommandExecutionException(wcre.getStatusCode());
        }
    }

    public String getKeycloakAdminAuthorization() {
        JwtResponse jwtResponse = login(
            adminUsername,
            adminPassword,
            "master"
        );
        return "Bearer " + jwtResponse.getToken();
    }


    public void deleteUser(String id) {
        String authorization = getKeycloakAdminAuthorization();
        keycloakHttpClient.deleteUser(id, authorization);
    }
}
