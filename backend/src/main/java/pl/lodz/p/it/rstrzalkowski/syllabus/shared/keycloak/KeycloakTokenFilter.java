package pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.UserInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KeycloakTokenFilter extends OncePerRequestFilter {

    private final KeycloakTokenValidator keycloakTokenValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String token = getToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        DecodedJWT jwt = JWT.decode(token);

        if (!keycloakTokenValidator.validateToken(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        UserInfo userInfo = getUserInfo(jwt);

        List<SimpleGrantedAuthority> authorities = userInfo.getRoles().stream()
                .filter(role -> role.contains("SYLLABUS_"))
                .map(role -> role.replaceAll("SYLLABUS_", ""))
                .map(SimpleGrantedAuthority::new)
                .toList();

        if (authorities.isEmpty()) {
            response.sendError(401);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userInfo,
                null,
                authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }

    private UserInfo getUserInfo(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();
        UserInfo userInfo = new UserInfo();
        userInfo.setRoles((List<String>) claims.get("realm_access").asMap().get("roles"));
        userInfo.setId(UUID.fromString(claims.get("sub").as(String.class)));
        userInfo.setEmail(claims.get("email").as(String.class));

        return userInfo;
    }
}
