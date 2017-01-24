<?php
if (PHP_SAPI == 'cli-server') {
    // To help the built-in PHP dev server, check if the request was actually for
    // something which should probably be served as a static file
    $file = __DIR__ . $_SERVER['REQUEST_URI'];
    if (is_file($file)) {
        return false;
    }
}

require __DIR__ . '/../vendor/autoload.php';

session_start();

// Instantiate the app
$settings = require __DIR__ . '/../src/settings.php';
$app = new \Slim\App($settings);

// Set up dependencies
require __DIR__ . '/../src/dependencies.php';

require_once __DIR__ . '/../src/model/Artist.php';
require_once __DIR__ . '/../src/model/HomeAudioSystem.php';
require_once __DIR__ . '/../src/model/Album.php';
require_once __DIR__ . '/../src/model/Genre.php';
require_once __DIR__ . '/../src/model/Location.php';
require_once __DIR__ . '/../src/model/Playlist.php';
require_once __DIR__ . '/../src/model/Player.php';
require_once __DIR__ . '/../src/model/Song.php';

require_once __DIR__ . '/../src/persistence/PersistenceHomeAudioSystem.php';
require_once __DIR__ . '/../src/controller/Controller.php';
require_once __DIR__ . '/../src/controller/Inputvalidator.php';

// Register middleware
require __DIR__ . '/../src/middleware.php';

// Register routes
require __DIR__ . '/../src/routes.php';

// Run app
$app->run();
