package persistence;

import java.util.Iterator;

import model.Album;
import model.Artist;
import model.HomeAudioSystem;
import model.Location;
import model.Playlist;
import model.Song;


public class PersistenceHomeAudioSystem {
static String fileName="";
	
	private static void initializeXStream()
	{
		PersistenceXStream.setFilename("HomeAudioSystem.xml"); 
		PersistenceXStream.setAlias("Artist", Artist.class);
		PersistenceXStream.setAlias("Album", Album.class);
		PersistenceXStream.setAlias("Song", Song.class);
		PersistenceXStream.setAlias("Location", Location.class);
		PersistenceXStream.setAlias("Playlist", Playlist.class);
	}
	
	public static void loadHomeAudioSystemModel()
	{
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		PersistenceHomeAudioSystem.initializeXStream();
		HomeAudioSystem HAS2 = (HomeAudioSystem) PersistenceXStream.loadFromXMLwithXStream();
		if(HAS2 != null)
		{
			Iterator<Artist> IA = HAS2.getArtists().iterator();
			while(IA.hasNext())
			{
				HAS.addArtist(IA.next());
			}
			Iterator<Album> IAL = HAS2.getAlbums().iterator();
			while(IAL.hasNext())
			{
				HAS.addAlbum(IAL.next());
			}
			Iterator <Song> IS= HAS2.getSongs().iterator();
			while(IS.hasNext()){
				HAS.addSong(IS.next());
			}
			Iterator <Location> IL = HAS2.getLocations().iterator();
			while(IL.hasNext()){
				HAS.addLocation(IL.next());				
			}
			Iterator <Playlist> IP= HAS2.getPlaylists().iterator();
			while(IP.hasNext())
			{
				HAS.addPlaylist(IP.next());
			}
		}
	}
	
    public static void setFileName(String name) {
        fileName = name;
    }
	
}



