package ileinterdite;

public class Observe extends javax.swing.JFrame{
        private Observateur obs;
        
	public void addObservateur(Observateur o) {
            this.obs = o;
        };
	public void notifierObservateur(Message m){
            if (obs != null) {
                obs.traiterMessage(m);
            }
        };

}