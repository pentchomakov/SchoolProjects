<?php

trait ArtistController
{
    /**
     *
     * Create an Artist
     *
     * @param $artist_name
     * @throws Exception
     */
    public function createArtist($artist_name)
    {
        // 1. Validate input
        $name = Inputvalidator::validate_input($artist_name);

        $error = "";
        if ($name == null || strlen($name) == 0) {
            $error .= "Artist name cannot be empty! ";
        }

        if (strlen($error) == 0) {
            // 2. Load all of the data
            $pm = new PersistenceHomeAudioSystem();
            $rm = $pm->loadDataFromStore();

            // 3. Add the new artist
            $artist = new Artist($name);
            $rm->addArtist($artist);

            // 4. Write all of the data
            $pm->writeDataToStore($rm);
        } else {
            throw new Exception(trim($error));
        }
    }

    /**
     *
     * Get an artist by name
     *
     * @param $artist_name
     * @return $artist
     */
    public function getArtist($artist_name)
    {
        $name = Inputvalidator::validate_input($artist_name);
        $pm = new PersistenceHomeAudioSystem();
        $rm = $pm->loadDataFromStore();
        foreach ($rm->getArtists() as $artist) {
            if (strcmp($artist->getName(), $name) == 0) {
                return $artist;
            }
        }
        return null;
    }

    /**
     *
     * Get all HAS Artists
     *
     * @return array $artists
     */
    public function getArtists()
    {
        $pm = new PersistenceHomeAudioSystem();
        $rm = $pm->loadDataFromStore();
        return $rm->getArtists();
    }
}