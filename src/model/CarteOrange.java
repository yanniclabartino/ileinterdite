package model;

import model.Aventurier;

public abstract class CarteOrange {

    private Aventurier piochée;

    public void setOwner(Aventurier a) {
        this.piochée = a;
    }

    void delOwner() {
        this.piochée = null;
    }
}
