<?php

trait SongController
{
    /**
     *
     * Create a song
     *
     * @param $song_name
     * @param $song_duration
     * @param $albumPosition
     * @param $song_album
     * @param $song_artist
     * @throws Exception
     */
    public function createSong($song_name, $song_duration, $albumPosition, $song_album, $song_artist)
    {
        // 1. Validate input
        $name = Inputvalidator::validate_input($song_name);
        $duration = Inputvalidator::validate_input($song_duration);
        $position = Inputvalidator::validate_input($albumPosition);
        $album = Inputvalidator::validate_input($song_album);
        $artist = Inputvalidator::validate_input($song_artist);

        $error = "";
        if ($name == null || strlen($name) == 0) {
            $error .= "@1Song name cannot be empty! ";
        }

        if ($duration == null || strlen($duration) == 0 || !strtotime($duration)) {
            $error .= "@2Song duration must be specified correctly (MM:SS)! ";
        }

        if ($position == null || strlen($position) == 0) {
            $error .= "@3Album position must be specified! ";
        }

        if ($album == null || strlen($album) == 0) {
            $error .= "@4Album name cannot be empty! ";
        }

        if ($artist == null || strlen($artist) == 0) {
            $error .= "@5Artist name cannot be empty! ";
        }

        if (strlen($error) == 0) {
            // 2. Load all of the data
            $pm = new PersistenceHomeAudioSystem();
            $has = $pm->loadDataFromStore();

            $myAlbum = $this->getAlbum($album);

            // 3. Add the new song
            $song = new Song($name, $duration, $position, $myAlbum);
            $has->addSong($song);

            // 4. Write all of the data
            $pm->writeDataToStore($has);
        } else {
            throw new Exception(trim($error));
        }
    }

    /**
     *
     * Get a song by name
     *
     * @param $song_name
     * @return $song
     */
    public function getSong($song_name)
    {
        $pm = new PersistenceHomeAudioSystem();
        $rm = $pm->loadDataFromStore();

        foreach ($rm->getSongs() as $song) {
            if (strcmp($song->getTitle(), $song_name) == 0) {
                return $song;
            }
        }
        return null;
    }

    /**
     *
     * Get all HAS Songs
     * @return array $songs
     *
     */
    public function getSongs()
    {
        $pm = new PersistenceHomeAudioSystem();
        $rm = $pm->loadDataFromStore();
        return $rm->getSongs();
    }
}