package fr.unice.polytech.devint.dinstallor.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.unice.polytech.devint.dinstallor.controllers.InstallationController;

public class ConfigView extends InstallationView {
	
	private JTextField installFolderInfo;
	
	private String defaultInstallationFolder;

	public ConfigView(InstallationController ic, String defaultInstallationFolder) {
		super(ic);
		
		this.defaultInstallationFolder = defaultInstallationFolder;
		
		this.setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Configuration de l'installation");
		this.add(title, BorderLayout.NORTH);
		
		JPanel configPanel = new JPanel();
		configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
		this.add(configPanel);
		
		JPanel installFolderPanel = new JPanel();
		installFolderPanel.setLayout(new BoxLayout(installFolderPanel, BoxLayout.Y_AXIS));
		configPanel.add(installFolderPanel);
		
		JLabel installFolderLabel =  new JLabel("Dossier d'installation:");
		installFolderPanel.add(installFolderLabel);
		this.installFolderInfo = new JTextField(this.defaultInstallationFolder);
		installFolderPanel.add(installFolderInfo);
		
		JPanel actions = new JPanel();
		actions.setLayout(new BorderLayout());
		this.add(actions, BorderLayout.SOUTH);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		actions.add(buttons, BorderLayout.EAST);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				cancel();
			}
			
		});
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
