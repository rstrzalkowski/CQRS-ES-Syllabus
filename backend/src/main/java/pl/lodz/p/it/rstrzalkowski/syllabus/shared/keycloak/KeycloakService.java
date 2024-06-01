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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final KeycloakHttpClient keycloakHttpClient;

    @Value("${keycloak.external.service-client.secret}")
    private String secret;


    public JwtResponse login(String email, String password) {
        try {
            return keycloakHttpClient.login(
                    email,
                    password,
                    null,
                    null,
                    null);
        } catch (WebClientResponseException wcre) {
            throw new SyllabusCommandExecutionException(wcre.getStatusCode());
        }
    }

    public JwtResponse loginAsServiceClient() {
        try {
            return keycloakHttpClient.loginAsServiceClient(null, null, secret);
        } catch (WebClientResponseException wcre) {
            throw new SyllabusCommandExecutionException(wcre.getStatusCode());
        }
    }

    public String createUser(CreateUserDto dto) {
        String authorization = getKeycloakServiceClientAuthorization();

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

    public void assignRole(UUID userId, String role) {
        String authorization = getKeycloakServiceClientAuthorization();

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

    public void unassignRole(UUID userId, String role) {
        String authorization = getKeycloakServiceClientAuthorization();

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

    public String getKeycloakServiceClientAuthorization() {
        JwtResponse jwtResponse = loginAsServiceClient();
        return "Bearer " + jwtResponse.getToken();
    }

    public void changePassword(String oldPassword, String newPassword) {
        //TODO
    }
}
