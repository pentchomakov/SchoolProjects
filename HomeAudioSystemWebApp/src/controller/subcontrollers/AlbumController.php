<?php

trait AlbumController
{
    /**
     *
     * Create an Album
     *
     * @param $album_name
     * @param $album_releasedate
     * @param $album_genre
     * @param $album_artist
     * @throws Exception
     */
    public function createAlbum($album_name, $album_releasedate, $album_genre, $album_artist)
    {
        // 1. Validate input
        $name = Inputvalidator::validate_input($album_name);
        $release = Inputvalidator::validate_input($album_releasedate);
        $artist_name = Inputvalidator::validate_input($album_artist);
        $genre = Inputvalidator::validate_input($album_genre);

        $error = "";
        if ($name == null || strlen($name) == 0) {
            $error .= "Album name cannot be empty! ";
        }

        if ($release == null || strlen($release) == 0) {
            $error .= "Album release date must be specified correctly (YYYY-MM-DD)! ";
        }

        if ($genre == null || strlen($genre) == 0) {
            $error .= "Album genre cannot be empty! ";
        }

        if ($artist_name == null || strlen($artist_name) == 0) {
            $error .= "Artist name cannot be empty! ";
        }

        if (strlen($error) == 0) {
            $pm = new PersistenceHomeAudioSystem();
            $rm = $pm->loadDataFromStore();

            $myArtist = $this->getArtist($artist_name);

            // 3. Add the new album
            $album = new Album($name, $release, $myArtist);
            $rm->addAlbum($album);

            // 4. Write all of the data
            $pm->writeDataToStore($rm);
        } else {
            throw new Exception(trim($error));
        }
    }

    /**
     *
     * Get an album by name
     *
     * @param $album_name
     * @return $album
     */
    public function getAlbum($album_name)
    {
        $name = Inputvalidator::validate_input($album_name);
        $pm = new PersistenceHomeAudioSystem();
        $rm = $pm->loadDataFromStore();
        foreach ($rm->getAlbums() as $album) {
            if (strcmp($album->getName(), $name) == 0) {
                return $album;
            }
        }
        return null;
    }


    /**
     * Get all HAS Albums
     * @return array $albums
     */
    public function getAlbums()
    {
        $pm = new PersistenceHomeAudioSystem();
        $rm = $pm->loadDataFromStore();
        return $rm->getAlbums();
    }
}