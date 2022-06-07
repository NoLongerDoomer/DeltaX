package com.deltax.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.deltax.app.model.Artist;
import com.deltax.app.model.Ratings;
import com.deltax.app.model.Song;

public class SpotifyAppDaoImpl implements SpotifyAppDao {

	@Autowired
	private JdbcTemplate template;

	@Override
	public int addSongs(Song song) {
		int update = 0;
		try {
			update = template.update("insert into m_tracks(track_name, date_of_release, image_url) values (?, ?, ?)",
					song.getName(), song.getDateOfRelease(), song.getImage().getOriginalFilename());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String sql = "insert into artist_track_list (artist_id, track_id) values ((select artist_id from m_artist where artist_name = ?),"
				+ "(select track_id from m_tracks where track_name = ? and date_of_release = ?))";
		try {
			for (String artist : song.getArtists()) {
				update = template.update(sql, artist, song.getName(), song.getDateOfRelease());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return update;
	}

	@Override
	public int addArtist(Artist artist) {
		String sql = "insert into m_artist (artist_name, date_of_birth, artist_bio) values (?, ?, ?)";
		int update = 0;
		try {
			update = template.update(sql, artist.getName(), artist.getDateofbirth(), artist.getBio());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return update;
	}

	@Override
	public List<String> getAllArtists() {
		String sql = "select artist_name from m_artist";
		List<String> artists = null;
		try {
			artists = template.queryForList(sql, String.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return artists;
	}

	@Override
	public List<Song> getAllSongs() {
		String sql = "select avg(r.ratings) as ratings, t.track_name, t.image_url, t.date_of_release, group_concat(distinct a.artist_name order by a.artist_id) as artists from m_tracks t \r\n"
				+ "left outer join artist_track_list atl on t.track_id = atl.track_id\r\n"
				+ "left outer join m_artist a on atl.artist_id = a.artist_id\r\n"
				+ "left outer join user_reviews r on r.track_id = t.track_id  group by t.track_id\r\n"
				+ "order by avg(r.ratings) desc limit 10";
		List<Song> songs = null;
		try {
			songs = template.query(sql, new ResultSetExtractor<List<Song>>() {
				@Override
				public List<Song> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Song> songList = new ArrayList<Song>();
					while (rs.next()) {
						Song song = new Song();
						song.setDateOfRelease(rs.getString("date_of_release"));
						song.setImageUrl(rs.getString("image_url"));
						song.setName(rs.getString("track_name"));
						song.setRatings(rs.getString("ratings"));
						song.setArtists(rs.getString("artists").split(","));
						songList.add(song);
					}
					return songList;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return songs;
	}

	@Override
	public int rateTrack(Ratings ratings) {
		int update = 0;
		try {
			update = template.update("insert into m_user (user_name, user_email) values (?, ?)", ratings.getUsername(),
					ratings.getEmail());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			update = template.update(
					"insert into user_reviews (track_id, user_id, review, ratings) values ("
							+ "(select track_id from m_tracks where track_name = ? and date_of_release = ?), "
							+ "(select user_id from m_user where user_name = ? and user_email = ?),? ,? )",
					ratings.getTrackName(), ratings.getDateOfRelease(), ratings.getUsername(), ratings.getEmail(),
					ratings.getReview(), ratings.getRatings());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return update;
	}

	@Override
	public List<Artist> getTopArtists() {
		List<Artist> artists = null;
		String sql = "select a.artist_name , a.date_of_birth, group_concat(distinct t.track_name order by r.ratings) as tracks from m_tracks t\r\n"
				+ "left outer join artist_track_list atl on t.track_id = atl.track_id\r\n"
				+ "left outer join m_artist a on atl.artist_id = a.artist_id\r\n"
				+ "left outer join user_reviews r on r.track_id = t.track_id  group by a.artist_id\r\n"
				+ "order by avg(r.ratings) desc limit 10";
		try {
			artists = template.query(sql, new ResultSetExtractor<List<Artist>>() {

				@Override
				public List<Artist> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Artist> artistsTemp = new ArrayList<Artist>();
					while (rs.next()) {
						Artist artist = new Artist();
						artist.setName(rs.getString("artist_name"));
						artist.setDateofbirth(rs.getString("date_of_birth"));
						artist.setTracks(rs.getString("tracks").split(","));
						artistsTemp.add(artist);
					}
					return artistsTemp;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return artists;
	}

}
