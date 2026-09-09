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

    Scanner entrada = new Scanner(System.in);
    //Scanner entradaNumero = new Scanner(System.in);

    public void iniciar(){
        boolean rodando = true;

        while(rodando){
            menuPrincipal();
            int opcao = entrada.nextInt();
            entrada.nextLine();

            switch (opcao) {
                case 1:
                    int opcaoCliente;
                    do {
                        menuClientes();
                        opcaoCliente = entrada.nextInt();
                        entrada.nextLine();

                        switch (opcaoCliente) {
                            case 1 -> cadastroCliente();
                            case 2 -> listarTodosClientes();
                            case 3 -> buscarClientePorId();
                            case 4 -> alterarDadosCliente();
                            case 5 -> excluirCliente();
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
                        opcaoMesa = entrada.nextInt();
                        entrada.nextLine();
                        switch (opcaoMesa){
                            case 1 -> registrarMesa();
                            case 2 -> listarTodasAsMesas();
                            case 3 -> buscarMesaPorId();
                            case 4 -> alterarDadosMesa();
                            case 5 -> excluirMesa();
                            case 0 ->{
                                System.out.println("--Voltando...");
                            }
                            default -> System.out.println("--Opção inválida.");
                        }
                    } while(opcaoMesa != 0);
                    break;
                }
                case 3: {
                    int opcaoReserva;
                    do{
                        menuReservas();
                        opcaoReserva = entrada.nextInt();
                        entrada.nextLine();
                        switch (opcaoReserva){
                            case 1 -> registrarReserva();
                            case 2 -> listarTodasAsReservas();
                            case 3 -> buscarReservaPorId();
                            case 4 -> excluirReserva();
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
                [4] - Alterar Dados do Cliente
                [5] - Excluir Cliente
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
                [4] - Alterar Dados da Mesa
                [5] - Excluir Mesa
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
                [4] - Excluir Reserva
                [0] - Voltar
                ==============================================
                
                Escolha uma opção:
                """;
        System.out.print(menuReservas);
    }

    //Cliente
    public void cadastroCliente(){
        try {
            System.out.print("Digite o nome: ");
            String nome = entrada.nextLine();

            System.out.print("Digite o sobrenome: ");
            String sobrenome = entrada.nextLine();

            clienteService.criarCliente(nome, sobrenome);
            System.out.println("--Cliente cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("--" + e );
        } catch (RuntimeException e){
            System.out.println("Erro ao acessar o banco de dados: " + e);
        }
    }

    public void listarTodosClientes(){
        try {
            List<Cliente> clientes = clienteService.listarTodosClientes();
            if(clientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado.");
                return;
            }

            for(Cliente cliente : clientes){
                System.out.println(cliente);
            }
        } catch (Exception e) {
            System.out.println("--" + e.getMessage());
        }

    }

    public void buscarClientePorId(){
        System.out.print("Digite o ID do cliente: ");
        Long id = entrada.nextLong();
        entrada.nextLine();

        try{
            Cliente clienteEncontrado = clienteService.buscarClientePorId(id);
            System.out.println(clienteEncontrado);
        } catch (IllegalArgumentException e) {
            System.out.println("--" + e.getMessage());
        } catch (RuntimeException e){
            System.out.println("Erro ao acessar o banco de dados: " + e);
        }
    }

    public void alterarDadosCliente(){
        System.out.print("Digite o ID do cliente que deseja atualizar: ");
        Long id = entrada.nextLong();
        entrada.nextLine();

        try{
            clienteService.buscarClientePorId(id);

            System.out.print("Digite o novo nome do cliente: ");
            String novoNome = entrada.nextLine();

            System.out.print("Digite o novo sobrenome do cliente: ");
            String novoSobrenome = entrada.nextLine();

            clienteService.alterarDadosCliente(id, novoNome, novoSobrenome);
            System.out.println("--Dados alterados com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("--" + e.getMessage());
        } catch (RuntimeException e){
            System.out.println("Erro ao acessar o banco de dados: " + e);
        }
    }

    public void excluirCliente(){
        System.out.print("Digite o ID do cliente que deseja excluir: ");
        Long id = entrada.nextLong();
        entrada.nextLine();

        try {
            Cliente clienteEncontrado = clienteService.buscarClientePorId(id);
            clienteService.excluirCliente(clienteEncontrado);
            System.out.println("--Cliente excluído com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("--" + e.getMessage());
        } catch (RuntimeException e){
            System.out.println("Erro ao acessar o banco de dados: " + e);
        }
    }

    //Mesa
    public void registrarMesa(){
        try {
            System.out.print("Digite o numero da mesa: ");
            String numeroMesa = entrada.nextLine();

            System.out.print("Digite a capacidade: ");
            Integer capacidade = entrada.nextInt();
            entrada.nextLine();

            mesaService.registrarMesa(numeroMesa, capacidade);
            System.out.println("--Mesa registrada com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("--" + e.getMessage());
        }
    }

    public void listarTodasAsMesas(){
        try {
            List<Mesa> mesas = mesaService.listarTodasAsMesas();
            if (mesas.isEmpty()){
                System.out.println("Nenhuma mesa encontrada.");
                return;
            }

            for(Mesa mesa : mesas){
                System.out.println(mesa);
            }
        } catch (Exception e) {
            System.out.println("--" + e.getMessage());
        }
    }

    public void buscarMesaPorId(){
        System.out.print("Digite o ID da mesa: ");
        Long idMesa = entrada.nextLong();
        entrada.nextLine();
        try{
            Mesa mesaEncontrada = mesaService.buscarMesaPorId(idMesa);
            System.out.println(mesaEncontrada);
        } catch (IllegalArgumentException e) {
            System.out.println("--" + e.getMessage());
        } catch (RuntimeException e){
            System.out.println("Erro ao acessar o banco de dados: " + e);
        }
    }

    public void alterarDadosMesa(){
        System.out.print("Digite o ID da mesa que deseja alterar: ");
        Long id = entrada.nextLong();
        entrada.nextLine();

        try{
            mesaService.buscarMesaPorId(id);

            System.out.print("Digite o novo numero da mesa: ");
            String novoNumero = entrada.nextLine();

            System.out.print("Digite a nova capacidade da mesa: ");
            int novaCapacidade = entrada.nextInt();
            entrada.nextLine();

            mesaService.alterarDadosMesa(id, novoNumero, novaCapacidade);
            System.out.println("--Dados alterados com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("--" + e.getMessage());
        } catch (RuntimeException e){
            System.out.println("Erro ao acessar o banco de dados: " + e);
        }
    }

    public void excluirMesa(){
        System.out.print("Digite o ID da mesa que deseja excluir: ");
        Long id = entrada.nextLong();
        entrada.nextLine();

        try {
            Mesa mesaEncontrada = mesaService.buscarMesaPorId(id);
            mesaService.excluirMesa(mesaEncontrada);
            System.out.println("--Mesa excluída com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("--" + e.getMessage());
        } catch (RuntimeException e){
            System.out.println("Erro ao acessar o banco de dados: " + e);
        }
    }

    //Reserva

    public void registrarReserva(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataReserva;
        try {
            System.out.print("Digite o ID do cliente: ");
            Long idCliente = entrada.nextLong();
            entrada.nextLine();

            System.out.print("Digite o ID da mesa: ");
            Long idMesa = entrada.nextLong();
            entrada.nextLine();

            System.out.print("Quantidade de pessoas que ocuparão a mesa: ");
            int qntPessoa = entrada.nextInt();
            entrada.nextLine();

            System.out.print("Digite a data da sua reserva (dd/MM/aaaa HH:mm): ");
            String reserva = entrada.nextLine();
            dataReserva = LocalDateTime.parse(reserva, formatter);

            reservaService.fazerReserva(idCliente, idMesa, qntPessoa, dataReserva);
            System.out.println("--Reserva registrada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("--" + e.getMessage());
        } catch (RuntimeException e){
            System.out.println("Erro ao acessar o banco de dados: " + e);
        }
    }

    public void listarTodasAsReservas(){
        try {
            List<Reserva> reservas = reservaService.listarReservas();
            if (reservas.isEmpty()){
                System.out.println("Nenhuma reserva encontrada.");
                return;
            }

            for(Reserva reserva : reservas){
                System.out.println(reserva);
            }
        } catch (RuntimeException e) {
            System.out.println("--" + e.getMessage());
        }
    }

    public void buscarReservaPorId(){
        System.out.print("Digite o ID da reserva: ");
        Long idReserva = entrada.nextLong();
        entrada.nextLine();

        try {
            Reserva reservaEncontrada = reservaService.buscarReservaPorId(idReserva);
            System.out.println(reservaEncontrada);
        } catch (IllegalArgumentException e) {
            System.out.println("--" + e.getMessage());
        } catch (RuntimeException e){
            System.out.println("Erro ao acessar o banco de dados: " + e);
        }
    }

    public void excluirReserva(){
        System.out.print("Digite o ID da reserva que deseja excluir: ");
        Long id = entrada.nextLong();
        entrada.nextLine();

        try {
            Reserva reservaEncontrada = reservaService.buscarReservaPorId(id);
            reservaService.excluirReserva(reservaEncontrada);
            System.out.println("--Reserva excluída com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("--" + e.getMessage());
        } catch (RuntimeException e){
            System.out.println("Erro ao acessar o banco de dados: " + e);
        }
    }
}
