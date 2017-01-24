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


class PlayerIntegrationTest extends PHPUnit_Framework_TestCase
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

    public function testCreateLocationPlayerPlaylistIndependently()
    {
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getSongs()));

        $locationName = "Living Room";
        $locationVolume = "100";
        $locationMuted = "false";
        $playerName = "Player One";
        $playlistName = "Gunter's Top Hits";

        $error = "";
        try {
            $this->c->createLocation($locationName, $locationVolume, $locationMuted);
            $this->c->createPlayer($playerName);
            $this->c->createPlaylist($playlistName);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals($error, "");
        $this->assertEquals(1, count($this->c->getPlayers()));
        $this->assertEquals(1, count($this->c->getLocations()));
        $this->assertEquals(1, count($this->c->getPlaylists()));
    }

    public function testCreateMultiLocationPlaylist()
    {
        $this->assertEquals(0, count($this->has->getArtists()));
        $this->assertEquals(0, count($this->has->getAlbums()));
        $this->assertEquals(0, count($this->has->getSongs()));

        $locationName = "Living Room";
        $secondLocationName = "Bed Room";
        $locationVolume = "100";
        $locationMuted = "false";
        $playerName = "Player One";
        $playlistName = "Gunter's Top Hits";

        $error = "";
        $player = null;
        try {
            $this->c->createLocation($locationName, $locationVolume, $locationMuted);

            $this->c->createLocation($secondLocationName, $locationVolume, $locationMuted);

            $this->c->createPlayer($playerName);
            $this->c->createPlaylist($playlistName);

            $player = $this->c->getPlayer($playerName);
            foreach ($this->c->getLocations() as $location) {
                $player->addLocation($location);
            }

        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        $this->assertEquals($error, "");
        $this->assertEquals(1, count($this->c->getPlayers()));
        $this->assertEquals(2, count($this->c->getLocations()));
        $this->assertEquals(1, count($this->c->getPlaylists()));

        $playlist = $this->c->getPlaylist($playlistName);
        $player->addPlaylist($playlist);
        $locations = $this->c->getLocations();

        // Test the the location names are satisfied
        foreach ($locations as $location) {
            if (strcmp($location->getName(), $locationName) == 0) {
                $this->assertEquals($locationName, $location->getName());
            } else {
                $this->assertEquals($secondLocationName, $location->getName());
            }
        }

        $this->assertEquals($playlistName, $player->getPlaylists()[0]->getTitle());
    }
}