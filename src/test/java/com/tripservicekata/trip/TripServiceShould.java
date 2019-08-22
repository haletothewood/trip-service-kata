package com.tripservicekata.trip;

import com.tripservicekata.exception.UserNotLoggedInException;
import com.tripservicekata.user.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TripServiceShould {

    private static final User GUEST = null;
    private static final User REGISTERED_USER = mock(User.class);
    private User loggedInUser;
    private TripService tripService = new TestableTripService();
    private Trip TO_LONDON = new Trip();
    private List<Trip> trips = new ArrayList<>();

    @Test
    void throw_exception_when_user_not_logged_in() {
        loggedInUser = GUEST;
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(loggedInUser));
    }


    @Test
    void provide_no_trips_for_a_user_with_no_friends() {
        loggedInUser = REGISTERED_USER;
        User user = new User();
        assertThat(tripService.getTripsByUser(user).size()).isEqualTo(0);
    }

    @Test
    void provides_trips_for_a_user_with_friends() {
        loggedInUser = REGISTERED_USER;
        User user = new User();
        user.addFriend(loggedInUser);
        user.addTrip(TO_LONDON);
        trips = new ArrayList<Trip>(){{
            add(TO_LONDON);
        }};

        assertThat(tripService.getTripsByUser(user)).contains(TO_LONDON);
    }

    private class TestableTripService extends TripService {
        @Override
        protected User getLoggedUser() {
            return loggedInUser;
        }

        @Override
        protected List<Trip> findTripsByUser(User user) {
            return trips;
        }
    }
}
