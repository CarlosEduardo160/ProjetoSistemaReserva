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
    private final ClienteDAO clienteDAO;
    private final MesaDAO mesaDAO;

    public ReservaDAO(DataBaseConnection dataBaseConnection, ClienteDAO clienteDAO, MesaDAO mesaDAO) {
        this.dataBaseConnection = dataBaseConnection;
        this.clienteDAO = clienteDAO;
        this.mesaDAO = mesaDAO;
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

            String sql = "SELECT * FROM cliente c LEFT JOIN reserva r ON c.id_cliente = r.id_reserva ";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id_reserva");
                Long id_cliente = rs.getLong("id_cliente");
                Long id_mesa = rs.getLong("id_mesa");
                LocalDateTime data_reserva = rs.getObject("data_reserva", LocalDateTime.class);

                Cliente cliente = clienteDAO.buscarClientePorId(id_cliente);
                Mesa mesa = mesaDAO.buscarMesaPorId(id_mesa);
                reservas.add(new Reserva(id, cliente, mesa, data_reserva));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  reservas;
    }

    public Reserva buscarReservaPorId(Long id){
        try {
            Connection conexao = dataBaseConnection.conexao();

            String sql = "SELECT * FROM reserva WHERE id_reserva = (?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Long id_reserva = rs.getLong("id_reserva");
                Long id_cliente = rs.getLong("id_cliente");
                Long id_mesa = rs.getLong("id_mesa");
                LocalDateTime data_reserva = rs.getObject("data_reserva", LocalDateTime.class);

                Cliente cliente = clienteDAO.buscarClientePorId(id_cliente);
                Mesa mesa = mesaDAO.buscarMesaPorId(id_mesa);
                Reserva reservaEncontrada = new Reserva(id_reserva, cliente, mesa, data_reserva);
                return reservaEncontrada;
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

            String sql = "SELECT * FROM reserva WHERE id_mesa = (?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Long id_reserva = rs.getLong("id_reserva");
                Long id_cliente = rs.getLong("id_cliente");
                Long id_mesa = rs.getLong("id_mesa");
                LocalDateTime data_reserva = rs.getObject("data_reserva", LocalDateTime.class);

                Cliente cliente = clienteDAO.buscarClientePorId(id_cliente);
                Mesa mesa = mesaDAO.buscarMesaPorId(id_mesa);
                reservas.add(new Reserva(id_reserva, cliente, mesa, data_reserva));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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
