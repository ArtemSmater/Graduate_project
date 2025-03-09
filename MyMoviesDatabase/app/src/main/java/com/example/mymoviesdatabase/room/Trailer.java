package com.example.mymoviesdatabase.room;

public class Trailer {

    private String videoKey;
    private String trailerName;

    public Trailer(String videoKey, String trailerName) {
        this.videoKey = videoKey;
        this.trailerName = trailerName;
    }

    public String getVideoKey() {
        return videoKey;
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }
}
