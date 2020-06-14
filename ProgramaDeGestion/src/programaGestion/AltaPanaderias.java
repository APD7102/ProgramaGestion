package programaGestion;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class AltaPanaderias extends WindowAdapter implements ActionListener
{

	AltaPanaderias()
	{
		Vista.altaPanaderia.setLayout(new FlowLayout());
		Vista.altaPanaderia.setSize(350, 130);
		Vista.altaPanaderia.setResizable(false);
		Vista.altaPanaderia.addWindowListener(this);
		Vista.btnAPAceptar.addActionListener(this);
		Vista.btnAPBorrar.addActionListener(this);
		Vista.altaPanaderia.add(Vista.lblAPDireccionPanaderia);
		Vista.altaPanaderia.add(Vista.txtAPDireccionPanaderia);
		Vista.altaPanaderia.add(Vista.btnAPAceptar);
		Vista.altaPanaderia.add(Vista.btnAPBorrar);
		Vista.altaPanaderia.setLocationRelativeTo(null);
		Vista.altaPanaderia.setVisible(true);
		Vista.txtAPDireccionPanaderia.selectAll();
		Vista.txtAPDireccionPanaderia.setText("");
	}
	
	public void windowClosing(WindowEvent arg0) 
	{
		if(Vista.altaPanaderia.isActive())
		{
			Vista.altaPanaderia.setVisible(false);
		}
	}
	
	public void actionPerformed(ActionEvent evento)
	{

		//Alta

		if(evento.getSource().equals(Vista.btnAPAceptar)) 
		{
			if (Vista.txtAPDireccionPanaderia.getSelectedText().equals("")) 
			{
				
			}
			try
			{
				Modelo.ConexionBD();
				Modelo.sentencia = "INSERT INTO panaderias (direccionPanaderia) VALUES ('"+Vista.txtAPDireccionPanaderia.getText()+"')";
				Modelo.statement.executeUpdate(Modelo.sentencia);
			}

			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
			}

			finally
			{
				Log.creacionLog("Alta de panaderia realizada");
				try
				{
					if(Modelo.connection!=null)
					{
						Modelo.connection.close();
					}
				}
				catch (SQLException e)
				{
					System.out.println("Error 3-"+e.getMessage());
					
				}
				JOptionPane.showMessageDialog(Vista.altaPanaderia, "Alta de panadería realizada");
			}
				
		}
		if(evento.getSource().equals(Vista.btnAPBorrar)) 
		{
			Vista.txtAPDireccionPanaderia.selectAll();
			Vista.txtAPDireccionPanaderia.setText("");
			Vista.txtAPDireccionPanaderia.requestFocus();
		}
	}
}
