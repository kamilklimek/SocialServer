package eu.geniusgamedev.StepLink.profile;

import eu.geniusgamedev.StepLink.metadata.entity.Follower;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@ToString
public class ProfileModel {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private List<Follower> following = new LinkedList<>();
    private List<Follower> followers = new LinkedList<>();
}
