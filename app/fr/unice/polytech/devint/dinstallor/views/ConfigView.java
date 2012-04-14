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
import javax.swing.JTextField;

import controllers.InstallationController;

import fr.unice.polytech.devint.dinstallor.models.Picture;

public class ConfigView extends InstallationView {
	
	private JTextField installFolderInfo;
	
	private String defaultInstallationFolder;

	public ConfigView(InstallationController ic, String defaultInstallationFolder) {
		super(ic);
		
		this.defaultInstallationFolder = defaultInstallationFolder;
		
		this.setLayout(new BorderLayout());
		
		JPanel headPanel = new JPanel();
		headPanel.setLayout(new BorderLayout());
		
		headPanel.add((new Picture("." + File.separator + "resources" + File.separator + "logo_devint.png")).getJLabel(), BorderLayout.WEST);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		
		titlePanel.add(Box.createHorizontalGlue());
		
		JLabel title = new JLabel("Configuration de l'installation");
		title.setFont(title.getFont().deriveFont(Font.BOLD));
		title.setFont(title.getFont().deriveFont(Float.parseFloat("25")));
		titlePanel.add(title);
		
		titlePanel.add(Box.createHorizontalGlue());
		
		
		headPanel.add(titlePanel, BorderLayout.CENTER);
		
		this.add(headPanel, BorderLayout.NORTH);
		
		
		
		JPanel configPanel = new JPanel();
		configPanel.setLayout(new BorderLayout());
		configPanel.setBackground(Color.WHITE);
		this.add(configPanel);
		
		JPanel installFolderPanel = new JPanel();
		installFolderPanel.setBackground(Color.WHITE);
		installFolderPanel.setLayout(new BoxLayout(installFolderPanel, BoxLayout.X_AXIS));
		configPanel.add(installFolderPanel, BorderLayout.CENTER);
		
		JLabel installFolderLabel =  new JLabel("Dossier d'installation:");
		installFolderPanel.add(installFolderLabel);
		this.installFolderInfo = new JTextField(this.defaultInstallationFolder);
		Dimension d = new Dimension(800, 50);
		this.installFolderInfo.setPreferredSize(d);
		this.installFolderInfo.setSize(d);
		this.installFolderInfo.setMaximumSize(d);
		this.installFolderInfo.setMinimumSize(d);
		
		installFolderPanel.add(installFolderInfo);
		
//		configPanel.add(Box.createVerticalGlue());
		
		
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
		    "Voulez-vous vraiment quitter l'installation?",
		    "",
		    JOptionPane.YES_NO_OPTION);
		if(n == JOptionPane.YES_OPTION) {
			this.getController().cancel();
		}
	}
	
	public void nextStep() {
		this.getController().setInstallationFolder(this.installFolderInfo.getText());
		this.getController().nextStep();
	}
}
