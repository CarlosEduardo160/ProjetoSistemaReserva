package Service;

import DAO.ReservaDAO;
import Entity.Cliente;
import Entity.Mesa;
import Entity.Reserva;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaService {
    private final ReservaDAO reservaDAO;
    private final ClienteService clienteService;
    private final MesaService mesaService;

    public ReservaService(ReservaDAO reservaDAO, ClienteService clienteService, MesaService mesaService) {
        this.reservaDAO = reservaDAO;
        this.clienteService = clienteService;
        this.mesaService = mesaService;
    }

    public void fazerReserva(Long idCliente, Long idMesa, int qntPessoas, LocalDateTime horarioReserva){
        Cliente clienteEncontrado = clienteService.buscarClientePorId(idCliente);
        Mesa mesaEncontrada = mesaService.buscarMesaPorId(idMesa);
        List<Reserva> reservasExistentes = reservaDAO.buscarReservaPorMesa(idMesa);

        validarHorarios(horarioReserva);

        if(!mesaDisponivel(horarioReserva, reservasExistentes)){
            throw new IllegalArgumentException("Mesa já reservada neste horário.");
        }

        if (mesaEncontrada.getCapacidade() - qntPessoas < 0 ) {
            throw new IllegalArgumentException("Capacidade da mesa é insuficiente.");
        }

        reservaDAO.fazerReserva(clienteEncontrado, mesaEncontrada, horarioReserva);
    }

    public List<Reserva> listarReservas(){
        List<Reserva> reservas = reservaDAO.listarReservas();

        if(reservas.isEmpty()){
            throw new RuntimeException("Nenhuma reserva cadastrada.");
        }
        return reservas;
    }

    public Reserva buscarReservaPorId(Long id){
        Reserva reservaEncontrada = reservaDAO.buscarReservaPorId(id);

        if(reservaEncontrada == null){
            throw  new IllegalArgumentException("Reserva não encontrada ou registrada.");
        }
        return reservaEncontrada;
    }

    public void validarHorarios(LocalDateTime horarioReserva) {
        LocalTime horarioAbertura = LocalTime.of(10, 0, 0);
        LocalTime horarioFechamento = LocalTime.of(21, 0, 0);

        if (horarioReserva.toLocalTime().isBefore(horarioAbertura)) {
            throw new IllegalArgumentException("Fora do horário de funcionamento.");
        }

        LocalTime reservaConvertida = horarioReserva.plusHours(1).toLocalTime();
        boolean verifica = reservaConvertida.isAfter(horarioFechamento);
        if (verifica) {
            throw new IllegalArgumentException("A reserva deve ser marcada em até 1h antes do horário de fechamento.");
        }
    }

    public boolean mesaDisponivel(LocalDateTime inicioNovaReserva, List<Reserva> reservaExistentes){
        LocalDateTime fimNovaReserva = inicioNovaReserva.plusHours(2);

        for (Reserva reserva : reservaExistentes){
            LocalDateTime inicioReservaExistente = reserva.getDataReserva();
            LocalDateTime fimReservaExistente = inicioReservaExistente.plusHours(2);

            boolean semConflito = fimReservaExistente.isBefore(inicioNovaReserva) || fimNovaReserva.isBefore(inicioReservaExistente);

            if(!semConflito){
                return false;
            }
        }
        return true;
    }
}
