package fr.unice.polytech.devint.dinstallor.models;
import java.util.HashMap;


public enum GameCategory {
    PLATEFORME("Plate-Forme"), REFLEXION("Réflexion"),
    MULTIJEUX("Multi-jeux"), ROLE("Jeu de rôle"),
    STRATEGIE("Stratégie"), ATELIER("Atelier DeViNT"),
    ARCADE("Arcade"), REFLEXE("Réflexe"),
    CONDUITE("Conduite"), DESSIN("Dessin"),
    CONNAISSANCES("Connaissances"), LETTRES("Lettres"),
    MEMOIRE("Mémoire"), MUSIQUE("Musique"),
    RAPIDITE("Rapidité"), MOUVEMENT("Mouvement"),
    CALCUL("calcul");
    
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
