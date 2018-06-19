package model;

public abstract class CarteOrange {

    private Aventurier piochée;
    private String roleCarte;
    
    public final void setOwner(Aventurier a) {
        this.piochée = a;
    }

    public final void delOwner() {
        this.piochée = null;
    }

    public Aventurier getOwner() {
        return piochée;
    }    

    public final void setRole(String roleCarte) {
        this.roleCarte = roleCarte;
    }
    
    public final String getRole() {
        return roleCarte;
    }
    
    public void affiche() {
        System.out.println("Carte \033[33;45m"+getRole()+"\033[0m (appartient à : "+getOwner().getClass().toString().substring(12)+")");
    }
}
