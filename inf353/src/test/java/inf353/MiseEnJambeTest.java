//package inf234;
package inf353;

import inf353.MiseEnJambe;
import org.junit.Test;

import static org.junit.Assert.*;

public class MiseEnJambeTest {

    // ------------------------------------------------------------------
    // contient
    // ------------------------------------------------------------------

    @Test
    public void testContient1() throws Exception {
        MiseEnJambe m1 = new MiseEnJambe("<a>xyz<b>uvw</b>toto</a>");
        assertTrue(m1.contient("uvw</"));
    }

    @Test
    public void testContientDebut() throws Exception {
        // la sous-chaîne est au tout début du texte
        MiseEnJambe m = new MiseEnJambe("<a>xyz<b>uvw</b>toto</a>");
        assertTrue(m.contient("<a>xyz"));
    }

    @Test
    public void testContientFin() throws Exception {
        // la sous-chaîne est tout à la fin du texte
        MiseEnJambe m = new MiseEnJambe("<a>xyz<b>uvw</b>toto</a>");
        assertTrue(m.contient("toto</a>"));
    }

    @Test
    public void testContientAbsente() throws Exception {
        // "xyw" ressemble à "xyz" mais n'apparaît pas
        MiseEnJambe m = new MiseEnJambe("<a>xyz<b>uvw</b>toto</a>");
        assertFalse(m.contient("xyw"));
    }

    @Test
    public void testContientPlusLongueQueTexte() throws Exception {
        MiseEnJambe m = new MiseEnJambe("ab");
        assertFalse(m.contient("abc"));
    }

    @Test
    public void testContientChaineVide() throws Exception {
        // la chaîne vide est contenue dans tout texte, même vide
        assertTrue(new MiseEnJambe("").contient(""));
    }

    // ------------------------------------------------------------------
    // contientBalise
    // ------------------------------------------------------------------

    @Test
    public void testContientBaliseSimple() throws Exception {
        MiseEnJambe m = new MiseEnJambe("<a>xyz<b>uvw</b>toto</a>");
        assertTrue(m.contientBalise("b"));
    }

    @Test
    public void testContientBaliseRacine() throws Exception {
        MiseEnJambe m = new MiseEnJambe("<a>xyz<b>uvw</b>toto</a>");
        assertTrue(m.contientBalise("a"));
    }

    @Test
    public void testContientBaliseAbsente() throws Exception {
        MiseEnJambe m = new MiseEnJambe("<a>xyz<b>uvw</b>toto</a>");
        assertFalse(m.contientBalise("c"));
    }

    @Test
    public void testContientBaliseAvecAttribut() throws Exception {
        // cas complexe : balise avec attribut
        MiseEnJambe m = new MiseEnJambe("<a href=\"x\">lien</a>");
        assertTrue(m.contientBalise("a"));
    }

    @Test
    public void testContientBaliseNomIncomplet() throws Exception {
        // <br> n'est pas une balise <b>
        MiseEnJambe m = new MiseEnJambe("<br>texte");
        assertFalse(m.contientBalise("b"));
    }

    @Test
    public void testContientBaliseAutoFermante() throws Exception {
        MiseEnJambe m = new MiseEnJambe("<img/>");
        assertTrue(m.contientBalise("img"));
    }

    // ------------------------------------------------------------------
    // contientContenu
    // ------------------------------------------------------------------

    @Test
    public void testContientContenuSimple() throws Exception {
        MiseEnJambe m = new MiseEnJambe("<a>b</a>");
        assertTrue(m.contientContenu("b"));
    }

    @Test
    public void testContientContenuNomDeBalise() throws Exception {
        // "a" est le nom de la balise, pas son contenu
        MiseEnJambe m = new MiseEnJambe("<a>b</a>");
        assertFalse(m.contientContenu("a"));
    }

    @Test
    public void testContientContenuImbrique() throws Exception {
        MiseEnJambe m = new MiseEnJambe("<a>xyz<b>uvw</b>toto</a>");
        assertTrue(m.contientContenu("uvw"));
    }

    @Test
    public void testContientContenuNomBaliseImbriquee() throws Exception {
        // "b" n'apparaît que comme nom de balise
        MiseEnJambe m = new MiseEnJambe("<a>xyz<b>uvw</b>toto</a>");
        assertFalse(m.contientContenu("b"));
    }

    @Test
    public void testContientContenuDansCommentaire() throws Exception {
        // un commentaire n'est pas du contenu
        MiseEnJambe m = new MiseEnJambe("<a>x<!-- secret --></a>");
        assertFalse(m.contientContenu("secret"));
    }

    @Test
    public void testContientContenuValeurAttribut() throws Exception {
        // la valeur d'un attribut est dans la balise, donc n'est pas du contenu
        MiseEnJambe m = new MiseEnJambe("<a href=\"cible\">lien</a>");
        assertFalse(m.contientContenu("cible"));
    }

    // ------------------------------------------------------------------
    // sansCommentaire
    // ------------------------------------------------------------------

    @Test
    public void testSansCommentaireSimple() throws Exception {
        MiseEnJambe m = new MiseEnJambe("<a>x<!-- c --></a>");
        assertEquals("<a>x</a>", m.sansCommentaire());
    }

    @Test
    public void testSansCommentaireAucun() throws Exception {
        // pas de commentaire : le texte ne change pas
        MiseEnJambe m = new MiseEnJambe("<a>x</a>");
        assertEquals("<a>x</a>", m.sansCommentaire());
    }

    @Test
    public void testSansCommentaireSeul() throws Exception {
        MiseEnJambe m = new MiseEnJambe("<!-- c -->");
        assertEquals("", m.sansCommentaire());
    }

    @Test
    public void testSansCommentaireDeux() throws Exception {
        MiseEnJambe m = new MiseEnJambe("a<!--1-->b<!--2-->c");
        assertEquals("abc", m.sansCommentaire());
    }

    @Test
    public void testSansCommentaireAuDebut() throws Exception {
        MiseEnJambe m = new MiseEnJambe("<!-- c --><a/>");
        assertEquals("<a/>", m.sansCommentaire());
    }

    @Test
    public void testSansCommentaireMultiligne() throws Exception {
        MiseEnJambe m = new MiseEnJambe("x<!-- l1\nl2 -->y");
        assertEquals("xy", m.sansCommentaire());
    }

    // ------------------------------------------------------------------
    // filtrer
    // ------------------------------------------------------------------

    @Test
    public void testFiltrerSimple() throws Exception {
        // exemple de l'énoncé
        MiseEnJambe m = new MiseEnJambe("<a>x<b>y</b>z</a>");
        assertEquals("<a>xz</a>", m.filtrer("b"));
    }

    @Test
    public void testFiltrerNomIncomplet() throws Exception {
        // <br> n'est pas une balise <b> : rien n'est supprimé
        MiseEnJambe m = new MiseEnJambe("<a><br>x</a>");
        assertEquals("<a><br>x</a>", m.filtrer("b"));
    }

    @Test
    public void testFiltrerImbriquees() throws Exception {
        // balises b imbriquées : tout est supprimé jusqu'au dernier </b>
        MiseEnJambe m = new MiseEnJambe("<a><b>x<b>y</b>z</b>w</a>");
        assertEquals("<a>w</a>", m.filtrer("b"));
    }

    @Test
    public void testFiltrerPlusieurs() throws Exception {
        MiseEnJambe m = new MiseEnJambe("<a><b>1</b>x<b>2</b></a>");
        assertEquals("<a>x</a>", m.filtrer("b"));
    }

    @Test
    public void testFiltrerAvecAttribut() throws Exception {
        MiseEnJambe m = new MiseEnJambe("<a><b id=\"1\">y</b>z</a>");
        assertEquals("<a>z</a>", m.filtrer("b"));
    }

    @Test
    public void testFiltrerAutoFermante() throws Exception {
        // <b/> ne contient rien : elle est simplement supprimée
        MiseEnJambe m = new MiseEnJambe("<a>x<b/>y</a>");
        assertEquals("<a>xy</a>", m.filtrer("b"));
    }

}