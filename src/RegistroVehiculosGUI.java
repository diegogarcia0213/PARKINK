import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroVehiculosGUI extends JFrame {
    private List<Vehiculo> vehiculos = new ArrayList<>();
    private DefaultListModel<String> listaVehiculosModel = new DefaultListModel<>();
    private JList<String> listaVehiculos;

    public RegistroVehiculosGUI() {
        setTitle("Registro y Facturación de Vehículos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new FlowLayout());

        JLabel labelPlaca = new JLabel("Placa:");
        JTextField campoPlaca = new JTextField(10);
        JButton botonRegistrar = new JButton("Registrar");

        panelPrincipal.add(labelPlaca);
        panelPrincipal.add(campoPlaca);
        panelPrincipal.add(botonRegistrar);

        add(panelPrincipal, BorderLayout.NORTH);

        listaVehiculos = new JList<>(listaVehiculosModel);
        add(new JScrollPane(listaVehiculos), BorderLayout.CENTER);

        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = campoPlaca.getText().toUpperCase();
                boolean placaExiste = false;

                // Verificar si la placa ya está registrada
                for (Vehiculo vehiculo : vehiculos) {
                    if (vehiculo.getPlaca().equals(placa)) {
                        placaExiste = true;
                        break;
                    }
                }

                if (placaExiste) {
                    JOptionPane.showMessageDialog(RegistroVehiculosGUI.this, "La placa ya está registrada.");
                } else {
                    if (placa.matches("[A-Z]{3}[0-9]{3}")) {
                        Vehiculo vehiculo = new Vehiculo(placa);
                        vehiculos.add(vehiculo);
                        listaVehiculosModel.addElement(placa);
                        guardarDatos();
                    } else {
                        JOptionPane.showMessageDialog(RegistroVehiculosGUI.this, "La placa debe tener 3 letras y 3 números.");
                    }
                }

                campoPlaca.setText("");
            }
        });

        listaVehiculos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String placaSeleccionada = listaVehiculos.getSelectedValue();
                for (Vehiculo vehiculo : vehiculos) {
                    if (vehiculo.getPlaca().equals(placaSeleccionada)) {
                        mostrarPaginaFacturacion(vehiculo);
                        break;
                    }
                }
            }
        });

        cargarDatos();
    }

    private void mostrarPaginaFacturacion(Vehiculo vehiculo) {
        FacturacionGUI facturacionGUI = new FacturacionGUI(vehiculo, this);
        facturacionGUI.setVisible(true);
    }

    public void eliminarVehiculo(Vehiculo vehiculo) {
        vehiculos.remove(vehiculo);
        listaVehiculosModel.removeElement(vehiculo.getPlaca());
        guardarDatos();
    }

    private void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("vehiculos.dat"))) {
            oos.writeObject(vehiculos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarDatos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("vehiculos.dat"))) {
            vehiculos = (List<Vehiculo>) ois.readObject();
            for (Vehiculo vehiculo : vehiculos) {
                listaVehiculosModel.addElement(vehiculo.getPlaca());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistroVehiculosGUI().setVisible(true));
    }
}
