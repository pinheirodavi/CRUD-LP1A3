package br.edu.ifsp.model.cargo;

import br.edu.ifsp.model.departamento.Departamento;

/**
 * <p>Classe de dom�nio da entidade Cargo. Juntas, as classes de dom�nio modelam  
   a estrutura de neg�cio da aplica��o. Possuem basicamente atributos e seus respectivos   
   m�todos get e set, usados para recupera��o e atribui��o de valores aos seus dados.</p>
   
   <p>Para criar automaticamente os m�todos get e set para os atributos da classe,  
   no editor de c�digo, clique com o bot�o direito do mouse no c�digo / Source / 
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
	 * <p>Sobrescreve o m�todo toString da classe Object, para que ele retorne o valor do 
	   atributo Descricao. Com isso, o JComboBox do formul�rio de cadastro de funcion�rios 
	   mostrar� apenas a descri��o dos cargos.</p>
       
       <p>Object � superclasse de todas as demais classes do Java, inclusive das classes 
	   criadas pelos desenvolvedores. Por este motivo, Object define alguns comportamentos 
	   comuns que todos objetos devem ter, como a habilidade de serem comparados entre si, 
	   com o m�todo equals(), e poderem ser representados como uma cadeia de caracteres, 
	   com o m�todo toString().</p>
	 */
	@Override
	public String toString() {
		return descricao;
	}
}
