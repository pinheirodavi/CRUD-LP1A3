package br.edu.ifsp.controller;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.dao.CargoDao;
import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.cargo.CargoValidacao;
import br.edu.ifsp.model.departamento.Departamento;

public class CargoController {
	private Cargo cargo;
	private List<String> erros;
	
	public List<String> insereCargo(String descricao, Departamento departamento){
		recebeDadosCargo(null, descricao, departamento);
		
		if (erros.size() == 0) 
			erros.add(new CargoDao().insereCargo(cargo));
		
		return erros;
	}
	
	public void recebeDadosCargo(Integer id, String descricao, Departamento departamento) {
		cargo = new Cargo();
		erros = new ArrayList<String>();
		
		cargo.setId(id);
		cargo.setDescricao(descricao);
		cargo.setDepartamento(departamento);
		
		erros = CargoValidacao.validaCargo(cargo);
	}
	
	public List<Departamento> recuperaDepartamentos() {
    	// Recupera os deptos cadastrados no banco de dados para que sejam carregados no JComboBox Depto.
		return new CargoDao().recuperaDepartamentos();		
    }
	
	public String getExcecao() {
    	// Retorna a exce��o lan�ada ao recuperar os departamentos (ao abrir a tela "Cadastro de Cargos").
    	return new CargoDao().getExcecao();
    }
	
	public List<Cargo> consultaCargos(){
		return new CargoDao().consultaCargo();
	}
	
	public List<String> alteraCargo(Integer id, String descricao, Departamento depto){
		recebeDadosCargo(id, descricao, depto);
		
		if(erros.size() == 0)
			erros.add(new CargoDao().alteraCargo(cargo));
		
		return erros;
	}
	
	public String excluiCargo(Integer id) {
		String erro = new CargoDao().excluiCargo(id);
		return erro;
	}
}
