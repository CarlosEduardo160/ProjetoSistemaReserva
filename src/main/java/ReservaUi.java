import Entity.Cliente;
import Service.ClienteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservaUi {
    private ClienteService clienteService;

    public ReservaUi(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    Scanner entradaTexto = new Scanner(System.in);
    Scanner entradaNumero = new Scanner(System.in);

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
}
