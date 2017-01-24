package controller;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Time;
import java.sql.Date;
import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Album;
import model.Album.Genre;
import model.Artist;
import model.HomeAudioSystem;
import model.Location;
import model.Player;
import model.Playlist;
import model.Song;
import persistence.PersistenceXStream;

public class TestHomeAudioSystemController {

	private Artist artist1;
	private Artist artist2;
	private Album album1;
	private Song song1;
	private Playlist playlist1;
	private Player player1;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.setFilename("test"+File.separator+"data.xml"); 
		PersistenceXStream.setAlias("Artist", Artist.class);
		PersistenceXStream.setAlias("Album", Album.class);
		PersistenceXStream.setAlias("Song", Song.class);
		PersistenceXStream.setAlias("Location", Location.class);
		PersistenceXStream.setAlias("Playlist", Playlist.class);
	}
	
	@Before
	public void initAllObjects(){
		
		// Artist
		String aName= "Adele";
		artist1 = new Artist(aName);
		artist2 = new Artist("Drake");
		
		// Album
		java.sql.Date releaseDate = java.sql.Date.valueOf( "2016-01-31" );
		album1= new Album ("Take Care", releaseDate, artist2, Album.Genre.Rap);	
		
		// Song
		String title = "Marche Slave";
		String time = "00:08:55";
		Time duration = Time.valueOf(time);
		int albumPos = 1;
		song1 = new Song (title, duration, albumPos, album1);
		
		// Playlist
		String title2 = "Gunter's Playlist";
		playlist1 = new Playlist(title2);
		
		// Player
		String name = "Player1";
		player1 = new Player(name);
	}
	
	
	@After
	public void tearDown() throws Exception {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		HAS.delete();
	}
	
	@Test
	public void testCreateArtist()
	{
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getArtists().size());
		
		String name = "Adele";
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try
		{
			HASc.createArtist(name);
		}
		catch (InvalidInputException e)
		{
			// Check that no error occurred
			fail();
		}
		
		checkResultArtist(name, HAS);
		
		HomeAudioSystem HAS2 = (HomeAudioSystem) PersistenceXStream.loadFromXMLwithXStream();
		
		checkResultArtist( name, HAS2);
	}

	
	@Test
	public void testCreateArtistNull()
	{
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getArtists().size());
		
		String name = null;
		String error = null;
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try
		{
			HASc.createArtist(name);
		}
		catch (InvalidInputException e)
		{
			error=e.getMessage();
		}
		
		assertEquals("Artist name cannot be empty!", error);
		
		assertEquals(0, HAS.getArtists().size());
	}
	
	@Test
	public void testCreateArtistEmpty()
	{
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getArtists().size());
		
		String name = "";
		String error = null;
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try
		{
			HASc.createArtist(name);
		}
		catch (InvalidInputException e)
		{
			error=e.getMessage();
		}
		
		assertEquals("Artist name cannot be empty!", error);
		
		assertEquals(0, HAS.getArtists().size());
	}
	
	@Test
	public void testCreateArtistSpaces()
	{
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getArtists().size());
		
		String name = " ";
		String error = null;
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try
		{
			HASc.createArtist(name);
		}
		catch (InvalidInputException e)
		{
			error=e.getMessage();
		}
		
		assertEquals("Artist name cannot be empty!", error);
		
		assertEquals(0, HAS.getArtists().size());
	}
	
	
	@Test
	public void testCreateAlbum() {
		
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getAlbums().size());
		
		String name = "21";
		java.sql.Date releaseDate = java.sql.Date.valueOf( "2016-01-31" );
		Genre genre = Genre.Pop;

		HAS.addArtist(artist1);
		assertEquals(1, HAS.getArtists().size());
			
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		try
		{
			HASc.createAlbum(name,releaseDate,artist1,genre);
		}
		catch (InvalidInputException e)
		{
			// Check that no error occurred
			fail();
		}
		
		checkResultAlbum(name, artist1, releaseDate, HAS);
		
		HomeAudioSystem HAS2 = (HomeAudioSystem) PersistenceXStream.loadFromXMLwithXStream();
		
		// Check file contents
		checkResultAlbum(name, artist1, releaseDate, HAS2);
	}

	
	@Test
	public void testcreateAlbumNull(){
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getAlbums().size());
		
		String name =null;
		Date releaseDate = null;
		Artist artist= null;
		assertEquals(0, HAS.getArtists().size());
		Genre genre=null;
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		String error=null;
		try
		{
			HASc.createAlbum(name,releaseDate,artist,genre);
		}
		catch (InvalidInputException e)
		{
			error=e.getMessage();
		}
		assertEquals("Album name cannot be empty!Release Date cannot be empty!Artist cannot be empty!Genre cannot be empty!", error);
		assertEquals(0,HAS.getAlbums().size());
		
	}

	@Test
	
	public void testcreateAlbumEmptyName(){
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getAlbums().size());
		
		String name ="";
		java.sql.Date releaseDate = java.sql.Date.valueOf( "2016-01-31" );
		Genre genre = Genre.Pop;
		

		HAS.addArtist(artist1);
		assertEquals(1, HAS.getArtists().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		String error=null;
		try
		{
			HASc.createAlbum(name,releaseDate,artist1, genre);
		}
		catch (InvalidInputException e)
		{
			error=e.getMessage();
		}
		assertEquals("Album name cannot be empty!", error);
		assertEquals(0,HAS.getAlbums().size());
		
	}

	@Test
	
	public void testcreateAlbumSpacesName(){
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getAlbums().size());
		
		String name =" ";
		java.sql.Date releaseDate = java.sql.Date.valueOf( "2016-01-31" );
		Genre genre = Genre.Pop;
		
		String aName= "Adele";
		Artist artist = new Artist(aName);
		HAS.addArtist(artist);
		assertEquals(1, HAS.getArtists().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		String error=null;
		try
		{
			HASc.createAlbum(name,releaseDate,artist, genre);
		}
		catch (InvalidInputException e)
		{
			error=e.getMessage();
		}
		assertEquals("Album name cannot be empty!", error);
		assertEquals(0,HAS.getAlbums().size());
		
	}
	
	@Test
	public void testcreateSong(){
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getAlbums().size());
		
		String title = "Over";
		String time = "0:3:53";
		Time duration = Time.valueOf(time);
		int albumPos= 1;

		HAS.addArtist(artist2);
		assertEquals(1,HAS.getArtists().size());
		
		HAS.addAlbum(album1);
		assertEquals(1,HAS.getAlbums().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		String error=null;

		try{
			HASc.createSong(title,duration,albumPos,album1);
		}
		catch(InvalidInputException e){
			fail();	
		}
		checkResultSong(title, duration, albumPos, album1, HAS);
		
		HomeAudioSystem HAS2 = (HomeAudioSystem) PersistenceXStream.loadFromXMLwithXStream();
		
		checkResultSong(title, duration, albumPos, album1, HAS2);	
	}

	
	
	@Test
	public void testNullTitleSong(){
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getSongs().size());

		String title = "";
		String time = "00:03:53";
		Time duration = Time.valueOf(time);
		int albumPos= 1;
		
	
		HAS.addArtist(artist2);
		assertEquals(1,HAS.getArtists().size());
		

		HAS.addAlbum(album1);
		assertEquals(1,HAS.getAlbums().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		String error=null;

		try{
			HASc.createSong(title,duration,albumPos,album1);
		}
		catch(InvalidInputException e){
			error=e.getMessage();		
		}
		assertEquals("Song title cannot be empty!", error);
		assertEquals(0, HAS.getSongs().size());
	}
	
	@Test
	public void testZeroLengthSong() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getSongs().size());

		String title = "Over";
		String time = "00:00:00";
		Time duration = Time.valueOf(time);
		int albumPos= 1;
		
	
		HAS.addArtist(artist2);
		assertEquals(1,HAS.getArtists().size());
		
	
		HAS.addAlbum(album1);
		assertEquals(1,HAS.getAlbums().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		String error=null;

		try{
			HASc.createSong(title,duration,albumPos,album1);
		}
		catch(InvalidInputException e){
			error=e.getMessage();		
		}
		assertEquals("Song must have a duration!", error);
		assertEquals(0, HAS.getSongs().size());
	}
	
	@Test
	public void testNullPositionSong() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getSongs().size());

		String title = "Over";
		String time = "00:03:50";
		Time duration = Time.valueOf(time);
		int albumPos = 0;
			
		HAS.addArtist(artist2);
		assertEquals(1,HAS.getArtists().size());
		

		HAS.addAlbum(album1);
		assertEquals(1,HAS.getAlbums().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		String error=null;

		try{
			HASc.createSong(title, duration, albumPos, album1);
		}
		catch(InvalidInputException e){
			error = e.getMessage();		
		}
		assertEquals("Song position on Album must not be empty!", error);
		assertEquals(0, HAS.getSongs().size());

	}
	
	@Test
	public void testNegativePositionSong(){
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getSongs().size());
		
		String title2 = "Started From The Bottom";
		String time2 = "00:03:30";
		Time duration2 = Time.valueOf(time2);
		int albumPos2 = -5;
		
		HAS.addArtist(artist2);
		assertEquals(1,HAS.getArtists().size());
		
		HAS.addAlbum(album1);
		assertEquals(1,HAS.getAlbums().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		String error=null;

		try{
			HASc.createSong(title2, duration2, albumPos2, album1);
		}
		catch(InvalidInputException e){
			error = e.getMessage();		
		}
		assertEquals("Song position on Album must not be empty!", error);
		assertEquals(0, HAS.getSongs().size());
	}
	
	
	@Test
	public void testCreateSongNullAlbum() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getSongs().size());

		String title = "Over";
		String time = "00:03:50";
		Time duration = Time.valueOf(time);
		int albumPos = 1;
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		String error = null;

		try{
			HASc.createSong(title, duration, albumPos, null);
		}
		catch(InvalidInputException e){
			error = e.getMessage();		
		}
		assertEquals("Song must be assigned an Album!", error);
		assertEquals(0, HAS.getSongs().size());
	}
	
	@Test
	public void testSpacesTitleSong(){
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getSongs().size());

		String title = "  ";
		String time = "00:03:53";
		Time duration = Time.valueOf(time);
		int albumPos= 1;
		
	
		HAS.addArtist(artist2);
		assertEquals(1,HAS.getArtists().size());
		
		HAS.addAlbum(album1);
		assertEquals(1,HAS.getAlbums().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		String error=null;

		try{
			HASc.createSong(title,duration,albumPos,album1);
		}
		catch(InvalidInputException e){
			error=e.getMessage();		
		}
		assertEquals("Song title cannot be empty!", error);
		assertEquals(0, HAS.getSongs().size());
	}
	
	/**
	 * @param title
	 * @param duration
	 * @param albumPos
	 * @param album
	 * @param HAS2
	 */
	private void checkResultSong(String title, Time duration, int albumPos,
			Album album, HomeAudioSystem HAS2) {
		
		assertEquals(1, HAS2.getSongs().size());
		assertEquals(title,HAS2.getSong(0).getTitle());
		assertEquals(duration,HAS2.getSong(0).getDuration());
		assertEquals(albumPos, HAS2.getSong(0).getAlbumPos());
		assertEquals(album.getName(), HAS2.getSong(0).getAlbum().getName());
		assertEquals(album.getReleaseDate(), HAS2.getSong(0).getAlbum().getReleaseDate());	
		assertEquals(album.getArtist().getName(), HAS2.getSong(0).getAlbum().getArtist().getName());	
		assertEquals(album.getGenre(), HAS2.getSong(0).getAlbum().getGenre());
	}
	
	/**
	 * @param HAS
	 * @param name
	 * @param HAS2
	 */
	private void checkResultArtist(String name,
			HomeAudioSystem HAS2) {
		assertEquals(1, HAS2.numberOfArtists());
		assertEquals(name, HAS2.getArtist(0).getName());
		assertEquals(0, HAS2.getAlbums().size());
		assertEquals(0, HAS2.getSongs().size());
		assertEquals(0, HAS2.getLocations().size());
		assertEquals(0, HAS2.getPlaylists().size());
	}
	
	/**
	 * @param name
	 * @param releaseDate
	 * @param HAS2
	 * @param artist
	 */
	private void checkResultAlbum(String name, Artist artist, java.sql.Date releaseDate,
			HomeAudioSystem HAS2) {
		assertEquals(1, HAS2.getAlbums().size());
		assertEquals(name, HAS2.getAlbum(0).getName());
		assertEquals(releaseDate, HAS2.getAlbum(0).getReleaseDate());
		assertEquals(artist.getName(), HAS2.getAlbum(0).getArtist().getName());
		assertEquals(1, HAS2.getArtists().size());
		assertEquals(0, HAS2.getSongs().size());
		assertEquals(0, HAS2.getLocations().size());
		assertEquals(0, HAS2.getPlaylists().size());
	}
	
	@Test
	public void testCreateLocation()
	{
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getLocations().size());
		
		String name = "Bedroom";
		int volume = 5;
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try {
			HASc.createLocation(name, volume);
		} catch (InvalidInputException e) {
			// Check that no error occurred
			fail();
		}
		
		checkResultLocation(name, volume, HAS);
		
		HomeAudioSystem HAS2 = (HomeAudioSystem) PersistenceXStream.loadFromXMLwithXStream();
		
		checkResultLocation(name, volume, HAS2);
	}

	private void checkResultLocation(String name, int volume, HomeAudioSystem HAS2) {
		assertEquals(1, HAS2.numberOfLocations());
		assertEquals(name, HAS2.getLocation(0).getName());
		assertEquals(0, HAS2.getAlbums().size());
		assertEquals(0, HAS2.getSongs().size());
		assertEquals(0, HAS2.getPlaylists().size());
	}
	
	@Test
	public void testCreateLocationNull()
	{
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getLocations().size());
		
		String name = null;
		int volume = 5;
		String error = null;
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try {
			HASc.createLocation(name, volume);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Location name cannot be empty!", error);
		assertEquals(0, HAS.getLocations().size());
	}
	
	@Test
	public void testCreateLocationEmpty()
	{
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getLocations().size());
		
		String name = "";
		int volume = 5;
		String error = null;
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try {
			HASc.createLocation(name, volume);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Location name cannot be empty!", error);
		assertEquals(0, HAS.getLocations().size());
	}
	
	@Test
	public void testCreateLocationSpaces()
	{
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getLocations().size());
		
		String name = " ";
		int volume = 5;
		String error = null;
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try {
			HASc.createLocation(name, volume);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Location name cannot be empty!", error);
		assertEquals(0, HAS.getLocations().size());
	}
	
	@Test
	public void testCreatePlaylist() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getPlaylists().size());
		
		String title = "Gunter's Playlist";
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try {
			HASc.createPlaylist(title);
		} catch (InvalidInputException e) {
			// Check that no error occurred
			fail();
		}
		
		checkResultPlaylist(title, HAS);
		HomeAudioSystem HAS2 = (HomeAudioSystem) PersistenceXStream.loadFromXMLwithXStream();
		checkResultPlaylist(title, HAS2);
	}
	
	@Test
	public void testCreatePlaylistNull () {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getPlaylists().size());
		
		String title = null;
		String error = null;
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try {
			HASc.createPlaylist(title);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Playlist title cannot be empty!", error);
		assertEquals(0, HAS.getPlaylists().size());
	}
	

	@Test
	public void testCreatePlaylistEmpty () {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getPlaylists().size());
		
		String title = "";
		String error = null;
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try {
			HASc.createPlaylist(title);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Playlist title cannot be empty!", error);
		assertEquals(0, HAS.getPlaylists().size());
	}
	

	@Test
	public void testCreatePlaylistSpaces () {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getPlaylists().size());
		
		String title = "   ";
		String error = null;
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try {
			HASc.createPlaylist(title);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Playlist title cannot be empty!", error);
		assertEquals(0, HAS.getPlaylists().size());
	}
	
	@Test
	public void testAddSongToPlaylistNull() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		assertEquals(0, HAS.getSongs().size());
		HAS.addSong(song1);
		assertEquals(1, HAS.getSongs().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addSongToPlaylist(song1, null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Playlist needs to be selected!", error);
		assertEquals(0, HAS.getPlaylists().size());
	}
	
	@Test
	public void testAddSongNullToPlaylist() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		assertEquals(0, HAS.getPlaylists().size());
		HAS.addPlaylist(playlist1);
		assertEquals(1, HAS.getPlaylists().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addSongToPlaylist(null, playlist1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Song needs to be selected!", error);
		assertEquals(1, HAS.getPlaylists().size());
	}
	
	@Test
	public void testAddSongPlaylistSongNotSaved() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		String title = "Temp";
		String time = "00:09:30";
		Time duration = Time.valueOf(time);
		int albumPos = 1;
		
		Song tempSong = new Song(title, duration, albumPos, album1);
		
		assertEquals(0, HAS.getPlaylists().size());
		HAS.addPlaylist(playlist1);
		assertEquals(1, HAS.getPlaylists().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addSongToPlaylist(tempSong, playlist1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Song does not exist!", error);
		assertEquals(1, HAS.getPlaylists().size());
		assertEquals(0, HAS.getSongs().size());
	}
	
	@Test
	public void testAddSongPlaylistPlaylistNotSaved() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		assertEquals(0, HAS.getSongs().size());
		HAS.addSong(song1);
		assertEquals(1, HAS.getSongs().size());
		
		
		assertEquals(0, HAS.getPlaylists().size());
		Playlist tempPL = new Playlist("tempPlayList");
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addSongToPlaylist(song1, tempPL);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Playlist does not exist!", error);
		assertEquals(0, HAS.getPlaylists().size());
	}

	private void checkResultPlaylist(String title, HomeAudioSystem HAS2) {
		assertEquals(1, HAS2.numberOfPlaylists());
		assertEquals(title, HAS2.getPlaylist(0).getTitle());
		assertEquals(0, HAS2.getAlbums().size());
		assertEquals(0, HAS2.getSongs().size());
		assertEquals(1, HAS2.getPlaylists().size());	
	}
	
	@Test
	public void testCreatePlayer() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getPlayers().size());
		
		String name = "Player1";
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try {
			HASc.createPlayer(name);
		} catch (InvalidInputException e) {
			// Check that no error occurred
			fail();
		}
		
		checkResultPlayer(name, HAS);
		
		HomeAudioSystem HAS2 = (HomeAudioSystem) PersistenceXStream.loadFromXMLwithXStream();
		
		checkResultPlayer(name, HAS2);
	}
	
	@Test
	public void testCreatePlayerNull () {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getPlayers().size());
		
		String name = null;
		String error = null;
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try {
			HASc.createPlayer(name);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Player name cannot be empty!", error);
		assertEquals(0, HAS.getPlayers().size());
	}
	

	@Test
	public void testCreatePlayerEmpty () {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getPlayers().size());
		
		String name = "";
		String error = null;
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try {
			HASc.createPlayer(name);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Player name cannot be empty!", error);
		assertEquals(0, HAS.getPlayers().size());
	}
	

	@Test
	public void testCreatePlayerSpaces () {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		assertEquals(0, HAS.getPlayers().size());
		
		String title = "   ";
		String error = null;
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		try {
			HASc.createPlayer(title);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Player name cannot be empty!", error);
		assertEquals(0, HAS.getPlayers().size());
	}

	private void checkResultPlayer(String name, HomeAudioSystem HAS2) {
		assertEquals(1, HAS2.numberOfPlayers());
		assertEquals(name, HAS2.getPlayer(0).getName());
		assertEquals(0, HAS2.getAlbums().size());
		assertEquals(0, HAS2.getSongs().size());
		assertEquals(0, HAS2.getLocations().size());
		assertEquals(0, HAS2.getPlaylists().size());
	}
	
	@Test
	public void testAddSongNullToPlayer() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		assertEquals(0, HAS.getPlayers().size());
		HAS.addPlayer(player1);
		assertEquals(1, HAS.getPlayers().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addSongToPlayer(player1, null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Song needs to be selected!", error);
		assertEquals(1, HAS.getPlayers().size());
		assertEquals(0, HAS.getPlayer(0).getSongs().size());
	}
	
	@Test
	public void testAddSongToPlayerNull() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		assertEquals(0, HAS.getSongs().size());
		HAS.addSong(song1);
		assertEquals(1, HAS.getSongs().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addSongToPlayer(null, song1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Player needs to be selected!", error);
		assertEquals(0, HAS.getPlayers().size());
		
	}
	
	@Test
	public void testAddSongToPlayerSongPlayerNotSaved() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		assertEquals(0, HAS.getSongs().size());
		HAS.addSong(song1);
		assertEquals(1, HAS.getSongs().size());
		
		
		assertEquals(0, HAS.getPlayers().size());
		Player tempPlayer = new Player("TempPlayer");
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addSongToPlayer(tempPlayer, song1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Player does not exist!", error);
		assertEquals(0, HAS.getPlayers().size());
	}
	
	@Test
	public void testAddSongToPlayerSongNotSaved() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		assertEquals(0, HAS.getSongs().size());
		HAS.addPlayer(player1);
		assertEquals(1, HAS.getPlayers().size());
		
		String title = "Temp";
		String time = "00:09:30";
		Time duration = Time.valueOf(time);
		int albumPos = 1;
		
		Song TempSong = new Song(title, duration, albumPos, album1);
		
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addSongToPlayer(player1, TempSong);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Song does not exist!", error);
		assertEquals(1, HAS.getPlayers().size());
		assertEquals(0, HAS.getSongs().size());
	}
	
	@Test
	public void testAddAlbumNullToPlayer() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		assertEquals(0, HAS.getPlayers().size());
		HAS.addPlayer(player1);
		assertEquals(1, HAS.getPlayers().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addAlbumToPlayer(player1, null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Album needs to be selected!", error);
		assertEquals(1, HAS.getPlayers().size());
		assertEquals(0, HAS.getPlayer(0).getAlbums().size());
	}
	
	@Test
	public void testAddAlbumToPlayerNull() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		assertEquals(0, HAS.getAlbums().size());
		HAS.addAlbum(album1);
		assertEquals(1, HAS.getAlbums().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addAlbumToPlayer(null, album1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Player needs to be selected!", error);
		assertEquals(0, HAS.getPlayers().size());
	}
	
	@Test
	public void testAddAlbumToPlayerPlayerNotSaved() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		assertEquals(0, HAS.getAlbums().size());
		HAS.addAlbum(album1);
		assertEquals(1, HAS.getAlbums().size());
		
		
		assertEquals(0, HAS.getPlayers().size());
		Player tempPlayer = new Player("TempPlayer");
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addAlbumToPlayer(tempPlayer, album1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Player does not exist!", error);
		assertEquals(0, HAS.getPlayers().size());
	}
	
	@Test
	public void testAddAlbumToPlayerAlbumNotSaved() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		assertEquals(0, HAS.getPlayers().size());
		HAS.addPlayer(player1);
		assertEquals(1, HAS.getPlayers().size());
		
//		java.sql.Date releaseDate = java.sql.Date.valueOf( "2016-01-31" );
//		Album TempAlbum = new Album ("Take Care", releaseDate, artist2, Album.Genre.Rap);	
		assertEquals(0, HAS.getAlbums().size());
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addAlbumToPlayer(player1, album1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Album does not exist!", error);
		assertEquals(1, HAS.getPlayers().size());
		assertEquals(0, HAS.getAlbums().size());
	}
	
	@Test
	public void testAddPlaylistNullToPlayer() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		assertEquals(0, HAS.getPlayers().size());
		HAS.addPlayer(player1);
		assertEquals(1, HAS.getPlayers().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addPlayListToPlayer(player1, null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Playlist needs to be selected!", error);
		assertEquals(1, HAS.getPlayers().size());
		assertEquals(0, HAS.getPlaylists().size());
	}
	
	@Test
	public void testAddPlaylistToPlayerNull() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		assertEquals(0, HAS.getPlaylists().size());
		HAS.addPlaylist(playlist1);
		assertEquals(1, HAS.getPlaylists().size());
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addPlayListToPlayer(null, playlist1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Player needs to be selected!", error);
		assertEquals(0, HAS.getPlayers().size());
	}
	
	@Test
	public void testAddPlaylistToPlayerPlayerNotSaved() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		assertEquals(0, HAS.getPlaylists().size());
		HAS.addPlaylist(playlist1);
		assertEquals(1, HAS.getPlaylists().size());
		
		
		assertEquals(0, HAS.getPlayers().size());
		Player tempPlayer = new Player("TempPlayer");
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addPlayListToPlayer(tempPlayer, playlist1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Player does not exist!", error);
		assertEquals(0, HAS.getPlayers().size());
	}
	
	@Test
	public void testAddPlaylistToPlayerPlaylistNotSaved() {
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		assertEquals(0, HAS.getPlayers().size());
		HAS.addPlayer(player1);
		assertEquals(1, HAS.getPlayers().size());
		
		Playlist playlistTemp = new Playlist("PlaylistTemp");
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		
		String error = "";
		
		try {
			HASc.addPlayListToPlayer(player1, playlistTemp);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("Playlist does not exist!", error);
		assertEquals(1, HAS.getPlayers().size());
		assertEquals(0, HAS.getPlayer(0).getPlaylists().size());
	}
}
