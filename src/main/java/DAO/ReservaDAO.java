package DAO;

import Connection.DataBaseConnection;
import Entity.Cliente;
import Entity.Mesa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class ReservaDAO {
    private final DataBaseConnection dataBaseConnection;

    public ReservaDAO(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    //ZonedDateTime horarioReserva - removido dos parametros para teste
    public void fazerReserva(Cliente cliente, Mesa mesa){
        try {
            Connection conexao = dataBaseConnection.conexao();

            //data_reserva - removido dos parametros para teste
            String sql = "INSERT INTO reserva (id_cliente, id_mesa) values (?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setLong(1, cliente.getIdCliente());
            stmt.setLong(2, mesa.getIdMesa());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
