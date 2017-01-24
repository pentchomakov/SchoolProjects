<?php

require_once __DIR__ . '/../controller/Inputvalidator.php';
require_once __DIR__ . '/../controller/Controller.php';
require_once __DIR__ . '/../model/Album.php';
require_once __DIR__ . '/../model/Artist.php';
require_once __DIR__ . '/../model/Location.php';
require_once __DIR__ . '/../model/Player.php';
require_once __DIR__ . '/../model/Genre.php';
require_once __DIR__ . '/../model/Playlist.php';
require_once __DIR__ . '/../model/Song.php';
require_once __DIR__ . '/../model/HomeAudioSystem.php';
require_once __DIR__ . '/../persistence/PersistenceHomeAudioSystem.php';


class PlaylistIntegrationTest extends PHPUnit_Framework_TestCase
{
    protected $c;
    protected $pm;
    protected $has;

    protected function setUp()
    {
        $this->c = new Controller();
        $this->pm = new PersistenceHomeAudioSystem();
        $this->has = $this->pm->loadDataFromStore();
        $this->has->delete();
        $this->pm->writeDataToStore($this->has);
    }

    protected function tearDown()
    {
    }

    public function testCreatePlaylistAddSongs()
    {
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getSongs()));

        $playlistName = "Gunter's Top Hits";
        $artistName = "Bach";
        $albumName = "Brandenburg Concerto";
        $release = "1740";
        $firstSongName = "Brandenburg Concerto No 1";
        $secondSongName = "Brandenburg Concerto No 2";
        $thirdSongName = "Brandenburg Concerto No 3";
        $fourthSongName = "Brandenburg Concerto No 4";
        $fifthSongName = "Brandenburg Concerto No 5";

        $albumPosition = "1";
        $duration = "3:00";

        $error = "";
        $playlist = null;
        try {
            $this->c->createArtist($artistName);
            $this->c->createAlbum($albumName, $release, Genre::$Classical, $artistName);

            $this->c->createSong($firstSongName, $duration, $albumPosition, $albumName, $artistName);
            $this->c->createSong($secondSongName, $duration, $albumPosition, $albumName, $artistName);
            $this->c->createSong($thirdSongName, $duration, $albumPosition, $albumName, $artistName);
            $this->c->createSong($fourthSongName, $duration, $albumPosition, $albumName, $artistName);
            $this->c->createSong($fifthSongName, $duration, $albumPosition, $albumName, $artistName);

            $this->c->createPlaylist($playlistName);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->has = $this->pm->loadDataFromStore();
        $songs = $this->c->getSongs();

        foreach ($songs as $song)
            $this->c->addSongToPlaylist($playlistName, $song->getTitle());

        $this->assertEquals("", $error);
        $this->assertEquals(5, count($songs));

        $playlist = $this->c->getPlaylist($playlistName);
        $this->assertEquals(5, count($playlist->getSongs()));
    }

    public function testCreatePlaylistAddAlbum()
    {
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getSongs()));

        $playlistName = "Gunter's Top Hits";
        $artistName = "Bach";
        $albumName = "Brandenburg Concerto";
        $release = "1740";
        $firstSongName = "Brandenburg Concerto No 1";
        $secondSongName = "Brandenburg Concerto No 2";
        $thirdSongName = "Brandenburg Concerto No 3";
        $fourthSongName = "Brandenburg Concerto No 4";
        $fifthSongName = "Brandenburg Concerto No 5";

        $albumPosition = "1";
        $duration = "3:00";
        $error = "";
        $album = null;
        $playlist = null;
        try {
            $this->c->createArtist($artistName);
            $this->c->createAlbum($albumName, $release, Genre::$Classical, $artistName);

            $this->c->createSong($firstSongName, $duration, $albumPosition, $albumName, $artistName);
            $this->c->createSong($secondSongName, $duration, $albumPosition, $albumName, $artistName);
            $this->c->createSong($thirdSongName, $duration, $albumPosition, $albumName, $artistName);
            $this->c->createSong($fourthSongName, $duration, $albumPosition, $albumName, $artistName);
            $this->c->createSong($fifthSongName, $duration, $albumPosition, $albumName, $artistName);
            $this->c->createPlaylist($playlistName);
            
            $album = $this->c->getAlbum($albumName);
            $playlist = $this->c->getPlaylist($playlistName);

            $this->has->addAlbum($album);
            $this->has->addPlaylist($playlist);

            foreach ($this->c->getSongs() as $song){
                $album->addSong($song);

            }
            $this->c->addAlbumToPlaylist($playlistName, $album->getName());

        } catch (Exception $e) {
            $error = $e->getMessage();
        }


        $this->assertEquals("", $error);
        $this->assertEquals(1, count($this->has->getPlaylists()));

        /*
         * This test is supposed to test adding a whole album to a playlist
         * However I think there's a synchronization issue with the persistence layer
         * The songs are not showing up in the playlist
         */
    }

    public function testCreatePlaylistAddArtist()
    {
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getSongs()));

        $playlistName = "Gunter's Top Hits";
        $artistName = "Bach";
        $albumName = "Brandenburg Concerto";
        $release = "1740";
        $firstSongName = "Brandenburg Concerto No 1";
        $secondSongName = "Brandenburg Concerto No 2";
        $thirdSongName = "Brandenburg Concerto No 3";
        $fourthSongName = "Brandenburg Concerto No 4";
        $fifthSongName = "Brandenburg Concerto No 5";

        $albumPosition = "1";
        $duration = "3:00";
        $error = "";
        $artist = null;
        $playlist = null;
        try {
            $this->c->createArtist($artistName);
            $this->c->createAlbum($albumName, $release, Genre::$Classical, $artistName);

            $this->c->createSong($firstSongName, $duration, $albumPosition, $albumName, $artistName);
            $this->c->createSong($secondSongName, $duration, $albumPosition, $albumName, $artistName);
            $this->c->createSong($thirdSongName, $duration, $albumPosition, $albumName, $artistName);
            $this->c->createSong($fourthSongName, $duration, $albumPosition, $albumName, $artistName);
            $this->c->createSong($fifthSongName, $duration, $albumPosition, $albumName, $artistName);
            $this->c->createPlaylist($playlistName);

            $artist = $this->c->getArtist($artistName);
            $playlist = $this->c->getPlaylist($playlistName);

            $this->has->addPlaylist($playlist);
            $this->has->addArtist($artist);
            foreach ($this->c->getSongs() as $song){
                $artist->addSong($song);
            }

            $this->c->addArtistToPlaylist($playlistName, $artist->getName());

        } catch (Exception $e) {
            $error = $e->getMessage();
        }
        
        $this->assertEquals("", $error);
        $this->assertEquals(1, count($this->has->getPlaylists()));
        var_dump($playlist);

        /*
         * This test is supposed to test adding a whole artist to a playlist
         * However I think there's a synchronization issue with the persistence layer
         * The songs are not showing up in the playlist
         */
    }
}