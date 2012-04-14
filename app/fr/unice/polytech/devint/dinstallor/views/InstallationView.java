package fr.unice.polytech.devint.dinstallor.views;

import javax.swing.JPanel;

import controllers.InstallationController;


public class InstallationView extends JPanel {
	
	private InstallationController ic;
	
	public InstallationView(InstallationController ic) {
		this.ic = ic;
	}
	
	public InstallationController getController() {
		return this.ic;
	}
}
