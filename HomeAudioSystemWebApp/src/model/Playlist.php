<?php


/*PLEASE DO NOT EDIT THIS CODE*/

/*This code was generated using the UMPLE 1.24.0-3fa5fec modeling language!*/

class Playlist
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Playlist Attributes
    public $title;

    //Playlist Associations
    public $songs;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public function __construct($aTitle)
    {
        $this->title = $aTitle;
        $this->songs = array();
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

    public function getTitle()
    {
        return $this->title;
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
        $existingPlaylist = $aSong->getPlaylist();
        if ($existingPlaylist == null) {
            $aSong->setPlaylist($this);
        } elseif ($this !== $existingPlaylist) {
            $existingPlaylist->removeSong($aSong);
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
            if ($this === $aSong->getPlaylist()) {
                $aSong->setPlaylist(null);
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

    public function equals($compareTo)
    {
        return $this == $compareTo;
    }

    public function delete()
    {
        foreach ($this->songs as $aSong) {
            $aSong->setPlaylist(null);
        }
    }

}


?>
