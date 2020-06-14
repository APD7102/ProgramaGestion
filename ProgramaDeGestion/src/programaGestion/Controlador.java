package programaGestion;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import javax.swing.JFrame;

public class Controlador extends JFrame implements ActionListener, WindowListener
{
	private static final long serialVersionUID = 1L;
	
	static int tipoUsuario = 0;
	
	
	Controlador()
	{
		
		Vista.login.setLayout(new FlowLayout());
		Vista.login.setSize(315, 130);
		Vista.login.setResizable(false);
		Vista.login.setLocationRelativeTo(null);
		Vista.login.addWindowListener(this);
		Vista.btnAceptar.addActionListener(this);
		Vista.btnBorrar.addActionListener(this);
		Vista.login.add(Vista.lblUsuario);
		Vista.login.add(Vista.txtUsuario);
		Vista.login.add(Vista.lblContraseña);
		Vista.txtClave.setEchoChar('*');
		Vista.login.add(Vista.txtClave);
		Vista.login.add(Vista.btnAceptar);
		Vista.login.add(Vista.btnBorrar);
		Vista.login.setVisible(true);
		Vista.login.getContentPane();

	}

	// Declaro los eventos que suceden en el login
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(Vista.btnBorrar)) 
		{
			Vista.txtUsuario.selectAll();
			Vista.txtUsuario.setText("");
			Vista.txtClave.selectAll();
			Vista.txtClave.setText("");
			Vista.txtUsuario.requestFocus();
		}

		else if(evento.getSource().equals(Vista.btnAceptar)) 
		{
			char[] contraseña1 = Vista.txtClave.getPassword();
			String contraseña2 = new String(contraseña1);
			String contraseñaEncriptada = Encriptacion.getSHA256(contraseña2);
			Modelo.sentencia = "SELECT * FROM usuarios WHERE nombreUsuario = '"+ Vista.txtUsuario.getText()+ "'AND contraseñaUsuario = '"+ contraseñaEncriptada+"'";

			try
			{
				Modelo.ConexionBD();
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				Modelo.rs = Modelo.statement.executeQuery(Modelo.sentencia);
				if(Modelo.rs.next())

				{
					// Para saber si el usuario es administrador o usuario básico
					Modelo.sentencia = "SELECT tipoUsuario FROM usuarios WHERE nombreUsuario = '" + Vista.txtUsuario.getText()+"'";
					Modelo.rs = Modelo.statement.executeQuery(Modelo.sentencia);
					Modelo.rs.next();
					tipoUsuario = Modelo.rs.getInt("tipoUsuario");
					if (tipoUsuario == 1)
					{
						Vista.menuPrincipal.setLayout(new FlowLayout());
						Vista.menuPrincipal.setSize(800, 400);
						Vista.menuPrincipal.setResizable(false);
						Vista.menuPrincipal.addWindowListener(this);
						Vista.menuPrincipal.setLocationRelativeTo(null);
						Vista.menuPrincipal.setVisible(true);
						Vista.menuPrincipal.setJMenuBar(Vista.barraDeMenu);
						Vista.menuProductos.add(Vista.ProductosAlta);
						Vista.ProductosAlta.addActionListener(this);
						Vista.menuProductos.add(Vista.ProductosModificar);
						Vista.ProductosModificar.addActionListener(this);
						Vista.menuProductos.add(Vista.ProductosConsultar);
						Vista.ProductosConsultar.addActionListener(this);
						Vista.menuProveedores.add(Vista.ProveedoresAlta);
						Vista.ProveedoresAlta.addActionListener(this);
						Vista.menuProveedores.add(Vista.ProveedoresBaja);
						Vista.ProveedoresBaja.addActionListener(this);
						Vista.menuProveedores.add(Vista.ProveedoresModificar);
						Vista.ProveedoresModificar.addActionListener(this);
						Vista.menuProveedores.add(Vista.ProveedoresConsultar);
						Vista.ProveedoresConsultar.addActionListener(this);
						Vista.menuPanaderias.add(Vista.PanaderiasAlta);
						Vista.PanaderiasAlta.addActionListener(this);
						Vista.menuPanaderias.add(Vista.PanaderiasConsultar);
						Vista.PanaderiasConsultar.addActionListener(this);
						Vista.menuVentas.add(Vista.VentasAlta);
						Vista.VentasAlta.addActionListener(this);
						Vista.menuVentas.add(Vista.VentasConsultar);
						Vista.VentasConsultar.addActionListener(this);
						Vista.menuAyuda.add(Vista.Ayuda);
						Vista.Ayuda.addActionListener(this);
						Vista.barraDeMenu.add(Vista.menuProductos);
						Vista.barraDeMenu.add(Vista.menuProveedores);
						Vista.barraDeMenu.add(Vista.menuPanaderias);
						Vista.barraDeMenu.add(Vista.menuVentas);
						Vista.barraDeMenu.add(Vista.menuAyuda);
						Vista.login.setVisible(false);
						Vista.menuPrincipal.getContentPane();

					}
					else if (tipoUsuario == 2)
					{
						Vista.menuPrincipal.setLayout(new FlowLayout());
						Vista.menuPrincipal.setSize(800, 400);
						Vista.menuPrincipal.setResizable(false);
						Vista.menuPrincipal.addWindowListener(this);
						Vista.menuPrincipal.setLocationRelativeTo(null);
						Vista.menuPrincipal.setVisible(true);
						Vista.menuPrincipal.setJMenuBar(Vista.barraDeMenu);
						Vista.menuProductos.add(Vista.ProductosAlta);
						Vista.ProductosAlta.addActionListener(this);
						Vista.menuProveedores.add(Vista.ProveedoresAlta);
						Vista.ProveedoresAlta.addActionListener(this);
						Vista.menuPanaderias.add(Vista.PanaderiasAlta);
						Vista.PanaderiasAlta.addActionListener(this);
						Vista.menuVentas.add(Vista.VentasAlta);
						Vista.VentasAlta.addActionListener(this);
						Vista.menuAyuda.add(Vista.Ayuda);
						Vista.Ayuda.addActionListener(this);
						Vista.barraDeMenu.add(Vista.menuProductos);
						Vista.barraDeMenu.add(Vista.menuProveedores);
						Vista.barraDeMenu.add(Vista.menuPanaderias);
						Vista.barraDeMenu.add(Vista.menuVentas);
						Vista.barraDeMenu.add(Vista.menuAyuda);
						Vista.login.setVisible(false);
						Vista.menuPrincipal.getContentPane();
					}
				}

				else
				{
					Vista.errorLogin.setLayout(new FlowLayout());
					Vista.errorLogin.setSize(200, 100);
					Vista.errorLogin.setResizable(false);
					Vista.errorLogin.addWindowListener(this);
					Vista.errorLogin.add(Vista.lblErrorLogin);
					Vista.btnErrorLoginVolver.addActionListener(this);
					Vista.errorLogin.add(Vista.btnErrorLoginVolver);
					Vista.errorLogin.setLocationRelativeTo(null);
					Vista.errorLogin.setVisible(true);

				}
			}

			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
			}

			finally
			{
				Log.creacionLog("Conexión de usuario realizada");
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
		}

		// Alta de productos
		else if(evento.getSource().equals(Vista.ProductosAlta)) 
		{
			new AltaProductos();
		}
		// Modificacion de productos
		else if(evento.getSource().equals(Vista.ProductosModificar)) 
		{
			new ModificarProductos();
		}
		// Consulta de productos;
		else if(evento.getSource().equals(Vista.ProductosConsultar)) 
		{
			new ConsultaProductos();
		}
		// Alta de proveedores
		else if(evento.getSource().equals(Vista.ProveedoresAlta)) 
		{
			new AltaProveedores();
		}
		// Baja de proveedores
		else if(evento.getSource().equals(Vista.ProveedoresBaja)) 
		{
			new BajaProveedores(); 
		}
		// Modificacion de proveedores
		else if(evento.getSource().equals(Vista.ProveedoresModificar)) 
		{
			new ModificarProveedores();
		}
		// Consulta de proveedores;
		else if(evento.getSource().equals(Vista.ProveedoresConsultar)) 
		{
			new ConsultaProveedores();
		}	
		// Alta de locales
		else if(evento.getSource().equals(Vista.PanaderiasAlta)) 
		{
			new AltaPanaderias();
		}
		// Consulta de locales;
		else if(evento.getSource().equals(Vista.PanaderiasConsultar)) 
		{
			new ConsultaPanaderias();
		}
		// Alta de ventas
		else if(evento.getSource().equals(Vista.VentasAlta)) 
		{
			new AltaVentas();
		}
		// Consulta de ventas;
		else if(evento.getSource().equals(Vista.VentasConsultar)) 
		{
			new ConsultaVentas();
		}
		// Botón de ayuda;
		else if(evento.getSource().equals(Vista.Ayuda)) 
		{
			new Ayuda();
		}
		else
		{
			// Tareas del Volver
			Vista.errorLogin.setVisible(false);
		}
	}

	public void windowClosing(WindowEvent arg0)
	{
		if(Vista.errorLogin.isActive())
		{
			Vista.errorLogin.setVisible(false);
		}
		else
		{
			Log.creacionLog("Salida del programa");
			System.exit(0);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
}