package Entity;

import java.time.LocalDateTime;

public class Reserva {
    private Long idReserva;
    private final Cliente cliente;
    private final Mesa mesa;
    private final LocalDateTime dataReserva;

    public Reserva(Long idReserva, Cliente cliente, Mesa mesa, LocalDateTime dataReserva) {
        this.idReserva = idReserva;
        this.cliente = cliente;
        this.mesa = mesa;
        this.dataReserva = dataReserva;
    }

    public Reserva(Cliente cliente, Mesa mesa, LocalDateTime dataReserva) {
        this.cliente = cliente;
        this.mesa = mesa;
        this.dataReserva = dataReserva;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", cliente=" + cliente +
                ", mesa=" + mesa +
                ", dataReserva=" + dataReserva +
                '}';
    }
}
