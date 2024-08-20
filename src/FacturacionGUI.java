import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FacturacionGUI extends JFrame {
    public FacturacionGUI(Vehiculo vehiculo, RegistroVehiculosGUI registroVehiculosGUI) {
        setTitle("Facturación");
        setSize(300, 200);
        setLayout(new GridLayout(5, 1));

        vehiculo.registrarSalida();
        long costo = vehiculo.calcularCosto();

        JLabel labelPlaca = new JLabel("Placa: " + vehiculo.getPlaca());
        JLabel labelHoraEntrada = new JLabel("Hora de entrada: " + vehiculo.getHoraEntrada());
        JLabel labelHoraSalida = new JLabel("Hora de salida: " + vehiculo.getHoraSalida());
        JLabel labelCosto = new JLabel("Costo: " + costo + " pesos");

        JButton botonFacturar = new JButton("Facturar y eliminar del sistema");
        botonFacturar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registroVehiculosGUI.eliminarVehiculo(vehiculo);
                dispose();
            }
        });

        add(labelPlaca);
        add(labelHoraEntrada);
        add(labelHoraSalida);
        add(labelCosto);
        add(botonFacturar);
    }
}
