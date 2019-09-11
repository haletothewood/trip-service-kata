package com.tripservicekata.trip;

import com.tripservicekata.exception.UserNotLoggedInException;
import com.tripservicekata.user.User;
import com.tripservicekata.user.UserBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TripServiceShould {

    private static final User GUEST = null;
    private static final User REGISTERED_USER = mock(User.class);
    private static final User TIMMY = new User();
    private User loggedInUser = REGISTERED_USER;
    private TripService tripService = new TestableTripService();
    private static final Trip TO_LONDON = new Trip();
    private static final Trip TO_SYDNEY = new Trip();
    private List<Trip> trips = new ArrayList<>();

    @Test
    void throw_exception_when_user_not_logged_in() {
        loggedInUser = GUEST;
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(loggedInUser));
    }

    @Test
    void provide_no_trips_for_a_user_with_no_friends() {
        User user = new User();
        assertThat(tripService.getTripsByUser(user)).isEmpty();
    }

    @Test
    void provides_no_trips_for_a_user_not_friends_with_logged_in_user() {
        User user = UserBuilder.aUser()
                .withFriends(TIMMY)
                .withTrips(TO_LONDON, TO_SYDNEY)
                .build();

        assertThat(tripService.getTripsByUser(user)).isEmpty();
    }

    @Test
    void provides_trips_for_a_user_friends_with_logged_in_user() {

        User user = UserBuilder.aUser()
                .withFriends(TIMMY, loggedInUser)
                .withTrips(TO_LONDON, TO_SYDNEY)
                .build();

        trips.add(TO_LONDON);
        trips.add(TO_SYDNEY);

        assertThat(tripService.getTripsByUser(user)).containsExactly(TO_LONDON, TO_SYDNEY);
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
