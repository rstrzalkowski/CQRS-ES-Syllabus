package pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak;

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
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.UserInfo;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class KeycloakTokenFilter extends OncePerRequestFilter {
    private final KeycloakHttpClient keycloakHttpClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
        throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        UserInfo userInfo;

        try {
            userInfo = keycloakHttpClient.getUserInfo(authorization);
        } catch (WebClientResponseException wcre) {
            filterChain.doFilter(request, response);
            return;
        }

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
}
