/**
 * THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW. 
 * EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES 
 * PROVIDE THE PROGRAM “AS IS” WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR 
 * IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY 
 * AND FITNESS FOR A PARTICULAR PURPOSE. THE ENTIRE RISK AS TO THE QUALITY AND 
 * PERFORMANCE OF THE PROGRAM IS WITH YOU. SHOULD THE PROGRAM PROVE DEFECTIVE, YOU 
 * ASSUME THE COST OF ALL NECESSARY SERVICING, REPAIR OR CORRECTION.
 */

package fr.unice.polytech.devint.dinstallor.controllers;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

import fr.unice.polytech.devint.dinstallor.models.FileUtils;
import fr.unice.polytech.devint.dinstallor.models.Game;
import fr.unice.polytech.devint.dinstallor.models.GameCategory;
import fr.unice.polytech.devint.dinstallor.models.HelpUtils;
import fr.unice.polytech.devint.dinstallor.models.OSValidator;
import fr.unice.polytech.devint.dinstallor.models.Shortcut;
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
	private String HelpShortcutPath;
	private String GameShortcutPath;
	private ArrayList<Game> toInstall;
	private ArrayList<Game> toUninstall;
	
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
		
		this.GameShortcutPath = "";
		this.HelpShortcutPath = "";
		
		if (OSValidator.isWindowsSeven()) {
			this.GameShortcutPath = System.getProperty("user.home")+File.separator+"Desktop"+File.separator+"Jeux DeViNT.lnk";
			this.HelpShortcutPath = System.getProperty("user.home")+File.separator+"Desktop"+File.separator+"Aide DeViNT.lnk";
		} else if (OSValidator.isWindowsXP()) {
			if (Locale.getDefault().equals(Locale.FRANCE)) {
				this.GameShortcutPath = System.getProperty("user.home")+File.separator+"Bureau"+File.separator+"Jeux DeViNT.lnk";
				this.HelpShortcutPath = System.getProperty("user.home")+File.separator+"Bureau"+File.separator+"Aide DeViNT.lnk";
			}else if (Locale.getDefault().equals(Locale.ENGLISH)) {
				this.GameShortcutPath = System.getProperty("user.home")+File.separator+"Desktop"+File.separator+"Jeux DeViNT.lnk";
				this.HelpShortcutPath = System.getProperty("user.home")+File.separator+"Desktop"+File.separator+"Aide DeViNT.lnk";
			}
		}
		
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
								launchCopy();
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
	
	public void launchUninstall() {
		File idfile = new File(this.getInstallationFolder()+File.separator+"installationid");
		if (idfile.exists() && idfile.isFile()) {
			try {
				FileReader fr = new FileReader(idfile);
				char[] cbuf = new char[50]; 
				int nbChar = fr.read(cbuf);
				String fileNumber = "";
				for (int i = 0; i<nbChar; i++) {
					fileNumber+=cbuf[i];
				}
				try {
					if (Integer.parseInt(fileNumber) != this.getInstallationFolder().hashCode()) {
						iv.concat("Il n'existe pas de projets déjà installés dans "+this.getInstallationFolder());
						return;
					}
				} catch (NumberFormatException e) {
					iv.concat("Il n'existe pas de projets déjà installés dans "+this.getInstallationFolder());
					return;
				}
			} catch (FileNotFoundException e) {
				iv.concat("Il n'existe pas de projets déjà installés dans "+this.getInstallationFolder());
				e.printStackTrace();
				return;
			} catch (IOException e) {
				iv.concat("Il n'existe pas de projets déjà installés dans "+this.getInstallationFolder());
				e.printStackTrace();
				return;
			}
			
		} else {
			iv.concat("Il n'existe pas de projets déjà installés dans "+this.getInstallationFolder());
			return;
		}
		boolean uninstallAll = false;
		ArrayList<Game> installedGames = Game.getAll(this.getInstallationFolder());
		if (toInstall.size() == 0) {
			uninstallAll = true;
		}
		
		if (installedGames == null) {
			return;
		}
		
		for (Game game : installedGames) {
			if ((toUninstall.contains(game) || uninstallAll) && game != null) {
				iv.concat("Désinstallation de "+game.getTitle());
				FileUtils.rmDir(game.getGameRep());
			}
		}
		
		iv.concat("Suppression des aides générées précédemment.");
		FileUtils.rmDir(new File(this.getInstallationFolder()+File.separator+"Aide"));
		
		if (uninstallAll) {
			iv.concat("Destruction du répertoire \"lib\" et de ses sous répertoires.");
			FileUtils.rmDir(new File(this.getInstallationFolder()+File.separator+"lib"));
			iv.concat("Destruction du répertoire \"jre\" et de ses sous répertoires.");
			FileUtils.rmDir(new File(this.getInstallationFolder()+File.separator+"jre"));
			iv.concat("Destruction du répertoire \"VocalyzeSIVOX\" et de ses sous répertoires.");
			FileUtils.rmDir(new File(this.getInstallationFolder()+File.separator+"VocalyzeSIVOX"));
			iv.concat("Destruction du répertoire \"Listor\" et de ses sous répertoires.");
			FileUtils.rmDir(new File(this.getInstallationFolder()+File.separator+"Listor"));
			iv.concat("Destruction du répertoire \"DListor\" et de ses sous répertoires.");
			FileUtils.rmDir(new File(this.getInstallationFolder()+File.separator+"DListor"));
			iv.concat("Destruction du répertoire \"Aide\" et de ses sous répertoires.");
			FileUtils.rmDir(new File(this.getInstallationFolder()+File.separator+"Aide"));
			iv.concat("Destruction du répertoire d'installation et de ses sous répertoires.");
			FileUtils.rmDir(new File(this.getInstallationFolder()+File.separator));
			if (OSValidator.isWindows() && !this.GameShortcutPath.equals("") && !this.HelpShortcutPath.equals("")) {
				iv.concat("Suppression du raccourcis Jeux DeViNT.");
				FileUtils.rmDir(new File(this.GameShortcutPath));
				iv.concat("Suppression du raccourcis Aide DeViNT.");
				FileUtils.rmDir(new File(this.HelpShortcutPath));
				new File(this.getInstallationFolder()+File.separator+"installationid").delete();
			}
		}
	}
	
	public void launchCopy() {
		long startCopyTime = System.currentTimeMillis();
		FileUtils.iv = this.iv;
		
		launchUninstall();
		
		if (toInstall.size() != 0) {
			File installDir =  new File(this.getInstallationFolder());
			if(!installDir.exists()) {
				installDir.mkdirs();
			}
			
			File idfile = new File(this.getInstallationFolder()+File.separator+"installationid");
			if (!idfile.exists()) {
				try {
					FileWriter fw = new FileWriter(idfile);
					fw.write(""+this.getInstallationFolder().hashCode());
					fw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			/* Copie du bon répertoire Jre */
			if (!new File(this.getInstallationFolder() + File.separator + "jre" + File.separator).exists()) {
				if(OSValidator.isWindows()) {
					try {
						iv.concat("Copie du répertoire \"jre\" et de ses sous-répertoires");
						FileUtils.copy(new File("." + File.separator + "jre" + File.separator + "win"), new File(this.getInstallationFolder() + File.separator + "jre" + File.separator));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(OSValidator.isUnix()) {
					try {
						iv.concat("Copie du répertoire \"jre\" et de ses sous-répertoires");
						FileUtils.copy(new File("." + File.separator + "jre" + File.separator + "linux"), new File(this.getInstallationFolder() + File.separator + "jre" + File.separator));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(OSValidator.isMac()) {
					try {
						iv.concat("Copie du répertoire \"jre\" et de ses sous-répertoires");
						FileUtils.copy(new File("." + File.separator + "jre" + File.separator + "mac"), new File(this.getInstallationFolder() + File.separator + "jre" + File.separator));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			/* Copie du bon répertoire Lib */
			if (!new File(this.getInstallationFolder() + File.separator + "lib" + File.separator).exists()) {
				if(OSValidator.isWindows()) {
					try {
						iv.concat("Copie du répertoire \"lib\" et de ses sous-répertoires");
						FileUtils.copy(new File("." + File.separator + "lib" + File.separator + "win"), new File(this.getInstallationFolder() + File.separator + "lib" + File.separator));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(OSValidator.isUnix()) {
					try {
						iv.concat("Copie du répertoire \"lib\" et de ses sous-répertoires");
						FileUtils.copy(new File("." + File.separator + "lib" + File.separator + "linux"), new File(this.getInstallationFolder() + File.separator + "lib" + File.separator));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(OSValidator.isMac()) {
					try {
						iv.concat("Copie du répertoire \"lib\" et de ses sous-répertoires");
						FileUtils.copy(new File("." + File.separator + "lib" + File.separator + "mac"), new File(this.getInstallationFolder() + File.separator + "lib" + File.separator));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			/* Copie de DListor */
			if (!new File(this.getInstallationFolder() + File.separator + "DListor" + File.separator).exists()) {
				try {
					iv.concat("Copie du répertoire \"DListor\" et de ses sous-répertoires");
					FileUtils.copy(new File("." + File.separator + "DListor" + File.separator), new File(this.getInstallationFolder() + File.separator + "DListor" + File.separator));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (!new File(this.getInstallationFolder() + File.separator + "VocalyzeSIVOX" + File.separator).exists()) {
				/* Copie de VocalyzeSIVOX */
				try {
					iv.concat("Copie du répertoire \"VocalyzeSIVOX\" et de ses sous-répertoires");
					FileUtils.copy(new File("." + File.separator + "LesLogiciels" + File.separator + "VocalyzeSIVOX" + File.separator), new File(this.getInstallationFolder() + File.separator + "VocalyzeSIVOX" + File.separator));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			/* Copie des jeux sélectionnés  */
			for(Game g: this.toInstall) {
				try {
					iv.concat("Copie du projet \"" + g.getTitle() + "\" (répertoire \"" + g.getGameRep().getName() + "\" et ses sous-répertoires)");
					FileUtils.copy(g.getGameRep(), new File(this.getInstallationFolder() + File.separator + g.getGameRep().getName() + File.separator));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HelpUtils.GAMELIST += "<li><a href=\"jeux/" + g.getGameRep().getName() + ".html\" title=\"" + g.getTitle() + "\"><strong>" + g.getTitle() + "</strong></a></li>\n";
				HelpUtils.GAMELIST_INGAMEFOLDER += "<li><a href=\"" + g.getGameRep().getName() + ".html\" title=\"" + g.getTitle() + "\"><strong>" + g.getTitle() + "</strong></a></li>\n";
			}
			
			/* Création de l'aide */
			iv.concat("-- Génération de l'aide --");
			try {
				iv.concat("Copie des fichiers de base de l'aide");
				FileUtils.copy(new File("." + File.separator + "DHelp" + File.separator), new File(this.getInstallationFolder() + File.separator + "Aide" + File.separator));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			HelpUtils.GAMELIST = "<h1>Aide sur les projets installés</h1>\n<ul id=\"listjeux\">\n" + HelpUtils.GAMELIST + "</ul>\n";
			HelpUtils.GAMELIST_INGAMEFOLDER = "<h1>Aide sur les projets installés</h1>\n<ul id=\"listjeux\">\n" + HelpUtils.GAMELIST_INGAMEFOLDER + "</ul>\n";
			
				// Création du fichier jeux.html
			
			
			String jeuxContent = HelpUtils.HEADER + HelpUtils.GAMELIST;
			jeuxContent += "<div id=\"aidejeu\">\n";
			jeuxContent += "Cliquez sur le nom d'un jeu ci-contre pour voir l'aide associée :)\n";
			jeuxContent += "</div>\n";
			jeuxContent += HelpUtils.FOOTER;
			FileUtils.write(this.getInstallationFolder() + File.separator + "Aide" + File.separator + "jeux.html"  , jeuxContent);
			
				
				//Création de tous les fichiers aides des jeux installés
			for(Game g: this.toInstall) {
				iv.concat("Génération de l'aide pour le jeu \"" + g.getTitle() + "\"");
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
				FileUtils.write(this.getInstallationFolder() + File.separator + "Aide" + File.separator + "jeux" + File.separator + g.getGameRep().getName() + ".html" , gameContent);
				
				File helpFolder = new File(g.getGameRep().getAbsolutePath() + File.separator + "doc" + File.separator + g.getGameRep().getName() + File.separator);
				if(helpFolder.exists()) {
					try {
						FileUtils.copy(helpFolder, new File(this.getInstallationFolder() + File.separator + "Aide" + File.separator + "jeux" + File.separator + g.getGameRep().getName() + File.separator));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			/* Création des icones pour windows */
			if(OSValidator.isWindows() && !this.GameShortcutPath.equals("") && !this.HelpShortcutPath.equals("")) {
				try {
					Shortcut scutJeux = new Shortcut(new File(this.getInstallationFolder()+File.separator+"DListor"+File.separator+"bin"+File.separator+"execution.bat"));
					Shortcut scutAide = new Shortcut(new File(this.getInstallationFolder()+File.separator+"Aide"+File.separator+"index.html"));
					OutputStream osJeux = new FileOutputStream(this.GameShortcutPath);
					OutputStream osAide = new FileOutputStream(this.HelpShortcutPath);
					osJeux.write(scutJeux.getBytes());
					osJeux.flush();
					osJeux.close();
					osAide.write(scutAide.getBytes());
					osAide.flush();
					osAide.close();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
		
		long copyTotalTime = System.currentTimeMillis() - startCopyTime;
		
		long copyTotalTimeSec = (copyTotalTime / 1000);
		
		long copyTotalTimeMin = (copyTotalTimeSec / 60);
		
		long copyTotalTimeSecRest = copyTotalTimeSec - (copyTotalTimeMin*60);		
			
		iv.concat("Temps total de l'installation: " + copyTotalTimeMin + " minutes et "  + copyTotalTimeSecRest + " secondes");
		
		this.iv.installationFinished();
		
	}
	
	public int getYear() {
		return this.year;
	}
	
	public String getInstallationFolder() {
		return this.installFolder;
	}
	
	public void setInstallationFolder(String installFolder) {
		if(OSValidator.isWindows()) {
			char c = installFolder.charAt(installFolder.length()-1);
			if(c == '\\') {
				this.installFolder = installFolder;
			} else {
				this.installFolder = installFolder + "\\";
			}
		} else if(OSValidator.isUnix()) {
			char c = installFolder.charAt(installFolder.length()-1);
			if(c == '/') {
				this.installFolder = installFolder;
			} else {
				this.installFolder = installFolder + "/";
			}
		} else if(OSValidator.isMac()) {
			char c = installFolder.charAt(installFolder.length()-1);
			if(c == '/') {
				this.installFolder = installFolder;
			} else {
				this.installFolder = installFolder + "/";
			}
		}
	}
	
	public void setGamesToInstall(ArrayList<Game> toInstall) {
		this.toInstall = toInstall;
	}
	
	public void cancel() {
		this.dispose();
	}

	public void setGamesToUninstall(ArrayList<Game> toUninstall) {
		this.toUninstall = toUninstall;
		
	}
}
