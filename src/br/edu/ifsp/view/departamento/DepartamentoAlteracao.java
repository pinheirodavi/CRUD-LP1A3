package br.edu.ifsp.view.departamento;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.edu.ifsp.model.funcionario.Funcionario;
import br.edu.ifsp.controller.DepartamentoController;

@SuppressWarnings("serial")
public class DepartamentoAlteracao extends JDialog {
	private JLabel lbTitulo, lbNomeDepto, lbFuncGerente;
	private JTextField tfNomeDepto;
	private JComboBox<Funcionario> cbFuncGerente;
	private JButton btSalvar;
	private Container cp;
	private int idDepto;
	private int linhaSelecionada;
	private DepartamentoModeloTabela mtTabela;
	
	public DepartamentoAlteracao(int idDepto, String nomeDepto, String gerente, int linha, DepartamentoModeloTabela mtTabela) {
		setTitle("Alteração de Departamento");
		setSize(450, 250);
		setLocationRelativeTo(null);
		setModal(true);
		
		lbTitulo = new JLabel("Cadastro de Departamentos");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19)); // Ajusta a fonte do JLabel.
		
		lbNomeDepto = new JLabel("Nome do Departamento");
		lbFuncGerente = new JLabel("Gerente");
		
		tfNomeDepto = new JTextField();
		cbFuncGerente = new JComboBox<>();
		
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		funcionarios = new DepartamentoController().recuperaGerente();
		if(funcionarios != null) {
			for(Funcionario f: funcionarios)
				cbFuncGerente.addItem(f);
		}
		
		String erro = new DepartamentoController().getExcecao();
		
		if(erro!=null)
			JOptionPane.showMessageDialog(null, "N�o foi poss�vel recuperar os dados dos funcionários:\n" + erro, 
                    "Erro", JOptionPane.ERROR_MESSAGE);
		
		btSalvar = new JButton("Salvar");
		
		this.idDepto = idDepto;
		
		tfNomeDepto.setText(nomeDepto);
		
		for (int i = 0; i < cbFuncGerente.getItemCount(); i++)
			if (cbFuncGerente.getItemAt(i).getNome().equals(gerente))
				cbFuncGerente.setSelectedItem(cbFuncGerente.getItemAt(i));
		
		this.linhaSelecionada = linha;
		this.mtTabela = mtTabela;
		
		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(180, 205, 205));
		
		lbTitulo.setBounds(125, 10, 300, 25);
		lbNomeDepto.setBounds(20, 50, 140, 25);
		tfNomeDepto.setBounds(170, 50, 220, 25);
		lbFuncGerente.setBounds(20, 90, 100, 25);
		cbFuncGerente.setBounds(170, 90, 220, 25);
		btSalvar.setBounds(200, 140, 100, 25);
		
		cp.add(lbTitulo);
		cp.add(lbNomeDepto);
		cp.add(tfNomeDepto);
		cp.add(lbFuncGerente);
		cp.add(cbFuncGerente);
		cp.add(btSalvar);
		
		btSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				btSalvarAction();
			}
		});
	}
	
	public void btSalvarAction() {
		List<String> erros = new ArrayList<String>();
		
		erros = new DepartamentoController().alteraDepartamento(this.idDepto,
																tfNomeDepto.getText(),
																(Funcionario) cbFuncGerente.getSelectedItem());
		
		if(erros.get(0) == null) {
			JOptionPane.showMessageDialog(this, "Departamento alterado com sucesso.", 
                    "Informa��o", JOptionPane.INFORMATION_MESSAGE);
			this.mtTabela.setValueAt(tfNomeDepto.getText(), this.linhaSelecionada, 1);
			this.mtTabela.setValueAt(cbFuncGerente.getSelectedItem(), this.linhaSelecionada, 2);
			this.setVisible(false);
		}else {
			String mensagem = "N�o foi poss�vel alterar o departamento:\n";
			for (String e : erros) // Cria uma mensagem contendo todos os erros armazenados no ArrayList.
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}
}
