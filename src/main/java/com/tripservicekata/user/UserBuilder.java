package com.tripservicekata.user;

import com.tripservicekata.trip.Trip;

public class UserBuilder {
    private User[] friends = new User[]{};
    private Trip[] trips = new Trip[]{};

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder withFriends(User... friends) {
        this.friends = friends;
        return this;
    }

    public UserBuilder withTrips(Trip... trips) {
        this.trips = trips;
        return this;
    }

    public User build() {
        User user = new User();
        addFriends(user);
        addTrips(user);

        return user;
    }

    private void addTrips(User user) {
        for (Trip trip : trips) {
            user.addTrip(trip);
        }
    }

    private void addFriends(User user) {
        for (User friend : friends) {
            user.addFriend(friend);
        }
    }
}
