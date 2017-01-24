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

class AlbumControllerTest extends PHPUnit_Framework_TestCase {
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

    public function testCreateAlbum()
    {
        $this->assertEquals(0, count($this->has->getAlbums()));

        $artist_name = "Bach";

        try {
            $this->c->createArtist($artist_name);
        } catch (Exception $e) {
            $this->fail();
        }

        $album_name = "Brandenburg Concerto No. 2 in F major, BWV 1047";
        $release = "1721-03-24";
        try {
            $this->c->createAlbum($album_name, $release, Genre::$Classical, $artist_name);
        } catch (Exception $e) {
            $this->fail();
        }

        // check file contents
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(1, count($this->has->getAlbums()));
        $this->assertEquals($album_name, $this->has->getAlbum_index(0)->getName());
        $this->assertEquals(1, count($this->has->getArtists()));
        $this->assertEquals($artist_name, $this->has->getArtist_index(0)->getName());
        $this->assertEquals(0, count($this->has->getSongs()));
    }

    public function testCreateAlbumNull()
    {
        $this->assertEquals(0, count($this->has->getAlbums()));

        $name = null;
        $release = null;
        $artist = null;
        $genre = null;
        $error = "";
        try {
            $this->c->createAlbum($name, $release, $genre, $artist);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // check error
        $this->assertEquals('Album name cannot be empty! Album release date must be specified correctly (YYYY-MM-DD)! Album genre cannot be empty! Artist name cannot be empty!', $error);
        // check file contents
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getSongs()));
    }

    public function testCreateAlbumEmpty()
    {
        $this->assertEquals(0, count($this->has->getAlbums()));

        $name = "";
        $release = "";
        $genre = "";
        $error = "";
        $artist_name = "";

        try {
            $this->c->createAlbum($name, $release, $genre, $artist_name);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // check error
        $this->assertEquals('Album name cannot be empty! Album release date must be specified correctly (YYYY-MM-DD)! Album genre cannot be empty! Artist name cannot be empty!', $error);
        // check file contents
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getSongs()));
    }

    public function testCreateAlbumSpaces()
    {
        $this->assertEquals(0, count($this->has->getAlbums()));

        $artist_name = "Bach";

        $name = " ";
        $release = " ";
        $genre = " ";
        $error = "";
        try {
            $this->c->createArtist($artist_name);
            $this->c->createAlbum($name, $release, $genre, $artist_name);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // check error
        $this->assertEquals("Album name cannot be empty! Album release date must be specified correctly (YYYY-MM-DD)! Album genre cannot be empty!", $error);
        // check file contents
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(1, count($this->has->getArtists()));
        $this->assertEquals($artist_name, $this->has->getArtist_index(0)->getName());
        $this->assertEquals(0, count($this->has->getSongs()));
    }
}