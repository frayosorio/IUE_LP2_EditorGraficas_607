import java.awt.Graphics;
import java.util.List;
import java.awt.Color;

import javax.swing.JPanel;

import com.fasterxml.jackson.core.type.TypeReference;

import DTOs.TrazoDto;

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

    public int getLongitud() {
        int totalNodos = 0;
        Nodo actual = cabeza;
        while (actual != null) {
            totalNodos++;
            actual = actual.siguiente;
        }
        return totalNodos;
    }

    public void dibujar(JPanel pnl, Estado estado) {
        limpiarPanel(pnl);
        Graphics g = pnl.getGraphics();
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual == nodoSeleccionado) {
                actual.getTrazo().dibujar(g, actual.getColor(), estado);
            } else {
                actual.getTrazo().dibujar(g, actual.getColor(), Estado.NADA);
            }
            actual = actual.siguiente;
        }
    }

    private Nodo nodoSeleccionado;

    public Nodo getNodoSeleccionado() {
        return nodoSeleccionado;
    }

    public boolean seleccionar(int x, int y) {
        nodoSeleccionado = null;
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.getTrazo().cercano(x, y)) {
                nodoSeleccionado = actual;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public void eliminarNodo(Nodo nodo) {
        Nodo actual = cabeza;
        Nodo anterior = null;
        while (actual != null) {
            if (actual == nodo) {
                if (anterior == null) {
                    cabeza = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }
            }
            anterior = actual;
            actual = actual.siguiente;
        }
    }

    public boolean guardarJSON(String nombreArchivo) {
        TrazoDto[] trazos = new TrazoDto[getLongitud()];
        Nodo actual = cabeza;
        int fila = 0;
        while (actual != null) {
            trazos[fila] = actual.toDTO();
            fila++;
            actual = actual.siguiente;
        }
        return Archivo.guardarJson(nombreArchivo, trazos);
    }

    public void desdeJSON(String nombreArchivo) {
        List<TrazoDto> trazosDto = Archivo.leerJson(nombreArchivo, new TypeReference<List<TrazoDto>>() {
        });
        if (trazosDto != null) {
            cabeza = null;
            for (TrazoDto dto : trazosDto) {
                Trazo trazo = null;
                switch (TipoTrazo.valueOf(dto.getTipo())) {
                    case LINEA:
                        trazo = new Linea(dto.getX1(), dto.getY1(), dto.getX2(), dto.getY2());
                        break;
                    case RECTANGULO:
                        trazo = new Rectangulo(dto.getX1(), dto.getY1(), dto.getX2(), dto.getY2());
                        break;
                    case OVALO:
                        trazo = new Ovalo(dto.getX1(), dto.getY1(), dto.getX2(), dto.getY2());
                        break;
                }
                Nodo nodo = new Nodo(trazo, new Color(dto.getRed(), dto.getGreen(), dto.getBlue()));
                agregarNodo(nodo);
            }
        }

    }

    // ********** Metodos Estaticos **********

    public static void limpiarPanel(JPanel pnl) {
        Graphics g = pnl.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, pnl.getWidth(), pnl.getHeight());
    }

}
