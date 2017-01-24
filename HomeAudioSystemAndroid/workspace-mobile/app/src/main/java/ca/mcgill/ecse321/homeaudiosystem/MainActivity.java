package ca.mcgill.ecse321.homeaudiosystem;

import android.media.AudioManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import controller.HomeAudioSystemController;
import controller.InvalidInputException;
import model.Album;
import model.Artist;
import model.Location;
import model.Song;
import model.HomeAudioSystem;
import model.Playlist;
import persistence.PersistenceHomeAudioSystem;

import org.w3c.dom.Text;

import java.sql.Array;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {


    public TextView error_message;
    public HomeAudioSystem HAS;
    public String artist_select="";
    public String album_select =" ";
    public String genre_select=" ";
    public String position_select= " ";
    public String playlist_select=" ";
    public String location_select=" ";
    public String player_select=" ";
    public String song_select = " ";
    public String[] genres;
    public int position;
    public Date album_Date;
    public Artist selected_artist;
    public Album selected_album;
    public Playlist selected_playlist;
    public Location selected_location;
    public Song selected_song;
    public HashMap<Integer, Artist> artists;
    public HashMap<Integer, Album> albums;
    public HashMap<Integer, Playlist> playlists;
    public HashMap<Integer, Location> locations;
    public HashMap<Integer, Song> songs;
    public Album.Genre selected_genre;
    public int locationVolume;
    public int artist_index= 0;
    public int album_index= 0;
    public int playlist_index =0;
    public int song_index = 0;
    public Time song_Duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PersistenceHomeAudioSystem.setFileName(getFilesDir().getAbsolutePath() + "HomeAudioSystem.xml");
        PersistenceHomeAudioSystem.loadHomeAudioSystemModel();
        HAS = HomeAudioSystem.getInstance();
        refreshData();

    }

    private void refreshData(){
        TextView tv = (TextView) findViewById(R.id.newArtist_name);
        TextView album = (TextView) findViewById(R.id.newAlbum_name);
        TextView position = (TextView) findViewById(R.id.newSong_position);
        this.error_message= (TextView) findViewById(R.id.errormessage);

        album.setText("");
        tv.setText("");
        HAS = HomeAudioSystem.getInstance();

        Spinner spinner = (Spinner) findViewById(R.id.artistSpinner);
        ArrayAdapter<CharSequence> artistAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        artistAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                artist_select = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.artists = new HashMap<Integer, Artist>();
        int i = 0;
        for (Iterator<Artist> artists = HAS.getArtists().iterator(); artists.hasNext(); i++) {
            Artist a = artists.next();
            artistAdapter.add(a.getName());
            this.artists.put(i, a);
            if(a.getName().equals(artist_select)){
                artist_index=i;
                this.selected_artist=a;
            }
        }
        spinner.setAdapter(artistAdapter);

        Spinner spinner2 = (Spinner) findViewById(R.id.albumGenreSpinner);
        ArrayAdapter<CharSequence> genreAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genre_select = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.genres = names();
        for (int j = 0; j<14; j++) {
            String g = (genres[j]);
            genreAdapter.add(g);
            if(g.equals(genre_select)){
                this.selected_genre=Album.Genre.valueOf(g);
            }
        }
        spinner2.setAdapter(genreAdapter);

        Spinner spinner3 = (Spinner) findViewById(R.id.albumNameSpinner);
        ArrayAdapter<CharSequence> albumAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        albumAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                album_select = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
        this.albums = new HashMap<Integer, Album>();
        int j = 0;
        for (Iterator<Album> albums = HAS.getAlbums().iterator(); albums.hasNext(); i++) {
            Album alb = albums.next();
            albumAdapter.add(alb.getName());
            this.albums.put(j, alb);
            if(alb.getName().equals(album_select)){
                album_index=j;
                this.selected_album=alb;
            }
        }
        spinner3.setAdapter(albumAdapter);

        Spinner spinner4 = (Spinner) findViewById(R.id.playlist_spinner);
        ArrayAdapter<CharSequence> playlistAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        playlistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                playlist_select = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
        this.playlists = new HashMap<Integer, Playlist>();
        int k = 0;
        for (Iterator<Playlist> playlists = HAS.getPlaylists().iterator(); playlists.hasNext(); i++) {
            Playlist pList = playlists.next();
            playlistAdapter.add(pList.getTitle());
            this.playlists.put(k, pList);
            if(pList.getTitle().equals(playlist_select)){
                album_index=j;
                this.selected_playlist=pList;
            }
        }
        spinner4.setAdapter(playlistAdapter);

        Spinner spinner5 = (Spinner) findViewById(R.id.location_spinner);
        ArrayAdapter<CharSequence> locationAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location_select = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
        this.locations = new HashMap<Integer, Location>();
        int l = 0;
        for (Iterator<Location> locations = HAS.getLocations().iterator(); locations.hasNext(); i++) {
            Location location = locations.next();
            locationAdapter.add(location.getName());
            this.locations.put(k, location);
            if(location.getName().equals(location_select)){
                album_index=l;
                this.selected_location=location;
            }
        }
        spinner5.setAdapter(locationAdapter);

        Spinner spinner6 = (Spinner) findViewById(R.id.player_spinner);
        ArrayAdapter<CharSequence> playerAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        playerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                player_select = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
        spinner6.setAdapter(playerAdapter);

        Spinner spinner7 = (Spinner) findViewById(R.id.song_spinner);
        ArrayAdapter<CharSequence> songAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        songAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               song_select = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
        spinner7.setAdapter(songAdapter);

    }

    public static String[] names(){
        return Arrays.toString(Album.Genre.values()).replaceAll("^.|.$", "").split(", ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addArtist(View v){
        error_message.setText("");
        TextView tv = (TextView) findViewById(R.id.newArtist_name);
        HomeAudioSystemController pc= new HomeAudioSystemController();
        try{
            pc.createArtist(tv.getText().toString());
        } catch(InvalidInputException e){

            this.error_message.setText(e.getMessage());

        }

        refreshData();
    }

    public void addAlbum(View v){
        error_message.setText("");

        TextView album_name= (TextView) findViewById(R.id.newAlbum_name);
        TextView album_date=(TextView) findViewById(R.id.newAlbum_date);


        Bundle dates = getDateFromLabel(album_date.getText());
        album_Date= getDateFromBundle(dates);

        Artist a = artists.get(artist_index);

        HomeAudioSystemController pc= new HomeAudioSystemController();
        try{
            pc.createAlbum(album_name.getText().toString(), album_Date, a, Album.Genre.valueOf(genre_select));
        } catch(InvalidInputException e){
            this.error_message.setText(e.getMessage());
        }

        refreshData();
    }

    public void addSong(View V){
        error_message.setText("");

        TextView song_duration = (TextView) findViewById(R.id.addSong_duration);
        TextView song_name = (TextView) findViewById(R.id.addSong_Name);
        TextView song_position = (TextView) findViewById(R.id.songposition_hint);

        Bundle times = getTimeFromLabel(song_duration.getText());
        song_Duration = getTimeFromBundle(times);

        HomeAudioSystemController pc = new HomeAudioSystemController();
        Album alb = albums.get(album_index);

        try{
            pc.createSong(song_name.getText().toString(),song_Duration, Integer.parseInt(song_position.toString()),alb);
        } catch(InvalidInputException e){
            this.error_message.setText(e.getMessage());
        }
        refreshData();
    }

    public void addPlaylist (View V){
        error_message.setText("");

        TextView playlist_name = (TextView) findViewById(R.id.newplaylist_hint);
        HomeAudioSystemController pc= new HomeAudioSystemController();
        try{
            pc.createPlaylist(playlist_name.getText().toString());
        } catch(InvalidInputException e){

            this.error_message.setText(e.getMessage());

        }
        refreshData();
    }

    public void addLocation (View V){
        error_message.setText("");

        TextView location = (TextView) findViewById(R.id.newlocation_hint);
        HomeAudioSystemController pc= new HomeAudioSystemController();

        SeekBar seekBar =(SeekBar) findViewById(R.id.seekBar);
        int value = seekBar.getProgress();

        try{
            pc.createLocation(location.getText().toString(),value);
        } catch(InvalidInputException e){

            this.error_message.setText(e.getMessage());

        }
        refreshData();
    }
    public void addPlayer(View V){
        error_message.setText("");

        TextView player = (TextView) findViewById(R.id.newplayer_hint);
        HomeAudioSystemController pc= new HomeAudioSystemController();
        try{
            pc.createPlayer(player.getText().toString());
        } catch(InvalidInputException e){

            this.error_message.setText(e.getMessage());

        }
        refreshData();
    }
    public void addSongToPlaylist(View V){

        error_message.setText("");

        //TextView playlist = (TextView) findViewById(R.id.playlist_spinner);
        HomeAudioSystemController pc= new HomeAudioSystemController();

        Playlist pList2 = playlists.get(playlist_index);
        Song song = songs.get(song_index);

        try{
            pc.addSongToPlaylist(song, pList2);
        } catch(InvalidInputException e){

            this.error_message.setText(e.getMessage());

        }
        refreshData();
    }

    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText());
        args.putInt("id", v.getId());
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v){

        TextView tp= (TextView) v;
        Bundle args = getTimeFromLabel(tp.getText());
        args.putInt("id", v.getId());
        TimePickerFragment newFragment= new TimePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(),"TimePicker");
    }


    private Time getTimeFromBundle (Bundle bundle){
       // int time_hour = bundle.getInt("hour");
        int time_min = bundle.getInt("minute");
        int time_sec = bundle.getInt("second");
        return new Time(time_min,time_sec,0);
    }

    private Date getDateFromBundle(Bundle bundle){
        int date_day= bundle.getInt("day");
        int date_month=bundle.getInt("month");
        int date_year=bundle.getInt("year")-1900;
        return new Date(date_year,date_month,date_day);

    }

    private Bundle getTimeFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split(":");
        //int hour = 12;
        int minute = 0;
        int second = 00;
        if (comps.length == 2) {
            minute = Integer.parseInt(comps[0]);
            second = Integer.parseInt(comps[1]);
        }
        rtn.putInt("minute", minute);
        rtn.putInt("seconds", second);
        return rtn;
    }

    private Bundle getDateFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day = 1;
        int month = 1;
        int year = 1;
        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }
        rtn.putInt("day", day);
        rtn.putInt("month", month-1);
        rtn.putInt("year", year);
        return rtn;
    }
    public void setTime(int id, int m, int s) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d:%02d", m, s));
    }

    public void setDate(int id, int d, int m, int y) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", d, m + 1, y));
    }
}
