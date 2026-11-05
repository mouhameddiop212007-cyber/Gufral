package inf353;

public class DictionnaireNaif implements Dictionnaire {
    int N;
    char t[];
    int l; //longueur du dico
    char MARQUE;

    public DictionnaireNaif(int N) {
        this.N = N;
        this.t = new char[N];
        this.l = 0;
        this.MARQUE = '\0'; //Idée de Mouhamed adaptée à vos codes.
    }


    //==============================================
    //                Vider
    //===============================================
    @Override
    public void vider() {
        this.t = new char[N];
        l = 0;
    }
    
    
    
    //==============================================
    //                nbMots
    //===============================================
    @Override
    public int nbMots() {
        int nb = 0;
        while(nb != l+1) {
            if(t[nb] == '\0') {
                nb = nb + 1 ;
            }
        }
        return nb;
    }
    
    
    
    
    //==============================================
    //                CONTIENT
    //===============================================
    
    //Vérifie si la sequence obtenue contient la sequence m 
    
@Override
public boolean contient(String m) {
    int i = 0;

    while (i < l) {
        int j = 0;

        while (j < m.length() && i + j < l
                && t[i + j] == m.charAt(j)) {
            j++;
        }

        if (j == m.length()) {
            return true;
        }

        while (i < l && t[i] != MARQUE) {
            i++;
        }
        i++;
    }

    return false;
}

    //==============================================
    //                Indice Mot
    //===============================================
@Override
public int indiceMot(String m) {
    int i = 0;
    int indice = 0;

    while (i < l) {
        int j = 0;

        while (j < m.length() && i + j < l
                && t[i + j] == m.charAt(j)) {
            j++;
        }

        if (j == m.length()) {
            return indice;
        }

        while (i < l && t[i] != MARQUE) {
            i++;
        }

        i++;
        indice++;
    }

    return -1;
}
    //public int indiceMot;
    
    
    
    //==============================================
    //                Ajouter Mot
    //===============================================
@Override
public void ajouterMot(String m) {
    if (!contient(m)) {
        if (l + m.length() > N) {
            throw new IllegalStateException("Dictionnaire plein");
        }

        int i = 0;

        while (i < m.length()) {
            t[l + i] = m.charAt(i);
            i++;
        }

        l = l + m.length();
    }
}



    //==============================================
    //                Mot Indice
    //===============================================
@Override
public String motIndice(int i) {
    int indice = 0;
    int j = 0;

    while (j < l && indice < i) {
        if (t[j] == MARQUE) {
            indice++;
        }
        j++;
    }

    String s = "";

    while (j < l && t[j] != MARQUE) {
        s += t[j];
        j++;
    }

    return s;
}


    //==============================================
    //                CONTIENT Préfixe
    //===============================================
@Override
public boolean contientPrefixe(String p) {
    int i = 0;

    while (i < l) {
        int j = 0;

        while (j < p.length() && i + j < l
                && t[i + j] == p.charAt(j)) {
            j++;
        }

        if (j == p.length()) {
            return true;
        }

        while (i < l && t[i] != MARQUE) {
            i++;
        }

        i++;
    }

    return false;
}



    //==============================================
    //                Plus long préfixe
    //===============================================
@Override
public String plusLongPrefixeDe(String mot) {
    int c_max = 0;
    int i = 0;
    int c_indice = 0;

    while (i < l) {
        int c = 0;

        while (c < mot.length() && i + c < l
                && mot.charAt(c) == t[i + c]
                && t[i + c] != MARQUE) {
            c++;
        }

        if (c > c_max) {
            c_max = c;
        }

        while (i < l && t[i] != MARQUE) {
            i++;
        }

        i++;
        c_indice++;
    }

    String resultat = "";
    int j = 0;

    while (j < c_max) {
        resultat = resultat + mot.charAt(j);
        j++;
    }

    return resultat;
}
}