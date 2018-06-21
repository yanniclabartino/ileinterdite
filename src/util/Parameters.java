package util;

public class Parameters {
    
    public static Boolean LOGS = false ; // Afficher des traces par System.out.println()
    public static Boolean ALEAS = true ; // Attribuer les aventuriers aléatoirement ou non, mélanger les défausses et les pioches

    public static void setLogs(boolean logs) {
        Parameters.LOGS = logs;
    }

    public static void setAleas(boolean aleas) {
        Parameters.ALEAS = aleas;
    }
    
    
    
}
