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


class LocationControllerTest extends PHPUnit_Framework_TestCase
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

    public function testCreateLocation()
    {
        $this->assertEquals(0, count($this->has->getLocations()));

        $name = "Living Room";
        $vol = "100";
        $muted = "false";

        try {
            $this->c->createLocation($name, $vol, $muted);
        } catch (Exception $e) {
            $this->fail();
        }

        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(1, count($this->has->getLocations()));
        $this->assertEquals($name, $this->has->getLocation_index(0)->getName());
        $this->assertEquals($vol, $this->has->getLocation_index(0)->getVolume());
        $this->assertEquals($muted, $this->has->getLocation_index(0)->getMuted());
    }

    public function testCreateLocationNull()
    {
        $this->assertEquals(0, count($this->has->getLocations()));

        $name = null;
        $vol = null;
        $muted = null;

        try {
            $this->c->createLocation($name, $vol, $muted);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals('@1Location name cannot be empty! @2Location volume cannot be empty! @3Location muted cannot be empty!', $error);
        // check file contents
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getSongs()));
    }

    public function testCreateLocationEmpty()
    {
        $this->assertEquals(0, count($this->has->getLocations()));

        $name = "";
        $vol = "";
        $muted = "";

        try {
            $this->c->createLocation($name, $vol, $muted);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals('@1Location name cannot be empty! @2Location volume cannot be empty! @3Location muted cannot be empty!', $error);
        // check file contents
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getSongs()));
    }

    public function testCreateLocationSpaces()
    {
        $this->assertEquals(0, count($this->has->getLocations()));

        $name = "    ";
        $vol = " ";
        $muted = "   ";

        try {
            $this->c->createLocation($name, $vol, $muted);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals('@1Location name cannot be empty! @2Location volume cannot be empty! @3Location muted cannot be empty!', $error);
        // check file contents
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getSongs()));
    }
}