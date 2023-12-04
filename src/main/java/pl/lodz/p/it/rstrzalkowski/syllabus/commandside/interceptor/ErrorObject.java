package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ErrorObject {

    @Getter
    private Integer statusCode;
}
