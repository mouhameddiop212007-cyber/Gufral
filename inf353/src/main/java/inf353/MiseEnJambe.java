package inf353;

import java.io.*;

/**
 * Classe mise en jambe dont le but est d'illustrer la définition de jeux de tests en JUnit.
 */
public class MiseEnJambe {
    public String texte;
    //public int N = 100000;

    public MiseEnJambe(String texte) {
        this.texte = texte;
    }

    public MiseEnJambe(File f) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        this.texte = fileData.toString();
    }

    /**
     * Vrai ssi texte contient la sous chaîne s.
     * @param s la sous chaîne à rechercher.
     * @return
     */
    public boolean contient(String s) {
        int i = 0;
        // on essaie chaque position i où s peut encore tenir dans texte
        while (i + s.length() <= texte.length()) {
            int k = 0;
            // on compare s au texte à partir de la position i
            while (k < s.length() && texte.charAt(i + k) == s.charAt(k)) {
                k++;
            }
            if (k == s.length()) {
                return true;              // tous les caractères de s ont été retrouvés
            }
            i++;
        }
        return false;
    }

    /**
     * Vrai ssi texte contient la balise de nom b.
     * Rappel, on devra prendre en compte les cas simples (ex :"<b>") et les cas complexes
     * (ex: "<b attr="...">
     * @param b le nom de la balise à rechercher.
     * @return
     */
    public boolean contientBalise(String b) {
        int i = 0;
        while (i < texte.length()) {
            if (texte.charAt(i) == '<') {
                // on compare le nom b avec ce qui suit le '<'
                int j = 0;
                while (j < b.length() && i + 1 + j < texte.length()
                        && texte.charAt(i + 1 + j) == b.charAt(j)) {
                    j++;
                }
                // le nom doit être complet : suivi de '>' (cas simple), d'un espace (attributs) ou de '/'
                if (j == b.length() && i + 1 + j < texte.length()) {
                    char d = texte.charAt(i + 1 + j);
                    if (d == '>' || d == ' ' || d == '/') {
                        return true;
                    }
                }
            }
            i++;
        }
        return false;
    }

    /**
     * Vrai ssi texte contient le contenu c (en ignorant les éléments contenus à l'intérieur d'une balise).
     * (ex: texte = "<a>b</a>" contient le contenu b, mais pas le contenu a).
     * @param c le contenu à rechercher.
     * @return
     */
    public boolean contientContenu(String c) {
        String sans = sansCommentaire();  // les commentaires ne sont pas du contenu
        String contenu = "";              // le texte situé en dehors des balises
        boolean dansBalise = false;
        int i = 0;

        while (i < sans.length()) {
            char x = sans.charAt(i);
            if (x == '<') {
                dansBalise = true;        // début de balise : on ne recopie plus
            } else if (dansBalise) {
                if (x == '>') {
                    dansBalise = false;   // fin de balise : on recopie à nouveau
                }
            } else {
                contenu = contenu + x;    // caractère de contenu
            }
            i++;
        }
        return new MiseEnJambe(contenu).contient(c);
    }

    /**
     * Renvoie une version sans commentaire XML de texte.
     * @return
     */
    public String sansCommentaire() {
        String r = "";
        int i = 0;
        int n = texte.length();
        while (i < n) {
            // début de commentaire : "<!--"
            if (i + 3 < n && texte.charAt(i) == '<' && texte.charAt(i + 1) == '!'
                    && texte.charAt(i + 2) == '-' && texte.charAt(i + 3) == '-') {
                i = i + 4;
                // on avance jusqu'à la fin du commentaire :
                while (i < n && !(i + 2 < n && texte.charAt(i) == '-'
                        && texte.charAt(i + 1) == '-' && texte.charAt(i + 2) == '>')) {
                    i++;
                }
                i = i + 3;
            } else {
                r = r + texte.charAt(i);
                i++;
            }
        }
        return r;
    }

    /**
     * Renvoie une version de texte où toutes les données contenues à l'intérieur d'une balise b on été supprimées.
     * ex: texte = "<a>x<b>y</b>z</a>" : filtere("b") renvoie "<a>xz</a>"
     * @return
     */
    public String filtrer(String b) {
        String r = "";
        int n = texte.length();
        int i = 0;
        int prof = 0;                     // > 0 : on est à l'intérieur d'une balise b

        while (i < n) {
            if (texte.charAt(i) != '<') {
                if (prof == 0) {
                    r = r + texte.charAt(i);   // texte hors de b : on le garde
                }
                i++;
            } else {
                // position du '>' qui termine cette balise
                int fin = i;
                while (fin < n && texte.charAt(fin) != '>') {
                    fin++;
                }

                // est-ce une balise ouvrante "<b>" / "<b ...>" / "<b/>" ?
                int j = 0;
                while (j < b.length() && i + 1 + j < n && texte.charAt(i + 1 + j) == b.charAt(j)) {
                    j++;
                }
                boolean ouvrante = false;
                if (j == b.length() && i + 1 + j < n) {
                    char d = texte.charAt(i + 1 + j);
                    ouvrante = (d == '>' || d == ' ' || d == '/');
                }

                // est-ce une balise fermante "</b>" ?
                int k = 0;
                while (k < b.length() && i + 2 + k < n && texte.charAt(i + 2 + k) == b.charAt(k)) {
                    k++;
                }
                boolean fermante = (i + 1 < n && texte.charAt(i + 1) == '/'
                        && k == b.length() && i + 2 + k < n && texte.charAt(i + 2 + k) == '>');

                if (ouvrante) {
                    // "<b/>" se ferme tout seul : on n'entre pas dans b
                    if (!(fin > i && texte.charAt(fin - 1) == '/')) {
                        prof++;
                    }
                } else if (fermante && prof > 0) {
                    prof--;
                } else if (prof == 0) {
                    // une autre balise, hors de b : on la recopie
                    int m = i;
                    while (m <= fin && m < n) {
                        r = r + texte.charAt(m);
                        m++;
                    }
                }
                i = fin + 1;
            }
        }
        return r;
    }

}