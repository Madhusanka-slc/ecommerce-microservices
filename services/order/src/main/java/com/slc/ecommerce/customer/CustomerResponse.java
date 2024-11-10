package com.slc.ecommerce.customer;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email

) {
}
