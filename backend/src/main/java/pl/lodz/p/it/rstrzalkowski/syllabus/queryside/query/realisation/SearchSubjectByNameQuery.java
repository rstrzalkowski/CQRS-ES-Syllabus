package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation;


import org.springframework.data.domain.Pageable;

public record SearchSubjectByNameQuery(String name, Pageable pageable) {

}
