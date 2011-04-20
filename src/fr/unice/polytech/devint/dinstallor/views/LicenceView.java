package fr.unice.polytech.devint.dinstallor.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import fr.unice.polytech.devint.dinstallor.controllers.InstallationController;
import fr.unice.polytech.devint.dinstallor.models.Picture;

public class LicenceView extends InstallationView {

	public LicenceView(InstallationController ic) {
		super(ic);
		
		this.setLayout(new BorderLayout());
		
		JPanel headPanel = new JPanel();
		headPanel.setLayout(new BorderLayout());
		
		headPanel.add((new Picture("." + File.separator + "resources" + File.separator + "logo_devint.png")).getJLabel(), BorderLayout.WEST);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		
		titlePanel.add(Box.createHorizontalGlue());
		
		JLabel title = new JLabel("Les projets DeViNT sont sous licence !");
		title.setFont(title.getFont().deriveFont(Font.BOLD));
		title.setFont(title.getFont().deriveFont(Float.parseFloat("25")));
		titlePanel.add(title);
		
		titlePanel.add(Box.createHorizontalGlue());
		
		
		headPanel.add(titlePanel, BorderLayout.CENTER);
		
		this.add(headPanel, BorderLayout.NORTH);
		
		
		/*JPanel licencePanel = new JPanel();
		licencePanel.setBackground(Color.WHITE);
		
		String licenceContent = "<html>";
		String licenceFile = "." + File.separator + "resources" + File.separator + "licence.txt";
			
		try {
			InputStream ips=new FileInputStream(licenceFile); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine()) != null){
				licenceContent += ligne + "<br/>";
			}
			br.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		licenceContent += "</html>";
		
		JLabel label = new JLabel(licenceContent);
		JTextField label = new JTextField(licenceContent);
		label.setEditable(false);
//		JPanel panel = new JPanel();
//		panel.add(label);

		licencePanel.add(label);
		
//		JScrollPane s = new JScrollPane(panel);
//		s.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JScrollPane s = new JScrollPane(licencePanel);
		s.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);*/
		
//		licencePanel.add(s);
		
		JPanel licencePanel = new JPanel();
		licencePanel.setBackground(Color.WHITE);
		
		String licenceText = "<html>";
		licenceText += "<p>Ce CD regroupe un ensemble de projets Devint, projets logiciels développés à l'école Polytech'Nice-Sophia, dans le cadre de la journée DeViNT ( cf. <a href=\"http://devint.polytech.unice.fr\">http://devint.polytech.unice.fr</a>).</p><br/>";
		licenceText += "<p>Les auteurs de ces projets sont les enseignants et les élèves du département Sciences Informatiques.</p><br/>";
		licenceText += "<p>Les projets DeViNT sont distribués gratuitement avec leurs sources, sous licence LGPL.</p><br/>";
		licenceText += "<p>Le projet VocalyzeSIVOX utilise le logiciel MBROLA de l'Université Polytechnique de Mons (Belgique) ainsi que les sept voix françaises de MBROLA qui sont soumis à la licence MBROLA, voir <a href=\"http://tcts.fpms.ac.be/synthesis/mbrola/mbrlicen.html\">http://tcts.fpms.ac.be/synthesis/mbrola/mbrlicen.html</a>.</p><br/>";
		licenceText += "<p>Les projets Devint n'enfreignent pas à notre connaissance les règles de copyright.</p><br/>";
		licenceText += "<p>Cependant, merci de nous signaler tout manquement éventuel à corriger.</p><br/>";
		licenceText += "<p>Vous pouvez modifier les projets DeViNT, les utiliser et les redistribuer sous les termes de la licence MBROLA et de la licence LGPL.</p><br/><br/>";
		licenceText += "<p>Cliquez sur le bouton 'Suivant' pour continuer l'installation.</p>";
		licenceText += "</html>";
		
		JLabel licenceContent = new JLabel(licenceText);
		licenceContent.setPreferredSize(new Dimension(900,718));
		licencePanel.add(licenceContent);
		
		this.add(licencePanel, BorderLayout.CENTER);
		
		//this.add(s, BorderLayout.CENTER);
		
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
		this.getController().nextStep();
	}
}
