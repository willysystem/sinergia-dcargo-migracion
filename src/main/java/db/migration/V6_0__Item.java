//package db.migration;
//
//import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
//
//import com.healthmarketscience.jackcess.DatabaseBuilder;
//import com.healthmarketscience.jackcess.Row;
//import com.healthmarketscience.jackcess.Table;
//import com.sinergia.dcargo.client.shared.Guia;
//import com.sinergia.dcargo.client.shared.Item;
//import com.sinergia.dcargo.client.shared.Unidad;
//import com.sinergia.dcargo.migracion.UtilORM;
//
//import java.io.File;
//import java.sql.Connection;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
///**
// * Example of a Java-based migration.
// */
//public class V6_0__Item implements JdbcMigration {
//
//	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("sinergia-dcargo-model" );
//	private static EntityManager em = emf.createEntityManager();
//	
//    public void migrate(final Connection connection) throws Exception {
//    	
//    	System.out.println("--> Migrando ITEM ......" );
//    	
//    	List<Guia> guias = UtilORM.buscarTodos(Guia.class, em); 
//    	
//    	// 1. Obteniendo datos del origen
//    	Table table = DatabaseBuilder.open(new File("I://Consultorias//Kipustec//DCargo//bd_transportadora2012//BD_TRANSP.mdb")).getTable("ITEMS");
//    	
//    	try {
//    		em.getTransaction().begin();
//    		for(Row row : table) {
//    			Item item = new Item();
//    			item.setNroSecuencial(row.getInt("nun_sec"));
//    			item.setCantidad(row.getInt("cantidad"));
//    			item.setContenido(row.getString("contenido"));
//    			item.setPeso(row.getDouble("peso"));
//    			item.setMonto(row.getDouble("monto"));
//    			item.setActivo(row.getString("estado").equals("AC")?true:false);
//    			
//    			Unidad unidad = new Unidad();
//    			Long idUnidad = Long.valueOf(row.getInt("unidad")+"");
//    			unidad.setId(idUnidad<1?((-1)*idUnidad):idUnidad);
//    			item.setUnidad(unidad);
//    			
//    			item.setTotal(row.getDouble("monto"));
//    		
//    			Guia guia = UtilORM.buscarPorNroGuia(row.getInt("nsec_guia"), guias);
//    			item.setGuia(guia);
//    		
//    			em.persist(item);
//    		
//    			System.out.println(item);
//    		}
//    		em.getTransaction().commit();
//    		em.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			em.getTransaction().rollback();
//		}	
//    	
//    }
//}    