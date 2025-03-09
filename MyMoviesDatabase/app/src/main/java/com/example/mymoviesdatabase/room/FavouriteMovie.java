package com.example.mymoviesdatabase.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favourite_movies")
public class FavouriteMovie extends Movie{

    public FavouriteMovie(int uniqueId ,int id, int voteCount, double voteAverage, String title, String originalTitle, String overview, String posterPath, String posterPathBig, String backdropPath, String releaseData) {
        super(uniqueId ,id, voteCount, voteAverage, title, originalTitle, overview, posterPath, posterPathBig, backdropPath, releaseData);
    }

    @Ignore
    public FavouriteMovie(@NonNull Movie movie){
        super(movie.getUniqueId() ,movie.getId(), movie.getVoteCount(), movie.getVoteAverage(), movie.getTitle(), movie.getOriginalTitle(), movie.getOverview(), movie.getPosterPath(), movie.getPosterPathBig(), movie.getBackdropPath(), movie.getReleaseData());
    }
}

