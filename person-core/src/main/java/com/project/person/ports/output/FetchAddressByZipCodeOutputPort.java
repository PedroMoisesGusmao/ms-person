package com.project.person.ports.output;

import com.project.person.domain.Address;

public interface FetchAddressByZipCodeOutputPort {
    Address fetchAddress(String zipCode);
}
