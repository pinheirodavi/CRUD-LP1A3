package br.edu.ifsp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.model.departamento.Departamento;
import br.edu.ifsp.model.funcionario.Funcionario;

public class DepartamentoDao extends GenericDao {
	private String instrucaoSql; // Atributo para armazenar a instru��o SQL a ser executada.
	private PreparedStatement comando; // Atributo usado para preparar e executar instru��es SQL.
	private ResultSet registros; // Atributo que recebe os dados retornados por uma instru��o SQL.
	private static String excecao = null; // Atributo para armazenar mensagens de excecao.

	public String insereDepartamento(Departamento departamento) {
		instrucaoSql = "INSERT INTO DEPARTAMENTO (NomeDepto, IdFuncGerente) VALUES (?,?)";
		return insereAlteraExclui(instrucaoSql, departamento.getNomeDepto(), departamento.getGerente().getId());
	}

	public List<Funcionario> recuperaGerente() {
		Funcionario gerente;
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		instrucaoSql = "SELECT * FROM FUNCIONARIO";

		try {
			excecao = ConnectionDatabase.conectaBd();
			if (excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);

				registros = comando.executeQuery();

				if (registros.next()) { // Se for retornado pelo menos um registro.
					registros.beforeFirst(); // Retorna o cursor para antes do 1� registro.
					while (registros.next()) {
						// Atribui o Id e a Descri��o do cargo ao objeto Cargo por meio dos m�todos set
						// e
						// adiciona este objeto ao ArrayList funcionarios.
						gerente = new Funcionario();
						gerente.setId(registros.getInt("Id"));
						gerente.setNome(registros.getString("Nome"));
						funcionarios.add(gerente);
					}
				}
				registros.close(); // Libera os recursos usados pelo objeto ResultSet.
				comando.close(); // Libera os recursos usados pelo objeto PreparedStatement.
				// Libera os recursos usados pelo objeto Connection e fecha a conex�o com o
				// banco de dados.
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch (Exception e) {
			excecao = "Tipo de Exce��o: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			funcionarios = null;
		}
		return funcionarios;
	}

	public String getExcecao() {
		return excecao;
	}

	public List<Departamento> consultaDepartamento() {
		Departamento departamento;
		Funcionario gerente;
		List<Departamento> departamentos = new ArrayList<Departamento>();
		instrucaoSql = "SELECT * FROM DEPARTAMENTO";

		try {
			excecao = ConnectionDatabase.conectaBd();
			if (excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);

				registros = comando.executeQuery();

				if (registros.next()) {
					registros.beforeFirst();
					while (registros.next()) {
						departamento = new Departamento();
						departamento.setId(registros.getInt("Id"));
						departamento.setNomeDepto(registros.getString("nomeDepto"));
						gerente = new Funcionario();
						gerente.setId(registros.getInt("IdFuncGerente"));
						departamento.setGerente(gerente);
						departamentos.add(departamento);
					}
				}
				registros.close();
				comando.close();
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch (Exception e) {
			excecao = "Tipo de Exce��o: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			departamentos = null; // Caso ocorra qualquer exce��o.
		}
		return departamentos;
	}
	
	public String alteraDepartamento(Departamento departamento) {
		instrucaoSql = "UPDATE DEPARTAMENTO SET NomeDepto = ?, IdFuncGerente = ? WHERE Id = ?";
		return insereAlteraExclui(instrucaoSql, departamento.getNomeDepto(), departamento.getGerente().getId(), departamento.getId());
	}
	
	public String excluiDepartamento(int id) {
		instrucaoSql = "DELETE FROM DEPARTAMENTO WHERE Id = ?";
		return insereAlteraExclui(instrucaoSql, id);
	}
}
