<?php

trait PlayerController
{
    /**
     *
     * Create a Player
     *
     * @param $player_name
     * @throws Exception
     */
    public function createPlayer($player_name)
    {
        $name = Inputvalidator::validate_input($player_name);

        $error = "";
        if ($name == null || strlen($name) == 0) {
            $error .= "@1Player name cannot be empty! ";
        }

        if (strlen($error) == 0) {
            // 2. Load all of the data
            $pm = new PersistenceHomeAudioSystem();
            $rm = $pm->loadDataFromStore();

            $player = new Player($name);
            $rm->addPlayer($player);

            // 4. Write all of the data
            $pm->writeDataToStore($rm);
        } else {
            throw new Exception(trim($error));
        }
    }

    /**
     *
     * Get a player by name
     *
     * @param $player_name
     * @return $player
     */
    public function getPlayer($player_name)
    {
        $name = Inputvalidator::validate_input($player_name);

        $pm = new PersistenceHomeAudioSystem();
        $has = $pm->loadDataFromStore();

        foreach ($has->getPlayers() as $player)
            if (strcmp($player->getName(), $name) == 0)
                return $player;
        return null;
    }

    /**
     *
     * Get all HAS Players
     * @return array $players
     */
    public function getPlayers()
    {
        $pm = new PersistenceHomeAudioSystem();
        $has = $pm->loadDataFromStore();
        return $has->getPlayers();
    }

    public function addPlaylistToPlayer($playerName, $playlistName)
    {
        $pm = new PersistenceHomeAudioSystem();
        $has = $pm->loadDataFromStore();

        $playlist = $this->getPlaylist($playlistName);
        $player = $this->getPlayer($playerName);

        $error = "";
        if ($player == NULL) {
            $error .= "@1Player " . $playerName . " not found! ";
        }

        if ($playlist == NULL) {
            $error .= "@2Playlist " . $playlistName . " not found! ";
        }

        if (strlen($error) == 0) {
            $player->addPlaylist($playlist);
            $pm->writeDataToStore($has);
        } else {
            throw new Exception(trim($error));
        }
    }

    public function addLocationToPlayer($locationName, $playerName)
    {
        $pm = new PersistenceHomeAudioSystem();
        $has = $pm->loadDataFromStore();

        $player = $this->getPlayer($playerName);
        $location = $this->getLocation($locationName);

        $error = "";
        if ($player == NULL) {
            $error .= "@1Player " . $playerName . " not found! ";
        }

        if ($location == NULL) {
            $error .= "@2Location " . $locationName . " not found! ";
        }

        if (strlen($error) == 0) {
            $player->addPlaylist($location);
            $pm->writeDataToStore($has);
        } else {
            throw new Exception(trim($error));
        }
    }
}