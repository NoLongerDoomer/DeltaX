package com.deltax.app.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deltax.app.dao.SpotifyAppDao;
import com.deltax.app.model.Artist;
import com.deltax.app.model.Ratings;
import com.deltax.app.model.Song;
import com.google.gson.Gson;

@Controller
public class HomeController {
	private static final Logger LOGGER = Logger.getLogger(HomeController.class);
	@Autowired
	private SpotifyAppDao dao;

	@RequestMapping("/")
	public String getHomePage() {
		LOGGER.info("LOGGING HOME");
		return "home";
	}

	/* Get all tracks from the database */
	@GetMapping("/gettracks")
	@ResponseBody
	public String getTracks() {
		List<Song> songs = dao.getAllSongs();
		return new Gson().toJson(songs);
	}

	/* Redirects you to add songs Page */
	@RequestMapping("/addsongpage")
	public String toSongPage() {
		return "addsongs";
	}

	@RequestMapping("/addsong")
	@ResponseBody
	public String addSong(@ModelAttribute Song song) throws IOException {
		String uploadPath = "C:\\Users\\Swarup\\spring-workspace\\deltax-webapp\\src\\main\\webapp\\static\\images\\";
		LOGGER.info(song.toString());
		FileCopyUtils.copy(song.getImage().getBytes(), new File(uploadPath + song.getImage().getOriginalFilename()));
		String fileName = song.getImage().getOriginalFilename();
		dao.addSongs(song);
		return fileName + " Uploaded";
	}

	@PostMapping(value = "/addartist")
	@ResponseBody
	public String addArtist(@ModelAttribute Artist artist) {
		LOGGER.info(artist.toString());
		dao.addArtist(artist);
		return artist.getName() + " Added";
	}

	@GetMapping("/getartists")
	@ResponseBody
	public String getArtists() {
		List<String> artists = dao.getAllArtists();
		return new Gson().toJson(artists);
	}

	@PostMapping("/ratetrack")
	@ResponseBody
	public String rateTrack(@ModelAttribute Ratings ratings) {
		dao.rateTrack(ratings);
		return "Success";
	}

	@GetMapping("/gettopartist")
	@ResponseBody
	public String getTopArtists() {
		List<Artist> artists = dao.getTopArtists();
		return new Gson().toJson(artists);
	}
}
