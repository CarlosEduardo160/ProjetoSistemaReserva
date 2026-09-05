package Service;

import DAO.ReservaDAO;
import Entity.Cliente;
import Entity.Mesa;
import Entity.Reserva;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ReservaService {
    private ReservaDAO reservaDAO;
    private ClienteService clienteService;
    private MesaService mesaService;

    public ReservaService(ReservaDAO reservaDAO, ClienteService clienteService, MesaService mesaService) {
        this.reservaDAO = reservaDAO;
        this.clienteService = clienteService;
        this.mesaService = mesaService;
    }

    public void fazerReserva(Long idCliente, Long idMesa, int qntPessoas, LocalDateTime horarioReserva){
        Cliente clienteEncontrado = clienteService.buscarClientePorId(idCliente);
        Mesa mesaEncontrada = mesaService.buscarMesaPorId(idMesa);
        validarHorarios(horarioReserva);

        if (mesaEncontrada.getCapacidade() - qntPessoas < 0 ) {
            throw new IllegalArgumentException("Capacidade da mesa é insuficiente");
        } else {
            reservaDAO.fazerReserva(clienteEncontrado, mesaEncontrada, horarioReserva);
        }
    }

//    public List<Reserva> listarReservas(){
//        List<Reserva> reservas = reservaDAO.listarReservas();
//
//        if(reservas.isEmpty()){
//            throw new RuntimeException("Nenhuma reserva cadastrada");
//        }
//        return reservas;
//    }
//
//    public void buscarReservaPorId(){
//
//    }

    public void validarHorarios(LocalDateTime horarioReserva){
        LocalTime horarioAbertura = LocalTime.of(10,0,0);
        LocalTime horarioFechamento = LocalTime.of(21,0,0);

        if(horarioReserva.toLocalTime().isBefore(horarioAbertura)){
            throw new IllegalArgumentException("Fora do horário de funcionamento");
        }

        LocalTime reservaConvertida = horarioReserva.plusHours(1).toLocalTime();
        boolean verifica = reservaConvertida.isAfter(horarioFechamento);
        if(verifica){
            throw new IllegalArgumentException("A reserva deve ser marcada em até 1h antes do horário de fechamento");
        }
    }
}
