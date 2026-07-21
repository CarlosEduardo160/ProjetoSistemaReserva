import Connection.DataBaseConnection;
import DAO.ClienteDAO;
import Service.ClienteService;

public class Main {
    public static void main(String[] args) {
        DataBaseConnection dataBaseConnection = DataBaseConnection.getInstance();

        ClienteDAO clienteDAO = new ClienteDAO(dataBaseConnection);
        ClienteService clienteService = new ClienteService(clienteDAO);

        ReservaUi sistema = new ReservaUi(clienteService);
        sistema.alterarDadosCliente();
    }
}








//        Connection conexao = DataBaseConnection.getInstance().conexao();
//
//        List<Cliente> usuarios = new ArrayList<>();
//        try {
//            PreparedStatement pstm = conexao.prepareStatement("SELECT * FROM cliente");
//            ResultSet resultSet = pstm.executeQuery();
//            while(resultSet.next()){
//                Long idCliente = resultSet.getLong(1);
//                String nome = resultSet.getString("nome");
//                String sobrenome = resultSet.getString("sobrenome");
//                String cpf = resultSet.getString("cpf");
//                usuarios.add(new Cliente(idCliente, nome, sobrenome, cpf));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        usuarios.forEach(cliente ->
//                System.out.println(cliente.getIdCliente() + " " + cliente.getNome()));
//    }
