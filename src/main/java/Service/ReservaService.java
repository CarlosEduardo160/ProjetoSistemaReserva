package Service;

import DAO.ReservaDAO;
import Entity.Cliente;
import Entity.Mesa;

import java.time.ZonedDateTime;

public class ReservaService {
    private ReservaDAO reservaDAO;
    private ClienteService clienteService;
    private MesaService mesaService;

    public ReservaService(ReservaDAO reservaDAO, ClienteService clienteService, MesaService mesaService) {
        this.reservaDAO = reservaDAO;
        this.clienteService = clienteService;
        this.mesaService = mesaService;
    }

    //ZonedDateTime horarioReserva - removido dos parametros para teste
    public void fazerReserva(Long idCliente, Long idMesa, int qntPessoas){
        Cliente clienteEncontrado = clienteService.buscarClientePorId(idCliente);
        Mesa mesaEncontrada = mesaService.buscarMesaPorId(idMesa);

        if(mesaEncontrada.getCapacidade() - qntPessoas < 0 || clienteEncontrado == null || mesaEncontrada == null){
            throw new IllegalArgumentException("Capacidade da mesa insuficiente");
        } else {
            //horarioReserva - removido dos parametros para teste
            reservaDAO.fazerReserva(clienteEncontrado, mesaEncontrada);
        }
    }
}
