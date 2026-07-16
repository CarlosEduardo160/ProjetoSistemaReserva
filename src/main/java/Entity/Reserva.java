package Entity;

public class Reserva {
    private Long idReserva;
    private Cliente cliente;
    private Mesa mesa;

    public Reserva(Long idReserva, Cliente cliente, Mesa mesa) {
        this.idReserva = idReserva;
        this.cliente = cliente;
        this.mesa = mesa;
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
}
