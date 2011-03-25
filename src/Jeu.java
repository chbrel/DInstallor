import java.util.ArrayList;

public class Jeu {

    private int annee;
    private ArrayList<GameCategory> gameCategories;
    private String shortDescription;
    private Public publicStyle;
    private String title;
    private ArrayList<String> authors;
    private ArrayList<String> notes;
    private String gamePlay;
    private String gameRules;

    public Jeu(int annee, ArrayList<GameCategory> gameCategories,
            String shortDescription, Public publicStyle, String title,
            ArrayList<String> authors, ArrayList<String> notes,
            String gamePlay, String gameRules) {
        this.annee = annee;
        this.authors = authors;
        this.gameCategories = gameCategories;
        this.publicStyle = publicStyle;
        this.title = title;
        this.authors = authors;
        this.notes = notes;
        this.gamePlay = gamePlay;
        this.gameRules = gameRules;
        this.shortDescription = shortDescription;
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

    public String toString() {
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
}
