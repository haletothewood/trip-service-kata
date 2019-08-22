package com.tripservicekata.trip;

import com.tripservicekata.exception.UserNotLoggedInException;
import com.tripservicekata.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TripServiceShould {
    @Test
    void throw_exception_when_user_not_logged_in() {
        TripService testtableTripService = new TesttableTripService();
        User user = new User();
        assertThrows(UserNotLoggedInException.class, () -> testtableTripService.getTripsByUser(user));
    }

    private class TesttableTripService extends TripService {
        @Override
        protected User getLoggedUser() {
            return null;
        }
    }
}
