package model;

import model.Aventurier;
import java.util.ArrayList;
import util.Utils;

public class Navigateur extends Aventurier {

    public Navigateur() {
        setCouleur(Utils.Pion.JAUNE);
    }

    @Override
    public ArrayList<Tuile> calculTuileDispo(Grille g) {
        ArrayList<Tuile> tuileDispo = new ArrayList<Tuile>();
        int y = this.getTuile().getLigne();
        int x = this.getTuile().getColonne();

        if (g.getTuile(x-1, y) != null && g.getTuile(x - 1, y).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispo.add(g.getTuile(x - 1, y));
        }

        if (g.getTuile(x, y-1) != null && g.getTuile(x, y - 1).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispo.add(g.getTuile(x, y - 1));
        }

        if (g.getTuile(x+1, y) != null && g.getTuile(x + 1, y).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispo.add(g.getTuile(x + 1, y));
        }

        if (g.getTuile(x, y+1) != null && g.getTuile(x, y + 1).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispo.add(g.getTuile(x, y + 1));
        }
        return tuileDispo;
    }

    @Override
    public ArrayList<Tuile> calculTuileAss(Grille g) {
        ArrayList<Tuile> tuileDispoAss = new ArrayList<Tuile>();
        int y = this.getTuile().getLigne();
        int x = this.getTuile().getColonne();

        if (g.getTuile(x-1, y) != null && g.getTuile(x - 1, y).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispoAss.add(g.getTuile(x - 1, y));
        }

        if (g.getTuile(x, y-1) != null && g.getTuile(x, y - 1).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispoAss.add(g.getTuile(x, y - 1));
        }

        if (g.getTuile(x+1, y) != null && g.getTuile(x + 1, y).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispoAss.add(g.getTuile(x + 1, y));
        }

        if (g.getTuile(x, y+1) != null && g.getTuile(x, y + 1).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispoAss.add(g.getTuile(x, y + 1));
        }
        return tuileDispoAss;
	}
    }
