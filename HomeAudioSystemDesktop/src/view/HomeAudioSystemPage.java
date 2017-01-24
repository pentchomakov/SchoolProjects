package view;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import controller.HomeAudioSystemController;
import controller.InvalidInputException;
import model.Album;
import model.Album.Genre;
import model.Artist;
import model.HomeAudioSystem;
import model.Location;
import model.Player;
import model.Playlist;
import model.Song;

import java.awt.Font;

import javax.swing.GroupLayout.Alignment;

import java.awt.Component;

import javax.swing.LayoutStyle.ComponentPlacement;


public class HomeAudioSystemPage extends JFrame {

	private static final long serialVersionUID = 2659235227180911903L;
	

	// Album vars
	private JTextField albumNameTextField;
	private JLabel albumNameLabel;
	private JDatePickerImpl releaseDatePicker;
	private JLabel releaseDateLabel;
	private JButton addAlbumButton;
	private JComboBox <String> genreList;
	private JLabel genreLabel;
	private String[] genres = Album.names();
	private Integer selectedGenre= 0;
	
	// Artist vars
	private JComboBox <String> artistList;
	private JLabel artistLabel;
	private JTextField artistNameTextField;
	private JLabel artistNameLabel;
	private JButton addArtistButton;
	private Integer selectedArtist= -1;
	private HashMap <Integer, Artist> artists;
	
	// Song vars
	private String error = null;
	private JLabel errorMessage;
	private JTextField songNameTextField;
	private JLabel songNameLabel;
	private JSpinner durationTimeSpinner;
	private JLabel durationLabel;
	private JLabel albumPosLabel;
	private JTextField albumPosTextField;
	private JComboBox<String> albumList;
	private int selectedAlbum=-1;
	private JLabel albumLabel;
	private JButton addSongButton;
	
	// Mute Location vars
	private JButton muteButton;
	private HashMap <Integer, Album> albums;
	private Integer selectedSongForPlayList;
	private JLabel addSongToPlayListLabel;
	private JComboBox <String> toBeAddedToPlayList;
	private Integer selectedPlayList;
	private JLabel toBeAddedPlayListLabel;
	private JButton addSongToPlayListButton;
	private HashMap <Integer, Song> songs;
	private HashMap <Integer, Playlist> playlists;
	
	// Mute Location vars
	private JComboBox<String> locationToBeMutedList;
	private int selectedLocationToBeMuted;
	private JLabel toBeMutedLocationLabel;
	private JCheckBox checkBoxToMuteLocation;
	private Boolean isMuted = false;
	private HashMap <Integer, Location> locations;
	private JTextField locationNameTextField;
	private JSlider locationVolume;
	private JLabel locationNameLabel;
	private JLabel locationVolumeLabel;
    private int selectedVolume = 0;
	private JButton addLocationButton;

	// Song to Playlist vars
	private JTextField playlistNameTextField;
	private JLabel playlistNameLabel;
	private JButton addPlaylistButton;
	private JComboBox<String> songList;
	private int selectedPlaylist;
	private JLabel playerLabel;
	private JComboBox<String> allPlayList;
	private int selectedPlayListForPlayer;
	
	// Songs, Albums, Playlists to Player vars
	private JLabel playListToBeAddedToPlayer;
	private JComboBox<String> allSongs;
	private JLabel songToBeAddedToPlayer;
	private int selectedSongForPlayer;
	private JComboBox<String> allAlbums;
	private JLabel albumToBeAddedToPlayer;
	private JComboBox<String> allLocations;
	private JLabel locationToBeSetToPlayer;
	private int selectedLocationForPlayer;
	private int selectedAlbumForPlayer;
	private JButton playButton;
	private JTextField playerNameTextField;
	private JButton addPlayerButton;
	private JComboBox<String> allPlayers;
	private int selectedPlayer;
	private JLabel selectPlayerLabel;
	private JButton addToPlayerButton;
	private HashMap<Integer, Player> players;
	private JLabel playerToBePlayedLabel;
	private JComboBox<String> allFinalPlayers;
	private int selectedFinalPlayer;
	private JLabel companyLabel;	
	
	public HomeAudioSystemPage(){
		getContentPane().setForeground(Color.WHITE);
		setForeground(Color.LIGHT_GRAY);
		setBackground(UIManager.getColor("Button.background"));
		getContentPane().setBackground(new Color(0, 0, 0));
		
		try { 
			 UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
		} catch(Exception e) { 
			 // e.printStack();
		}
		initialize();
		refreshData();	
	}
	
	private void initialize(){
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		companyLabel = new JLabel();
		companyLabel.setFont(new Font("Belgium", Font.PLAIN, 12));
		companyLabel.setForeground(new Color(255, 255, 255));
		
		// Add Artist UI
		artistNameTextField = new JTextField();
		artistNameLabel = new JLabel();
		artistNameLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
		artistNameLabel.setForeground(UIManager.getColor("Button.highlight"));
		artistNameLabel.setBackground(UIManager.getColor("Button.highlight"));
		addArtistButton = new JButton();
		addArtistButton.setBackground(new Color(51, 0, 0));
		addArtistButton.setForeground(Color.WHITE);
		addArtistButton.setFont(new Font("Belgium", Font.PLAIN, 13));
		
		artistList = new JComboBox<String>(new  String [0]);
		artistList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedArtist = cb.getSelectedIndex();
			}
		});
		
		artistLabel=new JLabel();
		artistLabel.setForeground(UIManager.getColor("Button.highlight"));
		artistLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
		
		
		// Add Album UI
		albumNameTextField = new JTextField();
		albumNameLabel = new JLabel();
		albumNameLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
		albumNameLabel.setForeground(UIManager.getColor("Button.highlight"));
		
		SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		releaseDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		releaseDatePicker.getJFormattedTextField().setBounds(0, 0, 172, 29);
		releaseDateLabel= new JLabel();
		releaseDateLabel.setForeground(UIManager.getColor("Button.highlight"));
		releaseDateLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
		DefaultComboBoxModel<String> comboModel2 = new DefaultComboBoxModel<String>(genres);
		genreList= new JComboBox<String>(comboModel2);
		genreList.setFont(new Font("Belgium", Font.PLAIN, 13));
		genreList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedGenre = cb.getSelectedIndex();
			}
		});
		
		genreLabel= new JLabel();
		genreLabel.setForeground(UIManager.getColor("Button.highlight"));
		genreLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
		addAlbumButton = new JButton();
		addAlbumButton.setBackground(new Color(51, 0, 0));
		addAlbumButton.setForeground(Color.WHITE);
		addAlbumButton.setFont(new Font("Belgium", Font.PLAIN, 13));
		
		// Add Song
		songNameTextField = new JTextField();
		songNameLabel = new JLabel();
		songNameLabel.setForeground(UIManager.getColor("Button.highlight"));
		songNameLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
		albumPosTextField = new JTextField();
		albumPosLabel = new JLabel();
		albumPosLabel.setForeground(UIManager.getColor("Button.highlight"));
		albumPosLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
		
		durationTimeSpinner = new JSpinner(new SpinnerDateModel());
		durationTimeSpinner.setFont(new Font("Belgium", Font.PLAIN, 13));
		JSpinner.DateEditor durationTimeEditor = new JSpinner.DateEditor(durationTimeSpinner, "mm:ss");
		durationTimeSpinner.setEditor(durationTimeEditor);
		durationLabel = new JLabel();
		durationLabel.setForeground(UIManager.getColor("Button.highlight"));
		durationLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
		
		// Add album to Song
		albumList = new JComboBox<String>(new  String [0]);
		albumList.addActionListener(new java.awt.event.ActionListener(){
			

			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedAlbum = cb.getSelectedIndex();
			}
			
		});
		albumLabel = new JLabel();
		albumLabel.setForeground(UIManager.getColor("Button.highlight"));
		albumLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
		addSongButton= new JButton();
		addSongButton.setBackground(new Color(51, 0, 0));
		addSongButton.setForeground(Color.WHITE);
		addSongButton.setFont(new Font("Belgium", Font.PLAIN, 13));
		
		
		// Add Location
		locationNameTextField = new JTextField();
		locationNameLabel = new JLabel();
		locationNameLabel.setForeground(Color.WHITE);
		locationNameLabel.setFont(new Font("Belgium", Font.PLAIN, 12));
		locationVolume = new JSlider();	
		addLocationButton= new JButton();
		addLocationButton.setForeground(Color.WHITE);
		addLocationButton.setBackground(new Color(51, 0, 0));
		addLocationButton.setFont(new Font("Belgium", Font.PLAIN, 12));
		locationVolumeLabel = new JLabel();
		locationVolumeLabel.setFont(new Font("Belgium", Font.PLAIN, 12));
		locationVolumeLabel.setForeground(Color.WHITE);
	    locationVolume.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
	           selectedVolume = locationVolume.getValue();
	        }
	    });
	    
		
	    // Create PLaylist
	    playlistNameTextField = new JTextField();
	    playlistNameLabel = new JLabel();
	    playlistNameLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
	    playlistNameLabel.setForeground(Color.WHITE);
	    addPlaylistButton= new JButton();
	    addPlaylistButton.setForeground(Color.WHITE);
	    addPlaylistButton.setFont(new Font("Belgium", Font.PLAIN, 13));
	    addPlaylistButton.setBackground(new Color(51, 0, 0));
	    	
		// Add Song to PlayList
		addSongToPlayListButton = new JButton();
		addSongToPlayListButton.setBackground(new Color(51, 0, 0));
		addSongToPlayListButton.setFont(new Font("Belgium", Font.PLAIN, 13));
		addSongToPlayListButton.setForeground(Color.WHITE);
		songList = new JComboBox<String>(new  String [0]);
		songList.addActionListener(new java.awt.event.ActionListener () {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedSongForPlayList = cb.getSelectedIndex();
			}
		});
		addSongToPlayListLabel = new JLabel();
		addSongToPlayListLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
		addSongToPlayListLabel.setForeground(Color.WHITE);
	
		toBeAddedToPlayList = new JComboBox<String>(new  String [0]);
		toBeAddedToPlayList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedPlayList = cb.getSelectedIndex();
			}
		});
		toBeAddedPlayListLabel = new JLabel();
		toBeAddedPlayListLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
		toBeAddedPlayListLabel.setForeground(Color.WHITE);
		toBeAddedPlayListLabel.setBackground(Color.WHITE);
	    
	    // Create Player
		playerLabel= new JLabel();
		playerNameTextField = new JTextField();
		addPlayerButton= new JButton();
		addPlayerButton.setFont(new Font("Belgium", Font.PLAIN, 13));
		addPlayerButton.setBackground(new Color(51, 0, 0));
		addPlayerButton.setForeground(Color.WHITE);
		playerLabel.setForeground(Color.WHITE);
		playerLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
		
		allPlayers = new JComboBox<String>(new  String [0]);
		allPlayers.addActionListener(new java.awt.event.ActionListener() {	

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedPlayer = cb.getSelectedIndex();
			}
		});
		selectPlayerLabel = new JLabel();
		selectPlayerLabel.setForeground(Color.WHITE);
		selectPlayerLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
		addToPlayerButton = new JButton();
		addToPlayerButton.setFont(new Font("Belgium", Font.PLAIN, 13));
		addToPlayerButton.setBackground(new Color(51, 0, 0));
		addToPlayerButton.setForeground(new Color(255, 255, 255));
		
		allPlayList = new JComboBox<String>(new  String [0]);
		allPlayList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedPlayListForPlayer = cb.getSelectedIndex();
			}
		});
		playListToBeAddedToPlayer = new JLabel();
		playListToBeAddedToPlayer.setForeground(Color.WHITE);
		playListToBeAddedToPlayer.setFont(new Font("Belgium", Font.PLAIN, 13));
		
		

		allSongs = new JComboBox<String>(new  String [0]);
		allSongs.addActionListener(new java.awt.event.ActionListener() {
	
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedSongForPlayer = cb.getSelectedIndex();
			}
		});
		
		songToBeAddedToPlayer = new JLabel();
		songToBeAddedToPlayer.setFont(new Font("Belgium", Font.PLAIN, 13));
		songToBeAddedToPlayer.setForeground(Color.WHITE);
		
		allAlbums = new JComboBox<String>(new  String [0]);
		allAlbums.addActionListener(new java.awt.event.ActionListener() {
	

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedAlbumForPlayer = cb.getSelectedIndex();
			}
		});
		albumToBeAddedToPlayer = new JLabel();
		albumToBeAddedToPlayer.setForeground(Color.WHITE);
		albumToBeAddedToPlayer.setFont(new Font("Belgium", Font.PLAIN, 13));
		
		allLocations = new JComboBox<String>(new  String [0]);
		allLocations.addActionListener(new java.awt.event.ActionListener() {
	
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedLocationForPlayer = cb.getSelectedIndex();
			}
		});
		
		playerToBePlayedLabel= new JLabel();
		playerToBePlayedLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
		playerToBePlayedLabel.setForeground(new Color(255, 255, 255));
		allFinalPlayers = new JComboBox<String>(new  String [0]);
		allFinalPlayers.addActionListener(new java.awt.event.ActionListener() {	

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> cb = (JComboBox<String>) evt.getSource();
				selectedFinalPlayer = cb.getSelectedIndex();
			}
		});
		
		locationToBeSetToPlayer = new JLabel();
		locationToBeSetToPlayer.setForeground(Color.WHITE);
		locationToBeSetToPlayer.setFont(new Font("Belgium", Font.PLAIN, 13));
		playButton = new JButton();
		playButton.setBackground(new Color(51, 0, 0));
		playButton.setForeground(Color.WHITE);
		playButton.setFont(new Font("Belgium", Font.PLAIN, 13));
		playButton.setText("Play\n");
	    
		// Mute Location
		
		locationToBeMutedList = new JComboBox<String>(new  String [0]);
		locationToBeMutedList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
					JComboBox<String> cb = (JComboBox<String>) evt.getSource();
					selectedLocationToBeMuted = cb.getSelectedIndex();
			}
		});
		toBeMutedLocationLabel = new JLabel();
		toBeMutedLocationLabel.setFont(new Font("Belgium", Font.PLAIN, 13));
		toBeMutedLocationLabel.setForeground(Color.WHITE);
					
		checkBoxToMuteLocation = new JCheckBox("Mute");
		checkBoxToMuteLocation.setFont(new Font("Belgium", Font.PLAIN, 13));
		checkBoxToMuteLocation.setForeground(Color.WHITE);
		checkBoxToMuteLocation.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JCheckBox cb = (JCheckBox) evt.getSource();
				if(cb.isSelected()){
					isMuted=true;
				}
				else{
					isMuted=false;
				}
							
			}
						
		});
		muteButton = new JButton();
		muteButton.setBackground(new Color(51, 0, 0));
		muteButton.setFont(new Font("Belgium", Font.PLAIN, 13));
		muteButton.setForeground(Color.WHITE);
		
		

		
		
		//Global Settings
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Home Audio System");
		
		companyLabel.setText("© 2016 P.A.M.P.Labs");
		
		//Add Artist Settings
		artistNameLabel.setText("Artist Name:");
		addArtistButton.setText("Add Artist");
		addArtistButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				addArtistButtonActionPerformed(evt);
			}
		});
		
		//Add Album Settings
		albumNameLabel.setText("Album Name:");
		releaseDateLabel.setText("Album Release Date:");
		artistLabel.setText("Artist");
		genreLabel.setText("Genre");
		addAlbumButton.setText("Add Album");
		addAlbumButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				addAlbumButtonActionPerformed(evt);
			}
		});
		
		
		
		//add songs
		songNameLabel.setText("Song Name:");
		durationLabel.setText("Song Duration:");
		albumPosLabel.setText("Album Position:");
		albumLabel.setText("Album:");
		addSongButton.setText("Add Song");
		addSongButton.addActionListener( new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				addSongButtonActionPerformed(evt);
			}
		});
		
		//Location
		locationNameLabel.setText("Location Name:");
		locationVolumeLabel.setText("Location Volume:");
		addLocationButton.setText("Create Location ");
		addLocationButton.addActionListener( new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				addLocationButtonActionPerformed(evt);
			}
		});
		
		toBeMutedLocationLabel.setText("Select Location");	
		muteButton.setText("Mute");
		muteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				muteButtonActionPerformed(evt);
			}
		});
		
		//Playlist
		playlistNameLabel.setText("PlayList Name: ");
		addPlaylistButton.setText("Create PlayList ");
		addPlaylistButton.addActionListener( new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				addPlaylistButtonActionPerformed(evt);
			}
		});
		
		// addSong to PLaylist
		addSongToPlayListLabel.setText("Select Song: ");
		toBeAddedPlayListLabel.setText("Select PlayList: ");
		addSongToPlayListButton.setText("Add to PlayList");
		addSongToPlayListButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addSongToPlayListButtonActionPerformed(evt);
			}
		});
		
		//Player
		playerLabel.setText("Player");
		addPlayerButton.setText("Create Player");
		addPlayerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addPlayerButtonActionPerformed(evt);
			}
		});
		selectPlayerLabel.setText("Select Player");
		songToBeAddedToPlayer.setText("Select Song");
		albumToBeAddedToPlayer.setText("Select Album:");
		playListToBeAddedToPlayer.setText("Select Playlist");
		addToPlayerButton.setText("Add To Player");
		addToPlayerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addToPlayerButtonActionPerformed(evt);
			}
		});
		
		playerToBePlayedLabel.setText("Select Player To Play");
		
		locationToBeSetToPlayer.setText("Select Location");
		playButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				playButtonActionPerformed(evt);
			}
		});
		

		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addComponent(errorMessage)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(artistNameLabel)
						.addComponent(locationNameLabel)
						.addComponent(locationVolumeLabel)
						.addComponent(toBeMutedLocationLabel)
						.addComponent(playerLabel))
					.addGap(27)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(artistNameTextField, 200, 200, 400)
						.addComponent(addArtistButton)
						.addComponent(locationNameTextField, 200, 200, 400)
						.addComponent(locationVolume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(addLocationButton)
						.addComponent(locationToBeMutedList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBoxToMuteLocation)
						.addComponent(muteButton)
						.addComponent(playerNameTextField, 105, 105, 105)
						.addComponent(addPlayerButton))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(albumNameLabel)
						.addComponent(releaseDateLabel)
						.addComponent(artistLabel)
						.addComponent(genreLabel)
						.addComponent(playlistNameLabel)
						.addComponent(addSongToPlayListLabel)
						.addComponent(toBeAddedPlayListLabel))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(albumNameTextField, 202, 202, 400)
						.addComponent(releaseDatePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(artistList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(genreList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(addAlbumButton)
						.addComponent(playlistNameTextField, 200, 200, 400)
						.addComponent(addPlaylistButton)
						.addComponent(songList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(toBeAddedToPlayList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(layout.createParallelGroup(Alignment.TRAILING)
								.addComponent(addSongToPlayListButton)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
									.addComponent(allFinalPlayers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(playerToBePlayedLabel)))))
					.addGap(6)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(songNameLabel)
						.addComponent(durationLabel)
						.addComponent(albumPosLabel)
						.addComponent(selectPlayerLabel)
						.addComponent(playListToBeAddedToPlayer)
						.addComponent(songToBeAddedToPlayer)
						.addComponent(albumToBeAddedToPlayer)
						.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(locationToBeSetToPlayer, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(albumLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(playButton)
						.addComponent(songNameTextField, 86, 86, 86)
						.addComponent(durationTimeSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(albumPosTextField, 86, 86, 86)
						.addComponent(albumList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(addSongButton)
						.addComponent(allPlayers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(allPlayList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(allSongs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(allAlbums, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(addToPlayerButton)
						.addComponent(allLocations, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6))
				.addGroup(Alignment.LEADING, layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(companyLabel)
					.addContainerGap(766, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(errorMessage)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(artistNameLabel)
						.addComponent(artistNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(albumNameLabel)
						.addComponent(albumNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(songNameLabel)
						.addComponent(songNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(addArtistButton)
						.addComponent(releaseDateLabel)
						.addComponent(releaseDatePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(durationLabel)
						.addComponent(durationTimeSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(locationNameLabel)
						.addComponent(locationNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(artistLabel)
						.addComponent(artistList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(albumPosTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(albumPosLabel))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(locationVolumeLabel)
						.addComponent(locationVolume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(genreLabel)
						.addComponent(genreList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(albumLabel)
						.addComponent(albumList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(addLocationButton)
						.addComponent(addAlbumButton)
						.addComponent(addSongButton))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(playlistNameLabel)
						.addComponent(playlistNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(selectPlayerLabel)
						.addComponent(allPlayers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(addPlaylistButton)
						.addComponent(playListToBeAddedToPlayer)
						.addComponent(allPlayList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(toBeMutedLocationLabel)
						.addComponent(locationToBeMutedList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(addSongToPlayListLabel)
						.addComponent(songList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(songToBeAddedToPlayer)
						.addComponent(allSongs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(checkBoxToMuteLocation)
						.addComponent(toBeAddedPlayListLabel)
						.addComponent(toBeAddedToPlayList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(albumToBeAddedToPlayer)
						.addComponent(allAlbums, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(muteButton)
						.addComponent(addSongToPlayListButton)
						.addComponent(addToPlayerButton))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(playerLabel)
						.addComponent(playerNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addComponent(addPlayerButton)
							.addComponent(allLocations, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(locationToBeSetToPlayer)
								.addComponent(playerToBePlayedLabel))
							.addGap(18)
							.addComponent(allFinalPlayers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(31)
					.addComponent(playButton)
					.addGap(13)
					.addComponent(companyLabel)
					.addContainerGap())
		);
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {allLocations, playButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {addSongToPlayListButton, toBeAddedToPlayList});
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {playlistNameTextField, addPlaylistButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {locationVolume, addLocationButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {albumLabel, addSongButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {genreList, addAlbumButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {artistNameTextField, addArtistButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {checkBoxToMuteLocation, muteButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {playerNameTextField, addPlayerButton});
		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {addToPlayerButton, allAlbums});
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		pack();
		
	}
	
	private void refreshData(){
		
		HomeAudioSystem HAS = HomeAudioSystem.getInstance();
		
		errorMessage.setText(error);
		if(error==null||error.length()==0){
			
			//Artist
			artists = new HashMap<Integer, Artist>();
			artistList.removeAllItems();
			Iterator<Artist> I = HAS.getArtists().iterator();
			Integer index = 0;
			while(I.hasNext())
			{	
				Artist a = I.next();
				artists.put(index, a);
				artistList.addItem(a.getName());
				index++;
			}
			selectedArtist = -1;
			artistList.setSelectedIndex(selectedArtist);
			
			
			//Album
			albums = new HashMap<Integer, Album>();
			albumList.removeAllItems();
			Iterator<Album> A= HAS.getAlbums().iterator();
			int index2=0;
			while(A.hasNext()){
				Album a =A.next();
				albums.put(index2,a);
				albumList.addItem(a.getName());
				index2++;
			}
			selectedAlbum=-1;
			albumList.setSelectedIndex(selectedAlbum);
			
			//Location
			locationNameTextField.setText("");
			locationVolume.setValue(50);
			locations = new HashMap<Integer, Location>();
			locationToBeMutedList.removeAllItems();
			Iterator<Location> L= HAS.getLocations().iterator();
			int n=0;
			while(L.hasNext()){
				Location l = L.next();
				locations.put(n,l);
				locationToBeMutedList.addItem(l.getName());
				n++;
			}
			selectedLocationToBeMuted=-1;
			locationToBeMutedList.setSelectedIndex(selectedLocationToBeMuted);
//			
			//Playlist
			playlistNameTextField.setText("");
			
			//Songs
			songs = new HashMap<Integer, Song>();
			songList.removeAllItems();
			Iterator<Song> S= HAS.getSongs().iterator();
			int index3=0;
			while(S.hasNext()){
				Song s =S.next();
				songs.put(index3,s);
				songList.addItem(s.getTitle());
				index3++;
			}
			selectedSongForPlayList=-1;
			songList.setSelectedIndex(selectedSongForPlayList);
			
			
			//PlayList
			playlists = new HashMap<Integer, Playlist>();
			toBeAddedToPlayList.removeAllItems();
			Iterator<Playlist> P= HAS.getPlaylists().iterator();
			int index4=0;
			while(P.hasNext()){
				Playlist p =P.next();
				playlists.put(index4,p);
				toBeAddedToPlayList.addItem(p.getTitle());
				index4++;
			}
			selectedPlaylist=-1;
			toBeAddedToPlayList.setSelectedIndex(selectedPlaylist);
			
			//Player
			allPlayList.removeAllItems();
			Iterator<Playlist> aP= HAS.getPlaylists().iterator();
			int i=0;
			while(aP.hasNext()){
				Playlist p =aP.next();
				playlists.put(i,p);
				allPlayList.addItem(p.getTitle());
				i++;
			}
			selectedPlayListForPlayer=-1;
			allPlayList.setSelectedIndex(selectedPlayListForPlayer);
			
			allSongs.removeAllItems();
			Iterator<Song> aS= HAS.getSongs().iterator();
			int j=0;
			while(aS.hasNext()){
				Song s =aS.next();
				songs.put(j,s);
				allSongs.addItem(s.getTitle());
				j++;
			}
			selectedSongForPlayer=-1;
			allSongs.setSelectedIndex(selectedSongForPlayer);
			
			allAlbums.removeAllItems();
			Iterator<Album> aA= HAS.getAlbums().iterator();
			int k=0;
			while(aA.hasNext()){
				Album a =aA.next();
				albums.put(k,a);
				allAlbums.addItem(a.getName());
				k++;
			}
			selectedAlbumForPlayer=-1;
			allAlbums.setSelectedIndex(selectedAlbumForPlayer);
			
			
			allLocations.removeAllItems();
			Iterator<Location> aL= HAS.getLocations().iterator();
			int m=0;
			while(aL.hasNext()){
				Location l = aL.next();
				locations.put(m,l);
				allLocations.addItem(l.getName());
				k++;
			}
			selectedLocationForPlayer=-1;
			allLocations.setSelectedIndex(selectedLocationForPlayer);
				
			players = new HashMap<Integer,Player>();
			allPlayers.removeAllItems();
			Iterator<Player> Pl= HAS.getPlayers().iterator();
			int s=0;
			while(Pl.hasNext()){
				Player p = Pl.next();
				players.put(s,p);
				allPlayers.addItem(p.getName());
				s++;
			}
			selectedPlayer=-1;
			allPlayers.setSelectedIndex(selectedPlayer);
			
			allFinalPlayers.removeAllItems();
			Iterator<Player> Pf= HAS.getPlayers().iterator();
			int v=0;
			while(Pf.hasNext()){
				Player p = Pf.next();
				players.put(v,p);
				allFinalPlayers.addItem(p.getName());
				v++;
			}
			selectedFinalPlayer=-1;
			allFinalPlayers.setSelectedIndex(selectedFinalPlayer);
			
			//Reset Input Fields
			artistNameTextField.setText("");
			albumNameTextField.setText("");
			releaseDatePicker.getModel().setValue(null);
			songNameTextField.setText("");
			albumPosTextField.setText("");
			playerNameTextField.setText("");
			
			SimpleDateFormat format = new SimpleDateFormat("mm:ss");
			try {
				durationTimeSpinner.setValue(format.parseObject("00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
		}
		
		pack();
	}

	// Add selected Song to selected PlayList
	private void addSongToPlayListButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		if(selectedSongForPlayList < 0) {
			error = error + "Song needs to be selected! ";
		}
		if(selectedPlayList < 0) {
			error = error + "PlayList needs to be selected to add to! ";
		}
		error = error.trim();
		if(error.length() == 0) {
			// Call the Controller
			HomeAudioSystemController HASc = new HomeAudioSystemController();
			try {
				HASc.addSongToPlaylist(songs.get(selectedSongForPlayList), playlists.get(selectedPlayList));
			} catch(InvalidInputException e) {
				error = e.getMessage();
			}
		}
		// Update Visuals
		refreshData();
	}
	
	private void addArtistButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		//Call the controller
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		error = null;
		try
		{
			HASc.createArtist(artistNameTextField.getText());
		}
		catch (InvalidInputException e)
		{
			error = e.getMessage();
		}
		// Update visuals
		refreshData();
	}
	
	private void addAlbumButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		//Call the controller
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		Genre albumGenre= Genre.valueOf(genres[selectedGenre]);
		error = "";
		
		if(selectedArtist < 0)
		{
			error = error + "Artist needs to be selected for Add Album! ";
		}
		
		if(error.length() == 0)
		{
			try
			{
				HASc.createAlbum(albumNameTextField.getText(),(java.sql.Date) releaseDatePicker.getModel().getValue(),artists.get(selectedArtist),albumGenre );
			}
			catch (InvalidInputException e)
			{
				error = e.getMessage();
			}
		}
		// Update visuals
		refreshData();
	}
	
	private void addSongButtonActionPerformed(java.awt.event.ActionEvent evt){
		
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime((Date) durationTimeSpinner.getValue());
		calendar.set(2016, 1, 1);
		Time durationTime = new Time(calendar.getTime().getTime());
		error = "";
		 if(selectedAlbum <0){
			 error= error + "Album needs to be selected to Add Song! ";
		 }
		 
		 if(error.length()==0){
			 try{
				 HASc.createSong(songNameTextField.getText(), durationTime, Integer.parseInt(albumPosTextField.getText()), albums.get(selectedAlbum));
			 }
			 catch(InvalidInputException e){
				 error = e.getMessage();
			 }
		 }
		
		refreshData();
	}
	
	private void addLocationButtonActionPerformed(java.awt.event.ActionEvent evt){
		//Call the controller
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		error = "";
		try{
		HASc.createLocation(locationNameTextField.getText(),locationVolume.getValue());
		}
		catch(InvalidInputException e){
			error=e.getMessage();
		}
		// Update visuals
		refreshData();
		
		
	}
	
	private void addPlaylistButtonActionPerformed(java.awt.event.ActionEvent evt){
	
		//Call the controller
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		error = null;
		try
		{
			HASc.createPlaylist(playlistNameTextField.getText());
		}
		catch (InvalidInputException e)
		{
			error = e.getMessage();
		}
		// Update visuals
		refreshData();
	}
	private void addPlayerButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		//Call the controller
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		error = null;
		try
		{
			HASc.createPlayer(playerNameTextField.getText());
		}
		catch (InvalidInputException e)
		{
			error = e.getMessage();
		}
		// Update visuals
		refreshData();
	}
	
	private void addToPlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		if(selectedAlbumForPlayer<0 & selectedSongForPlayer<0 & selectedPlayListForPlayer < 0)
		{
			error = error + "Content needs to be selected to Play! ";
		}
		error = error.trim();
		if(error.length() == 0) {
			// Call the Controller
			HomeAudioSystemController HASc = new HomeAudioSystemController();
			try {
				if(selectedSongForPlayer>=0){
					HASc.addSongToPlayer(players.get(selectedPlayer),songs.get(selectedSongForPlayer));
				}
				if(selectedAlbumForPlayer>=0){
					HASc.addAlbumToPlayer(players.get(selectedPlayer),albums.get(selectedAlbumForPlayer));
				}
				if(selectedPlayListForPlayer>=0){
					HASc.addPlayListToPlayer(players.get(selectedPlayer),playlists.get(selectedPlayListForPlayer));
				}
			} catch(InvalidInputException e) {
				error = e.getMessage();
			}
		}
		// Update Visuals
		refreshData();
	}
	private void playButtonActionPerformed(java.awt.event.ActionEvent evt){
		
		//Call the controller
		HomeAudioSystemController HASc = new HomeAudioSystemController();
		error = null;
		if(selectedLocationForPlayer < 0)
		{
			error = error + "Location needs to be set to Play! ";
		}
		
		if(error.length()==0){
			try
			{					
				HASc.addLocationToPlayer(players.get(selectedFinalPlayer),locations.get(selectedLocationForPlayer));
				HASc.playContent(players.get(selectedFinalPlayer));				
			}
			catch (InvalidInputException e)
			{
				error = e.getMessage();
			}
		}
		// Update visuals
		refreshData();
	}
	
	private void muteButtonActionPerformed(java.awt.event.ActionEvent evt) {
		HomeAudioSystemController HASc = new HomeAudioSystemController();

		try {
			HASc.muteLocation(locations.get(selectedLocationToBeMuted), locations.get(selectedLocationToBeMuted).setMuted(isMuted));
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
	}
}
