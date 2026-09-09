package DAO;

import Connection.DataBaseConnection;
import Entity.Cliente;
import Entity.Mesa;
import Entity.Reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private final DataBaseConnection dataBaseConnection;

    public ReservaDAO(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    public void fazerReserva(Cliente cliente, Mesa mesa, LocalDateTime horarioReserva) {
        try {
            Connection conexao = dataBaseConnection.conexao();

            String sql = "INSERT INTO reserva (id_cliente, id_mesa, data_reserva) values (?, ?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setLong(1, cliente.getIdCliente());
            stmt.setLong(2, mesa.getIdMesa());
            stmt.setObject(3, horarioReserva);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

     public List<Reserva> listarReservas(){
        List<Reserva> reservas = new ArrayList<>();

        try {
            Connection conexao = dataBaseConnection.conexao();

            String sql = "SELECT r.id_reserva, r.data_reserva, c.id_cliente, c.nome, c.sobrenome, m.id_mesa, m.numero_mesa, m.capacidade " +
                    "FROM reserva r " +
                    "JOIN cliente c ON r.id_cliente = c.id_cliente " +
                    "JOIN mesa m ON r.id_mesa = m.id_mesa";

            PreparedStatement stmt = conexao.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Long id_reserva = rs.getLong("id_reserva");
                LocalDateTime data_reserva = rs.getObject("data_reserva", LocalDateTime.class);

                Long id_cliente = rs.getLong("id_cliente");
                String nome = rs.getString("nome");
                String sobrenome = rs.getString("sobrenome");

                Long id_mesa = rs.getLong("id_mesa");
                String numero_mesa = rs.getString("numero_mesa");
                Integer capacidade = rs.getInt("capacidade");

                Cliente cliente  = new Cliente(id_cliente, nome, sobrenome);
                Mesa mesa  = new Mesa(id_mesa, numero_mesa, capacidade);
                reservas.add(new Reserva(id_reserva, cliente, mesa, data_reserva));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservas;
    }

    public Reserva buscarReservaPorId(Long id){
        try {
            Connection conexao = dataBaseConnection.conexao();

            String sql = "SELECT r.id_reserva, r.data_reserva, c.id_cliente, c.nome, c.sobrenome, m.id_mesa, m.numero_mesa, m.capacidade " +
                    "FROM reserva r " +
                    "JOIN cliente c ON r.id_cliente = c.id_cliente " +
                    "JOIN mesa m ON r.id_mesa = m.id_mesa WHERE id_reserva = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Long id_reserva = rs.getLong("id_reserva");
                LocalDateTime data_reserva = rs.getObject("data_reserva", LocalDateTime.class);

                Long id_cliente = rs.getLong("id_cliente");
                String nome = rs.getString("nome");
                String sobrenome = rs.getString("sobrenome");

                Long id_mesa = rs.getLong("id_mesa");
                String numero_mesa = rs.getString("numero_mesa");
                Integer capacidade = rs.getInt("capacidade");

                Cliente cliente  = new Cliente(id_cliente, nome, sobrenome);
                Mesa mesa  = new Mesa(id_mesa, numero_mesa, capacidade);
                return new Reserva(id_reserva, cliente, mesa, data_reserva);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Reserva> buscarReservaPorMesa(Long id){
        List<Reserva> reservas = new ArrayList<>();
        try {
            Connection conexao = dataBaseConnection.conexao();

            String sql = "SELECT r.id_reserva, r.data_reserva, c.id_cliente, c.nome, c.sobrenome, m.id_mesa, m.numero_mesa, m.capacidade " +
                    "FROM reserva r " +
                    "JOIN cliente c ON r.id_cliente = c.id_cliente " +
                    "JOIN mesa m ON r.id_mesa = m.id_mesa WHERE r.id_mesa = ?";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Long id_reserva = rs.getLong("id_reserva");
                LocalDateTime data_reserva = rs.getObject("data_reserva", LocalDateTime.class);

                Long id_cliente = rs.getLong("id_cliente");
                String nome = rs.getString("nome");
                String sobrenome = rs.getString("sobrenome");

                Long id_mesa = rs.getLong("id_mesa");
                String numero_mesa = rs.getString("numero_mesa");
                Integer capacidade = rs.getInt("capacidade");

                Cliente cliente  = new Cliente(id_cliente, nome, sobrenome);
                Mesa mesa  = new Mesa(id_mesa, numero_mesa, capacidade);
                reservas.add(new Reserva(id_reserva, cliente, mesa, data_reserva));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservas;
    }

    public void excluirReserva(Reserva reserva){
        try {
            Connection conexao = dataBaseConnection.conexao();

            String sql = "DELETE FROM reserva WHERE id_reserva = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setLong(1, reserva.getIdReserva());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
