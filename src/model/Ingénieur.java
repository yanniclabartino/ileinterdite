package model;

import model.Aventurier;
import java.util.ArrayList;
import util.Utils;

public class Ingénieur extends Aventurier {

    public Ingénieur() {
        setCouleur(Utils.Pion.ROUGE);
    }

    @Override
    public ArrayList calculTuileDispo(Grille g) {
        ArrayList<Tuile> tuileDispo = new ArrayList<Tuile>();
        int y = this.getTuile().getLigne();
        int x = this.getTuile().getColonne();

        if (g.getTuile(x
                - 1, y) != null && g.getTuile(x - 1, y).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispo.add(g.getTuile(x - 1, y));
        }

        if (g.getTuile(x, y
                - 1) != null && g.getTuile(x, y - 1).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispo.add(g.getTuile(x, y - 1));
        }

        if (g.getTuile(x
                + 1, y) != null && g.getTuile(x + 1, y).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispo.add(g.getTuile(x + 1, y));
        }

        if (g.getTuile(x, y
                + 1) != null && g.getTuile(x, y + 1).getEtat() != Utils.EtatTuile.COULEE) {
            tuileDispo.add(g.getTuile(x, y + 1));
        }
        return tuileDispo;
    }

    public ArrayList calculTuileAss(Grille g) {
        ArrayList<Tuile> tuileDispoAss = new ArrayList<Tuile>();
        int y = this.getTuile().getLigne();
        int x = this.getTuile().getColonne();

        if (g.getTuile(x
                - 1, y) != null && g.getTuile(x - 1, y).getEtat() != Utils.EtatTuile.COULEE && g.getTuile(x - 1, y).getEtat() != Utils.EtatTuile.ASSECHEE) {
            tuileDispoAss.add(g.getTuile(x - 1, y));
        }

        if (g.getTuile(x, y
                - 1) != null && g.getTuile(x, y - 1).getEtat() != Utils.EtatTuile.COULEE && g.getTuile(x, y - 1).getEtat() != Utils.EtatTuile.ASSECHEE) {
            tuileDispoAss.add(g.getTuile(x, y - 1));
        }

        if (g.getTuile(x
                + 1, y) != null && g.getTuile(x + 1, y).getEtat() != Utils.EtatTuile.COULEE && g.getTuile(x + 1, y).getEtat() != Utils.EtatTuile.ASSECHEE) {
            tuileDispoAss.add(g.getTuile(x + 1, y));
        }

        if (g.getTuile(x, y
                + 1) != null && g.getTuile(x, y + 1).getEtat() != Utils.EtatTuile.COULEE && g.getTuile(x, y + 1).getEtat() != Utils.EtatTuile.ASSECHEE) {
            tuileDispoAss.add(g.getTuile(x, y + 1));
        }
        return tuileDispoAss;
    }
}
