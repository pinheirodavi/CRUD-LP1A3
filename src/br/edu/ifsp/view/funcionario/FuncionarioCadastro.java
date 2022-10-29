package br.edu.ifsp.view.funcionario;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import br.edu.ifsp.controller.FuncionarioController;
import br.edu.ifsp.model.cargo.Cargo;

@SuppressWarnings("serial")
public class FuncionarioCadastro extends JDialog {
	private JLabel lbTitulo, lbNome, lbSexo, lbSalario, lbCargo;
	private JTextField tfNome;
	private JFormattedTextField tfSalario;
	private static final String valSexo[] = {"Masculino", "Feminino"}; // Valores dos JRadioButtons.
	private JRadioButton rbSexo[];
	private ButtonGroup bgSexo; // Grupo de botões para agrupar os JRadioButtons.
	private JComboBox<Cargo> cbCargo;
	private JCheckBox ckPlanoSaude;
	private JButton btCadastrar;
	private Container cp; // Container para organizar os componentes na janela.	

	public FuncionarioCadastro() { // Construtor.
		// Instanciação e configuração dos componentes de interface.
		setTitle("Cadastro de Funcionários"); // Título da janela.
		setSize(500, 335); // Tamanho da janela em pixels.
		setLocationRelativeTo(null); // Centraliza a janela na tela.
		setModal(true); // Torna a janela "modal" (janela que não permite acesso a outras janelas abertas).
		
		lbTitulo = new JLabel("Cadastro de Funcionários");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19)); // Ajusta a fonte do JLabel.
		
		lbNome = new JLabel("Nome");
		lbSexo = new JLabel("Sexo");
		lbSalario = new JLabel("Salário (R$)");
		lbCargo = new JLabel("Cargo");
		
		tfNome = new JTextField();
		
        tfSalario = new JFormattedTextField(); // Campo de texto que permite definir máscaras de entrada de dados.
		// Inclui uma máscara de entrada no campo Salário.
		DecimalFormat df = new DecimalFormat("##,##0.00"); // #: dígitos opcionais; 0: dígitos obrigatórios.
        NumberFormatter nf = new NumberFormatter();
        nf.setFormat(df); // Define o formato numérico, conforme a máscara configurada.
        nf.setAllowsInvalid(false); // Impede que o usuário digite caracteres não compatíveis com a máscara definida.
        // Associa a máscara configurada ao JFormattedTextField.
        tfSalario.setFormatterFactory(new DefaultFormatterFactory(nf));
		
		rbSexo = new JRadioButton[2];
		bgSexo = new ButtonGroup();
		for (int i = 0; i < rbSexo.length; i++){
			// Adiciona os valores "Masculino" e "Feminino" aos JRadioButtos.
			rbSexo[i] = new JRadioButton(valSexo[i]); 
			rbSexo[i].setBackground(new Color(180, 205, 205)); // Cor de fundo dos JRadioButtons.
			bgSexo.add(rbSexo[i]); // Adiciona os JRadioButtons ao ButtonGroup.
		}
		rbSexo[0].setSelected(true); // Faz com que o JRadioButton "Masculino" fique marcado.
		
		ckPlanoSaude = new JCheckBox("Possui Plano de Saúde");
		ckPlanoSaude.setBackground(new Color(180, 205, 205)); // Cor de fundo do JCheckBox.
		
		cbCargo = new JComboBox<>();
		
		List<Cargo> cargos = new ArrayList<Cargo>();
		
		// Retorna um ArrayList de objetos Cargo, contendo o Id e a Descrição dos cargos cadastrados.
		cargos = new FuncionarioController().recuperaCargos();
		if (cargos != null) // Se existir pelo menos um cargo cadastrado.
			for (Cargo c : cargos)
				// O método addItem adiciona o objeto Cargo (contendo os atributos Id e Descrição) ao JComboBox. Ao ser carregado,
				// o JComboBox chama automaticamente o método toString dos objetos Cargo para convertê-los para String, pois o
				// dado a ser exibido no JComboBox deve ser deste tipo. Como o método toString foi sobrescrito na classe Cargo, 
				// de modo a retornar a descrição do cargo, é esta a informação que será exibida no JComboBox.
				cbCargo.addItem(c);
		
		String erro = new FuncionarioController().getExcecao();
		
		if (erro != null) // Caso ocorra qualquer tipo de exceção.
			JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos cargos:\n" + erro, 
					                      "Erro", JOptionPane.ERROR_MESSAGE);
		
		btCadastrar = new JButton("Cadastrar");

		cp = getContentPane(); // Instancia o container da janela.
		cp.setLayout(null); // Configura o layout do container como nulo.
		cp.setBackground(new Color(180, 205, 205)); // Configura a cor de fundo do container.

		// Posicionamento dos componentes de interface na janela.
		lbTitulo.setBounds(125, 10, 300, 25); // x, y, largura, altura.
		lbNome.setBounds(20, 50, 100, 25);
		tfNome.setBounds(100, 50, 360, 25);
		lbSexo.setBounds(20, 90, 100, 25);
		rbSexo[0].setBounds(100, 90, 100, 25);
		rbSexo[1].setBounds(200, 90, 100, 25);
		lbSalario.setBounds(20, 130, 100, 25);
		tfSalario.setBounds(100, 130, 100, 25);
		lbCargo.setBounds(20, 170, 100, 25);
		cbCargo.setBounds(100, 170, 220, 25);
		ckPlanoSaude.setBounds(16, 210, 250, 25);
		btCadastrar.setBounds(200, 250, 100, 25);

		// Adição dos componentes de interface ao container.
		cp.add(lbTitulo);
		cp.add(lbNome);
		cp.add(tfNome);
		cp.add(lbSexo);
		cp.add(rbSexo[0]);
		cp.add(rbSexo[1]);
		cp.add(lbSalario);
		cp.add(tfSalario);
		cp.add(lbCargo);
		cp.add(cbCargo);
		cp.add(ckPlanoSaude);
		cp.add(btCadastrar);

		// Declaração do processador de evento referente ao clique no botão Cadastrar.
		btCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				btCadastrarAction();
			}
		});
	} // Final do construtor.

	private void btCadastrarAction() { // Método acionado pelo clique no botão Cadastrar.
		Character sexo = null;
		BigDecimal salario = null;
		for (JRadioButton rb : rbSexo) // Recupera o texto do JRadionButton selecionado.
			if (rb.isSelected())
				sexo = rb.getText().charAt(0);
		
		if (!tfSalario.getText().equals(""))
			salario = new BigDecimal(tfSalario.getText().replace(".", "").replace(",", "."));

		List<String> erros = new ArrayList<String>();
		
		// Envia os dados do funcionário (informados no formulário) ao controller. 
		// O controller retorna então um ArrayList contendo os erros encontrados.
		erros = new FuncionarioController().insereFuncionario(tfNome.getText(),
												              sexo,
												              salario,
										                      ckPlanoSaude.isSelected(),
										                      (Cargo) cbCargo.getSelectedItem());
		
		if (erros.get(0) == null) { // Se o primeiro elemento do ArrayList for null.
			JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso.", 
					                      "Informação", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false); // Fecha a janela.
		} else { // Se o primeiro elemento do ArrayList não for null.
			String mensagem = "Não foi possível cadastrar o funcionário:\n";
			for (String e : erros) // Cria uma mensagem contendo todos os erros armazenados no ArrayList.
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}
}






















