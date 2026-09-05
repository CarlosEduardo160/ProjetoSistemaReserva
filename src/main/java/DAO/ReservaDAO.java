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

    public void fazerReserva(Cliente cliente, Mesa mesa, LocalDateTime horarioReserva){
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

//    public List<Reserva> listarReservas(){
//        List<Reserva> reservas = new ArrayList<>();
//
//        try {
//            Connection conexao = dataBaseConnection.conexao();
//
//            String sql = "SELECT * FROM reserva";
//            PreparedStatement stmt = conexao.prepareStatement(sql);
//
//            ResultSet rs = stmt.executeQuery();
//
//            while(rs.next()){
//                Long id = rs.getLong("id_reserva");
//                Long id_cliente = rs.getLong("id_cliente");
//                Long id_mesa = rs.getLong("id_mesa");
//                LocalDateTime data_reserva = rs.getObject("data_reserva", LocalDateTime.class);
//                reservas.add(new Reserva(id, id_cliente, id_mesa, data_reserva));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return  reservas;
//    }
}
