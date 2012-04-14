package fr.unice.polytech.devint.dinstallor.models;
import java.io.File;
import java.util.ArrayList;

public class Game {

    private int annee;
    private ArrayList<GameCategory> gameCategories;
    private String shortDescription;
    private Public publicStyle;
    private String age;
    private String title;
    private ArrayList<String> authors;
    private ArrayList<String> notes;
    private String gamePlay;
    private String gameRules;
    
    private File gameRep;

    public Game(int annee, ArrayList<GameCategory> gameCategories,
            String shortDescription, Public publicStyle, String age, String title,
            ArrayList<String> authors, ArrayList<String> notes,
            String gamePlay, String gameRules) {
    	this(annee, gameCategories, shortDescription, publicStyle, age, title,
                authors, notes,
                gamePlay, gameRules, null);
    }
    
    public Game(int annee, ArrayList<GameCategory> gameCategories,
            String shortDescription, Public publicStyle, String age, String title,
            ArrayList<String> authors, ArrayList<String> notes,
            String gamePlay, String gameRules, File gameRep) {
        this.annee = annee;
        this.authors = authors;
        this.gameCategories = gameCategories;
        this.publicStyle = publicStyle;
        this.age = age;
        this.title = title;
        this.authors = authors;
        this.notes = notes;
        this.gamePlay = gamePlay;
        this.gameRules = gameRules;
        this.shortDescription = shortDescription;
        
        this.gameRep = gameRep;
    }
    
    public static ArrayList<Game> getAll(String gameFolder) {
    	
    	ArrayList<Game> games = new ArrayList<Game>();
    	
    	File gameDir = new File (gameFolder); 

    	if (gameDir.exists() && ! gameDir.isDirectory()) {
    		return null; 
    	}

    	String[] dirContent = gameDir.list(); 

    	for (int i=0; i < dirContent.length; i++ ) { 
    		File game = new File( gameFolder + File.separator + dirContent[i] ); 
    		
    		if(game.isDirectory()) {
    			Game newGame = Parsor.parse(gameFolder + File.separator + dirContent[i] + File.separator + "doc" + File.separator + "infos.xml");
    			if(newGame != null) {
    				newGame.setGameRep(game);
    			}
    			games.add(newGame);
    		}
    	}
    	
    	return games;
    }

    public int getAnnee() {
        return annee;
    }

    public ArrayList<GameCategory> getGameCategories() {
        return gameCategories;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public Public getPublicStyle() {
        return publicStyle;
    }
    
    public String getAge() {
        return age;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public String getGamePlay() {
        return gamePlay;
    }

    public String getGameRules() {
        return gameRules;
    }
    
    public void setGameRep(File gameRep) {
    	this.gameRep = gameRep;
    }
    
    public File getGameRep() {
    	return this.gameRep;
    }

    public String toString() {
    	return this.title;
    }
    
    public String completeToString() {
        String string = "Annee: " + this.annee + "\n";

        string += "Categories:\n";
        for (GameCategory category : this.gameCategories) {
            string += "   " + category + "\n";
        }

        string += "Short description: " + this.shortDescription + "\n";

        string += "Public: " + this.publicStyle + "\n";

        string += "Title: " + this.title + "\n";

        string += "Authors:\n";
        for (String author : this.authors) {
            string += "   " + author + "\n";
        }

        string += "Notes:\n";
        for (String note : this.notes) {
            string += "   " + note + "\n";
        }

        string += "Gameplay:\n" + this.gamePlay + "\n";

        string += "Gamerules:\n" + this.gameRules + "\n";

        return string;
    }
    
    public boolean equals(Object obj) {
    	if (obj.getClass().equals(this.getClass())) {
    		Game objGame = (Game) obj;
    		return this.title.equals(objGame.getTitle());
    	}
		return false;
    }
    
    public int hashCode() {
    	return this.title.hashCode();
    }
}
