package com.deltax.app.dao;

import java.util.List;

import com.deltax.app.model.Artist;
import com.deltax.app.model.Ratings;
import com.deltax.app.model.Song;

public interface SpotifyAppDao {
	int addSongs(Song song);

	int addArtist(Artist artist);

	List<String> getAllArtists();

	List<Song> getAllSongs();

	int rateTrack(Ratings ratings);

	List<Artist> getTopArtists();
}
