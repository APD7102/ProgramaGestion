package programaGestion;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class AltaVentas extends WindowAdapter implements ActionListener
{
    LocalDate fecha = LocalDate.now(); 
    String fechaAmericana = (fecha.toString());
    
	AltaVentas()
	{
		//Sentencia para recopilar los datos e introducirlos en el choice
		try
		{	
			Vista.choAVPanaderiaVenta.removeAll();
			Vista.choAVPanaderiaVenta.add("Elige una panaderia");
			Modelo.ConexionBD();
			Modelo.sentencia = "SELECT * FROM panaderias";
			Modelo.rs = Modelo.statement.executeQuery(Modelo.sentencia);

			while(Modelo.rs.next())
			{
				String poblarChoice = Integer.toString(Modelo.rs.getInt("idPanaderia"));
				poblarChoice = poblarChoice + "-"+ Modelo.rs.getString("direccionPanaderia");
				Vista.choAVPanaderiaVenta.add(poblarChoice);
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2-"+sqle.getMessage());
		}
		finally
		{
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
		}
		
				try
				{	
					Vista.choAVProductoVenta.removeAll();
					Vista.choAVProductoVenta.add("Elige un producto");
					Modelo.ConexionBD();
					Modelo.sentencia = "SELECT * FROM productos";
					Modelo.rs = Modelo.statement.executeQuery(Modelo.sentencia);

					while(Modelo.rs.next())
					{
						String rellenarChoice = Integer.toString(Modelo.rs.getInt("idProducto"));
						rellenarChoice = rellenarChoice + "-"+ Modelo.rs.getString("nombreProducto");
						Vista.choAVProductoVenta.add(rellenarChoice);
					}
				}
				catch (SQLException sqle)
				{
					System.out.println("Error 2-"+sqle.getMessage());
				}
				finally
				{
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
				}
		Vista.altaVenta.setLayout(new FlowLayout());
		Vista.altaVenta.setSize(350, 160);
		Vista.altaVenta.setResizable(false);
		Vista.altaVenta.addWindowListener(this);
		Vista.btnAVAceptar.addActionListener(this);
		Vista.btnAVBorrar.addActionListener(this);
		Vista.altaVenta.add(Vista.lblAVPanaderiaVenta);
		Vista.altaVenta.add(Vista.choAVPanaderiaVenta);
		Vista.altaVenta.add(Vista.lblAVProductoVenta);
		Vista.altaVenta.add(Vista.choAVProductoVenta);
		Vista.altaVenta.add(Vista.lblAVFechaVenta);
		Vista.altaVenta.add(Vista.txtAVFechaVenta);
		Vista.altaVenta.add(Vista.btnAVAceptar);
		Vista.altaVenta.add(Vista.btnAVBorrar);
		Vista.altaVenta.setLocationRelativeTo(null);
		Vista.altaVenta.setVisible(true);
		
	    String[] FechaAmericana = fechaAmericana.split("-");
	    String fechaEuropea = FechaAmericana[2].toString() + "-" + FechaAmericana[1] + "-" + FechaAmericana[0];
		
		Vista.txtAVFechaVenta.setText(fechaEuropea);
		
	}

	public void actionPerformed(ActionEvent evento)
	{

		//Alta 

		if(evento.getSource().equals(Vista.btnAVAceptar)) 
		{
			if (Vista.choAVPanaderiaVenta.getSelectedItem().equals("Elige una panaderia") || Vista.choAVProductoVenta.getSelectedItem().equals("Elige un producto")) 
			{
				JOptionPane.showMessageDialog(Vista.altaVenta, "Tienes que elegir una panaderia y un producto");
			}
			else
			{
				try
				{

					String[] panaderiaElegida=Vista.choAVPanaderiaVenta.getSelectedItem().split("-");
					String panaderiaVenta = panaderiaElegida[0];
					String[] productoElgido=Vista.choAVProductoVenta.getSelectedItem().split("-");
					String productoVenta = productoElgido[0];
					String[] fechaCambiada = fechaAmericana.split("-");
					Modelo.ConexionBD();
					Modelo.sentencia = "INSERT INTO venden (idPanaderiaFK,idProductoFK,fechaDeVenta) VALUES (" + panaderiaVenta+ "," + productoVenta+ ",'"+fechaCambiada[0]+"-"+fechaCambiada[1]+"-"+fechaCambiada[2]+"')";
					Modelo.statement.executeUpdate(Modelo.sentencia);
				}

				catch (SQLException sqle)
				{
					System.out.println("Error 2-"+sqle.getMessage());
				}

				finally
				{
					Log.creacionLog("Alta de venta realizada");
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
					JOptionPane.showMessageDialog(Vista.altaVenta, "Alta de ventas realizada");
				}
			}

		}
		if(evento.getSource().equals(Vista.btnAProdBorrar)) 
		{
			Vista.txtAVFechaVenta.selectAll();
			Vista.txtAVFechaVenta.setText("");
			Vista.txtAVFechaVenta.requestFocus();
		}

	}

	public void windowClosing(WindowEvent arg0)
	{
		if(Vista.altaVenta.isActive())
		{
			Vista.altaVenta.setVisible(false);
		}
	}
}