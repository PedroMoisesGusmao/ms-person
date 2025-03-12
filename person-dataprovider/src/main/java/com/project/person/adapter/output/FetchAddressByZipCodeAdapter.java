package com.project.person.adapter.output;

import com.project.person.adapter.mapper.PersonMapper;
import com.project.person.client.FetchAddressByZipCodeClient;
import com.project.person.domain.Address;
import com.project.person.domain.response.AddressResponse;
import com.project.person.ports.output.FetchAddressByZipCodeOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FetchAddressByZipCodeAdapter implements FetchAddressByZipCodeOutputPort {
    private final FetchAddressByZipCodeClient client;
    private final PersonMapper mapper;

    @Override
    public Address fetchAddress(String zipCode) {
        log.info("[FetchAddressByZipCodeAdapter][Start] Fetch address by zip code: {}",
                zipCode);
        AddressResponse addressResponse = client.fetchAddressByZipCode(zipCode);
        addressResponse.setCep(formatZipCode(addressResponse.getCep()));
        log.info("[FetchAddressByZipCodeAdapter][End] Fetched address: {}", addressResponse);
        return mapper.toAddressFromAddressResponse(addressResponse);
    }

    private static String formatZipCode(String zipCode) {
        return zipCode.replace("-", "");
    }
}
