import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import java.io.*;

public class Parsor {

    public Parsor() {

    }

    /*
     * Le type de retour sera à terme un objet Game regroupant toutes les
     * informations qui ont été tiré du xml.
     */
    public static void parse(String fileName) {
        SAXBuilder builder = new SAXBuilder();
        try {
            File f = new File(fileName);
            Element xmlRoot = builder.build(f).getRootElement();
            
            
        } catch (JDOMException e) {
            // return null;
        } catch (IOException e) {
            // return null;
        }
    }
}
