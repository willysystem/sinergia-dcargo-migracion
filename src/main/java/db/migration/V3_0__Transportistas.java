package db.migration;

import org.apache.commons.dbutils.QueryRunner;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import com.sinergia.dcargo.client.shared.Transportista;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Example of a Java-based migration.
 */
public class V3_0__Transportistas implements JdbcMigration {
	
	
    public void migrate(final Connection connection) throws Exception {
    	
    	System.out.println("--> Migrando TRANSPORTISTAS ......" );
    	
    	// 1. Obteniendo datos del origen
    	Table table = DatabaseBuilder.open(new File("I://Consultorias//Kipustec//DCargo//bd_transportadora2012//BD_TRANSP.mdb")).getTable("TRANSPORTISTA");
    	
    	
    	List<String> brevets = new ArrayList<>();
    	List<Transportista> transportistas = new ArrayList<Transportista>();
    	for(Row row : table) {
    		
    		String brevet = row.getString("brevet_ci");
    		boolean contiene = brevets.contains(brevet);//contiene(brevets, brevet);
    		
    		if(!contiene){
    			brevets.add(brevet);
    			
    			Transportista trans = new Transportista();
        		trans.setCodigo(row.getInt("codigo"));
        		trans.setBrevetCi(row.getString("brevet_ci"));
        		trans.setNombre(row.getString("nombre"));
        		trans.setDireccion(row.getString("direccion"));
        		trans.setTelefono(row.getString("telefono"));
        		trans.setPlaca(row.getString("placa"));
        		trans.setMarca(row.getString("marca"));
        		trans.setColor(row.getString("color"));
        		trans.setCapacidad(row.getString("capacidad"));
        		trans.setVecino_de(row.getString("vecino_de"));
        		trans.setCi(row.getString("ci"));
        		trans.setNit(row.getString("nit"));
        		trans.setEstado('V');
         	    transportistas.add(trans);
    		}
     	}
    	
    	
    	Object[][] params = new Object[transportistas.size()][13];
    	int i = 0;
    	for (Transportista g : transportistas) {
			params[i][0] = g.getCodigo();
			params[i][1] = g.getBrevetCi(); 
			params[i][2] = g.getNombre();
			params[i][3] = g.getDireccion();
			params[i][4] = g.getTelefono();
			params[i][5] = g.getPlaca();
			params[i][6] = g.getMarca();
			params[i][7] = g.getColor();
			params[i][8] = g.getCapacidad();
			params[i][9] = g.getVecino_de();
			params[i][10] = g.getCi();		
			params[i][11] = g.getNit();
			params[i][12] = g.getEstado().toString();
			i++;
		}
    	
    	// 2. Migrando a MySql
    	QueryRunner runDestino = new QueryRunner();
    	runDestino.batch(connection, "insert into transportista (codigo, brevetCi, nombre, direccion, telefono, placa, marca, color, capacidad, vecino_de, ci, nit, estado)" + 
    	                                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?)", params );
    	
    }
    
    private boolean contiene(List<Integer> list, Integer item) {
    	for(Integer i: list)
    		if(i == item) return true;
    	return false;
    }
    
}    