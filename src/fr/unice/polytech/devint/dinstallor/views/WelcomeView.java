package fr.unice.polytech.devint.dinstallor.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.unice.polytech.devint.dinstallor.controllers.InstallationController;
import fr.unice.polytech.devint.dinstallor.models.Picture;

public class WelcomeView extends InstallationView {

	public WelcomeView(InstallationController ic) {
		super(ic);
		
		this.setLayout(new BorderLayout());
		
		JPanel headPanel = new JPanel();
		headPanel.setLayout(new BorderLayout());
		
		headPanel.add((new Picture("." + File.separator + "resources" + File.separator + "header.png")).getJLabel(), BorderLayout.NORTH);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		
		titlePanel.add(Box.createHorizontalGlue());
		
		JLabel title = new JLabel("Bienvenue dans l'installation du CD DeViNT " + ic.getYear() + "!");
		title.setFont(title.getFont().deriveFont(Font.BOLD));
		title.setFont(title.getFont().deriveFont(Float.parseFloat("25")));
		titlePanel.add(title);
		
		titlePanel.add(Box.createHorizontalGlue());
		
		
		headPanel.add(titlePanel, BorderLayout.CENTER);
		
		this.add(headPanel, BorderLayout.NORTH);
		
		JPanel welcomePanel = new JPanel();
		welcomePanel.setBackground(Color.WHITE);
		
		String welcomeText = "<html>";
		welcomeText += "<p>Vous êtes sur le point d'installer les projets présents sur ce CD DeViNT " + ic.getYear() + " et nous vous en remercions.</p>";
		welcomeText += "<p>Cliquez sur le bouton 'Suivant' pour continuer l'installation.</p>";
		welcomeText += "</html>";
		
		JLabel welcomeContent = new JLabel(welcomeText);
		welcomePanel.add(welcomeContent);
		
		this.add(welcomePanel, BorderLayout.CENTER);
		
		JPanel actions = new JPanel();
		actions.setLayout(new BorderLayout());
		this.add(actions, BorderLayout.SOUTH);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		actions.add(buttons, BorderLayout.EAST);
		
		JButton cancelButton = new JButton("Annuler");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				cancel();
			}
			
		});
		buttons.add(cancelButton);
		
		JButton nextButton = new JButton("Suivant");
		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				nextStep();
			}
			
		});
		buttons.add(nextButton);
	}
	
	public void cancel() {
		int n = JOptionPane.showConfirmDialog(
			    this.getController(),
			    "Voulez-vous vraiment quitter l'installation ?",
			    "",
			    JOptionPane.YES_NO_OPTION);
			if(n == JOptionPane.YES_OPTION) {
				this.getController().cancel();
			}
	}
	
	public void nextStep() {
		this.getController().nextStep();
	}
}
