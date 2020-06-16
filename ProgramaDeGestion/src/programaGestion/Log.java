package programaGestion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log
{
	public static void creacionLog(String mensaje)
	{
		
		try
		{
			String usuario = "";
			if(Controlador.tipoUsuario == 1)
			{
				usuario = "Administrador";
			}
			else
			{
				usuario = "Usuario";
			}
			FileWriter fw = new FileWriter("Log.log", true);
			BufferedWriter bw = new BufferedWriter(fw);
			// Objeto
			PrintWriter salida = new PrintWriter(bw);
			Date fechaHoraActual = new Date();
			// Formato del log
			DateFormat fechaHoraFormato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			salida.print("["+fechaHoraFormato.format(fechaHoraActual)+"]");
			salida.print("["+usuario+"]");
			salida.println("["+mensaje+"]");
			salida.println("["+Modelo.sentencia+"]");
			salida.close();
			bw.close();
			fw.close();
		}
		catch(IOException i)
		{
			System.out.println("Se produjo un error de archivo");
		}
	}
}