package Entity;

public class Mesa {
    private Long idMesa;
    private Integer numeroMesa;
    private Integer capaciade;

    public Mesa(Long idMesa, Integer numeroMesa, Integer capaciade) {
        this.idMesa = idMesa;
        this.numeroMesa = numeroMesa;
        this.capaciade = capaciade;
    }

    public Long getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public Integer getCapaciade() {
        return capaciade;
    }

    public void setCapaciade(Integer capaciade) {
        this.capaciade = capaciade;
    }
}
