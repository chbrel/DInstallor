package fr.unice.polytech.devint.dinstallor.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.unice.polytech.devint.dinstallor.controllers.InstallationController;

public class EndView extends InstallationView {

	public EndView(InstallationController ic) {
		super(ic);
		
		this.setLayout(new BorderLayout());
		
		JLabel title = new JLabel("CD DeViNT 2011 Installation Finished!");
		this.add(title, BorderLayout.NORTH);
		
		JLabel endContent = new JLabel("Merci d'avoir installé les projets du CD DeViNT 2011!");
		this.add(endContent, BorderLayout.CENTER);
		
		JPanel actions = new JPanel();
		actions.setLayout(new BorderLayout());
		this.add(actions, BorderLayout.SOUTH);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		actions.add(buttons, BorderLayout.EAST);
		
		JButton endButton = new JButton("Quitter");
		endButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				end();
			}
			
		});
		buttons.add(endButton);
		
/*		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				nextStep();
			}
			
		});
		buttons.add(nextButton);
*/
	}
	
	public void end() {
		this.getController().cancel();
	}
	
/*	public void nextStep() {
		this.getController().nextStep();
	}
*/
}
