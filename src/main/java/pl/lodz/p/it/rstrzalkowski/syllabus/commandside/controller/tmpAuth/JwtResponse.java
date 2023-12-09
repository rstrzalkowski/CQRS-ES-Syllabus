package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.controller.tmpAuth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtResponse {
    private String token;
}