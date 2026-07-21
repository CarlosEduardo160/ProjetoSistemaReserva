package DAO;

import Connection.DataBaseConnection;
import Entity.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private final DataBaseConnection dataBaseConnection;

    public ClienteDAO(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    public void cadastrarCliente(Cliente cliente){
        try {
            Connection conexao = dataBaseConnection.conexao();

            String sql = "INSERT INTO cliente (nome, sobrenome, cpf) values (?, ?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setString(3, cliente.getCpf());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cliente> listarTodosClientes(){
        List<Cliente> clientes = new ArrayList<>();
        try {
            Connection conexao = dataBaseConnection.conexao();

            String sql = "SELECT * FROM cliente";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Long id = rs.getLong("id_cliente");
                String nome = rs.getString("nome");
                String sobrenome = rs.getString("sobrenome");
                String cpf = rs.getString("cpf");
                clientes.add(new Cliente(id, nome, sobrenome, cpf));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientes;
    }

    public Cliente buscarClientePorId(Long id){
        try {
            Connection conexao = dataBaseConnection.conexao();

            String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Long id_cliente = rs.getLong("id_cliente");
                String nome = rs.getString("nome");
                String sobrenome = rs.getString("sobrenome");
                String cpf = rs.getString("cpf");
                Cliente clienteEncontrado = new Cliente(id_cliente, nome, sobrenome, cpf);
                return clienteEncontrado;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void alterarDadosCliente(Long id, String novoNome, String novoSobrenome, String novoCpf){
        try {
            Connection conexao = dataBaseConnection.conexao();

            String sql = "UPDATE cliente SET nome = ?, sobrenome = ?, cpf = ? WHERE id_cliente = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, novoNome);
            stmt.setString(2, novoSobrenome);
            stmt.setString(3, novoCpf);
            stmt.setLong(4, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
