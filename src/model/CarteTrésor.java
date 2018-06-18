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
        System.out.println("Carte "+getRole()+ "["+ getNomTresor().toString() +"] (appartient à : "+getOwner().getClass().toString().substring(12)+")");
    }
}