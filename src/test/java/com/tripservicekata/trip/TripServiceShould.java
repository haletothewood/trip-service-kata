package com.tripservicekata.trip;

import com.tripservicekata.exception.UserNotLoggedInException;
import com.tripservicekata.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class TripServiceShould {

    private static final User GUEST = null;
    private static final User REGISTERED_USER = mock(User.class);
    private User loggedInUser;
    private TripService tripService = new TestableTripService();

    @Test
    void throw_exception_when_user_not_logged_in() {
        loggedInUser = GUEST;
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(loggedInUser));
    }


    @Test
    void provide_no_trips_for_a_user_with_no_friends() {
        loggedInUser = REGISTERED_USER;
        when(REGISTERED_USER.getFriends()).thenReturn(new ArrayList<>());

        assertThat(tripService.getTripsByUser(REGISTERED_USER).size()).isEqualTo(0);
    }

    private class TestableTripService extends TripService {
        @Override
        protected User getLoggedUser() {
            return loggedInUser;
        }
    }
}
