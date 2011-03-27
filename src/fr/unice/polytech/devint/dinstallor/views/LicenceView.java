package fr.unice.polytech.devint.dinstallor.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import fr.unice.polytech.devint.dinstallor.controllers.InstallationController;

public class LicenceView extends InstallationView {

	public LicenceView(InstallationController ic) {
		super(ic);
		
		this.setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Licence");
		this.add(title, BorderLayout.NORTH);
		
		String licenceContent="";
		String licenceFile ="../resources/licence.txt";
			
		try {
			InputStream ips=new FileInputStream(licenceFile); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine()) != null){
				licenceContent += ligne + "\n";
			}
			br.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JLabel label = new JLabel(licenceContent);
		JPanel panel = new JPanel();
		panel.add(label);
		JScrollPane s = new JScrollPane(panel);
						s.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(s, BorderLayout.CENTER);
		
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
