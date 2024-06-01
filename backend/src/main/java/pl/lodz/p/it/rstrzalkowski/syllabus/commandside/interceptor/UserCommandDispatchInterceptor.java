package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.RegisterCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user.UserPersonalIdEmailEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user.UserPersonalIdEmailRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user.UserAlreadyRegisteredCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.List;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
@WriteApplicationBean
public class UserCommandDispatchInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final UserPersonalIdEmailRepository userPersonalIdEmailRepository;

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {
        return (i, m) -> {
            if (RegisterCommand.class.equals(m.getPayloadType())) {
                final RegisterCommand registerCommand = (RegisterCommand) m.getPayload();
                reservePersonalIdAndEmail(registerCommand.getEmail(), registerCommand.getPersonalId());
            }
            return m;
        };
    }

    private void reservePersonalIdAndEmail(String email, String personalId) {
        try {
            userPersonalIdEmailRepository.save(new UserPersonalIdEmailEntity(email, personalId));
        } catch (DataIntegrityViolationException sqle) {
            throw new UserAlreadyRegisteredCommandExecutionException();
        }
    }
}
