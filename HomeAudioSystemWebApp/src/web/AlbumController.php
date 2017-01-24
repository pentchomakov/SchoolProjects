<?php

$app->get('/album', function ($request, $response, $args) {
    $this->logger->info("GET '/album' route");

    $c = new Controller();
    $albums = $c->getAlbums();

    $array = array_map(function ($input) {
        return [
            'name' => $input->getName(),
            'releaseDate' => $input->getReleaseDate(),
            'songs' => array_map(function ($input) {
                return $input->getName();
            }, $input->getSongs()),
            'artist' => $input->getArtist()->getName(),
        ];
    }, $albums);

    return json_encode(array_values($array));
});

$app->get('/album/{name}', function ($request, $response, $args) {
    $this->logger->info("GET '/album/" . $args["name"] . "' route");

    $c = new Controller();
    return json_encode($c->getAlbum($args["name"]), true, 2);
});

$app->post('/album', function ($request, $response, $args) {
    $album = json_decode($request->getBody(), true, 2);

    $this->logger->info("POST '/album' route: " . $album["name"] . "," . $album["releaseDate"] . "," . $album["genre"] . "," . $album["artist"]);
    try {
        $c = new Controller();
        $c->createAlbum($album["name"], $album["releaseDate"], $album["genre"], $album["artist"]);
    } catch (Exception $e) {
        $this->logger->addError($e->getMessage());
    }
});