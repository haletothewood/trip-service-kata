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
        if (loggedInUser == null) {
            throw new UserNotLoggedInException();
        }

        return user.isFriendsWith(loggedInUser) ?
                findTripsByUser(user) : noTrips();
    }

    private ArrayList<Trip> noTrips() {
        return new ArrayList<>();
    }

    private List<Trip> findTripsByUser(User user) {
        return tripDAO.tripsBy(user);
    }
}