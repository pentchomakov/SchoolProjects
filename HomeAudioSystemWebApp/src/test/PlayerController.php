<?php

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

class PlayerControllerTest extends PHPUnit_Framework_TestCase
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

    public function testCreatePlayer()
    {
        $this->assertEquals(0, count($this->has->getPlayers()));

        $name = "Player";

        try {
            $this->c->createPlayer($name);
        } catch (Exception $e) {
            $this->fail();
        }

        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(1, count($this->has->getPlayers()));
        $this->assertEquals($name, $this->has->getPlayer_index(0)->getName());
    }

    public function testCreatePlayerNull()
    {
        $this->assertEquals(0, count($this->has->getPlayers()));

        $name = null;

        try {
            $this->c->createPlayer($name);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals('@1Player name cannot be empty!', $error);
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->has->getPlayers()));
    }

    public function testCreatePlayerEmpty()
    {
        $this->assertEquals(0, count($this->has->getPlayers()));

        $name = "";

        try {
            $this->c->createPlayer($name);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals('@1Player name cannot be empty!', $error);
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->has->getPlayers()));
    }

    public function testCreatePlayerSpaces()
    {
        $this->assertEquals(0, count($this->has->getPlayers()));

        $name = "    ";
        try {
            $this->c->createPlayer($name);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals('@1Player name cannot be empty!', $error);
        $this->has = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->has->getPlayers()));
    }
}