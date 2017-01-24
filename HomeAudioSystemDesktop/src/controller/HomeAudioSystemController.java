package controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import model.Album;
import model.Album.Genre;
import model.Artist;
import model.HomeAudioSystem;
import model.Location;
import model.Playlist;
import model.Song;
import model.Player;
import persistence.PersistenceXStream;



public class HomeAudioSystemController {
	
	public HomeAudioSystemController (){
		
	}
	
	// Creates an Artist and saves to the HAS (Home Audio System Singleton)
	public void createArtist(String name) throws InvalidInputException {
		
		if(name == null || name.trim().length() == 0)
		{
			throw new InvalidInputException("Artist name cannot be empty!");
		}
		
		Artist a = new Artist(name);
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		HAS.addArtist(a);
		PersistenceXStream.saveToXMLwithXStream(HAS);
	}
	
	// Creates an Album and saves to the HAS
	public void createAlbum(String name, Date releaseDate, Artist artist, Genre genre) throws InvalidInputException{
		
		String error = "";
		if(name == null || name.trim().length() == 0){
			error= error+ "Album name cannot be empty!";
		}
		if(releaseDate ==null){
			error=error+"Release Date cannot be empty!";
		}
		if(artist==null){
			error=error+"Artist cannot be empty!";
		}
		if(genre==null){
			error=error+"Genre cannot be empty!";
		}
		error = error.trim();
		if(error.length() > 0)
		{
			throw new InvalidInputException(error);
		}
		Album a = new Album (name, releaseDate, artist, genre);
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		HAS.addAlbum(a);
		PersistenceXStream.saveToXMLwithXStream(HAS);
		
	}

	// Creates a Song and saves to the HAS
	public void createSong(String title, Time duration, int albumPos, Album album) throws InvalidInputException{
		String error="";
		if(title == null || title.trim().length() == 0){
			error += "Song title cannot be empty!";
		}
		if(duration.equals(Time.valueOf("00:00:00"))){
			error += "Song must have a duration!";
		}
		if(albumPos <= 0){	
			error += "Song position on Album must not be empty!";
		}
		if(album == null){
			error += "Song must be assigned an Album!";
		}
		
		if(error.length() > 0){
			throw new InvalidInputException(error);
		}
		
		Song s = new Song(title, duration, albumPos, album);
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		HAS.addSong(s);
		PersistenceXStream.saveToXMLwithXStream(HAS);		
	}
	
	// Creates a Location and saves to the HAS
	public void createLocation(String name, int Volume) throws InvalidInputException{
		String error="";
		if(name== null || name.trim().length()==0){
			error=error+"Location name cannot be empty!";
		}
		error=error.trim();
		if(error.length() > 0){
			throw new InvalidInputException(error);
		}
		Location l = new Location(name, Volume);
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		HAS.addLocation(l);
		PersistenceXStream.saveToXMLwithXStream(HAS);			
	}
	
	// Creates a Playlist and saves to the HAS
	public void createPlaylist(String title) throws InvalidInputException{
		String error ="";
		if(title==null || title.trim().length()==0){
			error=error+"Playlist title cannot be empty!";
		}
		error=error.trim();
		if(error.length() > 0){
			throw new InvalidInputException(error);
		}
		Playlist p = new Playlist(title);
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		HAS.addPlaylist(p);
		PersistenceXStream.saveToXMLwithXStream(HAS);	
	}
	
	// Adds a Song to a Playlist and saves to the HAS
	public void addSongToPlaylist(Song aSong, Playlist aPlayList) throws InvalidInputException {
		String error = "";
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		if (aPlayList == null) {
			error += "Playlist needs to be selected!";
		}
		else if (!HAS.getPlaylists().contains(aPlayList)) {
			error += "Playlist does not exist!";
		}
		if (aSong == null) {
			error += "Song needs to be selected!";
		}
		else if (!HAS.getSongs().contains(aSong)) {
			error += "Song does not exist!";
		}
		
		// Throw error
		error = error.trim();
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		aPlayList.addSong(aSong);
		PersistenceXStream.saveToXMLwithXStream(HAS);
	}
	
	// Mutes a Location and saves to the HAS
	public void muteLocation(Location aLocation, Boolean muted) throws InvalidInputException {
		String error = "";
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		if (aLocation == null) {
			error += "Location needs to be selected! ";
		}
		else if (!HAS.getLocations().contains(aLocation)) {
			error += "Location does not exist!";
		}
		else if((aLocation.getMuted() == true) && (muted == true)) {
			error += "Location is already muted!";
		}
		else if((aLocation.getMuted() == false) &&(muted == false)) {
			error += "Location is already unmuted!";
		}
		
		error = error.trim();
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		aLocation.setMuted(muted);
		PersistenceXStream.saveToXMLwithXStream(HAS);
	}
	
	// Creates a Player and saves to the HAS
	public void createPlayer(String name) throws InvalidInputException {
		
		if(name == null || name.trim().length() == 0)
		{
			throw new InvalidInputException("Player name cannot be empty!");
		}
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		Player player = new Player(name);
		HAS.addPlayer(player);
		PersistenceXStream.saveToXMLwithXStream(HAS);
	}
	
	// Adds a Location to a Player and saves to the HAS
	public void addLocationToPlayer(Player aPlayer, Location aLocation) throws InvalidInputException {
		String error = "";
		
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		if (aPlayer == null) {
			error += "Player needs to be selected! ";
		}
		else if (!HAS.getPlaylists().contains(aPlayer)) {
			error += "Player does not exist";
		}
		if (aLocation == null) {
			error += "Location needs to be selected! ";
		}
		else if (!HAS.getLocations().contains(aLocation)) {
			error += "Location does not exist";
		}
		
		error = error.trim();
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		aPlayer.addLocation(aLocation);
		PersistenceXStream.saveToXMLwithXStream(HAS);
	}
	
	// Adds a Song to a Player and saves to the HAS
	public void addSongToPlayer(Player aPlayer, Song aSong) throws InvalidInputException {
		String error = "";
		
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		if (aPlayer == null) {
			error += "Player needs to be selected! ";
		}
		else if (!HAS.getPlayers().contains(aPlayer)) {
			error += "Player does not exist!";
		}
		if (aSong == null) {
			error += "Song needs to be selected! ";
		}
		else if (!HAS.getSongs().contains(aSong)) {
			error += "Song does not exist!";
		}
		
		error = error.trim();
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		aPlayer.addSong(aSong);
		PersistenceXStream.saveToXMLwithXStream(HAS);
	}
	
	// Adds an Album to a Player and saves to the HAS
	public void addAlbumToPlayer(Player aPlayer, Album aAlbum) throws InvalidInputException {
		String error = "";
		
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		if (aPlayer == null) {
			error += "Player needs to be selected! ";
		}
		else if (!HAS.getPlayers().contains(aPlayer)) {
			error += "Player does not exist!";
		}
		if (aAlbum == null) {
			error += "Album needs to be selected! ";
		}
		else if (!HAS.getAlbums().contains(aAlbum)) {
			error += "Album does not exist!";
		}
		
		error = error.trim();
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		aPlayer.addAlbum(aAlbum);
		PersistenceXStream.saveToXMLwithXStream(HAS);
	}

	// Adds a Playlist (of Songs) to a Player and saves to the HAS
	public void addPlayListToPlayer(Player aPlayer, Playlist aPlaylist) throws InvalidInputException {
		String error = "";
		
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		if (aPlayer == null) {
			error += "Player needs to be selected! ";
		}
		else if (!HAS.getPlayers().contains(aPlayer)) {
			error += "Player does not exist!";
		}
		if (aPlaylist == null) {
			error += "Playlist needs to be selected! ";
		}
		else if (!HAS.getPlaylists().contains(aPlaylist)) {
			error += "Playlist does not exist!";
		}
		
		error = error.trim();
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		aPlayer.addPlaylist(aPlaylist);
		PersistenceXStream.saveToXMLwithXStream(HAS);
	}
	
	// Takes a Player and sends a POST call to the controller of the location(s)
	public void playContent(Player aPlayer) throws InvalidInputException {
		String error = "";
		boolean checkForContent = false;
		
		// Adds Songs, Albums and Playlists to the Player's Queue.
		if(aPlayer.hasSongs()) {
			List<Song> localSongs = aPlayer.getSongs();
			for (int i = 0; i < localSongs.size(); i++) {
				aPlayer.addSongQueue(localSongs.get(i));
			}
			
			checkForContent = true;
		}
		
		if(aPlayer.hasAlbums()) {
			List<Album> localAlbums = aPlayer.getAlbums();
			for (int i = 0; i < localAlbums.size(); i++) {
				List <Song> temp = localAlbums.get(i).getSongs();
				
				for (int j = 0;j  < temp.size(); j++) {
					aPlayer.addSongQueue(temp.get(j));
				}
			}
			
			if(checkForContent == false) {
				checkForContent = true;
			}
		}
		
		if(aPlayer.hasPlaylists()) {
			List<Playlist> localPlaylists = aPlayer.getPlaylists();
			
			for (int i = 0; i < localPlaylists.size(); i++) {
				List <Song> temp = localPlaylists.get(i).getSongs();
				
				for (int j = 0;j  < temp.size(); j++) {
					aPlayer.addSongQueue(temp.get(j));
				}
			}
			
			if(checkForContent == false) {
				checkForContent = true;
			}
		}
		
		if(checkForContent == false) {
			error += "No content available in Player! ";
		}
		
		if(!aPlayer.hasLocations()) {
			error += "No location selected in Player";
		}
		
		// Throws Error if any
		error = error.trim();
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		List<Location> locations = aPlayer.getLocations();
		Queue<Song> queue = aPlayer.getSongQueue();
		
		// Get the address of the queue in memory (doesn't do much in this case, but symbolically)
		// Encode it and send it over a POST call to every location in the Player.
		String encodedParams = queue.toString();
		byte[] encodedByteParams;
		for (int i = 0; i < locations.size(); i++) {
			
			URL obj;
			HttpURLConnection connect;
			String URL;
			
			try {
				// Encode URL to UTF-8 charset
				encodedByteParams = encodedParams.getBytes("UTF-8");
				URL = "https://www.localhost.com/8080/" + locations.get(i) + "/" + encodedByteParams.toString();
				obj = new URL(URL);
				
				try {
					// Send POST to server
					connect = (HttpURLConnection)obj.openConnection();
					connect.setRequestMethod("POST");
					connect.setRequestProperty("Accept-Charset", "UTF-8");
					connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					
					// To get the input stream, we would uncomment thus
					// InputStream in = connect.getInputStream();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (MalformedURLException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}
