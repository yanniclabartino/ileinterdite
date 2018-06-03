package model;

import model.Aventurier;
import java.util.ArrayList;
import util.Utils;

public class Navigateur extends Aventurier {

    public Navigateur(Tuile t, Utils.Pion p) {
        super(t, p);
    }

    @Override
    public ArrayList<Tuile> calculTuileDispo(Grille g) {
        ArrayList<Tuile> tuileDispo = new ArrayList<Tuile>();
        int c = this.getTuile().getColonne();
        int l = this.getTuile().getLigne();

        if (g.getTuile(l-1, c) != null && g.getTuile(l - 1, c).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispo.add(g.getTuile(l - 1, c));
        }

        if (g.getTuile(l, c-1) != null && g.getTuile(l, c - 1).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispo.add(g.getTuile(l, c - 1));
        }

        if (g.getTuile(l+1, c) != null && g.getTuile(l + 1, c).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispo.add(g.getTuile(l + 1, c));
        }

        if (g.getTuile(l, c+1) != null && g.getTuile(l, c + 1).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispo.add(g.getTuile(l, c + 1));
        }
        return tuileDispo;
    }

    @Override
    public ArrayList<Tuile> calculTuileAss(Grille g) {
        ArrayList<Tuile> tuileDispoAss = new ArrayList<Tuile>();
        int c = this.getTuile().getColonne();
        int l = this.getTuile().getLigne();

        if (g.getTuile(l-1, c) != null && g.getTuile(l - 1, c).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispoAss.add(g.getTuile(l - 1, c));
        }

        if (g.getTuile(l, c-1) != null && g.getTuile(l, c - 1).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispoAss.add(g.getTuile(l, c - 1));
        }

        if (g.getTuile(l+1, c) != null && g.getTuile(l + 1, c).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispoAss.add(g.getTuile(l + 1, c));
        }

        if (g.getTuile(l, c+1) != null && g.getTuile(l, c + 1).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispoAss.add(g.getTuile(l, c + 1));
        }
        return tuileDispoAss;
	}
    }
