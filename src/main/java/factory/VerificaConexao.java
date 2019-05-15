package factory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class VerificaConexao extends JFrame {
	
	private JButton bVerificaConexao;

	public VerificaConexao() {
		setTitle("Verifica Conexão");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setBounds(200, 200, 300, 120);

		bVerificaConexao = new JButton("Verifica Conexão");
		bVerificaConexao.setBounds(15, 35, 260, 30);
		bVerificaConexao.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConexaoFB.getConnection();
			}
		});

		add(bVerificaConexao);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		VerificaConexao v = new VerificaConexao();
	}

}
