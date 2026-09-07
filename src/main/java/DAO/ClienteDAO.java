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

            String sql = "INSERT INTO cliente (nome, sobrenome) values (?, ?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());

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
                clientes.add(new Cliente(id, nome, sobrenome));
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
                Cliente clienteEncontrado = new Cliente(id_cliente, nome, sobrenome);
                return clienteEncontrado;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void alterarDadosCliente(Long id, String novoNome, String novoSobrenome){
        try {
            Connection conexao = dataBaseConnection.conexao();

            String sql = "UPDATE cliente SET nome = ?, sobrenome = ? WHERE id_cliente = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, novoNome);
            stmt.setString(2, novoSobrenome);
            stmt.setLong(3, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
