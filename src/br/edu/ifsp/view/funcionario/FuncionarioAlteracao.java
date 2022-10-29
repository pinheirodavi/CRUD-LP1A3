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
public class FuncionarioAlteracao extends JDialog {
	private JLabel lbTitulo, lbNome, lbSexo, lbSalario, lbCargo;
	private JTextField tfNome;
	private JFormattedTextField tfSalario;
	private static final String valSexo[] = {"Masculino", "Feminino"}; // Valores dos JRadioButtons.
	private JRadioButton rbSexo[];
	private ButtonGroup bgSexo; // Grupo de bot�es para agrupar os JRadioButtons.
	private JComboBox<Cargo> cbCargo;
	private JCheckBox ckPlanoSaude;
	private JButton btSalvar;
	private Container cp; // Container para organizar os componentes na janela.	
	private int idFuncionario; // Id do funcion�rio a ser alterado.
	private int linhaSelecionada; // �ndice da linha selecionada no JTable.
	private FuncionarioModeloTabela mtTabela; // Modelo que define a estrutura da tabela.

	// Construtor.
	public FuncionarioAlteracao(int idFuncionario, String nome, String sexo, String salario, 
			                    boolean planoSaude, String cargo, int linha, FuncionarioModeloTabela mtTabela) { 
		// Instancia��o e configura��o dos componentes de interface.
		setTitle("Altera��o de Funcion�rios"); // T�tulo da janela.
		setSize(500, 335); // Tamanho da janela em pixels.
		setLocationRelativeTo(null); // Centraliza a janela na tela.
		setModal(true); // Torna a janela "modal" (janela que n�o permite acesso a outras janelas abertas).
		
		lbTitulo = new JLabel("Altera��o de Funcion�rios");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 19)); // Ajusta a fonte do JLabel.
		
		lbNome = new JLabel("Nome");
		lbSexo = new JLabel("Sexo");
		lbSalario = new JLabel("Sal�rio (R$)");
		lbCargo = new JLabel("Cargo");
		
		tfNome = new JTextField();
		
        tfSalario = new JFormattedTextField(); // Campo de texto que permite definir m�scaras de entrada de dados.
		// Inclui uma m�scara de entrada no campo Sal�rio.
		DecimalFormat df = new DecimalFormat("##,##0.00"); // #: d�gitos opcionais; 0: d�gitos obrigat�rios.
        NumberFormatter nf = new NumberFormatter();
        nf.setFormat(df); // Define o formato num�rico, conforme a m�scara configurada.
        nf.setAllowsInvalid(false); // Impede que o usu�rio digite caracteres n�o compat�veis com a m�scara definida.
        // Associa a m�scara configurada ao JFormattedTextField.
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
		
		ckPlanoSaude = new JCheckBox("Possui Plano de Sa�de");
		ckPlanoSaude.setBackground(new Color(180, 205, 205)); // Cor de fundo do JCheckBox.
		
		cbCargo = new JComboBox<>();		
		
		List<Cargo> cargos = new ArrayList<Cargo>();
		
		// Retorna um ArrayList de objetos Cargo, contendo o Id e a Descri��o dos cargos cadastrados.
		cargos = new FuncionarioController().recuperaCargos();
		if (cargos != null) // Se existir pelo menos um cargo cadastrado.
			for (Cargo c : cargos)
				// O m�todo addItem adiciona o objeto Cargo (contendo os atributos Id e Descri��o) ao JComboBox. Ao ser carregado,
				// o JComboBox chama automaticamente o m�todo toString dos objetos Cargo para convert�-los para String, pois o
				// dado a ser exibido no JComboBox deve ser deste tipo. Como o m�todo toString foi sobrescrito na classe Cargo, 
				// de modo a retornar a descri��o do cargo, � esta a informa��o que ser� exibida no JComboBox.
				cbCargo.addItem(c);
		
		String erro = new FuncionarioController().getExcecao();
		
		if (erro != null) // Caso ocorra qualquer tipo de exce��o.
			JOptionPane.showMessageDialog(null, "N�o foi poss�vel recuperar os dados dos cargos:\n" + erro, 
					                      "Erro", JOptionPane.ERROR_MESSAGE);
		
		btSalvar = new JButton("Salvar");
		
		this.idFuncionario = idFuncionario; // Recebe o id do funcion�rio selecionado no JTable.
		
		// Carrega os dados do funcion�rio selecionado no JTable de funcion�rios.
		tfNome.setText(nome);
		
		if (sexo.equals("Masculino"))
			rbSexo[0].setSelected(true);
		else
			rbSexo[1].setSelected(true);
		
		tfSalario.setText(salario.toString());
		
		if (planoSaude)
			ckPlanoSaude.setSelected(true);
		else
			ckPlanoSaude.setSelected(false);
		
		for (int i = 0; i < cbCargo.getItemCount(); i++)
			if (cbCargo.getItemAt(i).getDescricao().equals(cargo))
				cbCargo.setSelectedItem(cbCargo.getItemAt(i));
		
		this.linhaSelecionada = linha; // Recebe o �ndice da linha selecionada no JTable.
		this.mtTabela = mtTabela; // Recebe o modelo que define a estrutura da tabela.

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
		btSalvar.setBounds(200, 250, 100, 25);

		// Adi��o dos componentes de interface ao container.
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
		cp.add(btSalvar);

		// Declara��o do processador de evento referente ao clique no bot�o Salvar.
		btSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btSalvarAction();
			}
		});
	} // Final do construtor.

	private void btSalvarAction() { // M�todo acionado pelo clique no bot�o Salvar.
		Character sexo = null;
		BigDecimal salario = null;
		for (JRadioButton rb : rbSexo) // Recupera o texto do JRadionButton selecionado.
			if (rb.isSelected())
				sexo = rb.getText().charAt(0);
		
		if (!tfSalario.getText().equals(""))
			salario = new BigDecimal(tfSalario.getText().replace(".", "").replace(",", "."));
		
		List<String> erros = new ArrayList<String>();
		
		// Envia os dados do funcion�rio (informados no formul�rio) ao controller. 
		// O controller retorna ent�o um ArrayList contendo os erros encontrados.
		erros = new FuncionarioController().alteraFuncionario(this.idFuncionario,
															  tfNome.getText(),
															  sexo,
															  salario,
											                  ckPlanoSaude.isSelected(),
											                  (Cargo) cbCargo.getSelectedItem());	
		
		if (erros.get(0) == null) { // Se o primeiro elemento do ArrayList for null.
			JOptionPane.showMessageDialog(this, "Funcion�rio alterado com sucesso.", 
                    					  "Informa��o", JOptionPane.INFORMATION_MESSAGE);
			// Atualiza os dados do JTable ap�s a altera��o do funcion�rio.
			// O m�todo setValueAt recebe o valor (Object), a linha (int) e a coluna (int) 
			// para atualizar com este valor cada coluna da linha selecionada.
			this.mtTabela.setValueAt(tfNome.getText(), this.linhaSelecionada, 1);
			this.mtTabela.setValueAt(sexo, this.linhaSelecionada, 2);
			this.mtTabela.setValueAt(salario, this.linhaSelecionada, 3);
			this.mtTabela.setValueAt(ckPlanoSaude.isSelected(), this.linhaSelecionada, 4);
			this.mtTabela.setValueAt(cbCargo.getSelectedItem(), this.linhaSelecionada, 5);
			this.setVisible(false); // Fecha a janela.
		} else { // Se o primeiro elemento do ArrayList n�o for null.
			String mensagem = "N�o foi poss�vel alterar o funcion�rio:\n";
			for (String e : erros) // Cria uma mensagem contendo todos os erros armazenados no ArrayList.
				mensagem = mensagem + e + "\n";
			JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
		}
	}
}















