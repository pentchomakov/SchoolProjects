<?php

// get all, create one, get by name

$app->get('/location', function ($request, $response, $args) {
    $this->logger->info("GET '/location' route");

    $c = new Controller();
    $locations = $c->getLocations();
    return json_encode($locations, true, 3);
});

$app->get('/location/{name}', function ($request, $response, $args) {
    // Sample log message
    $this->logger->info("GET '/location/'" . $args["name"] . " route");

    $c = new Controller();
    $artist = $c->getLocation($args["name"]);
    return json_encode($artist, true, 3);
});

$app->post('/location', function ($request, $response, $args) {
    $location = json_decode($request->getBody(), true);
    $this->logger->info("POST '/location' route: " . $location["name"]);

    try {
        $c = new Controller();
        $c->createLocation($location["name"], $location["volume"], $location["muted"]);
    } catch (Exception $e) {
        $this->logger->info($e->getMessage());
    }
});