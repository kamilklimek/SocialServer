package eu.geniusgamedev.StepLink.security.register;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UserRegisterModel {
    private String name;
    private String lastName;
    private String password;
    private String email;
}
