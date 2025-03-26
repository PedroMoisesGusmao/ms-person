package com.project.adapter;

import com.project.person.adapter.mapper.PersonMapper;
import com.project.person.adapter.output.FetchAddressAdapter;
import com.project.person.client.FetchAddressByZipCodeClient;
import com.project.person.domain.Address;
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
import static org.mockito.ArgumentMatchers.anyString;
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

        Address address = fetchAddress.fetchAddress(zipCode);
        Address expectedAddress = toAddressDomain(addressResponse);

        verify(client).fetchAddressByZipCode(anyString());
        verify(mapper).toAddressFromAddressResponse(addressResponse);
        assertEquals(expectedAddress.getZipCode(), address.getZipCode());
        assertEquals(expectedAddress.getThoroughfare(), address.getThoroughfare());
        assertEquals(expectedAddress.getComplement(), address.getComplement());
        assertEquals(expectedAddress.getNeighborhood(), address.getNeighborhood());
        assertEquals(expectedAddress.getState(), address.getState());
    }

    @Test
    void should_throw_exception_when_address_is_not_found() {
        String zipCode = easyRandom.nextObject(String.class);

        when(client.fetchAddressByZipCode(zipCode)).thenReturn(new AddressResponse(null, null, null, null, null));

        InvalidZipCodeException exception = assertThrows(InvalidZipCodeException.class,
                () -> fetchAddress.fetchAddress(zipCode));
        assertEquals("Zip code not found", exception.getMessage());
    }

    private static Address toAddressDomain(AddressResponse addressResponse) {
        return new Address(
                addressResponse.getCep(),
                addressResponse.getLogradouro(),
                addressResponse.getComplemento(),
                addressResponse.getBairro(),
                addressResponse.getEstado()
        );
    }
}
