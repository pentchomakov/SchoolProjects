function stringToDate(str) {
    var date = str.split("/"),
        m = date[0],
        d = date[1],
        y = date[2],
        temp = [];
    temp.push(y, m, d);
    return y + "-" + m + "-" + d;
}

function xhrFailed(message) {
    console.log(message);
    alert(message);
}

function nextSong(audioSrc) {
    $("audio").attr('src', audioSrc);
    $("audio")[0].pause();
    $("audio")[0].load();
    $('audio').parent().attr("class", "audioplayer audioplayer-playing")
    $("audio")[0].play();
}

function refreshDataModel() {

    $.getJSON("/location", function (locations) {
        $('#location').empty();
        $('#locationList').empty();

        $.each(locations, function (key, val) {
            var option = $("<option></option>");
            option.text(locations[key].name);
            $("#location").append(option);
        });

        $.each(locations, function (key, val) {
            var option = $("<li class='list-group-item'></li>");
            option.text(locations[key].name);
            $("#locationList").append(option);
        });
    });

    $.getJSON("/song", function (songs) {
        $('#playlistSongs').empty();

        $.each(songs, function (key, val) {
            var option = $("<option></option>");
            option.text(songs[key].title);

            $("#playlistSongs").append(option);
        });
    });

    $.getJSON("/artist", function (data) {
        $('select[name=albumArtist]').empty();
        $('select[name=playlistArtists]').empty();
        $('select[name=songArtist]').empty();

        $.each(data, function (key, val) {
            var optgroup = $('<optgroup>');
            optgroup.attr('label', data[key].name);

            $.each(data[key].albums, function (i) {
                var option = $("<option></option>");
                option.val(i);
                option.text(data[key].albums[i].name);

                optgroup.append(option);
            });
            $("#playlistAlbums").append(optgroup);
        });

        $.each(data, function (key, val) {
            var optgroup = $('<optgroup>');
            optgroup.attr('label', data[key].name);

            $.each(data[key].albums, function (i) {
                var option = $("<optgroup>");
                //option.attr('label', data[key].albums[i].name);
                option.text(data[key].albums[i].name + " - ");

                /*$.each(data[key].albums[i].songs, function (j) {
                 var song = $("<option></option>");
                 song.text(data[key].albums[i].songs[j].title);
                 option.append(song);
                 });*/

                optgroup.append(option);
            });
            $("#playlistArtists").append(optgroup);
        });

        $.each(data, function (key, val) {
            $("select[name=albumArtist]").append('<option value=' + val.name + '>' + val.name + '</option>');
        });

        $.each(data, function (key, val) {
            var optgroup = $('<optgroup>');
            optgroup.attr('label', data[key].name);

            $.each(data[key].albums, function (i) {
                var option = $("<option></option>");
                option.val(i);
                option.text(data[key].albums[i].name);

                optgroup.append(option);
            });
            $("select[name=songArtist]").append(optgroup);
        });
    });

    $.getJSON("/playlist", function (data) {
        $('select[name=playlistName]').empty();

        $.each(data, function (key, val) {
            $("select[name=playlistName]").append('<option value=' + val.title + '>' + val.title + '</option>');
        });
    });

    $.getJSON("/player", function (data) {
        $('select[name=players]').empty();

        $.each(data, function (key, val) {
            $("select[name=players]").append('<option value=' + val.name + '>' + val.name + '</option>');
        });
    });
}

$(document).ready(function () {
    $("audio").audioPlayer();

    refreshDataModel();

    $('#players').on('change', function () {

        $.getJSON("/player/" + $("#players").find(":selected").text(), function (data) {
            $('#nowPlayingList').empty();
            $("#locationsPlayingList").empty();

            var locations = data.locations;
            var playlists = data.playlists || [];
            if (playlists.length > 0)
                songs = playlists[0].songs;
            else
                songs = [];

            if (songs.length > 0) {
                $.each(songs, function (key, val) {
                    var option = $("<li class='list-group-item'></li>");
                    option.text(songs[key].title);
                    $("#nowPlayingList").append(option);
                });
            } else {
                var option = $("<li class='list-group-item'></li>");
                option.text("No Songs Playing");
                $("#nowPlayingList").append(option);
            }

            if (locations.length > 0) {
                $.each(locations, function (key, val) {
                    var option = $("<li class='list-group-item'></li>");
                    option.text(locations[key].title);
                    $("#locationsPlayingList").append(option);
                });
            } else {
                var option = $("<li class='list-group-item'></li>");
                option.text("No Locations Attached");
                $("#locationsPlayingList").append(option);
            }
        });
    });


    $("#startPlaying").on('click', function () {
        nextSong("sound/Concerto-No.-3-Allegro-Adagio.mp3");
    });

    $("#addPlaylistToPlayerButton").on('click', function () {
        // Now execute your AJAX
        var url = "/player/" + $('#playlistPlayer').find(":selected").text() + "/playlist/" + $('#playlistPlaylist').find(":selected").text();
        $.ajax({
            type: "POST",
            url: url,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function (response) {
            console.log(response);
            refreshDataModel();
        }).fail(function (xhr, status, message) {
            console.log(xhr, status, message);
            refreshDataModel();
        });
    });

    $("#createAlbumButton").on('click', function () {
        // Now execute your AJAX
        var formData = {
            'name': $('input[name=albumName]').val(),
            'releaseDate': $('input[name=albumReleaseDate]').val(),
            'genre': $('select[name=albumGenre]').find(":selected").text(),
            'artist': $('select[name=albumArtist]').find(":selected").text()
        };

        $.ajax({
            type: "POST",
            url: "/album",
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function (response) {
            console.log(response);
            refreshDataModel();
        }).fail(function (xhr, status, message) {
            console.log(xhr, status, message);
            refreshDataModel();
        });
    });

    $("#createLocationButton").on('click', function () {
        // Now execute your AJAX
        var formData = {
            'name': $('input[name=locationName]').val(),
            'volume': "100",
            'muted': "false"
        };

        $.ajax({
            type: "POST",
            url: "/location",
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function (response) {
            console.log(response);
            refreshDataModel();
        }).fail(function (xhr, status, message) {
            console.log(xhr, status, message);
            refreshDataModel();
        });
    });

    $("#createPlayerButton").on('click', function () {
        // Now execute your AJAX
        var formData = {
            'name': $('input[name=playerName]').val()
        };

        $.ajax({
            type: "POST",
            url: "/player",
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function (response) {
            console.log(response);
            refreshDataModel();
        }).fail(function (xhr, status, message) {
            console.log(xhr, status, message);
            refreshDataModel();
        });
    });

    $("#createArtistButton").on('click', function () {
        // Now execute your AJAX
        var formData = {
            'name': $('input[name=artistName]').val()
        };

        $.ajax({
            type: "POST",
            url: "/artist",
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function (response) {
            console.log(response);
            refreshDataModel();
        }).fail(function (xhr, status, message) {
            console.log(xhr, status, message);
            refreshDataModel();
        });
    });

    $("#createPlaylistButton").on('click', function () {
        // Now execute your AJAX
        var formData = {
            'title': $('input[name=playlistName]').val(),
        };

        $.ajax({
            type: "POST",
            url: "/playlist",
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function (response) {
            console.log(response);
            refreshDataModel();
        }).fail(function (xhr, status, message) {
            console.log(xhr, status, message);
            refreshDataModel();
        });
    });

    $("#addAlbumToPlaylistButton").on('click', function () {
        // Now execute your AJAX
        var url = "/playlist/" + $('#artistPlaylistName').find(":selected").text() + "/album/" + $('#playlistAlbums').find(":selected").text();
        $.ajax({
            type: "POST",
            url: url,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function (response) {
            console.log(response);
            refreshDataModel();
        }).fail(function (xhr, status, message) {
            console.log(xhr, status, message);
            refreshDataModel();
        });
    });

    $("#addSongToPlaylistButton").on('click', function () {
        // Now execute your AJAX
        var url = "/playlist/" + $('#songPlaylistName').find(":selected").text() + "/song/" + $('#playlistSongs').find(":selected").text();
        $.ajax({
            type: "POST",
            url: url,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function (response) {
            console.log(response);
            refreshDataModel();
        }).fail(function (xhr, status, message) {
            console.log(xhr, status, message);
            refreshDataModel();
        });
    });

    $("#addArtistToPlaylistButton").on('click', function () {
        // Now execute your AJAX
        var url = "/playlist/" + $('#albumPlaylistName').find(":selected").text() + "/album/" + $('#playlistArtists').find(":selected").text();
        $.ajax({
            type: "POST",
            url: url,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function (response) {
            console.log(response);
            refreshDataModel();
        }).fail(function (xhr, status, message) {
            console.log(xhr, status, message);
            refreshDataModel();
        });
    });


    $("#createSongButton").on('click', function () {
        // Now execute your AJAX
        var formData = {
            'title': $('input[name=songName]').val(),
            'duration': "00:" + $('input[name=songDuration]').val(),
            'albumPos': $('input[name=songAlbumPosition]').val(),
            'album': $('select[name=songArtist]').find(":selected").text(),
            'artist': $('select[name=songArtist]').find(":selected").closest('optgroup').prop('label')
        };

        $.ajax({
            type: "POST",
            url: "/song",
            data: JSON.stringify(formData),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function (response) {
            console.log(response);
            refreshDataModel();
        }).fail(function (xhr, status, message) {
            console.log(xhr, status, message);
            refreshDataModel();
        });
    });
});