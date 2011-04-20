package fr.unice.polytech.devint.dinstallor.controllers;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import fr.unice.polytech.devint.dinstallor.models.FileUtils;
import fr.unice.polytech.devint.dinstallor.models.Game;
import fr.unice.polytech.devint.dinstallor.models.GameCategory;
import fr.unice.polytech.devint.dinstallor.models.HelpUtils;
import fr.unice.polytech.devint.dinstallor.models.OSValidator;
import fr.unice.polytech.devint.dinstallor.views.*;

public class InstallationController extends JFrame {

	private WelcomeView wv;
	private LicenceView lv;
	private ConfigView cv;
	private ComponentsChoicesView ccv;
	private InstallingView iv;
	private EndView ev;
	
	private int year;
	private String installFolder;
	private ArrayList<Game> toInstall;
	
	public InstallationController() {
		super();
		
            try{
                BufferedReader br = new BufferedReader(new FileReader("." + File.separator + "resources" + File.separator + "year.txt"));
                String line;
                try{
                    line = br.readLine();

                    this.year = Integer.parseInt(line);

                    br.close();
                }
                catch(IOException ioe){
                System.out.println("/!\\ Error while reading file \n");}
            }
            catch(FileNotFoundException fnfe){
                System.out.println("/!\\ File doesn't exists\n");
            }
		
        this.setTitle("Bienvenue dans l'installation du CD DeViNT " + this.year + "!");
            
		this.wv = new WelcomeView(this);
		this.lv = new LicenceView(this);
		
		String defaultInstallFolder = "";
		
		if(OSValidator.isWindows()) {
			defaultInstallFolder = "C:" + File.separator + "Program Files" + File.separator + "DeViNT" + File.separator + "DeViNT" + this.year + File.separator;
			if(OSValidator.isWindowsSeven()) {
				defaultInstallFolder = System.getProperty("user.home") + File.separator + "DeViNT" + File.separator + "DeViNT" + this.year + File.separator;
			}
		} else if(OSValidator.isUnix()) {
			defaultInstallFolder = File.separator + "usr" + File.separator + "DeViNT" + File.separator + "DeViNT" + this.year + File.separator;
		} else if(OSValidator.isMac()) {
			defaultInstallFolder = File.separator + "Applications" + File.separator + "DeViNT" + File.separator + "DeViNT" + this.year + File.separator;
		}
		
		this.cv = new ConfigView(this, defaultInstallFolder);
		this.ccv = new ComponentsChoicesView(this);
		
		this.iv = new InstallingView(this);
		
		this.ev = new EndView(this);
		
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
			this.setTitle("CD DeViNT " + this.year + ": Licence");
			this.setContentPane(this.lv);
			this.pack();
		} else {
			if(this.getContentPane().equals(this.lv)) {
				this.setTitle("CD DeViNT " + this.year + ": Configuration");
				this.setContentPane(this.cv);
				this.pack();
			} else {
				if(this.getContentPane().equals(this.cv)) {
					ArrayList<Game> games = Game.getAll("." + File.separator + "LesLogiciels" + File.separator);
					this.ccv.setGamesList(games);
					
					this.setTitle("CD DeViNT " + this.year + ": Choix des projets à installer");
					this.setContentPane(this.ccv);
					this.pack();
				} else {
					if(this.getContentPane().equals(this.ccv)) {
						this.setTitle("CD DeViNT " + this.year + ": Installation en cours");
						this.setContentPane(this.iv);
						this.pack();
						
						Thread t = new Thread() {
							public void run() {
								lunchCopy();
							}
						};
						
						t.start();
					} else {
						if(this.getContentPane().equals(this.iv)) {
							this.setTitle("CD DeViNT " + this.year + ": Installation terminée!");
							this.setContentPane(this.ev);
							this.pack();
						}
					}
				}
			}
		}
	}
	
	public void lunchCopy() {
		FileUtils.iv = this.iv;
		
		File installDir =  new File(this.getInstallationFolder());
		if(!installDir.exists()) {
			installDir.mkdirs();
		}
		
		/* Copie du bon répertoire Jre */
		if(OSValidator.isWindows()) {
			try {
				FileUtils.copy(new File("." + File.separator + "jre" + File.separator + "win"), new File(this.getInstallationFolder() + File.separator + "jre" + File.separator));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(OSValidator.isUnix()) {
			try {
				FileUtils.copy(new File("." + File.separator + "jre" + File.separator + "linux"), new File(this.getInstallationFolder() + File.separator + "jre" + File.separator));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(OSValidator.isMac()) {
			try {
				FileUtils.copy(new File("." + File.separator + "jre" + File.separator + "mac"), new File(this.getInstallationFolder() + File.separator + "jre" + File.separator));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/* Copie du bon répertoire Lib */
		if(OSValidator.isWindows()) {
			try {
				FileUtils.copy(new File("." + File.separator + "lib" + File.separator + "win"), new File(this.getInstallationFolder() + File.separator + "lib" + File.separator));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(OSValidator.isUnix()) {
			try {
				FileUtils.copy(new File("." + File.separator + "lib" + File.separator + "linux"), new File(this.getInstallationFolder() + File.separator + "lib" + File.separator));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(OSValidator.isMac()) {
			try {
				FileUtils.copy(new File("." + File.separator + "lib" + File.separator + "mac"), new File(this.getInstallationFolder() + File.separator + "lib" + File.separator));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/* Copie de Listor */
		try {
			FileUtils.copy(new File("." + File.separator + "Listor" + File.separator), new File(this.getInstallationFolder() + File.separator + "Listor" + File.separator));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* Copie de VocalyzeSIVOX */
		try {
			FileUtils.copy(new File("." + File.separator + "VocalyzeSIVOX" + File.separator), new File(this.getInstallationFolder() + File.separator + "VocalyzeSIVOX" + File.separator));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* Copie des jeux sélectionnés  */
		for(Game g: this.toInstall) {
			try {
				FileUtils.copy(g.getGameRep(), new File(this.getInstallationFolder() + File.separator + g.getGameRep().getName() + File.separator));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HelpUtils.GAMELIST += "<li><a href=\"jeux/" + g.getGameRep().getName() + ".html\" title=\"" + g.getTitle() + "\"><strong>" + g.getTitle() + "</strong></a></li>\n";
			HelpUtils.GAMELIST_INGAMEFOLDER += "<li><a href=\"" + g.getGameRep().getName() + ".html\" title=\"" + g.getTitle() + "\"><strong>" + g.getTitle() + "</strong></a></li>\n";
		}
		
		/* Création de l'aide */
		try {
			FileUtils.copy(new File("." + File.separator + "DHelp" + File.separator), new File(this.getInstallationFolder() + File.separator + "Aide" + File.separator));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HelpUtils.GAMELIST = "<h1>Aide sur les projets installés</h1>\n<ul id=\"listjeux\">\n" + HelpUtils.GAMELIST + "</ul>\n";
		HelpUtils.GAMELIST_INGAMEFOLDER = "<h1>Aide sur les projets installés</h1>\n<ul id=\"listjeux\">\n" + HelpUtils.GAMELIST_INGAMEFOLDER + "</ul>\n";
		
			// Création du fichier jeux.html
		iv.concat("-- Génération de l'aide --");
		
		String jeuxContent = HelpUtils.HEADER + HelpUtils.GAMELIST;
		jeuxContent += "<div id=\"aidejeu\">\n";
		jeuxContent += "Cliquez sur le nom d'un jeu ci-contre pour voir l'aide associée :)\n";
		jeuxContent += "</div>\n";
		jeuxContent += HelpUtils.FOOTER;
		FileUtils.write(this.getInstallationFolder() + File.separator + "aide" + File.separator + "jeux.html"  , jeuxContent);
		
			
			//Création de tous les fichiers aides des jeux installés
		for(Game g: this.toInstall) {
			String gameContent = HelpUtils.HEADER_INGAMEFOLDER + HelpUtils.GAMELIST_INGAMEFOLDER;
			gameContent += "<div id=\"aidejeu\">\n";
			
			gameContent += "	<div class=\"jeu_title\">\n";
			gameContent += "<h1>" + g.getTitle() + "</h1>\n";
			gameContent += "	</div>\n";
			
			
			
			gameContent += "	<div class=\"jeu_authors boite\">\n";
			gameContent += "		<div class=\"boite_title\">\n";
			gameContent += "		Auteurs\n";
			gameContent += "		</div>\n";
			gameContent += "		<div class=\"boite_content\">\n";
			for(String author: g.getAuthors()) {
				gameContent += "		<div class=\"jeu_author\">\n";
				gameContent += author + "\n";
				gameContent += "		</div>\n";
			}
			gameContent += "	<div class=\"clear\">&nbsp;</div>\n";
			gameContent += "		</div>\n";
			gameContent += "	</div>\n";
			
			gameContent += "	<div class=\"jeu_year\">\n";
			gameContent += "<u>Année</u>: " + g.getAnnee() + "\n";
			gameContent += "	</div>\n";
			
			gameContent += "	<div class=\"jeu_public\">\n";
			gameContent += "<u>Public</u>: " + g.getPublicStyle().toString() + "\n";
			gameContent += "	</div>\n";
			
			gameContent += "	<div class=\"jeu_age\">\n";
			gameContent += "<u>Age</u>: " + g.getAge() + "\n";
			gameContent += "	</div>\n";
			
			gameContent += "	<div class=\"jeu_categories\">\n";
			gameContent += "	<u>Catégories de jeux</u>:<br/>\n";
			for(GameCategory gc: g.getGameCategories()) {
				gameContent += "		<div class=\"jeu_category\">\n";
				gameContent += gc.toString() + "\n";
				gameContent += "		</div>\n";
			}
			gameContent += "	</div>\n";
			
			gameContent += "	<div class=\"clear\">&nbsp;</div>\n";
			
			gameContent += "	<div class=\"jeu_shortdesc boite\">\n";
			gameContent += "		<div class=\"boite_title\">\n";
			gameContent += "		Résumé\n";
			gameContent += "		</div>\n";
			gameContent += "		<div class=\"boite_content\">\n";
			gameContent += g.getShortDescription() + "\n";
			gameContent += "		</div>\n";
			gameContent += "	</div>\n";
			
			gameContent += "	<div class=\"jeu_gamerules boite\">\n";
			gameContent += "		<div class=\"boite_title\">\n";
			gameContent += "		Règles du jeu\n";
			gameContent += "		</div>\n";
			gameContent += "		<div class=\"boite_content\">\n";
			gameContent += g.getGameRules() + "\n";
			gameContent += "		</div>\n";
			gameContent += "	</div>\n";
			
			gameContent += "	<div class=\"jeu_gameplay boite\">\n";
			gameContent += "		<div class=\"boite_title\">\n";
			gameContent += "		Commandes du jeu\n";
			gameContent += "		</div>\n";
			gameContent += "		<div class=\"boite_content\">\n";
			gameContent += g.getGamePlay() + "\n";
			gameContent += "		</div>\n";
			gameContent += "	</div>\n";
			
			gameContent += "	<div class=\"jeu_notes boite\">\n";
			gameContent += "		<div class=\"boite_title\">\n";
			gameContent += "		Notes sur le jeu\n";
			gameContent += "		</div>\n";
			gameContent += "		<div class=\"boite_content\">\n";
			gameContent += "			<ul>\n";
			for(String note: g.getNotes()) {
				gameContent += "		<li class=\"jeu_note\">\n";
				gameContent += note + "\n";
				gameContent += "		</li>\n";
			}
			gameContent += "			</ul>\n";
			gameContent += "		</div>\n";
			gameContent += "	</div>\n";
			
			
			
			gameContent += "</div>\n";
			gameContent += HelpUtils.FOOTER;
			FileUtils.write(this.getInstallationFolder() + File.separator + "aide" + File.separator + "jeux" + File.separator + g.getGameRep().getName() + ".html" , gameContent);
		}
		
		/* Création des icones pour windows */
		//TODO
		
		this.iv.installationFinished();
	}
	
	public int getYear() {
		return this.year;
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
