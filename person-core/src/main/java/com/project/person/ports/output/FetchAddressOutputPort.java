package com.project.person.ports.output;

import com.project.person.domain.Address;

public interface FetchAddressOutputPort {
    Address fetchAddress(String zipCode);
}
