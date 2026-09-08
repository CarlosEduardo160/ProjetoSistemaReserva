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
        Mesa novaMesa = new Mesa(numeroMesa, capacidade);
        mesaDAO.registrarMesa(novaMesa);
    }

    public List<Mesa> listarTodasAsMesas(){
        List<Mesa> mesas = mesaDAO.listarTodasAsMesas();

        if(mesas.isEmpty()){
            throw new RuntimeException("Nenhuma mesa cadastrada.");
        }
        return mesas;
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
