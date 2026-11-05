
package inf353;

import inf353.MiseEnJambe;
import org.junit.Test;

import static org.junit.Assert.*;

public class DictionnaireTest {

    // ------------------------------------------------------------------
    // contient
    // ------------------------------------------------------------------

    public int N = 1000000;
    @Test
    public void testVider1() throws Exception {
        DictionnaireNaif dic = new DictionnaireNaif(0);
        dic.vider();
        assertEquals(dic.N,0);
    }


    @Test
    public void testVider2() throws Exception {
        DictionnaireNaif dic = new DictionnaireNaif(110000);
        assertEquals(dic.N,110000);
    }



    @Test 
    public void testContient1() throws Exception {
        DictionnaireNaif dic = new DictionnaireNaif(N);
        dic.ajouterMot("Bon\0");
        dic.ajouterMot("Comment\0");
        dic.ajouterMot("Yohan\0");
        assertEquals(dic.contient("Yohan\0"),true);
        boolean b = dic.contient("Bon\0");
        assertEquals(b,true);
        assertEquals(dic.contient("hn\0"),false);
        assertEquals(dic.contient("momo\0"),false);
        
    }
    @Test 
    public void testContient2() throws Exception {
        DictionnaireNaif dic = new DictionnaireNaif(N);
        dic.ajouterMot("l\0");
        boolean b = dic.contient("Alors");
        assertEquals(b,false);
    }
    
    
    @Test
    public void testIndiceMot1() throws Exception {
        DictionnaireNaif dic = new DictionnaireNaif(N);
        dic.ajouterMot("Bonjour\0");
        dic.ajouterMot("Commnent\0");
        dic.ajouterMot("Yohan\0");
        //assertEquals(dic.indiceMot("Yhan\0"),-1);
        assertEquals(dic.indiceMot("Bonjour\0"),0);
        //assertEquals(dic.indiceMot("Bonjour\0"),3);
        
    }
    
    @Test
    public void testMotIndice() {
      DictionnaireNaif dic = new DictionnaireNaif(100);

      dic.ajouterMot("Bonjour\0");
      dic.ajouterMot("Comment\0");
      dic.ajouterMot("Yohan\0");

      assertEquals("Bonjour", dic.motIndice(0));
      assertEquals("Comment", dic.motIndice(1));
      assertEquals("Yohan", dic.motIndice(2));
     }
     
     
     
     @Test
     public void testContientPrefixe() {
        DictionnaireNaif dic = new DictionnaireNaif(100);

        dic.ajouterMot("Bonjour\0");
        dic.ajouterMot("Comment\0");
        dic.ajouterMot("Yohan\0");

        assertTrue(dic.contientPrefixe("Bon"));
        assertTrue(dic.contientPrefixe("Commen"));
        assertTrue(dic.contientPrefixe("Yo"));

        assertFalse(dic.contientPrefixe("Bona"));
        assertFalse(dic.contientPrefixe("X"));
        assertFalse(dic.contientPrefixe("Bonsoir"));
     }
     
     
     @Test
    public void testPlusLongPrefixeDe() {
      DictionnaireNaif dic = new DictionnaireNaif(100);

      dic.ajouterMot("Bonjour\0");
      dic.ajouterMot("Comment\0");
      dic.ajouterMot("Yohan\0");

      assertEquals("Bonjour", dic.plusLongPrefixeDe("Bonjour"));
      assertEquals("Bon", dic.plusLongPrefixeDe("Bonbon"));
      assertEquals("Commen", dic.plusLongPrefixeDe("Commencer"));
      assertEquals("Yo", dic.plusLongPrefixeDe("Youssef"));
     }
    
}