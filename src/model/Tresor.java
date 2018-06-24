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
    
    public void affiche() {
        System.out.println("\tTrésor \033[33;34m"+this.nom.toString()+"\033[36m"+(this.isGagne() ? "est gagné." : "n'est pas encore gagné") + "\033[0m");
    }
}
