import DAO.ReservaDAO;
import Entity.Cliente;
import Entity.Mesa;
import Entity.Reserva;
import Service.ClienteService;
import Service.MesaService;
import Service.ReservaService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ReservaUi {
    private final ClienteService clienteService;
    private final MesaService mesaService;
    private final ReservaService reservaService;

    public ReservaUi(ClienteService clienteService, MesaService mesaService, ReservaService reservaService) {
        this.clienteService = clienteService;
        this.mesaService = mesaService;
        this.reservaService = reservaService;
    }

    Scanner entradaTexto = new Scanner(System.in);
    Scanner entradaNumero = new Scanner(System.in);

    //Cliente
    public void cadastroCliente(){
        System.out.print("Digite o nome: ");
        String nome = entradaTexto.nextLine();

        System.out.print("Digite o sobrenome: ");
        String sobrenome = entradaTexto.nextLine();

        System.out.print("Digite o cpf: ");
        String cpf = entradaTexto.nextLine();

        clienteService.criarCliente(nome, sobrenome, cpf);
    }

    public void listarTodosClientes(){
        try{
            List<Cliente> clientes = clienteService.listarTodosClientes();
            for(Cliente cliente : clientes){
                System.out.println(cliente);
            }
        } catch (RuntimeException e) {
            System.out.println("--" + e.getMessage());
        }
    }

    public void buscarClientePorId(){
        System.out.print("Digite o ID do cliente: ");
        Long id = entradaNumero.nextLong();

        try{
            Cliente clienteEncontrado = clienteService.buscarClientePorId(id);
            System.out.println(clienteEncontrado);
        } catch (IllegalArgumentException e) {
            System.out.println("--" + e.getMessage());
        }
    }

    public void alterarDadosCliente(){
        System.out.print("Digite o ID do cliente que deseja atualizar: ");
        Long id = entradaNumero.nextLong();

        try{
            clienteService.buscarClientePorId(id);

            System.out.print("Digite o novo nome do cliente: ");
            String novoNome = entradaTexto.nextLine();

            System.out.print("Digite o novo sobrenome do cliente: ");
            String novoSobrenome = entradaTexto.nextLine();

            System.out.print("Digite o novo cpf do cliente: ");
            String novoCpf = entradaTexto.nextLine();

            clienteService.alterarDadosCliente(id, novoNome, novoSobrenome, novoCpf);
        } catch (IllegalArgumentException e) {
            System.out.println("--" + e.getMessage());
        }
    }

    //Mesa
    public void registrarMesa(){
        System.out.print("Digite o numero da mesa: ");
        String numeroMesa = entradaTexto.nextLine();

        System.out.print("Digite a capacidade: ");
        Integer capacidade = entradaNumero.nextInt();

        mesaService.registrarMesa(numeroMesa, capacidade);
    }

    public void listarTodasAsMesas(){
        try{
            List<Mesa> mesas = mesaService.listarTodasAsMesas();
            for(Mesa mesa : mesas){
                System.out.println(mesa);
            }
        } catch (RuntimeException e) {
            System.out.println("--" + e.getMessage());
        }
    }

    public void buscarMesaPorId(){
        System.out.print("Digite o ID da mesa: ");
        Long idMesa = entradaNumero.nextLong();
        try{
            Mesa mesaEncontrada = mesaService.buscarMesaPorId(idMesa);
            System.out.println(mesaEncontrada);
        } catch (IllegalArgumentException e) {
            System.out.println("--" + e.getMessage());
        }
    }

    public void buscarMesaPorReserva(){

    }

    //Reserva

    public void registrarReserva(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataReserva;
        try {
            System.out.print("Digite o ID do cliente: ");
            Long idCliente = entradaNumero.nextLong();

            System.out.print("Digite o ID da mesa: ");
            Long idMesa = entradaNumero.nextLong();

            System.out.print("Quantidade de pessoas que ocuparão a mesa: ");
            int qntPessoa = entradaNumero.nextInt();

            System.out.print("Digite a data da sua reserva (dd/MM/aaaa HH:mm): ");
            String reserva = entradaTexto.nextLine();
            dataReserva = LocalDateTime.parse(reserva, formatter);

            reservaService.fazerReserva(idCliente, idMesa, qntPessoa, dataReserva);
            System.out.println("--Sucesso papai");
        } catch (IllegalArgumentException e) {
            System.out.println("--" + e.getMessage());
        }
    }

//    public void listarTodasAsReservas(){
//        try{
//            List<Reserva> reservas = reservaService.listarReservas();
//            for(Reserva reserva : reservas){
//                System.out.println(reserva);
//            }
//        } catch (IllegalArgumentException e) {
//            System.out.println("--" + e.getMessage());
//        }
//    }
}
