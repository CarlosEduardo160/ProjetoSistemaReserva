package Service;

import DAO.MesaDAO;
import Entity.Cliente;
import Entity.Mesa;

import java.util.List;

public class MesaService {
    private MesaDAO mesaDAO;

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
            throw new RuntimeException("Nenhum cliente cadastrado");
        }
        return mesas;
    }
}
