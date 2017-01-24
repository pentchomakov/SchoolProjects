<?php
/*PLEASE DO NOT EDIT THIS CODE*/

/*This code was generated using the UMPLE 1.24.0-3fa5fec modeling language!*/

class Artist
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Artist Attributes
    public $name;

    //Artist Associations
    public $songs;
    public $albums;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public function __construct($aName)
    {
        $this->name = $aName;
        $this->songs = array();
        $this->albums = array();
    }

    //------------------------
    // INTERFACE
    //------------------------

    public function setName($aName)
    {
        $wasSet = false;
        $this->name = $aName;
        $wasSet = true;
        return $wasSet;
    }

    public function getName()
    {
        return $this->name;
    }

    public function getSong_index($index)
    {
        $aSong = $this->songs[$index];
        return $aSong;
    }

    public function getSongs()
    {
        $newSongs = $this->songs;
        return $newSongs;
    }

    public function numberOfSongs()
    {
        $number = count($this->songs);
        return $number;
    }

    public function hasSongs()
    {
        $has = $this->numberOfSongs() > 0;
        return $has;
    }

    public function indexOfSong($aSong)
    {
        $wasFound = false;
        $index = 0;
        foreach ($this->songs as $song) {
            if ($song->equals($aSong)) {
                $wasFound = true;
                break;
            }
            $index += 1;
        }
        $index = $wasFound ? $index : -1;
        return $index;
    }

    public function getAlbum_index($index)
    {
        $aAlbum = $this->albums[$index];
        return $aAlbum;
    }

    public function getAlbums()
    {
        $newAlbums = $this->albums;
        return $newAlbums;
    }

    public function numberOfAlbums()
    {
        $number = count($this->albums);
        return $number;
    }

    public function hasAlbums()
    {
        $has = $this->numberOfAlbums() > 0;
        return $has;
    }

    public function indexOfAlbum($aAlbum)
    {
        $wasFound = false;
        $index = 0;
        foreach ($this->albums as $album) {
            if ($album->equals($aAlbum)) {
                $wasFound = true;
                break;
            }
            $index += 1;
        }
        $index = $wasFound ? $index : -1;
        return $index;
    }

    public static function minimumNumberOfSongs()
    {
        return 0;
    }

    public function addSong($aSong)
    {
        $wasAdded = false;
        if ($this->indexOfSong($aSong) !== -1) {
            return false;
        }
        $existingArtist = $aSong->getArtist();
        if ($existingArtist == null) {
            $aSong->setArtist($this);
        } elseif ($this !== $existingArtist) {
            $existingArtist->removeSong($aSong);
            $this->addSong($aSong);
        } else {
            $this->songs[] = $aSong;
        }
        $wasAdded = true;
        return $wasAdded;
    }

    public function removeSong($aSong)
    {
        $wasRemoved = false;
        if ($this->indexOfSong($aSong) != -1) {
            unset($this->songs[$this->indexOfSong($aSong)]);
            $this->songs = array_values($this->songs);
            if ($this === $aSong->getArtist()) {
                $aSong->setArtist(null);
            }
            $wasRemoved = true;
        }
        return $wasRemoved;
    }

    public function addSongAt($aSong, $index)
    {
        $wasAdded = false;
        if ($this->addSong($aSong)) {
            if ($index < 0) {
                $index = 0;
            }
            if ($index > $this->numberOfSongs()) {
                $index = $this->numberOfSongs() - 1;
            }
            array_splice($this->songs, $this->indexOfSong($aSong), 1);
            array_splice($this->songs, $index, 0, array($aSong));
            $wasAdded = true;
        }
        return $wasAdded;
    }

    public function addOrMoveSongAt($aSong, $index)
    {
        $wasAdded = false;
        if ($this->indexOfSong($aSong) !== -1) {
            if ($index < 0) {
                $index = 0;
            }
            if ($index > $this->numberOfSongs()) {
                $index = $this->numberOfSongs() - 1;
            }
            array_splice($this->songs, $this->indexOfSong($aSong), 1);
            array_splice($this->songs, $index, 0, array($aSong));
            $wasAdded = true;
        } else {
            $wasAdded = $this->addSongAt($aSong, $index);
        }
        return $wasAdded;
    }

    public static function minimumNumberOfAlbums()
    {
        return 0;
    }

    public function addAlbumVia($aName, $aReleaseDate)
    {
        return new Album($aName, $aReleaseDate, $this);
    }

    public function addAlbum($aAlbum)
    {
        $wasAdded = false;
        if ($this->indexOfAlbum($aAlbum) !== -1) {
            return false;
        }
        $existingArtist = $aAlbum->getArtist();
        $isNewArtist = $existingArtist != null && $this !== $existingArtist;
        if ($isNewArtist) {
            $aAlbum->setArtist($this);
        } else {
            $this->albums[] = $aAlbum;
        }
        $wasAdded = true;
        return $wasAdded;
    }

    public function removeAlbum($aAlbum)
    {
        $wasRemoved = false;
        //Unable to remove aAlbum, as it must always have a artist
        if ($this !== $aAlbum->getArtist()) {
            unset($this->albums[$this->indexOfAlbum($aAlbum)]);
            $this->albums = array_values($this->albums);
            $wasRemoved = true;
        }
        return $wasRemoved;
    }

    public function addAlbumAt($aAlbum, $index)
    {
        $wasAdded = false;
        if ($this->addAlbum($aAlbum)) {
            if ($index < 0) {
                $index = 0;
            }
            if ($index > $this->numberOfAlbums()) {
                $index = $this->numberOfAlbums() - 1;
            }
            array_splice($this->albums, $this->indexOfAlbum($aAlbum), 1);
            array_splice($this->albums, $index, 0, array($aAlbum));
            $wasAdded = true;
        }
        return $wasAdded;
    }

    public function addOrMoveAlbumAt($aAlbum, $index)
    {
        $wasAdded = false;
        if ($this->indexOfAlbum($aAlbum) !== -1) {
            if ($index < 0) {
                $index = 0;
            }
            if ($index > $this->numberOfAlbums()) {
                $index = $this->numberOfAlbums() - 1;
            }
            array_splice($this->albums, $this->indexOfAlbum($aAlbum), 1);
            array_splice($this->albums, $index, 0, array($aAlbum));
            $wasAdded = true;
        } else {
            $wasAdded = $this->addAlbumAt($aAlbum, $index);
        }
        return $wasAdded;
    }

    public function equals($compareTo)
    {
        return $this == $compareTo;
    }

    public function delete()
    {
        foreach ($this->songs as $aSong) {
            $aSong->setArtist(null);
        }
        foreach ($this->albums as $aAlbum) {
            $aAlbum->delete();
        }
    }

}


?>