package com.project.person.usecase.strategies;

import com.project.person.domain.Address;
import com.project.person.domain.Person;
import com.project.person.exception.InvalidZipCodeException;
import com.project.person.ports.output.FetchAddressOutputPort;
import com.project.person.ports.output.SavePersonOutputPort;
import com.project.person.usecase.UpdatePersonStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdatePersonAddressByZipCodeStrategy implements UpdatePersonStrategy {
    private final FetchAddressOutputPort fetchAddress;
    private final SavePersonOutputPort savePersonOutputPort;
    @Override
    public void updatePerson(final Person person, final Object body) {
        log.info("[UpdatePersonAddressStrategy][Start] Update person address: {}", person);
        String zipCode = (String) body;

        verifyZipCode(zipCode);

        Address address = fetchAddress.fetchAddress(formatZipCode(zipCode));
        person.setAddress(address);

        savePersonOutputPort.save(person);
        log.info("[UpdatePersonAddressStrategy][End] Updated person address: {}", person);
    }

    @Override
    public boolean isValid(String field) {
        return Objects.equals(field, "zip_code");
    }

    private static void verifyZipCode(final String zipCode) {
        if (zipCode.length() < 8 || zipCode.length() > 9) {
            throw new InvalidZipCodeException();
        }
    }

    private static String formatZipCode(final String zipCode) {
        return zipCode.replace("-", "");
    }
}
