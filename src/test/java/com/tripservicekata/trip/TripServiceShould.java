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

    private TripService tripService = new TestableTripService();
    private static final User GUEST = null;
    private static final User REGISTERED_USER = new User();
    private static final User TIMMY = new User();
    private static final User UNUSED_USER = new User();
    private static final Trip TO_LONDON = new Trip();
    private static final Trip TO_SYDNEY = new Trip();
    private List<Trip> trips = new ArrayList<>();

    @Test
    void throw_exception_when_user_not_logged_in() {
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(UNUSED_USER, GUEST));
    }

    @Test
    void provide_no_trips_for_a_user_with_no_friends() {
        User user = new User();
        assertThat(tripService.getTripsByUser(user, REGISTERED_USER)).isEmpty();
    }

    @Test
    void provides_no_trips_for_a_user_not_friends_with_logged_in_user() {
        User user = UserBuilder.aUser()
                .withFriends(TIMMY)
                .withTrips(TO_LONDON, TO_SYDNEY)
                .build();

        assertThat(tripService.getTripsByUser(user, REGISTERED_USER)).isEmpty();
    }

    @Test
    void provides_trips_for_a_user_friends_with_logged_in_user() {

        User user = UserBuilder.aUser()
                .withFriends(TIMMY, REGISTERED_USER)
                .withTrips(TO_LONDON, TO_SYDNEY)
                .build();

        trips.add(TO_LONDON);
        trips.add(TO_SYDNEY);

        assertThat(tripService.getTripsByUser(user, REGISTERED_USER)).containsExactly(TO_LONDON, TO_SYDNEY);
    }

    private class TestableTripService extends TripService {

        @Override
        protected List<Trip> findTripsByUser(User user) {
            return trips;
        }
    }
}
