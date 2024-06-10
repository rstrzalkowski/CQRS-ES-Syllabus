package pl.lodz.p.it.rstrzalkowski.syllabus.mock;

import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.KeycloakHttpClient;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.KeycloakService;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.CreateUserDto;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.JwtResponse;

import java.util.UUID;

public class MockKeycloakService extends KeycloakService {
    public MockKeycloakService(KeycloakHttpClient keycloakHttpClient) {
        super(keycloakHttpClient);
    }

    @Override
    public JwtResponse login(String email, String password) {
        return new JwtResponse("");
    }

    @Override
    public JwtResponse loginAsServiceClient() {
        return new JwtResponse("");
    }

    @Override
    public String createUser(CreateUserDto dto) {
        return UUID.randomUUID().toString();
    }

    @Override
    public void assignRole(UUID userId, String role) {

    }

    @Override
    public void unassignRole(UUID userId, String role) {

    }

    @Override
    public String getKeycloakServiceClientAuthorization() {
        return "";
    }
}
