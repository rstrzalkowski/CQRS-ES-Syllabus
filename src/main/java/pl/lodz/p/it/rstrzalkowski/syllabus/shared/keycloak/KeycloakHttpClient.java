package pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.CreateUserDto;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.JwtResponse;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.KeycloakRoleDto;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.SetPasswordDto;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.UserInfo;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.UserRolesDto;

import java.util.List;

public interface KeycloakHttpClient {

    @PostExchange(value = "/realms/external/protocol/openid-connect/token", contentType = "application/x-www-form-urlencoded")
    JwtResponse login(
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam(value = "grant_type", defaultValue = "password") String grantType,
        @RequestParam(value = "client_id", defaultValue = "external-client") String clientId,
        @RequestParam(value = "scope", defaultValue = "openid") String scope
    );

    @PostExchange(value = "/realms/master/protocol/openid-connect/token", contentType = "application/x-www-form-urlencoded")
    JwtResponse loginAsAdmin(
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam(value = "grant_type", defaultValue = "password") String grantType,
        @RequestParam(value = "client_id", defaultValue = "security-admin-console") String clientId,
        @RequestParam(value = "scope", defaultValue = "openid") String scope
    );

    @GetExchange("/realms/external/protocol/openid-connect/userinfo")
    UserInfo getUserInfo(@RequestHeader(value = "Authorization", defaultValue = "") String authorization);

    @PostExchange("/admin/realms/external/users")
    ResponseEntity<Void> createUser(
        @RequestBody CreateUserDto dto,
        @RequestHeader(value = "Authorization", defaultValue = "") String authorization
    );

    @PutExchange("/admin/realms/external/users/{userId}/reset-password")
    void setPassword(
        @RequestBody SetPasswordDto dto,
        @PathVariable String userId,
        @RequestHeader(value = "Authorization", defaultValue = "") String authorization
    );

    @PostExchange("/admin/realms/external/users/{userId}/role-mappings/realm")
    void assignRole(
        @RequestBody List<KeycloakRoleDto> dto,
        @PathVariable String userId,
        @RequestHeader(value = "Authorization", defaultValue = "") String authorization
    );

    @DeleteExchange("/admin/realms/external/users/{userId}/role-mappings/realm")
    void unassignRole(
        @RequestBody List<KeycloakRoleDto> dto,
        @PathVariable String userId,
        @RequestHeader(value = "Authorization", defaultValue = "") String authorization
    );

    @GetExchange("/admin/realms/external/roles")
    List<KeycloakRoleDto> getAllRoles(
        @RequestHeader(value = "Authorization", defaultValue = "") String authorization
    );

    @GetExchange("/admin/realms/external/users/{userId}/role-mappings")
    UserRolesDto getUserRoles(
        @PathVariable String userId,
        @RequestHeader(value = "Authorization", defaultValue = "") String authorization
    );

    @DeleteExchange("/admin/realms/external/users/{userId}")
    void deleteUser(
        @PathVariable String userId,
        @RequestHeader(value = "Authorization", defaultValue = "") String authorization
    );
}
