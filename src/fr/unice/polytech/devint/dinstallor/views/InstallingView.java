package fr.unice.polytech.devint.dinstallor.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import fr.unice.polytech.devint.dinstallor.controllers.InstallationController;
import fr.unice.polytech.devint.dinstallor.models.Picture;

public class InstallingView extends InstallationView {
	
	private JLabel copyingContent;
	private String copyingLogs;
	
	private JPanel buttons;
	private JButton nextButton;
	
	public InstallingView(InstallationController ic) {
		super(ic);
		
		this.setLayout(new BorderLayout());
		
		JPanel headPanel = new JPanel();
		headPanel.setLayout(new BorderLayout());
		
		headPanel.add((new Picture("." + File.separator + "resources" + File.separator + "logo_devint.png")).getJLabel(), BorderLayout.WEST);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		
		titlePanel.add(Box.createHorizontalGlue());
		
		JLabel title = new JLabel("Installation en cours");
		title.setFont(title.getFont().deriveFont(Font.BOLD));
		title.setFont(title.getFont().deriveFont(Float.parseFloat("25")));
		titlePanel.add(title);
		
		titlePanel.add(Box.createHorizontalGlue());
		
		
		headPanel.add(titlePanel, BorderLayout.CENTER);
		
		this.add(headPanel, BorderLayout.NORTH);
		
		JLabel welcomeContent = new JLabel("");
		this.add(welcomeContent, BorderLayout.CENTER);
		
		this.copyingContent = new JLabel("");
		this.copyingLogs = "";
		
		JPanel copyingPanel = new JPanel();
		copyingPanel.setBackground(Color.WHITE);
		copyingPanel.add(copyingContent);
		JScrollPane scrollPane = new JScrollPane(copyingPanel);
//		s.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){
			public void adjustmentValueChanged(AdjustmentEvent e){
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});
		this.add(scrollPane, BorderLayout.CENTER);
		
		
		JPanel actions = new JPanel();
		actions.setLayout(new BorderLayout());
		this.add(actions, BorderLayout.SOUTH);
		
		this.buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		actions.add(buttons, BorderLayout.EAST);
/*		
		JButton cancelButton = new JButton("Annuler");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				cancel();
			}
			
		});
		buttons.add(cancelButton);
*/		
		/*this.nextButton = new JButton("Suivant");
		this.nextButton.disable();
		this.nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				nextStep();
			}
			
		});
		buttons.add(this.nextButton);*/
	}
	
	public void concat(String content) {
		this.copyingLogs += "<br/>" + content;
		this.copyingContent.setText("<html>" + this.copyingLogs + "</html>");
		
//		Rectangle newRec = new Rectangle(getVisibleRect().width, this.copyingPanel.getHeight());
		
//		this.scrollPane.scrollRectToVisible(newRec);
		
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

	public void installationFinished() {
		this.concat("<br/><br/>");
		this.concat("<span style=\"color:green;font-style:bold;\">L'installation est terminée. Cliquez sur le bouton 'Suivant' pour continuer.</span>");
		this.nextButton = new JButton("Suivant");
//		this.nextButton.disable();
		this.nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				nextStep();
			}
			
		});
		buttons.add(this.nextButton);
		this.nextButton.enable();
		this.validate();
	}
}
