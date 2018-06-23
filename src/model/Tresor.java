package model;

import util.NomTresor;

public class Tresor {

    private boolean gagne;
    private NomTresor nom;

    public Tresor(NomTresor nom) {
        this.nom = nom;
        this.gagne = false;
    }

    public boolean isGagne() {
        return gagne;
    }

    public void setGagne(boolean gagne) {
        this.gagne = gagne;
    }
}
