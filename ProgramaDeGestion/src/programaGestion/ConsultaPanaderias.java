package programaGestion;

import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ConsultaPanaderias extends WindowAdapter implements ActionListener
{

	ConsultaPanaderias()
	{
		Vista.consultarPanaderia.setLayout(new FlowLayout());
		Vista.consultarPanaderia.setSize(550, 300);
		Vista.consultarPanaderia.setResizable(false);
		Vista.consultarPanaderia.addWindowListener(this);
		Vista.btnCPExportarPDF.addActionListener(this);
		Vista.consultarPanaderia.add(Vista.lblConsultarPanaderia);
		Vista.consultarPanaderia.add(Vista.taConsultarPanaderia);
		Vista.consultarPanaderia.add(Vista.btnCPExportarPDF);
		Vista.taConsultarPanaderia.setEditable(false);
		Vista.consultarPanaderia.setLocationRelativeTo(null);
		Vista.consultarPanaderia.setVisible(true);

		try	//Sentencia para recopilar los datos e introducirlos en el text area
		{
			Modelo.ConexionBD();
			Modelo.sentencia = "SELECT * FROM panaderias";
			Modelo.rs = Modelo.statement.executeQuery(Modelo.sentencia);
			Vista.taConsultarPanaderia.setText("");
			while(Modelo.rs.next())
			{
				if(Vista.taConsultarPanaderia.getText().length()==0)
				{
					Vista.taConsultarPanaderia.setText(Modelo.rs.getInt("idPanaderia")+
							", "+Modelo.rs.getString("direccionPanaderia"));
				}
				else
				{
					Vista.taConsultarPanaderia.setText(Vista.taConsultarPanaderia.getText() + "\n" +
							Modelo.rs.getInt("idPanaderia")+
							", "+Modelo.rs.getString("direccionPanaderia"));
				}
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2-"+sqle.getMessage());
		}
		finally
		{
			Log.creacionLog("Consulta de panaderia realizada");
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

	public void windowClosing(WindowEvent arg0)
	{
		if(Vista.consultarPanaderia.isActive())
		{
			Vista.consultarPanaderia.setVisible(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent evento) 
	{
		if(evento.getSource().equals(Vista.btnCPExportarPDF)) 
		{
			// Se crea el documento 
			Document documento = new Document();
			String resultado = "";
			try 
			{
				try	//Sentencia para recopilar los datos e introducirlos en la variable

				{
					Modelo.ConexionBD();
					Modelo.sentencia = "SELECT * FROM panaderias";
					Modelo.rs = Modelo.statement.executeQuery(Modelo.sentencia);
					Vista.taConsultaProducto.setText("");
					while(Modelo.rs.next())
					{
						resultado = resultado + Modelo.rs.getInt("idPanaderia")+
								"-"+Modelo.rs.getString("direccionPanaderia")+"\n";
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
				// Se crea el OutputStream para el fichero donde queremos dejar el pdf. 
				FileOutputStream ficheroPdf = new FileOutputStream("Panaderias.pdf");
				PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(22);
				// Se abre el documento. 
				documento.open();
				Paragraph titulo = new Paragraph("INFORME DE PANADERIAS", 
						FontFactory.getFont("arial", // fuente 
								22, // tama�o 
								Font.BOLD, // estilo 
								BaseColor.RED)); // color
				titulo.setAlignment(Element.ALIGN_CENTER);
				documento.add(titulo);
				// Sacar los datos
				String[] cadena = resultado.split("\n");
				PdfPTable tabla = new PdfPTable(2); // Se indica el n�mero de columnas
				tabla.setSpacingBefore(5); // Espaciado ANTES de la tabla
				tabla.addCell("ID");
				tabla.addCell("DIRECCI�N");
				String[] subCadena;
				for (int i = 0; i < cadena.length; i++) 
				{
					subCadena = cadena[i].split("-");
					for(int j = 0; j < 2;j++)
					{
						tabla.addCell(subCadena[j]);
					}
				}
				documento.add(tabla); 
				documento.close(); 
				//Abrimos el archivo PDF reci�n creado 
				try 
				{
					File path = new File ("Panaderias.pdf"); 
					Desktop.getDesktop().open(path); 
				}
				catch (IOException ex) 
				{
					System.out.println("Se ha producido un error al abrir el archivo PDF"); 
				}
			}
			catch ( Exception e ) 
			{ 
				System.out.println("Se ha producido un error al generar el archivo PDF");  
			}
		}
	}
}