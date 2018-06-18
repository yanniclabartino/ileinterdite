package util;

public enum NomTresor {
    LE_CRISTAL_ARDENT("Le cristal ardent"),
    LA_STATUE_DU_ZEPHYR("La statue de zéphyr"),
    LE_CALICE_DE_L_ONDE("Le calice de l'onde"),
    LA_PIERRE_SACREE("La pierre sacrée");

    private String nomTresor;
    
    private NomTresor(String s) {
        this.nomTresor = s;
    }

    @Override
    public String toString() {
        return this.nomTresor;
    }
}
