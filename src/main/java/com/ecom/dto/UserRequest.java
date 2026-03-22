package com.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	private String username;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private UserRole role;

	private AddressDto address;
}
