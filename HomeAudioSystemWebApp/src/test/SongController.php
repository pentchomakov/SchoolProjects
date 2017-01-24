<?php

require_once __DIR__ . '/../controller/Inputvalidator.php';
require_once __DIR__ . '/../controller/Controller.php';
require_once __DIR__ . '/../model/Album.php';
require_once __DIR__ . '/../model/Artist.php';
require_once __DIR__ . '/../model/Song.php';
require_once __DIR__ . '/../model/Player.php';
require_once __DIR__ . '/../model/Genre.php';
require_once __DIR__ . '/../model/Playlist.php';
require_once __DIR__ . '/../model/HomeAudioSystem.php';
require_once __DIR__ . '/../persistence/PersistenceHomeAudioSystem.php';


class SongControllerTest extends PHPUnit_Framework_TestCase
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

    public function testCreateSong()
    {
        $this->assertEquals(0, count($this->has->getSongs()));

        $artistName = "Bach";

        $albumName = "Brandenburg Concerto No. 5";
        $releaseDate = "1795";

        $title = "Living Room";
        $duration = "1:00";
        $albumPosition = "3";

        $error = "";
        try {
            $this->c->createArtist($artistName);
            $this->c->createAlbum($albumName, $releaseDate, $albumPosition, $artistName);
            $this->c->createSong($title, $duration, $albumPosition, $albumName, $artistName);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // No Error
        $this->assertEquals($error, "");

        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(1, count($this->has->getSongs()));
        $this->assertEquals($title, $this->has->getSong_index(0)->getTitle());
        $this->assertEquals($duration, $this->has->getSong_index(0)->getDuration());
        $this->assertEquals($albumPosition, $this->has->getSong_index(0)->getAlbumPos());
    }

    public function testCreateSongNull()
    {
        $this->assertEquals(0, count($this->has->getSongs()));

        $artistName = "Bach";

        $genre = Genre::$Pop;
        $albumName = "Brandenburg Concerto No. 5";
        $releaseDate = "1795";

        $title = null;
        $duration = null;
        $albumPosition = null;

        $error = "";
        try {
            $this->c->createArtist($artistName);
            $this->c->createAlbum($albumName, $releaseDate, $genre, $artistName);
            $this->c->createSong($title, $duration, $albumPosition, $albumName, $artistName);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals('@1Song name cannot be empty! @2Song duration must be specified correctly (MM:SS)! @3Album position must be specified!', $error);
        // check file contents
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(1, count($this->has->getAlbums()));
        $this->assertEquals(1, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getSongs()));
    }

    public function testCreateSongEmpty()
    {
        $this->assertEquals(0, count($this->has->getSongs()));

        $artistName = "Bach";

        $genre = Genre::$Pop;
        $albumName = "Brandenburg Concerto No. 5";
        $releaseDate = "1795";

        $title = "";
        $duration = "";
        $albumPosition = "";

        $error = "";
        try {
            $this->c->createArtist($artistName);
            $this->c->createAlbum($albumName, $releaseDate, $genre, $artistName);
            $this->c->createSong($title, $duration, $albumPosition, $albumName, $artistName);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals('@1Song name cannot be empty! @2Song duration must be specified correctly (MM:SS)! @3Album position must be specified!', $error);
        // check file contents
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(1, count($this->has->getAlbums()));
        $this->assertEquals(1, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getSongs()));
    }

    public function testCreateSongSpaces()
    {
        $this->assertEquals(0, count($this->has->getSongs()));

        $artistName = "Bach";

        $genre = Genre::$Pop;
        $albumName = "Brandenburg Concerto No. 5";
        $releaseDate = "1795";

        $title = " ";
        $duration = " ";
        $albumPosition = "  ";

        $error = "";
        try {
            $this->c->createArtist($artistName);
            $this->c->createAlbum($albumName, $releaseDate, $genre, $artistName);
            $this->c->createSong($title, $duration, $albumPosition, $albumName, $artistName);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals('@1Song name cannot be empty! @2Song duration must be specified correctly (MM:SS)! @3Album position must be specified!', $error);
        // check file contents
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(1, count($this->has->getAlbums()));
        $this->assertEquals(1, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getSongs()));
    }
}