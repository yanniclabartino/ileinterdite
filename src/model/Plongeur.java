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
        ArrayList<Tuile> tuilesTraite = new ArrayList<Tuile>();
        int y = this.getTuile().getLigne();
        int x = this.getTuile().getColonne();
        tuilesTraite.add(this.getTuile());

        //on ajoute les tuiles adjacentes dans les tuiles dispo si elles sont asséché et dans les tuiles non traitées si non
        if (g.getTuile(x - 1, y) != null) {
            ((g.getTuile(x - 1, y).getEtat() != Utils.EtatTuile.ASSECHEE)? tuilesNonTraite : tuilesDispo).add(g.getTuile(x - 1, y));
            if (g.getTuile(x - 1, y).getEtat() == Utils.EtatTuile.INONDEE){
                tuilesDispo.add(g.getTuile(x - 1, y));
            }
        }

        if (g.getTuile(x, y - 1) != null) {
            ((g.getTuile(x , y - 1).getEtat() != Utils.EtatTuile.ASSECHEE)? tuilesNonTraite : tuilesDispo).add(g.getTuile(x , y - 1));
            if (g.getTuile(x , y - 1).getEtat() == Utils.EtatTuile.INONDEE){
                tuilesDispo.add(g.getTuile(x , y - 1));
            }
        }

        if (g.getTuile(x + 1, y) != null) {
            ((g.getTuile(x + 1, y).getEtat() != Utils.EtatTuile.ASSECHEE)? tuilesNonTraite : tuilesDispo).add(g.getTuile(x + 1, y));
            if (g.getTuile(x + 1, y).getEtat() == Utils.EtatTuile.INONDEE){
                tuilesDispo.add(g.getTuile(x + 1, y));
            }
        }

        if (g.getTuile(x, y + 1) != null) {
            ((g.getTuile(x , y + 1).getEtat() != Utils.EtatTuile.ASSECHEE)? tuilesNonTraite : tuilesDispo).add(g.getTuile(x , y + 1));
            if (g.getTuile(x, y + 1).getEtat() == Utils.EtatTuile.INONDEE){
                tuilesDispo.add(g.getTuile(x, y + 1));
            }
        }

        while (!tuilesNonTraite.isEmpty()) {//boucle pour vérifier toute les tuiles non traitées (tant qu'il y en a)
            Tuile tuileTeste = tuilesNonTraite.get(0);//tuile temporaire pour les testes
            int tx = tuileTeste.getColonne();
            int ty = tuileTeste.getLigne();

            if (g.getTuile(tx - 1, ty) != null 
                    && !tuilesDispo.contains(g.getTuile(tx - 1, ty)) 
                    && !tuilesTraite.contains(g.getTuile(tx - 1, ty))
                    && !tuilesNonTraite.contains(g.getTuile(tx - 1, ty))) {//on ne vérifie l'état que des tuiles qui n'ont pas était vérifié
                
                switch (g.getTuile(tx - 1, ty).getEtat()){
                    case ASSECHEE: 
                        tuilesDispo.add(g.getTuile(tx - 1, ty));
                    break;
                    case INONDEE: 
                        tuilesDispo.add(g.getTuile(tx - 1, ty));
                        tuilesNonTraite.add(g.getTuile(tx - 1, ty));
                    break;
                    case COULEE:
                        tuilesNonTraite.add(g.getTuile(tx - 1, ty));
                }
            }
            if (g.getTuile(tx, ty - 1) != null 
                    && !tuilesDispo.contains(g.getTuile(tx, ty - 1)) 
                    && !tuilesTraite.contains(g.getTuile(tx, ty - 1))
                    && !tuilesNonTraite.contains(g.getTuile(tx, ty - 1))) {
                switch (g.getTuile(tx, ty - 1).getEtat()){
                    case ASSECHEE: 
                        tuilesDispo.add(g.getTuile(tx, ty - 1));
                    break;
                    case INONDEE: 
                        tuilesDispo.add(g.getTuile(tx, ty - 1));
                        tuilesNonTraite.add(g.getTuile(tx, ty - 1));
                    break;
                    case COULEE:
                        tuilesNonTraite.add(g.getTuile(tx, ty - 1));
                }
            }
            if (g.getTuile(tx + 1, ty) != null 
                    && !tuilesDispo.contains(g.getTuile(tx + 1, ty)) 
                    && !tuilesTraite.contains(g.getTuile(tx + 1, ty))
                    && !tuilesNonTraite.contains(g.getTuile(tx + 1, ty))) {
                switch (g.getTuile(tx + 1, ty).getEtat()){
                    case ASSECHEE: 
                        tuilesDispo.add(g.getTuile(tx + 1, ty));
                    break;
                    case INONDEE: 
                        tuilesDispo.add(g.getTuile(tx + 1, ty));
                        tuilesNonTraite.add(g.getTuile(tx + 1, ty));
                    break;
                    case COULEE:
                        tuilesNonTraite.add(g.getTuile(tx + 1, ty));
                }
            }
            if (g.getTuile(tx, ty + 1) != null 
                    && !tuilesDispo.contains(g.getTuile(tx, ty + 1)) 
                    && !tuilesTraite.contains(g.getTuile(tx, ty + 1))
                    && !tuilesNonTraite.contains(g.getTuile(tx, ty + 1))) {
                switch (g.getTuile(tx, ty + 1).getEtat()){
                    case ASSECHEE: 
                        tuilesDispo.add(g.getTuile(tx, ty + 1));
                    break;
                    case INONDEE: 
                        tuilesDispo.add(g.getTuile(tx, ty + 1));
                        tuilesNonTraite.add(g.getTuile(tx, ty + 1));
                    break;
                    case COULEE:
                        tuilesNonTraite.add(g.getTuile(tx, ty + 1));
                }
            }
            tuilesNonTraite.remove(tuileTeste);//on enleve la tuile traité des tuiles non traités
            tuilesTraite.add(tuileTeste);//on ajoute la tuile traité aux tuiles tuiles traitées

        }//fin de boucle
        
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
