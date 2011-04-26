package fr.unice.polytech.devint.dinstallor.views;

import it.cnr.imaa.essi.lablib.gui.checkboxtree.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import fr.unice.polytech.devint.dinstallor.controllers.InstallationController;
import fr.unice.polytech.devint.dinstallor.models.Game;
import fr.unice.polytech.devint.dinstallor.models.GameCategory;
import fr.unice.polytech.devint.dinstallor.models.Picture;

@SuppressWarnings("serial")
public class ComponentsChoicesView  extends InstallationView {

	private ArrayList<Game> games;
	private JComboBox sortedChoices;
	
	private CheckboxTree checkboxTree;
	private Boolean dateDisplay;
	
	public ComponentsChoicesView(InstallationController ic) {
		super(ic);
		
		this.games = new ArrayList<Game>();
		
		this.dateDisplay = true;
		
		this.setLayout(new BorderLayout());
		
		JPanel headPanel = new JPanel();
		headPanel.setLayout(new BorderLayout());
		
		headPanel.add((new Picture("." + File.separator + "resources" + File.separator + "logo_devint.png")).getJLabel(), BorderLayout.WEST);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		
		titlePanel.add(Box.createHorizontalGlue());
		
		JLabel title = new JLabel("Choix des projets à installer");
		title.setFont(title.getFont().deriveFont(Font.BOLD));
		title.setFont(title.getFont().deriveFont(Float.parseFloat("25")));
		titlePanel.add(title);
		
		titlePanel.add(Box.createHorizontalGlue());
		
		
		headPanel.add(titlePanel, BorderLayout.CENTER);
		
		this.add(headPanel, BorderLayout.NORTH);
		
		
//		this.gamesList = new JComboBox();
		JPanel jTreeCheckBox = this.createJCheckBoxTree();
		jTreeCheckBox.setBackground(Color.WHITE);
		this.add(jTreeCheckBox);
		
		
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
				cancel();
			}
			
		});
		buttons.add(cancelButton);
		
		JButton nextButton = new JButton("Suivant");
		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				nextStep();
			}
			
		});
		buttons.add(nextButton);
	}
	
	
	public JPanel createJCheckBoxTree() {
/*		try {
		    String lafName = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
		    UIManager.setLookAndFeel(lafName);
*/		    
		    JPanel checkBoxPane = new JPanel();
		    checkBoxPane.setLayout(new BorderLayout());
		    
		    this.sortedChoices = new JComboBox(); 
		    this.sortedChoices.addItem("Par Année");
		    this.sortedChoices.addItem("Par Catégorie");
		    
		    this.sortedChoices.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					if(((String) ComponentsChoicesView.this.sortedChoices.getSelectedItem()).equals("Par Année")) {
						if(!ComponentsChoicesView.this.dateDisplay) {
							ComponentsChoicesView.this.dateDisplay = true;
							ComponentsChoicesView.this.updateCheckBoxTree();
						}
					} else {
						if(ComponentsChoicesView.this.dateDisplay) {
							ComponentsChoicesView.this.dateDisplay = false;
							ComponentsChoicesView.this.updateCheckBoxTree();
						}
					}
				}
		    	
		    });
		    
		    checkBoxPane.add(this.sortedChoices, BorderLayout.NORTH);
		    
		    this.checkboxTree = new CheckboxTree();
		    this.checkboxTree.getCheckingModel().setCheckingMode(TreeCheckingModel.CheckingMode.PROPAGATE);
		    DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.checkboxTree.getModel().getRoot();
		    root.setUserObject("Les Logiciels");
		    
		    checkBoxPane.add(new JScrollPane(this.checkboxTree), BorderLayout.CENTER);
		    
		    return checkBoxPane;
/*		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
		} catch (InstantiationException e) {
		    e.printStackTrace();
		} catch (IllegalAccessException e) {
		    e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
		    e.printStackTrace();
		}
		
		
		return null;
*/
	}
	
/*	private JScrollPane getCheckboxTree() {
		if (this.checkboxTree == null) {
		    
		    DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.checkboxTree.getModel().getRoot();

		    DefaultMutableTreeNode ravioli = (DefaultMutableTreeNode) root.getChildAt(2).getChildAt(2);// ravioli;

		    ravioli.add(new DefaultMutableTreeNode("al tartufo"));
		    ravioli.add(new DefaultMutableTreeNode("alla salvia"));
		    for (int i = 2; i < 10; i++) {
			ravioli.add(new DefaultMutableTreeNode("ai " + i + " formaggi"));
		    }
		    ((DefaultTreeModel) this.checkboxTree.getModel()).nodeStructureChanged(root);
		    this.checkboxTree.addCheckingPath(new TreePath(ravioli.getPath()));
		    checkboxTree.expandAll();
		}
		return ;
		}
*/	
	
	public void setGamesList(ArrayList<Game> games) {
		this.games = games;
		this.updateCheckBoxTree();
		//this.updateList();
	}
	
/*	private void updateList() {
		this.gamesList.removeAllItems();
		for(Game g: this.games) {
			this.gamesList.addItem(g);
		}
		
	}
*/
	
	private void updateCheckBoxTree() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.checkboxTree.getModel().getRoot();
		this.checkboxTree.removeCheckingPaths(this.checkboxTree.getCheckingPaths());
		root.removeAllChildren();
		
		if(this.dateDisplay) {
			TreeMap<Integer, ArrayList<Game>> dateGames = new TreeMap<Integer, ArrayList<Game>>();
			for(Game g: this.games) {
				if(!g.getTitle().equals("SIVOX")) {//TODO A Supprimer lorsque le nouvel installeur sera en place SEUL
					if(!dateGames.containsKey(g.getAnnee())) {
						dateGames.put(g.getAnnee(), new ArrayList<Game>());
					}
					dateGames.get(g.getAnnee()).add(g);
				}
			}
			
			for(Integer annee: dateGames.keySet()) {
				DefaultMutableTreeNode anneeNode = new DefaultMutableTreeNode(String.valueOf(annee));
				for(Game g: dateGames.get(annee)) {
					anneeNode.add(new DefaultMutableTreeNode(g));
				}
				root.add(anneeNode);
			}
		} else {
			TreeMap<GameCategory, ArrayList<Game>> catGames = new TreeMap<GameCategory, ArrayList<Game>>();
			
			for(Game g: this.games) {
				if(!g.getTitle().equals("SIVOX")) {//TODO A Supprimer lorsque le nouvel installeur sera en place SEUL
					for(GameCategory gameCat: g.getGameCategories()) {
						if(!catGames.containsKey(gameCat)) {
							catGames.put(gameCat, new ArrayList<Game>());
						}
						catGames.get(gameCat).add(g);
					}
				}
			}
			
			for(GameCategory gameCat: catGames.keySet()) {
				DefaultMutableTreeNode gameCatNode = new DefaultMutableTreeNode(gameCat);
				for(Game g: catGames.get(gameCat)) {
					gameCatNode.add(new DefaultMutableTreeNode(g));
				}
				root.add(gameCatNode);
			}
		}
		
		((DefaultTreeModel) this.checkboxTree.getModel()).nodeStructureChanged(root);
		
		
	    this.checkboxTree.expandAll();
	    this.validate();
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
		ArrayList<Game> selectedGames = new ArrayList<Game>();
		
		TreePath[] tps = this.checkboxTree.getCheckingPaths();
		
		for(TreePath tp: tps) {
			if(tp.getPathCount() == 3) {
				DefaultMutableTreeNode gameNode = (DefaultMutableTreeNode) tp.getLastPathComponent();
				Game g = (Game) gameNode.getUserObject();
				selectedGames.add(g);
			}
		}
		
/*		for(Game g : selectedGames) {
			System.out.println(g.getTitle() + " : " + g.getShortDescription());
			System.out.println("-------");
		}
		
		System.out.println("####");
*/
		this.getController().setGamesToInstall(selectedGames);
		ArrayList<Game> unselectedGames = this.games;
		unselectedGames.removeAll(selectedGames);
		this.getController().setGamesToUninstall(unselectedGames);
		this.getController().nextStep();
	}
	
}
