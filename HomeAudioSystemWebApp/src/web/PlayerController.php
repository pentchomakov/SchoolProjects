<?php

$app->get('/player', function ($request, $response, $args) {
    $this->logger->info("GET '/player' route");

    $c = new Controller();
    $player = $c->getPlayers();
    return json_encode($player, true, 3);
});

$app->get('/player/{name}', function ($request, $response, $args) {
    $this->logger->info("GET '/player'/" . $args["name"] . " route");

    $c = new Controller();
    $player = $c->getPlayer($args["name"]);
    return json_encode($player, true, 3);
});

$app->post('/player', function ($request, $response, $args) {
    $player = json_decode($request->getBody(), true);
    $this->logger->info("POST '/player' route: " . $player["name"]);

    try {
        $c = new Controller();
        $c->createPlayer($player["name"]);
    } catch (Exception $e) {
        $this->logger->error($e->getMessage());
    }
});

$app->post('/player/{player}/playlist/{playlist}', function ($request, $response, $args) {
    $this->logger->info("POST '/player" . $args["player"] . "/playlist" . $args["playlist"] . "' route");

    try {
        $c = new Controller();
        $c->addPlaylistToPlayer($args["player"], $args["playlist"]);
    } catch (Exception $e) {
        $this->logger->addError($e);
    }
});

$app->post('/player/{player}/location/{location}', function ($request, $response, $args) {
    $this->logger->info("POST '/player" . $args["player"] . "/location" . $args["location"] . "' route");

    try {
        $c = new Controller();
        $c->addLocationToPlayer($args["player"], $args["location"]);
    } catch (Exception $e) {
        $this->logger->addError($e);
    }
});