package com.project.person.adapter.output;

import com.project.person.adapter.mapper.PersonMapper;
import com.project.person.client.FetchAddressByZipCodeClient;
import com.project.person.domain.Address;
import com.project.person.domain.response.AddressResponse;
import com.project.person.exception.InvalidZipCodeException;
import com.project.person.ports.output.FetchAddressOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FetchAddressAdapter implements FetchAddressOutputPort {
    private final FetchAddressByZipCodeClient client;
    private final PersonMapper mapper;

    @Override
    public Address fetchAddress(final String zipCode) {
        log.info("[FetchAddressByZipCodeAdapter][Start] Fetch address by zip code: {}",
                zipCode);
        final AddressResponse response = client.fetchAddressByZipCode(zipCode);
        verifyNullBody(response);
        response.setCep(formatZipCode(response.getCep()));
        log.info("[FetchAddressByZipCodeAdapter][End] Fetched address: {}", response);
        return mapper.toAddressFromAddressResponse(response);
    }

    private static void verifyNullBody(final AddressResponse response) {
        if (response == null) {
            throw new InvalidZipCodeException();
        }
    }
    private static String formatZipCode(final String zipCode) {
        return zipCode.replace("-", "");
    }
}
