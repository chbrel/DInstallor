package fr.unice.polytech.devint.dinstallor.models;
import java.util.HashMap;


public enum GameCategory {
    PLATEFORME("Plate-Forme"), REFLEXION("Reflexion"), DIVERS("Divers");
    
    // The categorie string.
    private String categorie;
    
    public static final HashMap<String, GameCategory> validCategory = createValidCategorie();
    
    /**
     * Initialise with the corresponding categorie.
     * @param categorie The categorie string.
     */
    GameCategory(String categorie)
    {
        this.categorie = categorie;
    }
    
    private static HashMap<String, GameCategory> createValidCategorie() {
        HashMap<String, GameCategory> map = new HashMap<String, GameCategory>();
        for(GameCategory categorie : GameCategory.values()) {
            map.put(categorie.toString(), categorie);
        }
        return map;
    }

    /**
     * @return The categorie as a string.
     */
    public String toString()
    {
        return categorie;
    }   
}
