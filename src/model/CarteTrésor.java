package model;

import util.NomTresor;

public class CarteTrésor extends CarteOrange {

    private NomTresor Trésor;

    public CarteTrésor(NomTresor nomT) {
        this.Trésor = nomT;
        this.setRole("Trésor");
    }        

    public NomTresor getNomTresor() {
        return Trésor;
    }
}