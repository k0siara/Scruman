package com.scruman.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends AbstractDTO {

    @Email(message = "Please enter a valid email address.")
    @NotEmpty(message = "Please enter a valid email address.")
    private String email;

    @NotEmpty(message = "Please enter a valid password.")
    private String password;
}
