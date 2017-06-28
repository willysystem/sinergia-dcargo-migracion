package db.migration;

import org.apache.commons.dbutils.QueryRunner;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import com.sinergia.dcargo.client.shared.Cliente;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example of a Java-based migration.
 */
public class V2_0__Clientes implements JdbcMigration {
	
	
    public void migrate(final Connection connection) throws Exception {
    	
    	System.out.println("--> Migrando CLIENTES ......" );
    	
    	// 1. Obteniendo datos del origen
    	Table table = DatabaseBuilder.open(new File("I://Consultorias//Kipustec//DCargo//bd_transportadora2012//BD_TRANSP.mdb")).getTable("CLIENTES");
    	
    	List<Cliente> clientes = new ArrayList<Cliente>();
    	Pattern pattern = Pattern.compile("^\\d*");
    	Set<String> nombres = new HashSet<>();
    	Set<String> nits = new HashSet<>();
    	for(Row row : table) {
    		String nombre = row.getString("nombre");
    		boolean contieneNombre = nombres.contains(nombre);
    		if(!contieneNombre) nombres.add(nombre);
    		
    		String nit = row.getString("nit");
    		//Matcher p = pattern.matcher(nit);
    		boolean contieneNit = nits.contains(nit);
    		if(!contieneNit) nits.add(nit);
    		
    		if(nit == null || nit.equals("") || !isNumeric(nit) || contieneNombre || contieneNit) continue;
    		
    		Cliente cliente = new Cliente();
    		cliente.setCodigo(row.getInt("codigo"));
    		cliente.setNombre(row.getString("nombre"));
    		cliente.setDireccion(row.getString("direccion"));
    		cliente.setTelefono(row.getString("telefono"));
    		cliente.setNit(nit);
    		cliente.setCi(row.getString("ci"));
    		cliente.setEstado('V');
     	    clientes.add(cliente);
     	}
    	
    	Object[][] params = new Object[clientes.size()][7];
    	int i = 0;
    	for (Cliente g : clientes) {
			params[i][0] = g.getCodigo(); 
			params[i][1] = g.getNombre();
			params[i][2] = g.getDireccion();
			params[i][3] = g.getTelefono();
			params[i][4] = g.getNit();
			params[i][5] = g.getCi();
			params[i++][6] = g.getEstado().toString();
		}
    	System.out.println("     --> Migrando CLIENTES ...  cargado Parametros" );
    	
    	// 2. Migrando a MySql
    	QueryRunner runDestino = new QueryRunner();
    	runDestino.batch(connection, "insert into cliente (codigo, nombre, direccion, telefono, nit, ci, estado)" + 
    	                                    "values (?,?,?,?,?,?,?)", params);
    	
    	System.out.println("     --> Migrando CLIENTES ...  Terminado" );
    	
    	
    }
    
    private boolean isNumeric(String cadena){
    	try {
    		Integer.parseInt(cadena);
    		return true;
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }
    
   
}    