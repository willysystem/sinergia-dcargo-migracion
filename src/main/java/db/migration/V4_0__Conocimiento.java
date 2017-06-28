//package db.migration;
//
//import org.apache.commons.dbutils.QueryRunner;
//import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
//
//import com.healthmarketscience.jackcess.DatabaseBuilder;
//import com.healthmarketscience.jackcess.Row;
//import com.healthmarketscience.jackcess.Table;
//import com.sinergia.dcargo.client.shared.Conocimiento;
//import com.sinergia.dcargo.client.shared.Oficina;
//import com.sinergia.dcargo.client.shared.Transportista;
//import com.sinergia.dcargo.client.shared.Usuario;
//import com.sinergia.dcargo.migracion.UtilORM;
//
//import java.io.File;
//import java.sql.Connection;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Example of a Java-based migration.
// */
//public class V4_0__Conocimiento implements JdbcMigration {
//	
//    public void migrate(final Connection connection) throws Exception {
//    	
//    	// 1. Obteniendo datos del origen
//    	Table table = DatabaseBuilder.open(new File("I://Consultorias//Kipustec//DCargo//bd_transportadora2012//BD_TRANSP.mdb")).getTable("CONOCIMIENTO");
//    	
//    	List<Conocimiento> conocimientos = new ArrayList<Conocimiento>();
//    	for(Row row : table) {
//    		Conocimiento cono = new Conocimiento();
//    		cono.setNroConocimiento(row.getInt("nro_conocimiento"));
//    		cono.setFecha(row.getDate("fecha"));
//    		
//    		Oficina oficinaOrigen = new Oficina();
//    		oficinaOrigen.setId(Long.valueOf(row.getInt("lugar_o")));
//    		cono.setOficinaOrigen(oficinaOrigen);
//    		
//    		Oficina oficinaDestino = new Oficina();
//    		oficinaDestino.setId(Long.valueOf(row.getInt("lugar_d")));
//    		cono.setOficinaDestino(oficinaDestino);
//    		
//    		cono.setMulta(row.getDouble("multa"));
//    		cono.setDias(row.getInt("dias"));
//    		cono.setObservacion(row.getString("observacion"));
//    		cono.setAdjunto(row.getString("adjunto1"));
//    		cono.setActivo(row.getString("estado").equals("AC")?true:false);
//    		
//    		
//    		Integer codTransportista = row.getInt("transportista");
//    		Transportista transportista = UtilORM.buscarEntityPorAtributo("codigo", codTransportista, Transportista.class);
//    		cono.setTransportista(transportista);
//    		
//    		cono.setFlete(row.getDouble("flete"));
//    		cono.setAcuenta(row.getDouble("acuenta"));
//    		cono.setEndestino(row.getDouble("endestino"));
//    		cono.setSaldo(row.getDouble("saldo"));
//    		
//    		String nombreUsusario = row.getInt("cod_usuario") + "";
//    		Usuario usuario = UtilORM.buscarEntityPorAtributo("nombreUsuario", nombreUsusario, Usuario.class);
//    		cono.setUsuario(usuario);
//    		
//    		cono.setFechaRegistro(row.getDate("fec_registro"));
//    		
//    		Integer codChofer = row.getInt("chofer");
//    		Transportista chofer = UtilORM.buscarEntityPorAtributo("codigo", codChofer, Transportista.class);
//    		cono.setTransportistaChofer(chofer);
//    		
//    		cono.setAdjunto2(row.getString("adjunto2"));
//    		cono.setAclaracion(row.getString("aclaracion1"));
//    		cono.setAclaracion2(row.getString("aclaracion2"));
//    		
//    		System.out.println("add: "  + cono);
//    		conocimientos.add(cono);
//     	}
//    	
//    	
//    	Object[][] params = new Object[conocimientos.size()][20];
//    	int i = 0;
//    	for (Conocimiento g : conocimientos) {
//			params[i][0] = g.getNroConocimiento();
//			params[i][1] = g.getFecha();
//			params[i][2] = g.getOficinaOrigen().getId();
//			params[i][3] = g.getOficinaDestino().getId();
//			params[i][4] = g.getMulta();
//			params[i][5] = g.getDias();
//			params[i][6] = g.getObservacion();
//			params[i][7] = g.getAdjunto();
//			params[i][8] = g.getActivo();
//			params[i][9] = g.getTransportista().getId();
//			params[i][10] = g.getFlete();
//			params[i][11] = g.getAcuenta();
//			params[i][12] = g.getEndestino();
//			params[i][13] = g.getSaldo();
//			params[i][14] = g.getUsuario().getId();
//			params[i][15] = g.getFechaRegistro();
//			params[i][16] = g.getTransportistaChofer().getId();
//			params[i][17] = g.getAdjunto2();
//			params[i][18] = g.getAclaracion();
//			params[i][19] = g.getAclaracion2();
//			i++;
//		}
//    	
//    	// 2. Migrando a MySql
//    	String sql = "insert into conocimiento (nroConocimiento, fecha , oficinaOrigen_id, oficinaDestino_id, multa, dias, observacion, adjunto," +
//    	                                       "activo, transportista_id, flete, acuenta, endestino, saldo, usuario_id, fechaRegistro, transportistaChofer_id, adjunto2, aclaracion, aclaracion2) ";
//    	QueryRunner runDestino = new QueryRunner();
//    	int[] inserts = runDestino.batch(connection,  sql + 
//    	                                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", params);
//    	
//    	for (int j : inserts) {
//			System.out.println("afectado: " + j);
//		}
//    	
//    }
//}    