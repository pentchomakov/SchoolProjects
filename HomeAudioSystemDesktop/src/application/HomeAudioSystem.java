package application;

import persistence.PersistenceHomeAudioSystem;
import view.HomeAudioSystemPage;



public class HomeAudioSystem {

	public static void main(String[] args) {

		//Load XML file
		PersistenceHomeAudioSystem.loadHomeAudioSystemModel();

		// Start UI
				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run()
					{
						new HomeAudioSystemPage().setVisible(true);
					}
				});

			}

		}
