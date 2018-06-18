package model;

import model.Aventurier;
import java.util.ArrayList;
import util.Utils;

public class Plongeur extends Aventurier {

    public Plongeur() {
        super();
        setCouleur(Utils.Pion.NOIR);
    }

    @Override
    public ArrayList<Tuile> calculTuileDispo(Grille g) {
        ArrayList<Tuile> tuilesDispo = new ArrayList<Tuile>();
        ArrayList<Tuile> tuilesNonTraite = new ArrayList<Tuile>();
        int y = this.getTuile().getLigne();
        int x = this.getTuile().getColonne();

        if (g.getTuile(x - 1, y) != null) {
            (g.getTuile(x - 1, y).getEtat() != Utils.EtatTuile.ASSECHEE)
                    ? tuilesNonTraite.add(g.getTuile(x - 1, y)) : tuilesDispo.add(g.getTuile(x - 1, y));
        }

        if (g.getTuile(x, y - 1) != null) {
            (g.getTuile(x, y - 1).getEtat() != Utils.EtatTuile.ASSECHEE)
                    ? tuilesNonTraite.add(g.getTuile(x, y - 1)) : tuilesDispo.add(g.getTuile(x, y - 1));
        }

        if (g.getTuile(x + 1, y) != null) {
            (g.getTuile(x + 1, y).getEtat() != Utils.EtatTuile.ASSECHEE)
                    ? tuilesNonTraite.add(g.getTuile(x + 1, y)) : tuilesDispo.add(g.getTuile(x + 1, y));
        }

        if (g.getTuile(x, y + 1) != null) {
            (g.getTuile(x, y +).getEtat() != Utils.EtatTuile.ASSECHEE)
                    ? tuilesNonTraite.add(g.getTuile(x, y + 1)) : tuilesDispo.add(g.getTuile(x, y + 1));
        }

        while (!tuilesNonTraite.isEmpty()) {
            Tuile tuileTeste = tuilesNonTraite.get(0);//tuile temporaire pour les testes
            int tx = tuileTeste.getColonne();
            int ty = tuileTeste.getLigne();

            if (g.getTuile(tx - 1, ty) != null) {
                (g.getTuile(tx - 1, ty).getEtat() != Utils.EtatTuile.ASSECHEE)
                        ? tuilesNonTraite.add(g.getTuile(tx - 1, ty)) : tuilesDispo.add(g.getTuile(tx - 1, ty));
            }

            if (g.getTuile(tx, ty - 1) != null) {
                (g.getTuile(tx, ty - 1).getEtat() != Utils.EtatTuile.ASSECHEE)
                        ? tuilesNonTraite.add(g.getTuile(tx, ty - 1)) : tuilesDispo.add(g.getTuile(tx, ty - 1));
            }

            if (g.getTuile(tx + 1, ty) != null) {
                (g.getTuile(tx + 1, ty).getEtat() != Utils.EtatTuile.ASSECHEE)
                        ? tuilesNonTraite.add(g.getTuile(tx + 1, ty)) : tuilesDispo.add(g.getTuile(tx + 1, ty));
            }

            if (g.getTuile(tx, ty + 1) != null) {
                (g.getTuile(tx, ty +).getEtat() != Utils.EtatTuile.ASSECHEE)
                        ? tuilesNonTraite.add(g.getTuile(tx, ty + 1)) : tuilesDispo.add(g.getTuile(tx, ty + 1));
            }

        }

        return tuilesDispo;

    }

    @Override
    public ArrayList<Tuile> calculTuileAss(Grille g) {
        ArrayList<Tuile> tuileDispoAss = new ArrayList<Tuile>();
        int y = this.getTuile().getLigne();
        int x = this.getTuile().getColonne();

        if (g.getTuile(x - 1, y) != null && g.getTuile(x - 1, y).getEtat() != Utils.EtatTuile.COULEE && g.getTuile(x - 1, y).getEtat() != Utils.EtatTuile.ASSECHEE) {
            tuileDispoAss.add(g.getTuile(x - 1, y));
        }

        if (g.getTuile(x, y - 1) != null && g.getTuile(x, y - 1).getEtat() != Utils.EtatTuile.COULEE && g.getTuile(x, y - 1).getEtat() != Utils.EtatTuile.ASSECHEE) {
            tuileDispoAss.add(g.getTuile(x, y - 1));
        }

        if (g.getTuile(x + 1, y) != null && g.getTuile(x + 1, y).getEtat() != Utils.EtatTuile.COULEE && g.getTuile(x + 1, y).getEtat() != Utils.EtatTuile.ASSECHEE) {
            tuileDispoAss.add(g.getTuile(x + 1, y));
        }

        if (g.getTuile(x, y + 1) != null && g.getTuile(x, y + 1).getEtat() != Utils.EtatTuile.COULEE && g.getTuile(x, y + 1).getEtat() != Utils.EtatTuile.ASSECHEE) {
            tuileDispoAss.add(g.getTuile(x, y + 1));
        }
        if (this.getTuile().getEtat() == Utils.EtatTuile.INONDEE) {
            tuileDispoAss.add(this.getTuile());
        }
        return tuileDispoAss;
    }
}
