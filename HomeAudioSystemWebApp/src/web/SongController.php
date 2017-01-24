<?php

$app->get('/song', function ($request, $response, $args) {
    $this->logger->info("GET '/song' route");

    $c = new Controller();
    return json_encode($c->getSongs(), true, 2);
});

$app->get('/song/{name}', function ($request, $response, $args) {
    $this->logger->info("GET '/song/'" . $args["name"] . " route");

    $c = new Controller();
    return json_encode($c->getSong($args["name"]), true, 2);
});

$app->post('/song', function ($request, $response, $args) {
    $song = json_decode($request->getBody(), true);
    $this->logger->info("POST '/song' route: " . $song["name"]);

    try {
        $c = new Controller();
        $c->createSong($song["name"], $song["duration"], $song["albumPosition"], $song["albumName"], $song["artistName"]);
    } catch (Exception $e) {
        $this->logger->addError($e);
    }
});