package com.jpmorgan.event.repository;

import com.jpmorgan.event.entity.Show;

import java.util.HashMap;
import java.util.Map;

public class ShowRepository {

    private static ShowRepository instance;
    private final Map<Integer, Show> shows;

    private ShowRepository() {
        this.shows = new HashMap<>();
    }

    public static ShowRepository getInstance() {
        if (instance == null) {
            instance = new ShowRepository();
        }
        return instance;
    }

    public Map<Integer, Show> getShows() {
        return shows;
    }

    public Show getShow(int showNumber) {
        return shows.get(showNumber);
    }

    public void addShow(Show show) {
        shows.put(show.getShowNumber(), show);
    }

    public void removeShow(int showNumber) {
        shows.remove(showNumber);
    }

    public void clearShows() {
        shows.clear();
    }

}
