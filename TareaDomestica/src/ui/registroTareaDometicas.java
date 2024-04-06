package ui;

import data.Tarea;
import network.TareaRetrofit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class registroTareaDometicas extends JFrame {

    private JButton guardarButton;
    private JButton newButton;
    private JButton eliminarButton;
    private LocalDate fechaActual = LocalDate.now();
    private JTextField tareaDomesticaIdTextField;
    private JTextField descripcionTextField;
    private JTextField fechaTextField;
    private JTextField nombreTextField;
    private DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String fechaActualStr = fechaActual.format(formatoFecha);
    private JComboBox<String> estadoComboBox;

    private  JComboBox<String> nombreComboBox;
    private Tarea tarea = new Tarea();
    private String[] nombres = {"Samuel", "Michael", "Henry", "Maria"};
    private int[] codigoAcceso = {1234, 4321, 2304, 4326};

    private int[]  empleadoId = {1, 2, 3, 4};


    public registroTareaDometicas() {

        pintarFrame();
    }

    public registroTareaDometicas(Tarea _tarea) {
        tarea = _tarea;
        pintarFrame();
        nombreTextField.setText(tarea.getNombre());
        descripcionTextField.setText(tarea.getDescripcion());
        fechaTextField.setText(tarea.getFecha());
        //setEstadoComboBox();
        int index = obtenerIndicePorCodigo(tarea.getCodigoAcceso());
        estadoComboBox.setSelectedIndex(index);

    }

    private void pintarFrame() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Gestor de Tareas Domésticas");
        this.setLayout(null);
        this.setBounds(300, 200, 450, 450);
        this.setContentPane(panelPricipal());
        this.setVisible(true);
    }

    private JPanel panelPricipal() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0xAD566A7A, true));

        JLabel imagenLabel = new JLabel();
        ImageIcon icono = new ImageIcon("src/resources/logoIcono.png"); // Ajusta la ruta a tu imagen
        imagenLabel.setIcon(new ImageIcon(icono.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
        imagenLabel.setBounds(100, 0, 300, 50);
        imagenLabel.setText("Registro de Tareas Domesticas");
        panel.add(imagenLabel);


        JLabel descripcion = texto("Descripcion:");
        descripcion.setBounds(25, 10, 100, 130);
        panel.add(descripcion);

        descripcionTextField = textField();
        descripcionTextField.setBounds(130, 60, 250, 30);
        panel.add(descripcionTextField);
        //100
        JLabel nombre = texto("Nombre:");
        nombre.setBounds(25, 10, 100, 230);//.setBounds(25, 10, 100, 330);
        panel.add(nombre);

        nombreTextField = textField();
        nombreTextField.setBounds(130, 110, 100, 30);//.setBounds(130, 160, 250, 30);
        panel.add(nombreTextField);
        //
        JLabel Fecha = texto("Fecha:");
        Fecha.setBounds(25, 10, 100, 330);
        panel.add(Fecha);

        fechaTextField = textField();
        fechaTextField.setText(fechaActualStr);
        fechaTextField.setBounds(130, 160, 250, 30);
        panel.add(fechaTextField);

        nombreComboBox = new JComboBox<>(nombres);
        nombreComboBox.setBounds(130, 110, 150, 30);
        panel.add(nombreComboBox);


        JLabel estado = texto("Estado:");
        estado.setBounds(25, 10, 100, 430);
        panel.add(estado);

        ConfigurarButton();
        panel.add(guardarButton);
        panel.add(newButton);
        panel.add(eliminarButton);
        addEstadoComboBox();
        panel.add(estadoComboBox);


        return panel;
    }

    private void addEstadoComboBox() {
        String[] estados = {"Por Hacer", "En Proceso", "Terminada"};
        estadoComboBox = new JComboBox<>(estados);
        // Establecer "Por Hacer" como opción seleccionada por defecto
        estadoComboBox.setSelectedIndex(0);
        estadoComboBox.setFont(new Font("arial", 1, 16));
        estadoComboBox.setBounds(130, 210, 150, 30);
    }

    private void setEstadoComboBox() {

        switch (tarea.getEstado()) {
            case "Por Hacer" -> estadoComboBox.setSelectedIndex(0);

            case "En Proceso" -> estadoComboBox.setSelectedIndex(1);

            case "Terminada" -> estadoComboBox.setSelectedIndex(2);
        }
    }

    private JLabel texto(String _texto) {
        JLabel texto = new JLabel(_texto);
        texto.setForeground(Color.BLACK);
        texto.setFont(new Font("arial", 1, 16));
        return texto;
    }

    private JTextField textField() {
        JTextField jTextField = new JTextField();
        jTextField.setForeground(Color.BLACK);
        jTextField.setFont(new Font("arial", 1, 16));
        return jTextField;
    }


    private void ConfigurarButton() {

        ImageIcon icono = new ImageIcon("src/resources/NewIcono.png");

        newButton = new JButton("Nuevo");
        newButton.setIcon(new ImageIcon(icono.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        newButton.setBounds(25, 280, 130, 30);
        newButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                limpiarCampos();
            }
        });

        guardarButton = new JButton("Guardar");
        icono = new ImageIcon("src/resources/GuardarIcono.png");
        guardarButton.setIcon(new ImageIcon(icono.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        guardarButton.setBounds(165, 280, 110, 30);
        guardarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                OpcionAgregar();
            }
        });


        eliminarButton = new JButton("Eliminar");
        icono = new ImageIcon("src/resources/EliminarIcono.png");
        eliminarButton.setIcon(new ImageIcon(icono.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        eliminarButton.setBounds(285, 280, 110, 30);
        eliminarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                optionPane("el eliminar no esta disponible");
            }
        });

        nombreComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = nombreComboBox.getSelectedIndex();
                if (index >= 0 && index < codigoAcceso.length) {
                    int codigo = codigoAcceso[index];
                    System.out.println("Código asociado:" + codigo);
                }
            }
        });
    }

    private void OpcionAgregar() {

        String descripcion = descripcionTextField.getText().trim();
        String nombre = nombreTextField.getText().trim();

        if (descripcion.isEmpty() || nombre.isEmpty()) {
            // Mostrar un mensaje de error si algún campo está vacío
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método sin realizar la operación
        }

        if (tarea.getTareaId() == 0)
            crearNuevo();
        else
            modificar();

        // Limpiar los campos del formulario después de agregar la tarea
        limpiarCampos();
    }

    private void modificar() {

        tarea.setDescripcion(descripcionTextField.getText());
        tarea.setNombre(nombreTextField.getText());
        tarea.setFecha(fechaActualStr);
        tarea.setEstado(estadoComboBox.getSelectedItem().toString());

        TareaRetrofit tareaRetrofit = new TareaRetrofit();

        if (tareaRetrofit.actualizarTarea(tarea)) {
            System.out.println("actualizado");
            optionPane("Actualizado con exito");
            this.dispose();
        } else {
            System.out.println("no actualizado");
        }
    }

    private void crearNuevo() {
        // Obtener los datos del formulario
        String descripcion = descripcionTextField.getText();
        String fecha = fechaTextField.getText(); // Suponiendo que la fecha está en formato de texto
        String nombre = nombreComboBox.getSelectedItem().toString();
        String estado = estadoComboBox.getSelectedItem().toString(); // Obtener el estado seleccionado del JComboBox
        int codigoAcceso = obtenerCodigoAsociado(nombre);
        int empleadoId = obtenerIdEmpleado(nombre);
        // Crear una instancia de Tarea con los datos obtenidos
        Tarea nuevaTarea = new Tarea();
        nuevaTarea.setDescripcion(descripcion);
        nuevaTarea.setFecha(fecha);
        nuevaTarea.setNombre(nombre);
        nuevaTarea.setEstado(estado);
        nuevaTarea.setCodigoAcceso(String.valueOf(codigoAcceso));
        nuevaTarea.setEmpleadoId(empleadoId);

        // Enviar la tarea al servidor utilizando TareaRetrofit (suponiendo que TareaRetrofit es una clase válida para hacer solicitudes HTTP)
        TareaRetrofit tareaRetrofit = new TareaRetrofit();

        if (tareaRetrofit.enviarTarea(nuevaTarea)) {
            System.out.println(" ");

        } else {
            System.out.println("no enviado");
        }
    }

    private void optionPane(String informacion) {
        JOptionPane seguro = new JOptionPane();
        seguro.showMessageDialog(this, informacion, "informacion", JOptionPane.INFORMATION_MESSAGE);
    }
    private int obtenerCodigoAsociado(String nombre) {
        // Buscar el nombre en el arreglo y obtener su índice
        int index = -1;
        for (int i = 0; i < nombres.length; i++) {
            if (nombres[i].equals(nombre)) {
                index = i;
                break;
            }
        }
        return (index != -1) ? codigoAcceso[index] : -1;
    }

    private void limpiarCampos() {
        descripcionTextField.setText("");
        nombreTextField.setText("");
        formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        fechaActualStr = fechaActual.format(formatoFecha);
        fechaTextField.setText(fechaActualStr.toString());
        estadoComboBox.setSelectedIndex(0); // Establecer el estado "Por Hacer" como seleccionado por defecto
    }

    private int obtenerIndicePorCodigo(String codigo) {
        for (int i = 0; i < codigoAcceso.length; i++) {
            continue;
        }
        return -1; // Si no se encuentra el código, devolver -1
    }

    private int obtenerIdEmpleado(String nombre) {
        for (int i = 0; i < nombres.length; i++) {
            if (nombres[i].equals(nombre)) {
                return empleadoId[i];
            }
        }
        return -1; // Si no se encuentra el nombre, devolver -1
    }
}