<?php

require_once __DIR__ . '/../model/Artist.php';
require_once __DIR__ . '/../model/HomeAudioSystem.php';
require_once __DIR__ . '/../persistence/PersistenceHomeAudioSystem.php';

class PersistenceEventRegistrationTest extends PHPUnit_Framework_TestCase
{
    protected $pm;
    protected function setUp()
    {
        $this->pm = new PersistenceHomeAudioSystem();
    }

    protected function tearDown()
    {
    }

    public function testPersistence()
    {
        // 1. Create test data
        $has          = HomeAudioSystem::getInstance();
        $artist = new Artist("Johann Sebastian Bach");
        $has->addArtist($artist);
        
        // 2. write all of the data
        $this->pm->writeDataToStore($has);
        
        // 3. Clear the data from memory
        $has->delete();
        
        $this->assertEquals(0, count($has->getArtists()));
        
        // 4. Load it back in
        $has = $this->pm->loadDataFromStore();
        
        // S. Check that we got it back
        $this->assertEquals(1, count($has->getArtists()));
        $myArtist = $has->getArtist_index(0);
        $this->assertEquals("Johann Sebastian Bach", $myArtist->getName());
    }
}