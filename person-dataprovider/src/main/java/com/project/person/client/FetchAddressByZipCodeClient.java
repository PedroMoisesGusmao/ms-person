package com.project.person.client;

import com.project.person.domain.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "FetchAddressByZipCodeClient",
        url = "${project.client.person.zipcode.url}"
)
public interface FetchAddressByZipCodeClient {
    @GetMapping("${project.client.person.zipcode.paths.get-zipcode}")
    AddressResponse fetchAddressByZipCode(@PathVariable("zip_code") String zipCode);
}
