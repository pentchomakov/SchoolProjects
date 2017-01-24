package persistence;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Album;
import model.Artist;
import model.HomeAudioSystem;
import model.Location;
import model.Playlist;
import model.Song;
import model.Album.Genre;
import model.Player;
import persistence.PersistenceXStream;

public class TestPersistence {

	@Before
	public void setUp() throws Exception{
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		//Create Artist
		String aName= "Adele";
		Artist artist = new Artist(aName);
		
		
		//Create Album
		String name = "21";
	    java.sql.Date releaseDate = java.sql.Date.valueOf( "2016-01-31" );
		Genre genre = Genre.Pop;
		Album album= new Album (name,releaseDate,artist,genre);
		
		//Create Songs
		String title = "Over";
		String time = "0:3:53";
		Time duration = Time.valueOf(time);
		int albumPos= 1;
		Song song = new Song(title, duration, albumPos, album);
			
		//Create Locations
		String room ="Bedroom";
		int volume= 60;
		boolean isMuted= false;
		Location location= new Location (room, volume);
			
		
		//Create Playlist	
		String playlistName = "Studying";
		Playlist nP = new Playlist(playlistName);
		
		HAS.addPlaylist(nP);
		HAS.addArtist(artist);
		HAS.addAlbum(album);
		HAS.addSong(song);
		HAS.addLocation(location);
		
	}
	
	@After
	public void tearDown() throws Exception{
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		HAS.delete();
	}
	
	
	@Test
	public void test() {
		
		//SAVE
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		PersistenceXStream.setFilename("test"+File.separator+"persistence"+File.separator+"data.xml"); 
		PersistenceXStream.setAlias("Artist", Artist.class);
		PersistenceXStream.setAlias("Album", Album.class);
		PersistenceXStream.setAlias("Song", Song.class);
		PersistenceXStream.setAlias("Location", Location.class);
		PersistenceXStream.setAlias("Playlist", Playlist.class);
		if(!PersistenceXStream.saveToXMLwithXStream(HAS))
		{
			fail("Could not save file.");
		}
		
		//CLEAR
		HAS.delete();
		assertEquals(0, HAS.getArtists().size());
		assertEquals(0, HAS.getAlbums().size());
		assertEquals(0, HAS.getSongs().size());
		assertEquals(0, HAS.getLocations().size());
		assertEquals(0, HAS.getPlaylists().size());
		
		//LOAD
	    HAS = (HomeAudioSystem) PersistenceXStream.loadFromXMLwithXStream();
	    if(HAS == null)
		{
			fail("Could not load file.");
		}
	    
	    //Check Artist
	    assertEquals(1, HAS.getArtists().size());
		assertEquals("Adele", HAS.getArtist(0).getName());
	    
	    //Check Album
		 assertEquals(1, HAS.getAlbums().size());
		 assertEquals("21", HAS.getAlbum(0).getName());
		 java.sql.Date releaseDate = java.sql.Date.valueOf( "2016-01-31" );
		 assertEquals(releaseDate.toString(), HAS.getAlbum(0).getReleaseDate().toString());	
		 assertEquals("Adele", HAS.getAlbum(0).getArtist().getName());
		 assertEquals(Genre.Pop,HAS.getAlbum(0).getGenre());
		 
		 
		 //Check Song
		 assertEquals(1, HAS.getSongs().size());
		 assertEquals("Over", HAS.getSong(0).getTitle());
		 String time = "0:3:53";
		 Time duration = Time.valueOf(time);
		 assertEquals(duration,HAS.getSong(0).getDuration());
		 assertEquals(1, HAS.getSong(0).getAlbumPos());
		 assertEquals(HAS.getAlbum(0),HAS.getSong(0).getAlbum());
		 
		 
		 //Check Location
		 assertEquals(1, HAS.getLocations().size());
		 assertEquals("Bedroom", HAS.getLocation(0).getName());
		 assertEquals(60, HAS.getLocation(0).getVolume());
		 assertEquals(false, HAS.getLocation(0).getMuted());

		 	 
		 //Check playList
		 assertEquals(1, HAS.getPlaylists().size());
		 assertEquals("Studying", HAS.getPlaylist(0).getTitle());
		
		
	}

}
