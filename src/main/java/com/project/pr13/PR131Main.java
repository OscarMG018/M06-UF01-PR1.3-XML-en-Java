package com.project.pr13;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/*
generar un fitxer XML denominat“biblioteca.xml” amb el contingut que es mostra
a l’annex Arxiu “biblioteca.xml”
<biblioteca>
    <llibre id="001">
        <titol>El viatge dels venturons</titol>
        <autor>Joan Pla</autor>
        <anyPublicacio>1998</anyPublicacio>
        <editorial>Edicions Mar</editorial>
        <genere>Aventura</genere>
        <pagines>320</pagines>
        <disponible>true</disponible>
    </llibre>
</biblioteca>

Cal realitzar l’exercici usant DocumentBuilder i el procediment explicat a la teoria.

*/


/**
 * Classe principal que crea un document XML amb informació de llibres i el guarda en un fitxer.
 * 
 * Aquesta classe permet construir un document XML, afegir elements i guardar-lo en un directori
 * especificat per l'usuari.
 */
public class PR131Main {

    private File dataDir;

    /**
     * Constructor de la classe PR131Main.
     * 
     * @param dataDir Directori on es guardaran els fitxers de sortida.
     */
    public PR131Main(File dataDir) {
        this.dataDir = dataDir;
    }

    /**
     * Retorna el directori de dades actual.
     * 
     * @return Directori de dades.
     */
    public File getDataDir() {
        return dataDir;
    }

    /**
     * Actualitza el directori de dades.
     * 
     * @param dataDir Nou directori de dades.
     */
    public void setDataDir(File dataDir) {
        this.dataDir = dataDir;
    }

    /**
     * Mètode principal que inicia l'execució del programa.
     * 
     * @param args Arguments passats a la línia de comandament (no s'utilitzen en aquest programa).
     */
    public static void main(String[] args) {
        String userDir = System.getProperty("user.dir");
        File dataDir = new File(userDir, "data" + File.separator + "pr13");

        PR131Main app = new PR131Main(dataDir);
        app.processarFitxerXML("biblioteca.xml");
    }

    /**
     * Processa el document XML creant-lo, guardant-lo en un fitxer i comprovant el directori de sortida.
     * 
     * @param filename Nom del fitxer XML a guardar.
     */
    public void processarFitxerXML(String filename) {
        if (comprovarIDirCrearDirectori(dataDir)) {
            Document doc = construirDocument();
            File fitxerSortida = new File(dataDir, filename);
            guardarDocument(doc, fitxerSortida);
        }
    }

    /**
     * Comprova si el directori existeix i, si no és així, el crea.
     * 
     * @param directori Directori a comprovar o crear.
     * @return True si el directori ja existeix o s'ha creat amb èxit, false en cas contrari.
     */
    private boolean comprovarIDirCrearDirectori(File directori) {
        if (!directori.exists()) {
            return directori.mkdirs();
        }
        return true;
    }

    /**
     * Crea un document XML amb l'estructura d'una biblioteca i afegeix un llibre amb els seus detalls.
     * 
     * @return Document XML creat o null en cas d'error.
     */
    private static Document construirDocument() {
       DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
       try {
           DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
           Document doc = dBuilder.newDocument();
           return doc; 
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
    }

    /**
     * Guarda el document XML proporcionat en el fitxer especificat.
     * 
     * @param doc Document XML a guardar.
     * @param fitxerSortida Fitxer de sortida on es guardarà el document.
     */
    private static void guardarDocument(Document doc, File fitxerSortida) {
        /*
        <biblioteca>
            <llibre id="001">
                <titol>El viatge dels venturons</titol>
                <autor>Joan Pla</autor>
                <anyPublicacio>1998</anyPublicacio>
                <editorial>Edicions Mar</editorial>
                <genere>Aventura</genere>
                <pagines>320</pagines>
                <disponible>true</disponible>
            </llibre>
        </biblioteca>
        */
        Element biblioteca = doc.createElement("biblioteca");
        doc.appendChild(biblioteca);

        Element llibre = doc.createElement("llibre");
        llibre.setAttribute("id", "001");
        biblioteca.appendChild(llibre);

        Element titol = doc.createElement("titol");
        titol.appendChild(doc.createTextNode("El viatge dels venturons"));
        llibre.appendChild(titol);

        Element autor = doc.createElement("autor");
        autor.appendChild(doc.createTextNode("Joan Pla"));
        llibre.appendChild(autor);

        Element anyPublicacio = doc.createElement("anyPublicacio");
        anyPublicacio.appendChild(doc.createTextNode("1998"));
        llibre.appendChild(anyPublicacio);

        Element editorial = doc.createElement("editorial");
        editorial.appendChild(doc.createTextNode("Edicions Mar"));
        llibre.appendChild(editorial);

        Element genere = doc.createElement("genere");
        genere.appendChild(doc.createTextNode("Aventura"));
        llibre.appendChild(genere);

        Element pagines = doc.createElement("pagines");
        pagines.appendChild(doc.createTextNode("320"));
        llibre.appendChild(pagines);

        Element disponible = doc.createElement("disponible");
        disponible.appendChild(doc.createTextNode("true"));
        llibre.appendChild(disponible);
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fitxerSortida);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
