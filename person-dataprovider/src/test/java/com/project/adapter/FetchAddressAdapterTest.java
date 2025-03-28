package com.project.adapter;

import com.project.person.adapter.mapper.PersonMapper;
import com.project.person.adapter.output.FetchAddressAdapter;
import com.project.person.client.FetchAddressByZipCodeClient;
import com.project.person.domain.response.AddressResponse;
import com.project.person.exception.InvalidZipCodeException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FetchAddressAdapterTest {
    @InjectMocks
    private FetchAddressAdapter fetchAddress;
    @Mock
    private FetchAddressByZipCodeClient client;
    @Spy
    private PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    private EasyRandom easyRandom;
    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    @Test
    void should_fetch_by_zip_code_then_return_address() {
        String zipCode = easyRandom.nextObject(String.class);
        AddressResponse addressResponse = easyRandom.nextObject(AddressResponse.class);

        when(client.fetchAddressByZipCode(zipCode)).thenReturn(addressResponse);

        fetchAddress.fetchAddress(zipCode);

        verify(client).fetchAddressByZipCode(zipCode);
        verify(mapper).toAddressFromAddressResponse(addressResponse);
    }

    @Test
    void should_throw_exception_when_address_is_not_found() {
        String zipCode = easyRandom.nextObject(String.class);

        when(client.fetchAddressByZipCode(zipCode)).thenReturn(AddressResponse.builder().build());

        InvalidZipCodeException exception = assertThrows(InvalidZipCodeException.class,
                () -> fetchAddress.fetchAddress(zipCode));
        assertEquals("Zip code not found", exception.getMessage());
    }
}
