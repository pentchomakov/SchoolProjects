<?php

// get all, create one, get by name, add album song artist
$app->get('/playlist', function ($request, $response, $args) {
    // Sample log message
    $this->logger->info("GET '/playlist' route");

    $c = new Controller();
    $playlist = $c->getPlaylists();

    // Render index view
    return json_encode($playlist, true, 3);
});

$app->get('/playlist/{name}', function($request, $response, $args) {
    // Sample log message
    $this->logger->info("GET '/playlist/". $args["name"]."' route");

    $c = new Controller();
    $playlist = $c->getPlaylist($args["name"]);

    // Render index view
    return json_encode($playlist, true, 3);
});

$app->post('/playlist', function($request, $response, $args) {
    $playlist = json_decode($request->getBody(), true);
    $this->logger->info("POST '/playlist' route: " . $playlist["title"]);

    try {
        $c = new Controller();
        $c->createPlaylist($playlist["title"]);
    } catch (Exception $e) {
        $this->logger->addError($e);
    }
});