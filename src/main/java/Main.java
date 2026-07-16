import Connection.DataBaseConnection;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conexao = DataBaseConnection.getInstance().conexao();
    }
}
