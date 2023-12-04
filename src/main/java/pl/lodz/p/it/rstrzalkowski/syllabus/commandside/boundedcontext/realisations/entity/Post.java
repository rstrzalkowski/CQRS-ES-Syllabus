package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.realisations.entity;

import lombok.AllArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractEntity;

import java.util.UUID;


@AllArgsConstructor
public class Post extends AbstractEntity {

    private UUID teacherId;

    private String title;

    private String content;

    private boolean edited;
}
