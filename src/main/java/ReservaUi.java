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

    public void iniciar(){
        boolean rodando = true;

        while(rodando){
            menuPrincipal();
            int opcao = entradaNumero.nextInt();

            switch (opcao) {
                case 1:
                    int opcaoCliente;
                    do {
                        menuClientes();
                        opcaoCliente = entradaNumero.nextInt();

                        switch (opcaoCliente) {
                            case 1 -> cadastroCliente();
                            case 2 -> listarTodosClientes();
                            case 3 -> buscarClientePorId();
                            case 0 ->{
                                System.out.println("--Voltando...");
                            }
                            default -> System.out.println("--Opção inválida.");
                        }
                    } while (opcaoCliente != 0);
                    break;

                case 2: {
                    int opcaoMesa;
                    do {
                        menuMesas();
                        opcaoMesa    = entradaNumero.nextInt();
                        switch (opcaoMesa){
                            case 1 -> registrarMesa();
                            case 2 -> listarTodasAsMesas();
                            case 3 -> buscarMesaPorId();
                            case 0 ->{
                                System.out.println("--Voltando...");
                            }
                            default -> System.out.println("--Opção inválida.");
                        }
                    } while(opcaoMesa    != 0);
                    break;
                }
                case 3: {
                    int opcaoReserva;
                    do{
                        menuReservas();
                        opcaoReserva = entradaNumero.nextInt();
                        switch (opcaoReserva){
                            case 1 -> registrarReserva();
                            case 2 -> listarTodasAsReservas();
                            case 3 -> buscarReservaPorId();
                            case 0 -> {
                                System.out.println("--Voltando...");
                            }
                            default -> System.out.println("--Opção inválida.");
                        }
                    } while (opcaoReserva != 0);
                    break;
                }
                case 0: {
                    System.out.println("--Encerrando.");
                    rodando = false;
                    break;
                }
                default:
                    System.out.println("--Opção inválida.");
            }
        }
    }

    private void menuPrincipal(){
        String menuPrincipal = """
                
                ==============Agendamento de Reservas==============
                [1] - Clientes
                [2] - Mesas
                [3] - Reservas
                [0] - Encerrar sistema
                ==============================================
                
                Escolha uma opção:
                """;
        System.out.print(menuPrincipal);
    }

    private void menuClientes(){
        String menuCliente = """
                ==============Clientes==============
                [1] - Cadastrar cliente
                [2] - Listar clientes
                [3] - Buscar cliente
                [0] - Voltar
                ==============================================
                
                Escolha uma opção:
                """;
        System.out.print(menuCliente);
    }

    private void menuMesas(){
        String menuMesas = """
                ==============Mesas==============
                [1] - Registrar Mesa
                [2] - Listar Mesas
                [3] - Buscar Mesa
                [0] - Voltar
                ==============================================
                
                Escolha uma opção:
                """;
        System.out.print(menuMesas);
    }

    private void menuReservas(){
        String menuReservas = """
                ==============Reservas==============
                [1] - Fazer reserva
                [2] - Listar reservas
                [3] - Buscar reserva
                [0] - Voltar
                ==============================================
                
                Escolha uma opção:
                """;
        System.out.print(menuReservas);
    }

    //Cliente
    public void cadastroCliente(){
        System.out.print("Digite o nome: ");
        String nome = entradaTexto.nextLine();

        System.out.print("Digite o sobrenome: ");
        String sobrenome = entradaTexto.nextLine();

        System.out.print("Digite o cpf: ");
        String cpf = entradaTexto.nextLine();

        clienteService.criarCliente(nome, sobrenome);
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

            clienteService.alterarDadosCliente(id, novoNome, novoSobrenome);
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

    public void listarTodasAsReservas(){
        try{
            List<Reserva> reservas = reservaService.listarReservas();
            for(Reserva reserva : reservas){
                System.out.println(reserva);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("--" + e.getMessage());
        }
    }

    public void buscarReservaPorId(){
        System.out.print("Digite o ID da reserva: ");
        Long idReserva = entradaNumero.nextLong();

        try {
            Reserva reservaEncontrada = reservaService.buscarReservaPorId(idReserva);
            System.out.println(reservaEncontrada);
        } catch (Exception e) {
            System.out.println("--" + e.getMessage());
        }
    }
}
