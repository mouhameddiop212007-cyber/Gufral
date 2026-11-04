package inf353;

import static org.junit.Assert.*;

import org.junit.Test;


public class TestDeMiseEnBouche {
    
    @Test
    public void testScaleneValide() {
        assertEquals(MiseEnBouche.typeDeTriangle(2, 3, 4), MiseEnBouche.SCALENE);
    }

    @Test
    public void testEquilateralValide() {
        assertEquals(MiseEnBouche.typeDeTriangle(2, 2, 2), MiseEnBouche.EQUILATERAL);
    }

    @Test
    public void testIsoceleValide() {
        assertEquals(MiseEnBouche.typeDeTriangle(3, 3, 4), MiseEnBouche.ISOCELE);
        assertEquals(MiseEnBouche.typeDeTriangle(3, 4, 3), MiseEnBouche.ISOCELE);
        assertEquals(MiseEnBouche.typeDeTriangle(4, 3, 3), MiseEnBouche.ISOCELE);
    }
    
    @Test
    public void testAiguille() {
        assertEquals(MiseEnBouche.typeDeTriangle(0, 4, 5), MiseEnBouche.ERREUR);
        assertEquals(MiseEnBouche.typeDeTriangle(4, 0, 5), MiseEnBouche.ERREUR);
        assertEquals(MiseEnBouche.typeDeTriangle(4, 5, 0), MiseEnBouche.ERREUR);
    }
    
    @Test
    public void testValeurNegative() {
        assertEquals(MiseEnBouche.typeDeTriangle(-1, 4, 5), MiseEnBouche.ERREUR);
        assertEquals(MiseEnBouche.typeDeTriangle(4, -1, 5), MiseEnBouche.ERREUR);
        assertEquals(MiseEnBouche.typeDeTriangle(4, 5, -1), MiseEnBouche.ERREUR);
    }
    
    @Test
    public void testPlat() {
        assertEquals(MiseEnBouche.typeDeTriangle(2, 5, 7), MiseEnBouche.ERREUR);
        assertEquals(MiseEnBouche.typeDeTriangle(7, 5, 2), MiseEnBouche.ERREUR);
        assertEquals(MiseEnBouche.typeDeTriangle(2, 7, 5), MiseEnBouche.ERREUR);
    }
    
    @Test
    public void testImpossible() {
        assertEquals(MiseEnBouche.typeDeTriangle(2, 2, 10), MiseEnBouche.ERREUR);
        assertEquals(MiseEnBouche.typeDeTriangle(10, 2, 2), MiseEnBouche.ERREUR);
        assertEquals(MiseEnBouche.typeDeTriangle(2, 10, 2), MiseEnBouche.ERREUR);
    }
    
    @Test
    public void testTousZero() {
        assertEquals(MiseEnBouche.typeDeTriangle(0, 0, 0), MiseEnBouche.ERREUR);
    } 
}
