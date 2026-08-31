package DAO;

import Connection.DataBaseConnection;
import Entity.Cliente;
import Entity.Mesa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MesaDAO {
    private final DataBaseConnection dataBaseConnection;

    public MesaDAO(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    public void registrarMesa(Mesa mesa){
        try {
            Connection conexao = dataBaseConnection.conexao();

            String sql = "INSERT INTO mesa (numero_mesa, capacidade) values (?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, mesa.getNumeroMesa());
            stmt.setInt(2, mesa.getCapacidade());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Mesa> listarTodasAsMesas(){
        List<Mesa> mesas = new ArrayList<>();
        try {
            Connection conexao = dataBaseConnection.conexao();

            String sql = "SELECT * FROM mesa";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Long id = rs.getLong("id_mesa");
                String numero_mesa = rs.getString("numero_mesa");
                Integer capacidade = rs.getInt("capacidade");
                mesas.add(new Mesa(id, numero_mesa, capacidade));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mesas;
    }

    public Mesa buscarMesaPorId(Long idMesa){
        try {
            Connection conexao = dataBaseConnection.conexao();

            String sql = "SELECT * FROM mesa WHERE id_mesa = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setLong(1, idMesa);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Long id_mesa = rs.getLong("id_mesa");
                String numero_mesa = rs.getString("numero_mesa");
                Integer capacidade = rs.getInt("capacidade");
                Mesa mesaEncontrada = new Mesa(id_mesa, numero_mesa, capacidade);
                return mesaEncontrada;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void buscarMesaPorReserva(){

    }


}
