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


class IntegrationTest extends PHPUnit_Framework_TestCase
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

    public function testCreateAlbumSongArtist()
    {
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getSongs()));

        $artistName = "Bach";
        $albumName = "Brandenburg Concerto";
        $release = "1740";
        $songName = "Brandenburg Concerto No 5";
        $albumPosition = "1";
        $duration = "3:00";

        $error = "";
        try {
            $this->c->createArtist($artistName);
            $this->c->createAlbum($albumName, $release, Genre::$Classical, $artistName);
            $this->c->createSong($songName, $duration, $albumPosition, $albumName, $artistName);
        } catch (Exception $e) {
            $error = $e->getMessage();
            $this->fail();
        }

        $this->assertEquals($error, "");
        $this->assertEquals(1, count($this->c->getAlbums()));
        $this->assertEquals(1, count($this->c->getArtists()));
        $this->assertEquals(1, count($this->c->getSongs()));
    }


    public function testCreateAlbumSongArtistDoesNotExist()
    {
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getSongs()));

        $artistName = "Bach";
        $albumName = "Brandenburg Concerto";
        $release = "1740";
        $songName = "Brandenburg Concerto No 5";
        $albumPosition = "1";
        $duration = "3:00";

        $error = "";
        try {
            $this->c->createAlbum($albumName, $release, Genre::$Classical, $artistName);
            $this->c->createSong($songName, $duration, $albumPosition, $albumName, $artistName);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals($error, "Unable to create album due to artist");
        $this->assertEquals(0, count($this->c->getAlbums()));
        $this->assertEquals(0, count($this->c->getArtists()));
        $this->assertEquals(0, count($this->c->getSongs()));
    }

    public function testCreateAlbumArtistSongDoesNotExist()
    {
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getSongs()));

        $artistName = "Bach";
        $albumName = "Brandenburg Concerto";
        $release = "1740";
        $songName = "Brandenburg Concerto No 5";

        $error = "";
        try {
            $this->c->createArtist($artistName);
            $this->c->createAlbum($albumName, $release, Genre::$Classical, $artistName);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals($error, "");
        $this->assertEquals(1, count($this->c->getAlbums()));
        $this->assertEquals(1, count($this->c->getArtists()));
        $this->assertEquals(0, count($this->c->getSongs()));
    }

    public function testCreateArtistSongAlbumDoesNotExist()
    {
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getSongs()));

        $artistName = "Bach";
        $albumName = "Brandenburg Concerto";
        $songName = "Brandenburg Concerto No 5";
        $albumPosition = "1";
        $duration = "3:00";

        $error = "";
        try {
            $this->c->createArtist($artistName);
            $this->c->createSong($songName, $duration, $albumPosition, $albumName, $artistName);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals($error, "Unable to create song due to album");
        $this->assertEquals(0, count($this->c->getAlbums()));
        $this->assertEquals(1, count($this->c->getArtists()));
        $this->assertEquals(0, count($this->c->getSongs()));
    }
}