package br.edu.ifsp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.departamento.Departamento;

public class CargoDao extends GenericDao {
	private String instrucaoSql; // Atributo para armazenar a instru��o SQL a ser executada.
	private PreparedStatement comando; // Atributo usado para preparar e executar instru��es SQL.
	private ResultSet registros; // Atributo que recebe os dados retornados por uma instru��o SQL.
	private static String excecao = null; // Atributo para armazenar mensagens de excecao.

	public String insereCargo(Cargo cargo) {
		instrucaoSql = "INSERT INTO CARGO (Descricao, IdDepto) VALUES (?,?)";
		return insereAlteraExclui(instrucaoSql, cargo.getDescricao(), cargo.getDepartamento().getId());
	}

	public List<Departamento> recuperaDepartamentos() {
		Departamento departamento;
		List<Departamento> departamentos = new ArrayList<Departamento>();
		instrucaoSql = "SELECT * FROM DEPARTAMENTO";

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
						departamento = new Departamento();
						departamento.setId(registros.getInt("Id"));
						departamento.setNomeDepto(registros.getString("NomeDepto"));
						departamentos.add(departamento);
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
			departamentos = null;
		}
		return departamentos;
	}

	public String getExcecao() {
		return excecao;
	}
	
	public List<Cargo> consultaCargo(){
		Cargo cargo;
		Departamento departamento;
		List<Cargo> cargos = new ArrayList<Cargo>();
		instrucaoSql = "SELECT * FROM CARGO";
		
		try {
			excecao = ConnectionDatabase.conectaBd();
			if(excecao == null) {
				comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
				
				registros = comando.executeQuery();
				
				if(registros.next()) {
					registros.beforeFirst();
					while (registros.next()) {
						cargo = new Cargo();
						cargo.setId(registros.getInt("Id"));
						cargo.setDescricao(registros.getString("Descricao"));
						departamento = new Departamento();
						departamento.setId(registros.getInt("IdDepto"));
						cargo.setDepartamento(departamento);
						cargos.add(cargo);
					}
				}
				registros.close();
				comando.close();
				ConnectionDatabase.getConexaoBd().close();
			}
		} catch(Exception e) {
			excecao = "Tipo de Exce��o: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
			cargos = null; // Caso ocorra qualquer exce��o.
		}
		return cargos;
	}
 
	public String alteraCargo(Cargo cargo) {
		instrucaoSql = "UPDATE CARGO SET Descricao = ?, IdDepto = ? WHERE Id = ?";
		return insereAlteraExclui(instrucaoSql, cargo.getDescricao(), cargo.getDepartamento().getId(), cargo.getId());
	}
	
	public String excluiCargo(int id) {
		instrucaoSql = "DELETE FROM CARGO WHERE Id = ?";
		return insereAlteraExclui(instrucaoSql, id);
	}
}
