package Service;

import DAO.MesaDAO;
import Entity.Mesa;

import java.util.List;

public class MesaService {
    private final MesaDAO mesaDAO;

    public MesaService(MesaDAO mesaDAO) {
        this.mesaDAO = mesaDAO;
    }

    public void registrarMesa(String numeroMesa, Integer capacidade){
        String regexMesa = "^[0-9]+$";

        if(numeroMesa.matches(regexMesa)) {
            Mesa novaMesa = new Mesa(numeroMesa, capacidade);
            mesaDAO.registrarMesa(novaMesa);
        } else {
            throw new IllegalArgumentException("Utilize apenas numeros.");
        }
    }

    public List<Mesa> listarTodasAsMesas(){
        return mesaDAO.listarTodasAsMesas();
    }

    public Mesa buscarMesaPorId(Long idMesa){
        Mesa mesaEncontrada = mesaDAO.buscarMesaPorId(idMesa);

        if(mesaEncontrada == null){
            throw new IllegalArgumentException("Mesa não encontrada ou cadastrada.");
        }
        return mesaEncontrada;
    }

    public void alterarDadosMesa(Long id, String novoNumero, int novaCapacidade){
        mesaDAO.alterarDadosMesa(id, novoNumero, novaCapacidade);
    }

    public void excluirMesa(Mesa mesa){
        mesaDAO.excluirMesa(mesa);
    }
}
