package fr.unice.polytech.devint.dinstallor.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import fr.unice.polytech.devint.dinstallor.controllers.InstallationController;

public class InstallingView extends InstallationView {

	private JLabel copyingContent;
	private String copyingLogs;
	
	public InstallingView(InstallationController ic) {
		super(ic);
		
		this.setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Copie des fichiers");
		this.add(title, BorderLayout.NORTH);
		
		JLabel welcomeContent = new JLabel("Welcome content!");
		this.add(welcomeContent, BorderLayout.CENTER);
		
		this.copyingContent = new JLabel("");
		this.copyingLogs = "";
		
		JPanel panel = new JPanel();
		panel.add(copyingContent);
		JScrollPane s = new JScrollPane(panel);
//		s.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(s, BorderLayout.CENTER);
		
		
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
	
	public void concat(String content) {
		this.copyingLogs += "<br/>" + content;
		this.copyingContent.setText("<html>" + this.copyingLogs + "</html>");
		this.validate();
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
