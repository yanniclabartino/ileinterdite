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

    public final void setRole(String roleCarte) {
        this.roleCarte = roleCarte;
    }
    
    public final String getRole() {
        return roleCarte;
    }
}
