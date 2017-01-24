<?php

/*PLEASE DO NOT EDIT THIS CODE*/

/*This code was generated using the UMPLE 1.24.0-3fa5fec modeling language!*/

class Song
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Song Attributes
    public $title;
    public $duration;
    public $albumPos;

    //Song Associations
    public $album;
    public $artist;
    public $playlist;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public function __construct($aTitle, $aDuration, $aAlbumPos, $aAlbum)
    {
        $this->title = $aTitle;
        $this->duration = $aDuration;
        $this->albumPos = $aAlbumPos;
        $didAddAlbum = $this->setAlbum($aAlbum);
        if (!$didAddAlbum) {
            throw new Exception("Unable to create song due to album");
        }
    }

    //------------------------
    // INTERFACE
    //------------------------

    public function setTitle($aTitle)
    {
        $wasSet = false;
        $this->title = $aTitle;
        $wasSet = true;
        return $wasSet;
    }

    public function setDuration($aDuration)
    {
        $wasSet = false;
        $this->duration = $aDuration;
        $wasSet = true;
        return $wasSet;
    }

    public function setAlbumPos($aAlbumPos)
    {
        $wasSet = false;
        $this->albumPos = $aAlbumPos;
        $wasSet = true;
        return $wasSet;
    }

    public function getTitle()
    {
        return $this->title;
    }

    public function getDuration()
    {
        return $this->duration;
    }

    public function getAlbumPos()
    {
        return $this->albumPos;
    }

    public function getAlbum()
    {
        return $this->album;
    }

    public function getArtist()
    {
        return $this->artist;
    }

    public function hasArtist()
    {
        $has = $this->artist != null;
        return $has;
    }

    public function getPlaylist()
    {
        return $this->playlist;
    }

    public function hasPlaylist()
    {
        $has = $this->playlist != null;
        return $has;
    }

    public function setAlbum($aAlbum)
    {
        $wasSet = false;
        if ($aAlbum == null) {
            return $wasSet;
        }

        $existingAlbum = $this->album;
        $this->album = $aAlbum;
        if ($existingAlbum != null && $existingAlbum != $aAlbum) {
            $existingAlbum->removeSong($this);
        }
        $this->album->addSong($this);
        $wasSet = true;
        return $wasSet;
    }

    public function setArtist($aArtist)
    {
        $wasSet = false;
        $existingArtist = $this->artist;
        $this->artist = $aArtist;
        if ($existingArtist != null && $existingArtist !== $aArtist) {
            $existingArtist->removeSong($this);
        }
        if ($aArtist != null && $aArtist !== $existingArtist) {
            $aArtist->addSong($this);
        }
        $wasSet = true;
        return $wasSet;
    }

    public function setPlaylist($aPlaylist)
    {
        $wasSet = false;
        $existingPlaylist = $this->playlist;
        $this->playlist = $aPlaylist;
        if ($existingPlaylist != null && $existingPlaylist !== $aPlaylist) {
            $existingPlaylist->removeSong($this);
        }
        if ($aPlaylist != null && $aPlaylist !== $existingPlaylist) {
            $aPlaylist->addSong($this);
        }
        $wasSet = true;
        return $wasSet;
    }

    public function equals($compareTo)
    {
        return $this == $compareTo;
    }

    public function delete()
    {
        $placeholderAlbum = $this->album;
        $this->album = null;
        $placeholderAlbum->removeSong($this);
        if ($this->artist != null) {
            $this->artist->removeSong($this);
        }
        if ($this->playlist != null) {
            $this->playlist->removeSong($this);
        }
    }

}

?>
