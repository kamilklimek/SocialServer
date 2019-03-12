package eu.geniusgamedev.StepLink.profile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@ToString
public class ProfileModel {
    private Long id;
    private String name;
    private String lastName;
    private String email;
}
