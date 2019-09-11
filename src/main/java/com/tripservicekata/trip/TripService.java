package com.tripservicekata.trip;

import com.tripservicekata.exception.UserNotLoggedInException;
import com.tripservicekata.user.User;
import com.tripservicekata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        User loggedUser = getLoggedUser();
        if (loggedUser == null) {
            throw new UserNotLoggedInException();
        }

        return user.isFriendsWith(loggedUser) ?
                findTripsByUser(user) : noTrips();
    }

    private ArrayList<Trip> noTrips() {
        return new ArrayList<>();
    }

    protected List<Trip> findTripsByUser(User user) {
        return TripDAO.findTripsByUser(user);
    }

    protected User getLoggedUser() {
        return UserSession.getInstance().getLoggedUser();
    }

}
