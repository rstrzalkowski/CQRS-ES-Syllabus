package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ErrorObject {

    @Getter
    private Integer statusCode;
}
