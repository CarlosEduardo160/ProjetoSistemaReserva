import Connection.DataBaseConnection;
import DAO.ClienteDAO;
import DAO.MesaDAO;
import DAO.ReservaDAO;
import Service.ClienteService;
import Service.MesaService;
import Service.ReservaService;

public class Main {
    public static void main(String[] args) {
        DataBaseConnection dataBaseConnection = DataBaseConnection.getInstance();

        ClienteDAO clienteDAO = new ClienteDAO(dataBaseConnection);
        ClienteService clienteService = new ClienteService(clienteDAO);
        MesaDAO mesaDAO = new MesaDAO(dataBaseConnection);
        MesaService mesaService = new MesaService(mesaDAO);
        ReservaDAO reservaDAO = new ReservaDAO(dataBaseConnection, clienteDAO, mesaDAO);
        ReservaService reservaService = new ReservaService(reservaDAO, clienteService, mesaService);

        ReservaUi sistema = new ReservaUi(clienteService, mesaService, reservaService);
        sistema.buscarReservaPorId();
    }
}