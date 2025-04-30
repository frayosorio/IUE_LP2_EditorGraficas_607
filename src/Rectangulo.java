import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Rectangulo extends Trazo {

    public Rectangulo(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public void dibujar(Graphics g, Color color, Estado estado) {
        Graphics2D g2 = (Graphics2D) g;
        switch (estado) {
            case SELECCIONANDO:
                g2.setColor(color);
                g2.setStroke(new BasicStroke(3));
                g2.drawRect(getXMinimo(), getYMinimo(), getAncho(), getAlto());
                break;

            default:
                g2.setColor(color);
                g2.setStroke(new BasicStroke(1));
                g2.drawRect(getXMinimo(), getYMinimo(), getAncho(), getAlto());
                break;
        }

    }

    @Override
    public boolean cercano(int x, int y) {
        int minX = getXMinimo();
        int minY = getYMinimo();
        int maxX = Math.max(getX1(), getX2());
        int maxY = Math.max(getY1(), getY2());

        return Trazo.puntoCercano(x, y, minX, minY, maxX, minY) ||
                Trazo.puntoCercano(x, y, maxX, minY, maxX, maxY) ||
                Trazo.puntoCercano(x, y, minX, maxY, maxX, maxY) ||
                Trazo.puntoCercano(x, y, minX, minY, minX, maxY);
    }

}
