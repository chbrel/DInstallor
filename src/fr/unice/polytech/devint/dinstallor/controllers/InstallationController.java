package fr.unice.polytech.devint.dinstallor.controllers;

import javax.swing.JFrame;

import fr.unice.polytech.devint.dinstallor.views.LicenceView;
import fr.unice.polytech.devint.dinstallor.views.WelcomeView;

public class InstallationController extends JFrame {

	private WelcomeView wv;
	private LicenceView lv;
	
	public InstallationController() {
		super("View Manager");
		this.wv = new WelcomeView(this);
		this.lv = new LicenceView(this);
		
		this.init();
	}
	
	public void init() {
		this.setContentPane(this.wv);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	public void nextStep() {
		if(this.getContentPane().equals(this.wv)) {
			this.setContentPane(this.lv);
			this.pack();
		}
	}
	
	public static void main(String[] args) {
		InstallationController ic = new InstallationController();
	}
}
