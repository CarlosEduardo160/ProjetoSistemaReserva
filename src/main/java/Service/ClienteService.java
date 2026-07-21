package Service;

import DAO.ClienteDAO;
import Entity.Cliente;

import java.util.List;

public class ClienteService {
    private ClienteDAO clienteDAO;

    public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void criarCliente(String nome, String sobrenome, String cpf){
        Cliente novoCliente = new Cliente(nome, sobrenome, cpf);
        clienteDAO.cadastrarCliente(novoCliente);
    }

    public List<Cliente> listarTodosClientes(){
        List<Cliente> clientes = clienteDAO.listarTodosClientes();

        if(clientes.isEmpty()){
            throw new RuntimeException("Nenhum cliente cadastrado");
        }
        return clientes;
    }

    public Cliente buscarClientePorId(Long id){
        Cliente clienteEncontrado = clienteDAO.buscarClientePorId(id);

        if(clienteEncontrado == null){
            throw new IllegalArgumentException("Cliente não encontrado ou registrado.");
        }
        return clienteEncontrado;
    }

    public void alterarDadosCliente(Long id, String novoNome, String novoSobrenome, String novoCpf){
        clienteDAO.alterarDadosCliente(id, novoNome, novoSobrenome, novoCpf);
    }
}
