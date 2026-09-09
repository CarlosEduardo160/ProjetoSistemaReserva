package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static DataBaseConnection singleton;
    private final Connection conexao;

    private DataBaseConnection(){
        try {
            conexao = DriverManager.getConnection("","", ""); //conecte o seu banco de dados aqui
        } catch (SQLException ex) {
            throw new RuntimeException("Erro de conexão com banco de dados");
        }
    }

    public static DataBaseConnection getInstance(){
        if(singleton == null){
            singleton = new DataBaseConnection();
        }
        return singleton;
    }

    public Connection conexao(){
        return conexao;
    }
}
