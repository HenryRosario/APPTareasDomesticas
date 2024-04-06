package ui;

import data.Tarea;
import ui.registroTareaDometicas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TareaRenderer extends JPanel implements ListCellRenderer<Tarea> {

    private JLabel lbName = new JLabel();
    private JLabel lbAuthor = new JLabel();
    private JLabel lbDescripcion = new JLabel();
    private JLabel lbEstado = new JLabel();

    JPanel panelMain = new JPanel(new BorderLayout(50, 50));
    JPanel panelRigth = new JPanel(new GridLayout(2, 1));
    JPanel panelLeft = new JPanel(new GridLayout(2, 1));
    JPanel panelText = new JPanel(new GridLayout(2, 2)); // Cambiado a GridLayout(2, 2) para tener dos líneas paralelas

    private static final Color COLOR_POR_HACER = new Color(0xFFFD6A6A, true);
    private static final Color COLOR_EN_PROCESO = new Color(-604963515, true);
    private static final Color COLOR_TERMINADA = new Color(8976498);

    public TareaRenderer() {
        setLayout(new BorderLayout(0, 0));
        panelText.add(new JLabel()); // Espacio en blanco para alinear las etiquetas a la izquierda
        panelText.add(new JLabel()); // Espacio en blanco para alinear las etiquetas a la izquierda
        panelText.add(lbName);
        panelText.add(lbAuthor);
        panelText.add(lbDescripcion);
        panelText.add(lbEstado);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        add(panelText, BorderLayout.CENTER); // Cambiado a CENTER para que el panel se expanda horizontalmente
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Tarea> list,
                                                  Tarea tarea, int index, boolean isSelected, boolean cellHasFocus) {
        lbName.setText(tarea.getNombre());
        lbDescripcion.setText(tarea.getDescripcion());
        lbEstado.setText(tarea.getEstado());
        lbAuthor.setText(tarea.getFecha());

        Color backgroundColor;
        switch (tarea.getEstado()) {
            case "Por Hacer":
                backgroundColor = COLOR_POR_HACER;
                break;
            case "En Proceso":
                backgroundColor = COLOR_EN_PROCESO;
                break;
            case "Terminada":
                backgroundColor = COLOR_TERMINADA;
                break;
            default:
                backgroundColor = isSelected ? list.getSelectionBackground() : list.getBackground();
        }
        panelText.setBackground(backgroundColor);

        panelRigth.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                editar(tarea);
            }
        });

        return this;
    }

    public void editar(Tarea tarea) {
        registroTareaDometicas registroTareaDometicas = new registroTareaDometicas(tarea);
    }
}