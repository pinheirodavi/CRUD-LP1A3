package br.edu.ifsp.model.cargo;

import br.edu.ifsp.model.departamento.Departamento;

/**
 * <p>Classe de domínio da entidade Cargo. Juntas, as classes de domínio modelam  
   a estrutura de negócio da aplicação. Possuem basicamente atributos e seus respectivos   
   métodos get e set, usados para recuperação e atribuição de valores aos seus dados.</p>
   
   <p>Para criar automaticamente os métodos get e set para os atributos da classe,  
   no editor de código, clique com o botão direito do mouse no código / Source / 
   Generate Getters and Setters.</p>
 * @author Leonardo Bertholdo
 *
 */

public class Cargo {
	private Integer id;
	private String descricao;
	private Departamento depto;
	
	public Integer getId() {
		return id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public Departamento getDepartamento() {
		return depto;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void setDepartamento(Departamento depto) {
		this.depto = depto;
	}

	/**
	 * <p>Sobrescreve o método toString da classe Object, para que ele retorne o valor do 
	   atributo Descricao. Com isso, o JComboBox do formulário de cadastro de funcionários 
	   mostrará apenas a descrição dos cargos.</p>
       
       <p>Object é superclasse de todas as demais classes do Java, inclusive das classes 
	   criadas pelos desenvolvedores. Por este motivo, Object define alguns comportamentos 
	   comuns que todos objetos devem ter, como a habilidade de serem comparados entre si, 
	   com o método equals(), e poderem ser representados como uma cadeia de caracteres, 
	   com o método toString().</p>
	 */
	@Override
	public String toString() {
		return descricao;
	}
}
