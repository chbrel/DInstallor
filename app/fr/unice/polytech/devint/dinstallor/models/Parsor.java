package fr.unice.polytech.devint.dinstallor.models;
import models.Game;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import java.io.*;
import java.util.ArrayList;

public class Parsor {

    public Parsor() {

    }

    /*
     * Le type de retour sera à terme un objet Game regroupant toutes les
     * informations qui ont été tiré du xml.
     */
    public static Game parse(String fileName) {
        SAXBuilder builder = new SAXBuilder();
        try {
            File f = new File(fileName);
            Element xmlRoot = builder.build(f).getRootElement();

            int annee = Integer.decode(xmlRoot.getChildText("year"));

            ArrayList<GameCategory> categories = new ArrayList<GameCategory>();
            Element gameCategories = xmlRoot.getChild("gamecategories");
            for (Object obj : gameCategories.getChildren("gamecategory")) {
                Element category = (Element) obj;
                if (GameCategory.validCategory.containsKey(category.getText())) {
                    categories.add(GameCategory.validCategory.get(category
                            .getText()));
                } else {
                    // TODO Throw an Exception.
                    return null;
                }
            }

            String shortDescription = xmlRoot.getChildText("shortdescription");

            Public dpublic = null;
            String publicString = xmlRoot.getChildText("public");
            if (Public.validPublic.containsKey(publicString)) {
                dpublic = Public.validPublic.get(publicString);
            } else {
                // TODO Throw an exception.
                return null;
            }

            String age = xmlRoot.getChildText("age");
            
            String title = xmlRoot.getChildText("title");
            
            ArrayList<String> authors = new ArrayList<String>();
            for (Object obj : xmlRoot.getChild("authors").getChildren("author")) {
                authors.add(((Element) obj).getText());
            }

            ArrayList<String> notes = new ArrayList<String>();
            for (Object obj : xmlRoot.getChild("notes").getChildren("note")) {
                notes.add(((Element) obj).getText());
            }

            String gameplay = xmlRoot.getChildText("gameplay");

            String gamerules = xmlRoot.getChildText("gamerules");

            return new Game(annee, categories, shortDescription, dpublic, age, title,
                    authors, notes, gameplay, gamerules);

        } catch (JDOMException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        Game test = parse("../resources/infos.xml");
        if (test != null) {
            System.out.println(test);
        }
    }
}
