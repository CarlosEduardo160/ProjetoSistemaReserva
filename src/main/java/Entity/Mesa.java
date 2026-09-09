package Entity;

public class Mesa {
    private Long idMesa;
    private String numeroMesa;
    private Integer capacidade;

    public Mesa(Long idMesa, String numeroMesa, Integer capacidade) {
        this.idMesa = idMesa;
        this.numeroMesa = numeroMesa;
        this.capacidade = capacidade;
    }

    public Mesa(String numeroMesa, Integer capacidade) {
        this.capacidade = capacidade;
        this.numeroMesa = numeroMesa;
    }

    public Long getIdMesa() {
        return idMesa;
    }

    public String getNumeroMesa() {
        return numeroMesa;
    }


    public Integer getCapacidade() {
        return capacidade;
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "idMesa=" + idMesa +
                ", numeroMesa='" + numeroMesa + '\'' +
                ", capacidade=" + capacidade +
                '}';
    }
}
