package br.edu.ifsp.view.cargo;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.departamento.Departamento;

@SuppressWarnings("serial")
public class CargoModeloTabela extends AbstractTableModel{
	private String[] colunas = { "Código","Cargo","Departamento"};
	private List<Cargo> cargos;
	private List<Departamento> departamentos;
	
	public CargoModeloTabela() {}
	
	public CargoModeloTabela(List<Cargo> cargos,List<Departamento> departamentos) {
		this.cargos = cargos;
		this.departamentos = departamentos;
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
		if (cargos != null)
			return cargos.size();
		return 0;
	}
	

	/*@Override
	public Class<?> getColumnClass(int coluna){
		return getValueAt(0, coluna).getClass();
	}*/
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Cargo cargo = cargos.get(linha);
		Object valor = null;
		
		switch(coluna) {
		case 0:
			valor=cargo.getId();
			break;
		case 1:
			valor=cargo.getDescricao();
			break;
		case 2:
			if (departamentos != null)
				for (Departamento d: departamentos)
					if (d.getId() ==cargo.getDepartamento().getId() )
						valor = d;
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
		case 1: 
			cargos.get(linha).setDescricao(valor.toString()); 
			break;
		case 2:
			cargos.get(linha).setDepartamento((Departamento) valor);
			break;
		}
		fireTableCellUpdated(linha, coluna);
	}
	
	// Remove o funcion�rio exclu�do do ArrayList de funcion�rios e atualiza o JTable.
    public void removeCargoTabela(int linha) {
    	cargos.remove(linha);
    	
        fireTableRowsDeleted(linha, linha);
    }
}
