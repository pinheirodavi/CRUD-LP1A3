package br.edu.ifsp.view.cargo;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import br.edu.ifsp.controller.CargoController;
import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.departamento.Departamento;

@SuppressWarnings("serial")
public class CargoConsulta extends JDialog {
	private JLabel lbTitulo;
	private JTable tbCargo;
	private CargoModeloTabela mtTabela;
	private JScrollPane spTabela;
	private JButton btAlterar;
	private JButton btExcluir;
	private Container cp;
	
	public CargoConsulta() {
		setTitle("Consulta de Cargos");
		setSize(550,340);
		setLocationRelativeTo(null);
		setModal(true);
		
		String excecaoCargos = null;
		String excecaoDepartamento = null;
		
		List<Cargo> cargos = new CargoController().consultaCargos();
		excecaoCargos = new CargoController().getExcecao();
		
		List<Departamento> departamentos = new CargoController().recuperaDepartamentos();
		excecaoDepartamento = new CargoController().getExcecao();
		
		if(excecaoCargos != null) {
			JOptionPane.showMessageDialog(null, "N�o foi poss�vel recuperar os dados dos Cargos:\n" + excecaoCargos, 
                    "Erro", JOptionPane.ERROR_MESSAGE);
			mtTabela = new CargoModeloTabela();
		} else if(excecaoDepartamento != null) {
			JOptionPane.showMessageDialog(null, "N�o foi poss�vel recuperar os dados dos departamentos:\n" + excecaoDepartamento, 
                    "Erro", JOptionPane.ERROR_MESSAGE);
			mtTabela = new CargoModeloTabela();
		} else {
			mtTabela = new CargoModeloTabela(cargos, departamentos);
		}
		
		lbTitulo = new JLabel("Consulta de Cargos");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));
		
		tbCargo = new JTable(mtTabela);
		spTabela = new JScrollPane(tbCargo);
		
		tbCargo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Desabilita o dimensionamento autom�tico das colunas.
		// Configura a largura de cada coluna do JTable (em pixels).
		tbCargo.getColumnModel().getColumn(0).setPreferredWidth(80);
		tbCargo.getColumnModel().getColumn(1).setPreferredWidth(200);
		tbCargo.getColumnModel().getColumn(2).setPreferredWidth(200);
		
		tbCargo.getTableHeader().setFont(new Font(null, Font.BOLD, 12));
		
		DefaultTableCellRenderer dtcrCentro = new DefaultTableCellRenderer();
		dtcrCentro.setHorizontalAlignment(SwingConstants.CENTER);
		tbCargo.getColumnModel().getColumn(0).setCellRenderer(dtcrCentro);
		
		tbCargo.getTableHeader().setReorderingAllowed(false);
		tbCargo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btAlterar = new JButton("Alterar");
		btExcluir = new JButton("Excluir");
		
		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(180,205,205));
		
		lbTitulo.setBounds(175, 10, 300, 25); // x, y, largura, altura.
		spTabela.setBounds(20, 40, 483, 182);
		btAlterar.setBounds(105, 240, 100, 25);
		btExcluir.setBounds(355, 240, 100, 25);
		
		cp.add(lbTitulo);
		cp.add(spTabela);
		cp.add(btAlterar);
		cp.add(btExcluir);
		
		btAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btAlterarAction();
			}
		});
		
		// Declara��o do processador de evento referente ao clique no bot�o Excluir.
		btExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btExcluirAction();
			}
		});
	}
	
	private void btAlterarAction() {
		if (tbCargo.getSelectedRow() != -1) {
			int linhaSelecionada = tbCargo.getSelectedRow();
			
			int id = Integer.parseInt(tbCargo.getModel().getValueAt(linhaSelecionada, 0).toString());
			String descricao = tbCargo.getModel().getValueAt(linhaSelecionada, 1).toString();
			String depto = tbCargo.getModel().getValueAt(linhaSelecionada, 2).toString();
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() { new CargoAlteracao(id, descricao, depto, linhaSelecionada, mtTabela).setVisible(true); }
			});
		} else {
			JOptionPane.showMessageDialog(this, "Selecione um cargo.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void btExcluirAction() {
		if(tbCargo.getSelectedRow() != -1) {
			int resposta = JOptionPane.showConfirmDialog(this, "Confirma a exclus�o?", "Confirma��o", 
					 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(resposta == 0) {
				int linhaSelecionada = tbCargo.getSelectedRow();
				
				int id = Integer.parseInt(tbCargo.getModel().getValueAt(linhaSelecionada, 0).toString());
				
				String erro = "";
				
				erro = new CargoController().excluiCargo(id);
				
				if(erro == null) {
					JOptionPane.showMessageDialog(this, "Cargo exclu�do com sucesso.", 
                            "Informa��o", JOptionPane.INFORMATION_MESSAGE);
					mtTabela.removeCargoTabela(linhaSelecionada);
				} else {
					String mensagem = "Não foi possível excluir o cargo";
					mensagem = mensagem + erro + "\n";
					JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} else if (resposta == 1)
				JOptionPane.showMessageDialog(this, "Opera��o cancelada.", "Confirma��o", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Selecione um cargo.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}
}
