package fr.unice.polytech.devint.dinstallor.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.unice.polytech.devint.dinstallor.controllers.InstallationController;

public class WelcomeView extends InstallationView {

	public WelcomeView(InstallationController ic) {
		super(ic);
		
		this.setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Welcome On Board! Lat's go for CD DeViNT 2011 Installation!");
		this.add(title, BorderLayout.NORTH);
		
		JLabel welcomeContent = new JLabel("Welcome content!");
		this.add(welcomeContent, BorderLayout.CENTER);
		
		JPanel actions = new JPanel();
		actions.setLayout(new BorderLayout());
		this.add(actions, BorderLayout.SOUTH);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		actions.add(buttons, BorderLayout.EAST);
		
		JButton cancelButton = new JButton("Cancel");
		buttons.add(cancelButton);
		
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				nextStep();
			}
			
		});
		buttons.add(nextButton);
	}
	
	public void nextStep() {
		this.getController().nextStep();
	}
}
