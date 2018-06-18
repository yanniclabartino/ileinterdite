package model;

import util.NomTresor;

public class Trésor {

    private boolean gagne;
    private NomTresor nom;

    public Trésor(NomTresor nom) {
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
