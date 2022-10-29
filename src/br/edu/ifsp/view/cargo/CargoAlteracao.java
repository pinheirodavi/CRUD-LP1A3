package br.edu.ifsp.view.cargo;

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

import br.edu.ifsp.controller.CargoController;
import br.edu.ifsp.model.departamento.Departamento;

@SuppressWarnings("serial")
public class CargoAlteracao extends JDialog {
	private JLabel lbTitulo, lbDescricao, lbDepartamento;
	private JTextField tfDescricao;
	private JComboBox<Departamento> cbDepartamento;
	private JButton btSalvar;
	private Container cp;
	private int idCargo;
	private int linhaSelecionada;
	private CargoModeloTabela mtTabela;

	public CargoAlteracao( int idCargo, String descricao, String depto, int linha, CargoModeloTabela mtTabela) {
		setTitle("Alteração de Cargo");
		setSize(450, 250);
		setLocationRelativeTo(null);
		setModal(true);

		lbTitulo = new JLabel("Alteração de Cargos");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19)); // Ajusta a fonte do JLabel.

		lbDescricao = new JLabel("Descrição");
		lbDepartamento = new JLabel("Departamento");

		tfDescricao = new JTextField();
		cbDepartamento = new JComboBox<>();

		List<Departamento> departamentos = new ArrayList<Departamento>();
		departamentos = new CargoController().recuperaDepartamentos();
		if (departamentos != null) {
			for (Departamento d : departamentos)
				cbDepartamento.addItem(d);
		}

		String erro = new CargoController().getExcecao();

		if (erro != null)
			JOptionPane.showMessageDialog(null, "N�o foi poss�vel recuperar os dados dos cargos:\n" + erro,
					"Erro", JOptionPane.ERROR_MESSAGE);

		btSalvar = new JButton("Salvar");
		
		this.idCargo = idCargo;
		
		tfDescricao.setText(descricao);
		
		for (int i = 0; i < cbDepartamento.getItemCount(); i++)
			if (cbDepartamento.getItemAt(i).getNomeDepto().equals(depto))
				cbDepartamento.setSelectedItem(cbDepartamento.getItemAt(i));
		
		this.linhaSelecionada = linha;
		this.mtTabela = mtTabela;

		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(180, 205, 205));
		
		lbTitulo.setBounds(125, 10, 300, 25);
		lbDescricao.setBounds(20, 50, 140, 25);
		tfDescricao.setBounds(170, 50, 220, 25);
		lbDepartamento.setBounds(20, 90, 100, 25);
		cbDepartamento.setBounds(170, 90, 220, 25);
		btSalvar.setBounds(170, 140, 100, 25);
		
		cp.add(lbTitulo);
		cp.add(lbDescricao);
		cp.add(tfDescricao);
		cp.add(lbDepartamento);
		cp.add(cbDepartamento);
		cp.add(btSalvar);
		
		btSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				btSalvarAction();
			}
		});
	}
	
	public void btSalvarAction() {
		List<String> erros = new ArrayList<String>();
		
		erros = new CargoController().alteraCargo(this.idCargo,
													tfDescricao.getText(),
													(Departamento) cbDepartamento.getSelectedItem());
		
		if(erros.get(0) == null) {
			JOptionPane.showMessageDialog(this, "Cargo alterado com sucesso.", 
                    "Informa��o", JOptionPane.INFORMATION_MESSAGE);
			this.mtTabela.setValueAt(tfDescricao.getText(), this.linhaSelecionada, 1);
			this.mtTabela.setValueAt(cbDepartamento.getSelectedItem(), this.linhaSelecionada, 2);
			this.setVisible(false);
		}else {
			String mensagem = "N�o foi poss�vel alterar o cargo:\n";
			for (String e : erros) // Cria uma mensagem contendo todos os erros armazenados no ArrayList.
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}
}
