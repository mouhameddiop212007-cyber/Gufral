
package inf353;

import inf353.MiseEnJambe;
import org.junit.Test;

import static org.junit.Assert.*;

public class DictionnaireTest {

    // ------------------------------------------------------------------
    // contient
    // ------------------------------------------------------------------


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

    /*
    @Test
    public void testVider3() throws Exception {
        DictionnaireNaif dic = new DictionnaireNaif(110000);
        assertEquals();
    }





    @Test
    public void testVider4() throws Exception {
        DictionnaireNaif dic = new DictionnaireNaif(110000);
        assertEquals();
    }


    @Test
    public void testVider5() throws Exception {
        DictionnaireNaif dic = new DictionnaireNaif(110000);
        assertEquals();
    }




*/
}