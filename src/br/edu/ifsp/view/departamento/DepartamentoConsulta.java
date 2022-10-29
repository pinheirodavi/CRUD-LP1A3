package br.edu.ifsp.view.departamento;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import br.edu.ifsp.controller.DepartamentoController;
import br.edu.ifsp.model.departamento.Departamento;
import br.edu.ifsp.model.funcionario.Funcionario;

@SuppressWarnings("serial")
public class DepartamentoConsulta extends JDialog {
	private JLabel lbTitulo;
	private JTable tbDepartamento;
	private DepartamentoModeloTabela mtTabela;
	private JScrollPane spTabela;
	private JButton btAlterar;
	private JButton btExcluir;
	private Container cp;

	public DepartamentoConsulta() {
		setTitle("Consulta de Departamentos");
		setSize(550, 340);
		setLocationRelativeTo(null);
		setModal(true);

		String excecaoDepartamentos = null;
		String excecaoGerente = null;

		List<Departamento> departamentos = new DepartamentoController().consultaDepartamentos();
		excecaoDepartamentos = new DepartamentoController().getExcecao();

		List<Funcionario> gerentes = new DepartamentoController().recuperaGerente();
		excecaoGerente = new DepartamentoController().getExcecao();

		if (excecaoDepartamentos != null) {
			JOptionPane.showMessageDialog(null,
					"N�o foi poss�vel recuperar os dados dos departamentos:\n" + excecaoDepartamentos, "Erro",
					JOptionPane.ERROR_MESSAGE);
			mtTabela = new DepartamentoModeloTabela();
		} else if (excecaoGerente != null) {
			JOptionPane.showMessageDialog(null, "N�o foi poss�vel recuperar os dados do gerente:\n" + excecaoGerente,
					"Erro", JOptionPane.ERROR_MESSAGE);
			mtTabela = new DepartamentoModeloTabela();
		} else {
			mtTabela = new DepartamentoModeloTabela(departamentos, gerentes);
		}

		lbTitulo = new JLabel("Consulta de Departamentos");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19)); // Ajusta a fonte do JLabel.

		tbDepartamento = new JTable(mtTabela);
		spTabela = new JScrollPane(tbDepartamento); // Vincula o JTable ao painel de rolagem.

		tbDepartamento.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Desabilita o dimensionamento autom�tico das
																	// colunas.
		// Configura a largura de cada coluna do JTable (em pixels).
		tbDepartamento.getColumnModel().getColumn(0).setPreferredWidth(80);
		tbDepartamento.getColumnModel().getColumn(1).setPreferredWidth(200);
		tbDepartamento.getColumnModel().getColumn(2).setPreferredWidth(200);

		tbDepartamento.getTableHeader().setFont(new Font(null, Font.BOLD, 12));

		DefaultTableCellRenderer dtcrCentro = new DefaultTableCellRenderer();
		dtcrCentro.setHorizontalAlignment(SwingConstants.CENTER);
		tbDepartamento.getColumnModel().getColumn(0).setCellRenderer(dtcrCentro);

		tbDepartamento.getTableHeader().setReorderingAllowed(false);
		tbDepartamento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		btAlterar = new JButton("Alterar");
		btExcluir = new JButton("Excluir");

		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(180, 205, 205));

		lbTitulo.setBounds(145, 10, 300, 25); // x, y, largura, altura.
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

		btExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btExcluirAction();
			}
		});
	}

	private void btAlterarAction() {
		if (tbDepartamento.getSelectedRow() != -1) {
			int linhaSelecionada = tbDepartamento.getSelectedRow();

			int id = Integer.parseInt(tbDepartamento.getModel().getValueAt(linhaSelecionada, 0).toString());
			String nomeDepto = tbDepartamento.getModel().getValueAt(linhaSelecionada, 1).toString();
			String gerente = tbDepartamento.getModel().getValueAt(linhaSelecionada, 2).toString();

			SwingUtilities.invokeLater(new Runnable() { // Chama o formul�rio de altera��o de funcion�rio.
				@Override
				public void run() {
					new DepartamentoAlteracao(id, nomeDepto, gerente, linhaSelecionada, mtTabela).setVisible(true);
				}
			});
		} else {
			JOptionPane.showMessageDialog(this, "Selecione um departamento.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void btExcluirAction() {
		if (tbDepartamento.getSelectedRow() != -1) {
			int resposta = JOptionPane.showConfirmDialog(this, "Confirma a Exclusão?", "Confirmação",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (resposta == 0) {
				int linhaSelecionada = tbDepartamento.getSelectedRow();

				int id = Integer.parseInt(tbDepartamento.getModel().getValueAt(linhaSelecionada, 0).toString());

				String erro;

				erro = new DepartamentoController().excluiDepartamento(id);

				if (erro == null) {
					JOptionPane.showMessageDialog(this, "Departamento excluído com sucesso", "Informação",
							JOptionPane.INFORMATION_MESSAGE);
					mtTabela.removeDepartamentoTabela(linhaSelecionada);
				} else {
					String mensagem = "Não foi possível excluir o departamento: \n";
					mensagem = mensagem + erro + "\n";
					JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} else if (resposta == 1)
				JOptionPane.showMessageDialog(this, "Opera��o cancelada.", "Confirma��o",
						JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Selecione um departamento.", "Mensagem", JOptionPane.WARNING_MESSAGE);
		}
	}
}
