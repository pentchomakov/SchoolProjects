/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package model;
import java.sql.Time;

// line 46 "../HomeAudioSystem.ump"
public class Song
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Song Attributes
  private String title;
  private Time duration;
  private int albumPos;

  //Song Associations
  private Album album;
  private Artist artist;
  private Playlist playlist;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Song(String aTitle, Time aDuration, int aAlbumPos, Album aAlbum)
  {
    title = aTitle;
    duration = aDuration;
    albumPos = aAlbumPos;
    boolean didAddAlbum = setAlbum(aAlbum);
    if (!didAddAlbum)
    {
      throw new RuntimeException("Unable to create song due to album");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTitle(String aTitle)
  {
    boolean wasSet = false;
    title = aTitle;
    wasSet = true;
    return wasSet;
  }

  public boolean setDuration(Time aDuration)
  {
    boolean wasSet = false;
    duration = aDuration;
    wasSet = true;
    return wasSet;
  }

  public boolean setAlbumPos(int aAlbumPos)
  {
    boolean wasSet = false;
    albumPos = aAlbumPos;
    wasSet = true;
    return wasSet;
  }

  public String getTitle()
  {
    return title;
  }

  public Time getDuration()
  {
    return duration;
  }

  public int getAlbumPos()
  {
    return albumPos;
  }

  public Album getAlbum()
  {
    return album;
  }

  public Artist getArtist()
  {
    return artist;
  }

  public boolean hasArtist()
  {
    boolean has = artist != null;
    return has;
  }

  public Playlist getPlaylist()
  {
    return playlist;
  }

  public boolean hasPlaylist()
  {
    boolean has = playlist != null;
    return has;
  }

  public boolean setAlbum(Album aAlbum)
  {
    boolean wasSet = false;
    if (aAlbum == null)
    {
      return wasSet;
    }

    Album existingAlbum = album;
    album = aAlbum;
    if (existingAlbum != null && !existingAlbum.equals(aAlbum))
    {
      existingAlbum.removeSong(this);
    }
    album.addSong(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setArtist(Artist aArtist)
  {
    boolean wasSet = false;
    Artist existingArtist = artist;
    artist = aArtist;
    if (existingArtist != null && !existingArtist.equals(aArtist))
    {
      existingArtist.removeSong(this);
    }
    if (aArtist != null)
    {
      aArtist.addSong(this);
    }
    wasSet = true;
    return wasSet;
  }

  public boolean setPlaylist(Playlist aPlaylist)
  {
    boolean wasSet = false;
    Playlist existingPlaylist = playlist;
    playlist = aPlaylist;
    if (existingPlaylist != null && !existingPlaylist.equals(aPlaylist))
    {
      existingPlaylist.removeSong(this);
    }
    if (aPlaylist != null)
    {
      aPlaylist.addSong(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Album placeholderAlbum = album;
    this.album = null;
    placeholderAlbum.removeSong(this);
    if (artist != null)
    {
      Artist placeholderArtist = artist;
      this.artist = null;
      placeholderArtist.removeSong(this);
    }
    if (playlist != null)
    {
      Playlist placeholderPlaylist = playlist;
      this.playlist = null;
      placeholderPlaylist.removeSong(this);
    }
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "title" + ":" + getTitle()+ "," +
            "albumPos" + ":" + getAlbumPos()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "duration" + "=" + (getDuration() != null ? !getDuration().equals(this)  ? getDuration().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "album = "+(getAlbum()!=null?Integer.toHexString(System.identityHashCode(getAlbum())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "artist = "+(getArtist()!=null?Integer.toHexString(System.identityHashCode(getArtist())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "playlist = "+(getPlaylist()!=null?Integer.toHexString(System.identityHashCode(getPlaylist())):"null")
     + outputString;
  }
}