<?php

$app->get('/artist', function ($request, $response, $args) {
    $this->logger->info("GET '/artist' route");

    $c = new Controller();
    $artists = $c->getArtists();
    
    $array = array_map(function ($input) {
        return [
            'name' => $input->getName(),
            'songs' => array_map(function ($input) {
                return $input->getName();
            }, $input->getSongs()),
            'albums' => array_map(function ($input) {
                return $input->getName();
            }, $input->getAlbums()),
        ];
    }, $artists);

    return json_encode(array_values($array));
});

$app->get('/artist/{name}', function ($request, $response, $args) {
    $this->logger->info("GET '/artist/'" . $args["name"] . " route");

    $c = new Controller();
    $artist = $c->getArtist($args["name"]);
    return json_encode($artist, true, 3);
});

$app->get('/artist/{name}/album', function ($request, $response, $args) {
    $this->logger->info("GET '/artist/'" . $args["name"] . "/album route");

    $c = new Controller();
    $artist = $c->getArtist($args["name"]);

    $albums = [];
    foreach ($c->getAlbums() as $album) {
        if (strcmp($album->getArtist()->getName(), $args["name"])) {
            $albums[] = $album;
        }
    }
    $albums = $artist->getAlbums();
    return json_encode($albums, true, 3);
});

$app->post('/artist', function ($request, $response, $args) {
    $artist = json_decode($request->getBody(), true);
    $this->logger->info("POST '/artist' route: " . $artist["name"]);

    try {
        $c = new Controller();
        $c->createArtist($artist["name"]);
    } catch (Exception $e) {
        $this->logger->addError($e);
    }
});