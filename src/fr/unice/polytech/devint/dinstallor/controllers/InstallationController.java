package fr.unice.polytech.devint.dinstallor.controllers;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import fr.unice.polytech.devint.dinstallor.models.Game;
import fr.unice.polytech.devint.dinstallor.models.OSValidator;
import fr.unice.polytech.devint.dinstallor.views.*;

public class InstallationController extends JFrame {

	private WelcomeView wv;
	private LicenceView lv;
	private ConfigView cv;
	private ComponentsChoicesView ccv;
	private InstallingView iv;
	
	
	private String installFolder;
	private ArrayList<Game> toInstall;
	
	public InstallationController() {
		super("View Manager");
		
		this.wv = new WelcomeView(this);
		this.lv = new LicenceView(this);
		
		String defaultInstallFolder = "";
		
		if(OSValidator.isWindows()) {
			defaultInstallFolder = "C:" + File.separator + "Program Files" + File.separator + "DeViNT" + File.separator;
		} else if(OSValidator.isUnix()) {
			defaultInstallFolder = File.separator + "usr" + File.separator + "DeViNT" + File.separator;
		} else if(OSValidator.isMac()) {
			defaultInstallFolder = File.separator + "Applications" + File.separator + "DeViNT" + File.separator;
		}
		
		this.cv = new ConfigView(this, defaultInstallFolder);
		this.ccv = new ComponentsChoicesView(this);
		
		this.iv = new InstallingView(this);
		
		this.init();
	}
	
	public void init() {
		this.setContentPane(this.wv);
		
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setMinimumSize(new Dimension(1024,768));
		this.setPreferredSize(new Dimension(1024,768));
		this.setMaximumSize(screenSize);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		

		this.setLocation((screenSize.width-this.getWidth())/2,(screenSize.height-this.getHeight())/2);
		
		this.setVisible(true);
	}
	
	public void nextStep() {
		if(this.getContentPane().equals(this.wv)) {
			this.setContentPane(this.lv);
			this.pack();
		} else {
		
			if(this.getContentPane().equals(this.lv)) {
				this.setContentPane(this.cv);
				this.pack();
			} else {
				if(this.getContentPane().equals(this.cv)) {
					ArrayList<Game> games = Game.getAll("." + File.separator + "LesLogiciels" + File.separator);
					this.ccv.setGamesList(games);
					this.setContentPane(this.ccv);
					this.pack();
				} else {
					if(this.getContentPane().equals(this.ccv)) {
						this.setContentPane(this.iv);
						this.lunchCopy();
					}
				}
			}
		}
	}
	
	public void lunchCopy() {
		//TODO
	}
	
	public String getInstallationFolder() {
		return this.installFolder;
	}
	
	public void setInstallationFolder(String installFolder) {
		this.installFolder = installFolder;
	}
	
	public void setGamesToInstall(ArrayList<Game> toInstall) {
		this.toInstall = toInstall;
	}
	
	public void cancel() {
		this.dispose();
	}
}
