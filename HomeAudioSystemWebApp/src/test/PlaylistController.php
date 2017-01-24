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

class PlaylistControllerTest extends PHPUnit_Framework_TestCase
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

    public function testCreatePlaylist()
    {
        $this->assertEquals(0, count($this->has->getPlaylists()));

        $name = "Playlist";

        try {
            $this->c->createPlaylist($name);
        } catch (Exception $e) {
            $this->fail();
        }

        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(1, count($this->has->getPlaylists()));
        $this->assertEquals($name, $this->has->getPlaylist_index(0)->getTitle());
    }

    public function testCreatePlaylistNull()
    {
        $this->assertEquals(0, count($this->has->getPlaylists()));

        $name = null;

        try {
            $this->c->createPlaylist($name);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals('@1Playlist name cannot be empty!', $error);
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->has->getPlaylists()));
    }

    public function testCreatePlaylistEmpty()
    {
        $this->assertEquals(0, count($this->has->getPlaylists()));

        $name = "";

        try {
            $this->c->createPlaylist($name);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals('@1Playlist name cannot be empty!', $error);
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->has->getPlaylists()));
    }

    public function testCreatePlaylistSpaces()
    {
        $this->assertEquals(0, count($this->has->getPlaylists()));

        $name = "    ";
        try {
            $this->c->createPlaylist($name);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals('@1Playlist name cannot be empty!', $error);
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->has->getPlaylists()));
    }
}