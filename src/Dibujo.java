import java.awt.Graphics;
import java.awt.Color;

import javax.swing.JPanel;

public class Dibujo {

    private Nodo cabeza;

    public Dibujo() {
        cabeza = null;
    }

    public void agregarNodo(Nodo nodo) {
        if (cabeza == null) {
            cabeza = nodo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nodo;
        }
        nodo.siguiente = null;
    }

    public void dibujar(JPanel pnl){
        limpiarPanel(pnl);
        Graphics g = pnl.getGraphics();
        Nodo actual = cabeza;
        while (actual != null) {
            actual.getTrazo().dibujar(g, actual.getColor());
            actual = actual.siguiente;
        }    
    }

    // ********** Metodos Estaticos **********

    public static void limpiarPanel(JPanel pnl) {
        Graphics g = pnl.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, pnl.getWidth(), pnl.getHeight());
    }

}
