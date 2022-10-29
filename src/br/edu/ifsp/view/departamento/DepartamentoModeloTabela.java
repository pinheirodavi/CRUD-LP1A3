package br.edu.ifsp.view.departamento;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.edu.ifsp.model.departamento.Departamento;
import br.edu.ifsp.model.funcionario.Funcionario;

@SuppressWarnings("serial")
public class DepartamentoModeloTabela extends AbstractTableModel {
	private String[] colunas = { "C�digo","Departamento","Gerente"};
	private List<Departamento> departamentos;
	private List<Funcionario> gerentes;
	
	public DepartamentoModeloTabela() {}
	
	public DepartamentoModeloTabela(List<Departamento> departamentos, List<Funcionario> gerentes) {
		this.departamentos = departamentos;
		this.gerentes = gerentes;
	}
	
	@Override
	public int getColumnCount() {
		return colunas.length;
	}
	
	@Override
	public String getColumnName(int coluna) {
		return colunas[coluna];
	}
	
	@Override
	public int getRowCount() {
		if (departamentos != null)
			return departamentos.size();
		return 0;
	}
	
	/*@Override
	public Class<?> getColumnClass(int coluna){
		return getValueAt(0, coluna).getClass();
	}*/
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Departamento departamento = departamentos.get(linha);
		Object valor = null;
		
		switch(coluna) {
		case 0:
			valor=departamento.getId();
			break;
		case 1:
			valor=departamento.getNomeDepto();
			break;
		case 2:
			if (gerentes != null)
				for (Funcionario g: gerentes)
					if (g.getId() ==departamento.getGerente().getId() )
						valor = g;
			break;
		}
		return valor;
	}
	
	@Override
	public boolean isCellEditable(int linha, int coluna) {
		return false;
	}

	@Override
	public void setValueAt(Object valor, int linha, int coluna) {
		switch (coluna) { 
		case 1: // Coluna Nome 
			departamentos.get(linha).setNomeDepto(valor.toString()); 
			break;
		case 2: // Coluna Gerente
			departamentos.get(linha).setGerente((Funcionario) valor); 
			break;
		}
		fireTableCellUpdated(linha, coluna);
	}
	
	public void removeDepartamentoTabela(int linha) {
		departamentos.remove(linha);
		
		fireTableRowsDeleted(linha, linha);
	}
}
