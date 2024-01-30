package com.example.LargeScaleProject.Model.MongoDB.Aggregate;

import com.example.LargeScaleProject.Model.MongoDB.WatchlistItem;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class WatchlistSpoiler {

    ArrayList<WatchlistItem> watchlistItemArrayList;

    public WatchlistSpoiler(ArrayList<WatchlistItem> watchlistItemArrayList) {
        this.watchlistItemArrayList = watchlistItemArrayList;
    }

    public WatchlistSpoiler() {
    }

    public void setWatchlistItemArrayList(ArrayList<WatchlistItem> watchlistItemArrayList) {
        this.watchlistItemArrayList = watchlistItemArrayList;
    }

    @Override
    public String toString() {
        return "WatchlistSpoiler{" +
                "watchlistItemArrayList=" + watchlistItemArrayList +
                '}';
    }
}
