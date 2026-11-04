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

    @Override
    public void vider() {
        this.t = new char[N];
        l = 0;
    }

    @Override
    public int nbMots() {
        return l;
    }
    //Vérifie si la sequence obtenue contient la sequence m 
    @Override
    public boolean contient(String m) {
        for (int i = 0; i < (this.l*40); i++) {
            int c = i;
            while (this.t[i] == m.charAt(i)) {
                c++;
            }
            if (this.t[c] == MARQUE) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int indiceMot(String m){
        int i;
        //String s =  "";
        int indice1 = 0;
        int k = 0;
        if(!contient(m)){
            indice1 = -1;
        }else{
            while(k != l){
                i = 0;
                String s = "";
                while(t[i] != MARQUE){
                    s = s + t[i];
                    i++;
                }
                if( m != s ){
                    if(t[i] == MARQUE){
                        indice1 = indice1 + 1;
                    }
                }

                k++;
            }

        }
        return indice1;
    }
    //public int indiceMot;
    @Override
    public void ajouterMot(String m){
        if(!contient(m)){
            int i = 0;
            while(i != m.length()){
                t[l+i] = m.charAt(i);
                i++;
            }
            t[i] = MARQUE;
            l++;
        }
    }

    @Override
    public String motIndice(int i) {
        String s = "";
        while (t[i] != MARQUE) {
            s += t[i];
            i++;
        }
        return s;
    }

    @Override
    public boolean contientPrefixe(String p) {
        for (int i = 0; i < (this.l*40); i++) {
            int c = i;
            while (c < p.length() && c < (this.l*40) && this.t[c] == p.charAt(c)) {
                c++;
            }
            if (c >= p.length()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String plusLongPrefixeDe(String mot) {
        int c_max = 0;
        int i_max = 0;
        int c_indice = 0;
        for (int i = 0; i < (this.l*40); i++) {
            int c = 0;
            while (c < (this.l*40) && c < mot.length() && mot.charAt(c) == this.t[c]) {
                c++;
            }
            if (c >= mot.length() && c > c_max) {
                c_max = c;
                i_max = c_indice;
            }
            c_indice++;
        }
        return motIndice(i_max);
    }

}