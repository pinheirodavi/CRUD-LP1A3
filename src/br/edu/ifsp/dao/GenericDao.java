package br.edu.ifsp.dao;

import java.sql.PreparedStatement;

public class GenericDao {
    private PreparedStatement comando; // Atributo usado para preparar e executar instru��es SQL.

    // Para incluir um texto de javadoc no c�digo-fonte, digite "/**" e tecle Enter. 
    // Em seguida, digite as informa��es desejadas entre os delimitadores "/**" e "*/". Ap�s incluir as
    // informa��es, para gerar o Javadoc na IDE Eclipse, selecione o menu Project >> Generate Javadoc.
    // Os documentos HTML s�o gerados em uma pasta "doc" dentro do diret�rio do projeto. Para acessar
    // a p�gina principal da documenta��o, abra o arquivo index.html.
    
    // Ao digitar "/**" e teclar Enter, antes de um m�todo, o Javadoc j� � gerado com algumas diretivas, como:
    // diretivas referentes aos par�metros (@param), poss�veis exce��es (@throws) e retorno (@return) do m�todo.
    /**
     * @param instrucaoSql Instru��o SQL a ser executada.
     * @param parametros Valores dos campos da instru��o SQL. As retic�ncias no tipo Object 
     * indicam que "parametros" pode receber um n�mero vari�vel de argumentos Object. 
     * @return Retorno do m�todo.
     */
    protected String insereAlteraExclui(String instrucaoSql, Object... parametros) {
    	try {
    		String excecao = ConnectionDatabase.conectaBd(); // Abre a conex�o com o banco de dados.
    		if (excecao == null) {
    			// Obt�m os dados de conex�o com o banco de dados e prepara a instru��o SQL.
                comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
    			
    	    	// Associa cada par�metro Object recebido ao objeto "comando".
    	        for (int i = 0; i < parametros.length; i++)
    	        	// 1� argumento: posi��o do par�metro na instru��o SQL; 2� argumento: par�metro.
    	        	// Para objetos Funcionario: 1) Nome, 2) Sexo, 3) Salario, 4) PlanoSaude, 5) IdCargo
    	        	comando.setObject(i + 1, parametros[i]); 

    	        comando.execute(); // Executa a instru��o SQL.
    	        
    	        comando.close(); // Libera os recursos usados pelo objeto PreparedStatement.
    	        // Libera os recursos usados pelo objeto Connection e fecha a conex�o com o banco de dados.
                ConnectionDatabase.getConexaoBd().close();
    		} else
        		return excecao; // Caso ocorra exce��o ao tentar conectar com o banco de dados.
        } catch (Exception e) {
        	// Caso ocorra qualquer tipo de exce��o.
            return "Tipo de Exce��o: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage(); 
        }
        return null; // Se o registro foi inserido com sucesso.
    }
}
