package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.RealisationDto;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler.RealisationQueryHandler;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetActiveRealisationsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetArchivedRealisationsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@RestController
@RequiredArgsConstructor
@RequestMapping("/realisations")
@ReadApplicationBean
public class RealisationQueryController {

    private final RealisationQueryHandler realisationQueryHandler;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<RealisationDto> getAllActiveRealisations(Pageable pageable) {
        return realisationQueryHandler.handle(new GetActiveRealisationsQuery(pageable));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/archived")
    public Page<RealisationDto> getAllArchivedRealisations(Pageable pageable) {
        return realisationQueryHandler.handle(new GetArchivedRealisationsQuery(pageable));
    }
}
