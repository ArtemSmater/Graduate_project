package com.example.mymoviesdatabase.room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    private int uniqueId;
    private int id;
    private int voteCount;
    private double voteAverage;
    private String title;
    private String originalTitle;
    private String overview;
    private String posterPath;
    private String posterPathBig;
    private String backdropPath;
    private String releaseData;

    public Movie(int uniqueId ,int id, int voteCount, double voteAverage, String title, String originalTitle, String overview, String posterPath, String posterPathBig, String backdropPath, String releaseData) {
        this.uniqueId = uniqueId;
        this.id = id;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.posterPathBig = posterPathBig;
        this.backdropPath = backdropPath;
        this.releaseData = releaseData;
    }

    @Ignore
    public Movie(int id, int voteCount, double voteAverage, String title, String originalTitle, String overview, String posterPath, String posterPathBig, String backdropPath, String releaseData) {
        this.id = id;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.posterPathBig = posterPathBig;
        this.backdropPath = backdropPath;
        this.releaseData = releaseData;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getPosterPathBig() {
        return posterPathBig;
    }

    public void setPosterPathBig(String posterPathBig) {
        this.posterPathBig = posterPathBig;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getReleaseData() {
        return releaseData;
    }

    public void setReleaseData(String releaseData) {
        this.releaseData = releaseData;
    }
}
