package Entity;

import java.time.ZonedDateTime;

public class Reserva {
    private Long idReserva;
    private Cliente cliente;
    private Mesa mesa;
    private ZonedDateTime dataReserva;

    //ZonedDateTime dataReserva - removido do construtor para teste

    public Reserva(Long idReserva, Cliente cliente, Mesa mesa) {
        this.idReserva = idReserva;
        this.cliente = cliente;
        this.mesa = mesa;
        //this.dataReserva = dataReserva;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public ZonedDateTime getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(ZonedDateTime dataReserva) {
        this.dataReserva = dataReserva;
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
