/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package model;
import java.sql.Date;
import java.util.*;
import java.sql.Time;

// line 3 "../JavaHomeAudioSystem.ump"
// line 31 "../HomeAudioSystem.ump"
public class Album
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Album Attributes
  private String name;
  private Date releaseDate;

  //Album Associations
  private List<Song> songs;
  private Artist artist;
  
 
  public enum Genre {Pop, Rock, Alternative, Rap, RB, Classical, Jazz, Contemporary, Country, Electronic, Latin, Gospel, Comedy, Reggae, AmericanRoots};

  private Genre genre ;
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Album(String aName, Date aReleaseDate, Artist aArtist, Album.Genre genre)
  {
    name = aName;
    releaseDate = aReleaseDate;
    songs = new ArrayList<Song>();
    this.genre= genre;
    boolean didAddArtist = setArtist(aArtist);
    if (!didAddArtist)
    {
      throw new RuntimeException("Unable to create album due to artist");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setReleaseDate(Date aReleaseDate)
  {
    boolean wasSet = false;
    releaseDate = aReleaseDate;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public Date getReleaseDate()
  {
    return releaseDate;
  }

  public Song getSong(int index)
  {
    Song aSong = songs.get(index);
    return aSong;
  }

  public List<Song> getSongs()
  {
    List<Song> newSongs = Collections.unmodifiableList(songs);
    return newSongs;
  }

  public int numberOfSongs()
  {
    int number = songs.size();
    return number;
  }

  public boolean hasSongs()
  {
    boolean has = songs.size() > 0;
    return has;
  }

  public int indexOfSong(Song aSong)
  {
    int index = songs.indexOf(aSong);
    return index;
  }

  public Artist getArtist()
  {
    return artist;
  }

  public static int minimumNumberOfSongs()
  {
    return 0;
  }

  public Song addSong(String aTitle, Time aDuration, int aAlbumPos)
  {
    return new Song(aTitle, aDuration, aAlbumPos, this);
  }

  public boolean addSong(Song aSong)
  {
    boolean wasAdded = false;
    if (songs.contains(aSong)) { return false; }
    Album existingAlbum = aSong.getAlbum();
    boolean isNewAlbum = existingAlbum != null && !this.equals(existingAlbum);
    if (isNewAlbum)
    {
      aSong.setAlbum(this);
    }
    else
    {
      songs.add(aSong);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSong(Song aSong)
  {
    boolean wasRemoved = false;
    //Unable to remove aSong, as it must always have a album
    if (!this.equals(aSong.getAlbum()))
    {
      songs.remove(aSong);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addSongAt(Song aSong, int index)
  {  
    boolean wasAdded = false;
    if(addSong(aSong))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSongs()) { index = numberOfSongs() - 1; }
      songs.remove(aSong);
      songs.add(index, aSong);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSongAt(Song aSong, int index)
  {
    boolean wasAdded = false;
    if(songs.contains(aSong))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSongs()) { index = numberOfSongs() - 1; }
      songs.remove(aSong);
      songs.add(index, aSong);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSongAt(aSong, index);
    }
    return wasAdded;
  }

  public boolean setArtist(Artist aArtist)
  {
    boolean wasSet = false;
    if (aArtist == null)
    {
      return wasSet;
    }

    Artist existingArtist = artist;
    artist = aArtist;
    if (existingArtist != null && !existingArtist.equals(aArtist))
    {
      existingArtist.removeAlbum(this);
    }
    artist.addAlbum(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=songs.size(); i > 0; i--)
    {
      Song aSong = songs.get(i - 1);
      aSong.delete();
    }
    Artist placeholderArtist = artist;
    this.artist = null;
    placeholderArtist.removeAlbum(this);
  }

  // line 8 "../JavaHomeAudioSystem.ump"
   public void setGenre(Genre genre){
    this.genre=genre;
  }

  // line 12 "../JavaHomeAudioSystem.ump"
   public Genre getGenre(){
    return genre;
  }
   
   public static String[] names() {
	    return Arrays.toString(Genre.values()).replaceAll("^.|.$", "").split(", ");
	}

  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "releaseDate" + "=" + (getReleaseDate() != null ? !getReleaseDate().equals(this)  ? getReleaseDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "artist = "+(getArtist()!=null?Integer.toHexString(System.identityHashCode(getArtist())):"null")
     + outputString;
  }  
  
  


  
}