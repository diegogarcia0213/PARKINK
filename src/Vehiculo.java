import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public class Vehiculo implements Serializable {
    private String placa;
    private LocalDateTime horaEntrada;
    private transient LocalDateTime horaSalida;

    public Vehiculo(String placa) {
        this.placa = placa;
        this.horaEntrada = LocalDateTime.now();
    }

    public String getPlaca() {
        return placa;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public void registrarSalida() {
        this.horaSalida = LocalDateTime.now();
    }

    public long calcularCosto() {
        if (horaSalida == null) {
            registrarSalida();
        }
        Duration duration = Duration.between(horaEntrada, horaSalida);
        long minutos = duration.toMinutes();
        return minutos * 40; // 40 pesos por minuto
    }
}
