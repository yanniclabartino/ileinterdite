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
    
    @Override
    public void affiche() {
        System.out.println("Carte \033[33;45m"+getRole()+ "\033[0m [\033[36m"+ getNomTresor().toString() +"\033[0m] (appartient à : "+getOwner().getClass().toString().substring(12)+")");
    }
}