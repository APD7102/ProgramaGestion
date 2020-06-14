package programaGestion;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class AltaProductos extends WindowAdapter implements ActionListener
{

	AltaProductos()
	{
	
		try
		{	
			Vista.choAPProveedorProducto.removeAll();
			Modelo.ConexionBD();
			Modelo.sentencia = "SELECT * FROM proveedores";
			Modelo.rs = Modelo.statement.executeQuery(Modelo.sentencia);

			while(Modelo.rs.next())
			{
				String rellenarChoice = Integer.toString(Modelo.rs.getInt("idProveedor"));
				rellenarChoice = rellenarChoice + "-"+ Modelo.rs.getString("nombreProveedor");
				Vista.choAPProveedorProducto.add(rellenarChoice);
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
		Vista.altaProducto.setLayout(new FlowLayout());
		Vista.altaProducto.setSize(325, 300);
		Vista.altaProducto.setResizable(false);
		Vista.altaProducto.addWindowListener(this);
		Vista.btnAProdAceptar.addActionListener(this);
		Vista.btnAProdBorrar.addActionListener(this);
		Vista.altaProducto.add(Vista.lblAPNombreProducto);
		Vista.altaProducto.add(Vista.txtAPNombreProducto);
		Vista.altaProducto.add(Vista.lblAPPrecioProducto);
		Vista.altaProducto.add(Vista.txtAPPrecioProducto);
		Vista.altaProducto.add(Vista.lblAPStockProducto);
		Vista.altaProducto.add(Vista.txtAPStockProducto);
		Vista.altaProducto.add(Vista.lblAPDescripcionProducto);
		Vista.altaProducto.add(Vista.taAPDescripcionProducto);
		Vista.altaProducto.add(Vista.lblAPProveedorProducto);
		Vista.altaProducto.add(Vista.choAPProveedorProducto);
		Vista.altaProducto.add(Vista.btnAProdAceptar);
		Vista.altaProducto.add(Vista.btnAProdBorrar);
		Vista.altaProducto.setLocationRelativeTo(null);
		Vista.altaProducto.setVisible(true);
		Vista.txtAPNombreProducto.selectAll();
		Vista.txtAPNombreProducto.setText("");
		Vista.txtAPPrecioProducto.selectAll();
		Vista.txtAPPrecioProducto.setText("");
		Vista.txtAPStockProducto.selectAll();
		Vista.txtAPStockProducto.setText("");		
	}

	public void actionPerformed(ActionEvent evento)
	{

		// Altas

		if(evento.getSource().equals(Vista.btnAProdAceptar)) 
		{
			try
			{
				String[] proveedorElegido=Vista.choAPProveedorProducto.getSelectedItem().split("-");
				String ProveedorProducto = proveedorElegido[0];
				Modelo.ConexionBD();
				Modelo.sentencia = "INSERT INTO productos (nombreProducto,precioProducto,stockProducto,descripcionProducto,idProveedorFK) VALUES ('" + Vista.txtAPNombreProducto.getText()+ "','" + Vista.txtAPPrecioProducto.getText()+ "',"+ Vista.txtAPStockProducto.getText()+",'"+ Vista.taAPDescripcionProducto.getText()+"','"+ProveedorProducto+"')";
				Modelo.statement.executeUpdate(Modelo.sentencia);
			}

			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
			}

			finally
			{
				Log.creacionLog("Alta de producto realizada");
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
				JOptionPane.showMessageDialog(Vista.altaProducto, "Alta de producto realizada");
			}

		}
		if(evento.getSource().equals(Vista.btnAProdBorrar)) 
		{
			Vista.txtAPNombreProducto.selectAll();
			Vista.txtAPNombreProducto.setText("");
			Vista.txtAPPrecioProducto.selectAll();
			Vista.txtAPPrecioProducto.setText("");
			Vista.txtAPStockProducto.selectAll();
			Vista.txtAPStockProducto.setText("");
			Vista.taAPDescripcionProducto.selectAll();
			Vista.taAPDescripcionProducto.setText("");
			Vista.txtAPNombreProveedor.requestFocus();
		}

	}

	public void windowClosing(WindowEvent arg0)
	{
		if(Vista.altaProducto.isActive())
		{
			Vista.altaProducto.setVisible(false);
		}
	}
}
