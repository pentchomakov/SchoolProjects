<?php

require_once 'SongController.php';

trait PlaylistController
{
    /**
     *
     * Create a Playlist
     *
     * @param $playlist_name
     * @throws Exception
     */
    public function createPlaylist($playlist_name)
    {
        // 1. Validate input
        $name = Inputvalidator::validate_input($playlist_name);

        $error = "";
        if ($name == null || strlen($name) == 0) {
            $error .= "@1Playlist name cannot be empty! ";
        }

        if (strlen($error) == 0) {
            // 2. Load all of the data
            $pm = new PersistenceHomeAudioSystem();
            $has = $pm->loadDataFromStore();

            // 3. Add the new playlist
            $playlist = new Playlist($name);
            $has->addPlaylist($playlist);

            // 4. Write all of the data
            $pm->writeDataToStore($has);
        } else {
            throw new Exception(trim($error));
        }
    }

    /**
     *
     * Get a playlist by name
     *
     * @param $playlist_name
     * @return null
     */
    public function getPlaylist($playlist_name)
    {
        $name = Inputvalidator::validate_input($playlist_name);

        $pm = new PersistenceHomeAudioSystem();
        $has = $pm->loadDataFromStore();

        foreach ($has->getPlaylists() as $playlist)
            if (strcmp($playlist->getTitle(), $name) == 0)
                return $playlist;
        return null;
    }

    /**
     *
     * Get all HAS Playlists
     *
     * @return array $playlists
     */
    public function getPlaylists()
    {
        $pm = new PersistenceHomeAudioSystem();
        $has = $pm->loadDataFromStore();
        return $has->getPlaylists();
    }

    public function addSongToPlaylist($playlistName, $songName)
    {
        $pm = new PersistenceHomeAudioSystem();
        $has = $pm->loadDataFromStore();

        $myplaylist = null;
        foreach ($has->getPlaylists() as $playlist)
            if (strcmp($playlist->getTitle(), $playlistName) == 0)
                $myplaylist = $playlist;

        $mysong = null;
        foreach ($has->getSongs() as $song)
            if (strcmp($song->getTitle(), $songName) == 0)
                $mysong = $song;

        $error = "";
        if ($myplaylist != NULL && $mysong != NULL) {
            $myplaylist->addSong($mysong);
            $has->addPlaylist($myplaylist);
            $pm->writeDataToStore($has);
        } else {
            if ($myplaylist == NULL) {
                $error .= "@1Playlist " . $playlistName . " not found! ";
            }

            if ($mysong == NULL) {
                $error .= "@2Song " . $playlistName . " not found! ";
            }
            throw new Exception(trim($error));
        }
    }

    public function addArtistToPlaylist($playlistName, $artistName)
    {
        $pm = new PersistenceHomeAudioSystem();
        $has = $pm->loadDataFromStore();

        $playlist = $this->getPlaylist($playlistName);
        $artist = $this->getArtist($artistName);

        $error = "";
        if ($playlist == NULL) {
            $error .= "@1Playlist " . $playlistName . " not found! ";
        }

        if ($artist == NULL) {
            $error .= "@2Artist " . $artistName . " not found! ";
        }

        if (strlen($error) == 0) {
            foreach ($artist->getSongs() as $song) {
                $playlist->addSong($song);
            }
            $pm->writeDataToStore($has);
        } else {
            throw new Exception(trim($error));
        }
    }

    public function addAlbumToPlaylist($playlistName, $albumName)
    {
        $pm = new PersistenceHomeAudioSystem();
        $has = $pm->loadDataFromStore();

        $myplaylist = $this->getPlaylist($playlistName);
        $myalbum = $this->getAlbum($albumName);

        $error = "";
        if ($myplaylist != NULL && $myalbum != NULL) {
            foreach ($myalbum->getSongs() as $song) {
                $myplaylist->addSong($song);
            }
            $has->addPlaylist($myplaylist);
            $pm->writeDataToStore($has);
        } else {
            if ($myplaylist == NULL) {
                $error .= "@1Playlist " . $playlistName . " not found! ";
            }

            if ($myalbum == NULL) {
                $error .= "@2Album " . $albumName . " not found! ";
            }
            throw new Exception(trim($error));
        }
    }
}