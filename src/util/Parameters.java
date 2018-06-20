
package util;

/**
 *
 * @author Eric
 */
public class Parameters {
    
    // Paramètres NF
    public static Boolean LOGS = true ; // Afficher des traces par System.out.println()
    public static Boolean ALEAS = true ;  
                    // Créer et attribuer les aventuriers aléatoirement ou non.
                    // Ainsi que la grille (initialisation + premiere tuile choisies).
                    // Ainsi que les mélanges de défausse.
    
    public static void setAleas(boolean b) {
        Parameters.ALEAS = b;
    }

    public static void setLogs(boolean b) {
        Parameters.LOGS = b;
    }
}

