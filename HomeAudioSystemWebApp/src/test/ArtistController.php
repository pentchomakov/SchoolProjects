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

class ArtistControllerTest extends PHPUnit_Framework_TestCase
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

    public function testCreateArtist()
    {
        $this->assertEquals(0, count($this->has->getArtists()));

        $name = "Bach";

        try {
            $this->c->createArtist($name);
        } catch (Exception $e) {
            $this->fail();
        }

        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(1, count($this->has->getArtists()));
        $this->assertEquals($name, $this->has->getArtist_index(0)->getName());
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getSongs()));
    }

    public function testCreateArtistNull()
    {
        $this->assertEquals(0, count($this->has->getArtists()));

        $name = null;

        $error = "";
        try {
            $this->c->createArtist($name);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // check error
        $this->assertEquals("Artist name cannot be empty!", $error);
        // check file contents
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getSongs()));
    }

    public function testCreateArtistEmpty()
    {
        $this->assertEquals(0, count($this->has->getArtists()));

        $name = "";

        $error = "";
        try {
            $this->c->createArtist($name);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // check error
        $this->assertEquals("Artist name cannot be empty!", $error);
        // check file contents
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getSongs()));
    }

    public function testCreateArtistSpaces()
    {
        $this->assertEquals(0, count($this->has->getArtists()));

        $name = " ";

        $error = "";
        try {
            $this->c->createArtist($name);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // check error
        $this->assertEquals("Artist name cannot be empty!", $error);
        // check file contents
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getSongs()));
    }
}