package br.edu.ifsp.model.departamento;

import br.edu.ifsp.model.funcionario.Funcionario;

public class Departamento {
	private Integer id;
	private String nomeDepto;
	private Funcionario gerente;
	
	public Integer getId() {
		return id;
	}
	
	public String getNomeDepto() {
		return nomeDepto;
	}
	
	public Funcionario getGerente() {
		return gerente;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setNomeDepto(String nomeDepto) {
		this.nomeDepto = nomeDepto;
	}
	
	public void setGerente(Funcionario gerente) {
		this.gerente = gerente;
	}

	/**
	 * <p>Sobrescreve o método toString da classe Object, para que ele retorne o valor do 
	   atributo NomeDepto. Com isso, o JComboBox do formulário de cadastro de cargos 
	   mostrará apenas o nome dos departamentos.</p>
	 */
	@Override
	public String toString() {
		return nomeDepto;
	}
}
