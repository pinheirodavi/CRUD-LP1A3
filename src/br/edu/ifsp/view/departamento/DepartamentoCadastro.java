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
public class DepartamentoCadastro extends JDialog {
	private JLabel lbTitulo, lbNomeDepto, lbFuncGerente;
	private JTextField tfNomeDepto;
	private JComboBox<Funcionario> cbFuncGerente;
	private JButton btCadastrar;
	private Container cp;
	
	public DepartamentoCadastro() {
		setTitle("Cadastro de Departamento");
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
		
		btCadastrar = new JButton("Cadastrar");
		
		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(180, 205, 205));
		
		lbTitulo.setBounds(125, 10, 300, 25);
		lbNomeDepto.setBounds(20, 50, 140, 25);
		tfNomeDepto.setBounds(170, 50, 220, 25);
		lbFuncGerente.setBounds(20, 90, 100, 25);
		cbFuncGerente.setBounds(170, 90, 220, 25);
		btCadastrar.setBounds(200, 140, 100, 25);
		
		cp.add(lbTitulo);
		cp.add(lbNomeDepto);
		cp.add(tfNomeDepto);
		cp.add(lbFuncGerente);
		cp.add(cbFuncGerente);
		cp.add(btCadastrar);
		
		btCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				btCadastrarAction();
			}
		});
	}
	
	public void btCadastrarAction() {
		List<String> erros = new ArrayList<String>();
		
		erros = new DepartamentoController().insereDepartamento(tfNomeDepto.getText(), (Funcionario) cbFuncGerente.getSelectedItem());
		
		if(erros.get(0) == null) {
			JOptionPane.showMessageDialog(this, "Departamento cadastrado com sucesso.", 
                    "Informa��o", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
		}else {
			String mensagem = "N�o foi poss�vel cadastrar o departamento:\n";
			for (String e : erros) // Cria uma mensagem contendo todos os erros armazenados no ArrayList.
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}
}
