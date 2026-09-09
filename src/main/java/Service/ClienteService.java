package Service;

import DAO.ClienteDAO;
import Entity.Cliente;

import java.util.List;

public class ClienteService {
    private final ClienteDAO clienteDAO;

    public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void criarCliente(String nome, String sobrenome){
        String regexNome = "^[a-zA-ZÀ-ÿ\\s]+$";

        if(nome.matches(regexNome) && sobrenome.matches(regexNome)) {
            Cliente novoCliente = new Cliente(nome, sobrenome);
            clienteDAO.cadastrarCliente(novoCliente);
        } else {
            throw  new IllegalArgumentException("Nome inválido, use apenas letras.");
        }
    }

    public List<Cliente> listarTodosClientes(){
        return clienteDAO.listarTodosClientes();
    }

    public Cliente buscarClientePorId(Long id){
        Cliente clienteEncontrado = clienteDAO.buscarClientePorId(id);

        if(clienteEncontrado == null){
            throw new IllegalArgumentException("Cliente não encontrado ou registrado.");
        }
        return clienteEncontrado;
    }

    public void alterarDadosCliente(Long id, String novoNome, String novoSobrenome){
        String regexNome = "^[a-zA-ZÀ-ÿ\\s]+$";

        if(novoNome.matches(regexNome) && novoSobrenome.matches(regexNome)){
            clienteDAO.alterarDadosCliente(id, novoNome, novoSobrenome);
        } else {
            throw new IllegalArgumentException("Nome inválido, use apenas letras.");
        }
    }

    public void excluirCliente(Cliente cliente){
        clienteDAO.excluirCliente(cliente);
    }
}
