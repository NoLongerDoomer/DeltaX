package com.deltax.app.model;

public class Artist {
	private String name;
	private String dateofbirth;
	private String bio;
	private String[] tracks;

	@Override
	public String toString() {
		return "Artist [name=" + name + ", dateofbirth=" + dateofbirth + ", bio=" + bio + ", tracks=" + tracks + "]";
	}

	public Artist(String name, String dateofbirth, String bio, String[] tracks) {
		super();
		this.name = name;
		this.dateofbirth = dateofbirth;
		this.bio = bio;
		this.tracks = tracks;
	}

	public String[] getTracks() {
		return tracks;
	}

	public void setTracks(String[] tracks) {
		this.tracks = tracks;
	}

	public Artist() {
	}

	public String getName() {
		return name;
	}

	public String getDateofbirth() {
		return dateofbirth;
	}

	public String getBio() {
		return bio;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

}
