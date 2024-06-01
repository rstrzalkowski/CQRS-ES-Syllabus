package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.realisation.CreateRealisationCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation.RealisedSubjectEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation.RealisedSubjectRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.realisation.RealisationAlreadyExistsCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.time.Year;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
@WriteApplicationBean
public class RealisationCommandDispatchInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final RealisedSubjectRepository realisedSubjectRepository;

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {
        return (i, m) -> {
            if (CreateRealisationCommand.class.equals(m.getPayloadType())) {
                final CreateRealisationCommand createRealisationCommand = (CreateRealisationCommand) m.getPayload();
                reserveSubjectIdAndClassIdAndYear(
                        createRealisationCommand.getSubjectId(),
                        createRealisationCommand.getClassId(),
                        createRealisationCommand.getYear());
            }
            return m;
        };
    }

    private void reserveSubjectIdAndClassIdAndYear(UUID subjectId, UUID schoolClassId, Year year) {
        try {
            realisedSubjectRepository.save(new RealisedSubjectEntity(subjectId, schoolClassId, year));
        } catch (DataIntegrityViolationException sqle) {
            throw new RealisationAlreadyExistsCommandExecutionException();
        }
    }
}
