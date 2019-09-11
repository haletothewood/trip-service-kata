package com.tripservicekata.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserShould {
    private static final User TIMMY = new User();
    public static final User BILLY = new User();

    @Test
    void inform_whether_its_not_friends_with_another_user() {
        User user = UserBuilder.aUser()
                .withFriends(TIMMY)
                .build();

        assertThat(user.isFriendsWith(BILLY)).isFalse();
    }

    @Test
    void inform_whether_its_friends_with_another_user() {
        User user = UserBuilder.aUser()
                .withFriends(BILLY, TIMMY)
                .build();

        assertThat(user.isFriendsWith(BILLY)).isTrue();
    }
}
