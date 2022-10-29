package br.edu.ifsp.controller;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.dao.DepartamentoDao;
import br.edu.ifsp.model.departamento.Departamento;
import br.edu.ifsp.model.departamento.DepartamentoValidacao;
import br.edu.ifsp.model.funcionario.Funcionario;

public class DepartamentoController {
	private Departamento departamento;
	private List<String> erros;
	
	public List<String> insereDepartamento(String nomeDepto, Funcionario gerente){
		recebeDadosDepartamento(null, nomeDepto, gerente);
		
		if (erros.size() == 0) 
			erros.add(new DepartamentoDao().insereDepartamento(departamento));	
		
		return erros;
	}
	
	public void recebeDadosDepartamento(Integer id, String nomeDepto, Funcionario gerente){
		departamento = new Departamento();
		erros = new ArrayList<String>();
		
		departamento.setId(id);
		departamento.setNomeDepto(nomeDepto);
		departamento.setGerente(gerente);
		
		erros = DepartamentoValidacao.validaDepartamento(departamento);
	}
	
	public List<Funcionario> recuperaGerente(){
		return new DepartamentoDao().recuperaGerente();
	}
	
	public String getExcecao() {
    	// Retorna a exce��o lan�ada ao recuperar os departamentos (ao abrir a tela "Cadastro de Cargos").
    	return new DepartamentoDao().getExcecao();
    }
	
	public List<Departamento> consultaDepartamentos(){
		return new DepartamentoDao().consultaDepartamento();
	}
	
	public List<String> alteraDepartamento(Integer id, String nomeDepto, Funcionario gerente){
		recebeDadosDepartamento(id, nomeDepto, gerente);
		
		if(erros.size() == 0)
			erros.add(new DepartamentoDao().alteraDepartamento(departamento));
		
		return erros;
	}
	
	public String excluiDepartamento(Integer id) {
		String erro = new DepartamentoDao().excluiDepartamento(id);
		return erro;
	}
}
