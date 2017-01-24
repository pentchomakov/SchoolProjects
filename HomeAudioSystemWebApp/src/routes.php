<?php
// Routes

require __DIR__ . '/../src/web/AlbumController.php';
require __DIR__ . '/../src/web/ArtistController.php';
require __DIR__ . '/../src/web/LocationController.php';
require __DIR__ . '/../src/web/PlayerController.php';
require __DIR__ . '/../src/web/PlaylistController.php';
require __DIR__ . '/../src/web/SongController.php';

$app->get('/[{name}]', function ($request, $response, $args) {
    // Sample log message
    $this->logger->info("Slim-Skeleton '/' route");

    // Render index view
    return $this->renderer->render($response, 'index.html', $args);
});

