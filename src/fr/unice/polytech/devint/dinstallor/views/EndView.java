package fr.unice.polytech.devint.dinstallor.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

public class EndView extends InstallationView {

	public EndView(InstallationController ic) {
		super(ic);
		
		this.setLayout(new BorderLayout());
		
		JPanel headPanel = new JPanel();
		headPanel.setLayout(new BorderLayout());
		
		headPanel.add((new Picture("." + File.separator + "resources" + File.separator + "logo_devint.png")).getJLabel(), BorderLayout.WEST);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		
		titlePanel.add(Box.createHorizontalGlue());
		
		JLabel title = new JLabel("L'installation du CD DeViNT " + ic.getYear() + " est terminée!");
		title.setFont(title.getFont().deriveFont(Font.BOLD));
		title.setFont(title.getFont().deriveFont(Float.parseFloat("25")));
		titlePanel.add(title);
		
		titlePanel.add(Box.createHorizontalGlue());
		
		
		headPanel.add(titlePanel, BorderLayout.CENTER);
		
		this.add(headPanel, BorderLayout.NORTH);
		
		/*JPanel endPanel = new JPanel();
		endPanel.setBackground(Color.WHITE);
		
		JLabel endContent = new JLabel("Merci d'avoir installé les projets du CD DeViNT 2011!");
		
		endPanel.add(endContent);
		this.add(endPanel, BorderLayout.CENTER);*/
		
		JPanel endPanel = new JPanel();
		endPanel.setBackground(Color.WHITE);
		
		String endText = "<html>";
		endText += "<p>Les projets sélectionnés ont été installés, et une icône de lancement intitulée \"Jeux DeViNT\" a été ajoutée sur le bureau.</p><br/>";
		endText += "<p>La synthèse vocale SIVOX qui a été installée pour les projets Devint utilise le moteur et les voix françaises du projet MBROLA de l'Université Polytechnique de Mons en Belgique.</p><br/>";
		endText += "<p>Une aide sur les projets a été installée, ainsi qu'une icône \"Aide DeViNT\" placée sur le bureau pour y accéder.</p><br/>";
		endText += "<p>Vous pourrez dans le futur rajouter de nouveaux projets tirés du CD, ou les désinstaller tous en une fois.</p><br/><br/>";
		endText += "<p>A vous de jouer maintenant !</p><br/><br/>";
		endText += "<p>Cliquez sur le bouton 'Quitter' pour sortir de l'installation.</p>";
		endText += "</html>";
		
		JLabel endContent = new JLabel(endText);
		endContent.setPreferredSize(new Dimension(900,718));
		endPanel.add(endContent);
		
		this.add(endPanel, BorderLayout.CENTER);
		
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
