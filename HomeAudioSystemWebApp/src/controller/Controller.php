<?php

require_once __DIR__ . '/subcontrollers/AlbumController.php';
require_once __DIR__ . '/subcontrollers/ArtistController.php';
require_once __DIR__ . '/subcontrollers/LocationController.php';
require_once __DIR__ . '/subcontrollers/PlayerController.php';
require_once __DIR__ . '/subcontrollers/PlaylistController.php';
require_once __DIR__ . '/subcontrollers/SongController.php';

/**
 * Class Controller
 * Contains all of the business logic for the web application
 */
class Controller
{
    public function __construct()
    {
    }

    use AlbumController;
    use ArtistController;
    use LocationController;
    use PlayerController;
    use PlaylistController;
    use SongController;
}