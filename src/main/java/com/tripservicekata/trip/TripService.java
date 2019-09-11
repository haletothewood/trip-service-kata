package com.tripservicekata.trip;

import com.tripservicekata.exception.UserNotLoggedInException;
import com.tripservicekata.user.User;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    private TripDAO tripDAO;

    public TripService(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    public List<Trip> getTripsByUser(User user, User loggedInUser) throws UserNotLoggedInException {
        validate(loggedInUser);

        return user.isFriendsWith(loggedInUser) ?
                findTripsByUser(user) : noTrips();
    }

    private void validate(User loggedInUser) {
        if (loggedInUser == null) {
            throw new UserNotLoggedInException();
        }
    }

    private ArrayList<Trip> noTrips() {
        return new ArrayList<>();
    }

    private List<Trip> findTripsByUser(User user) {
        return tripDAO.tripsBy(user);
    }
}