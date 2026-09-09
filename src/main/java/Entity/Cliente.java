package Entity;

public class Cliente {
    private Long idCliente;
    private String nome;
    private String sobrenome;

    public Cliente(Long idCliente, String nome, String sobrenome) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    public Cliente(String nome, String sobrenome) {
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                '}';
    }
}
