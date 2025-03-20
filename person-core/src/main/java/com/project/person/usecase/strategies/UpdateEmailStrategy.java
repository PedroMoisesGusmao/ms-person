package com.project.person.usecase.strategies;

import com.project.person.domain.Person;
import com.project.person.exception.InvalidEmailException;
import com.project.person.ports.output.SavePersonOutputPort;
import com.project.person.usecase.UpdatePersonStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateEmailStrategy implements UpdatePersonStrategy {
    private final SavePersonOutputPort savePerson;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    @Override
    public void updatePerson(final Person person, final Object body) {
        log.info("[UpdatePersonEmailStrategy][Start] Update person email: {}", person);

        String email = (String) body;

        verifyEmail(email);
        person.setEmail(email);

        savePerson.save(person);

        log.info("[UpdatePersonEmailStrategy][End] Updated person email: {}", person);
    }

    @Override
    public boolean isValid(String field) {
        return Objects.equals(field, "email");
    }

    private static void verifyEmail(final String email) {
        if (!Pattern.matches(EMAIL_REGEX, email)) throw new InvalidEmailException();
    }
}
