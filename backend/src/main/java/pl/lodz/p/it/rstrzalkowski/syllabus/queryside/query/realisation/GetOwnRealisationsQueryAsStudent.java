package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class GetOwnRealisationsQueryAsStudent {
    private UUID studentId;
}
