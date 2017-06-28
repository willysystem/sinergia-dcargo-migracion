//package db.migration;
//
//import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
//
//import com.healthmarketscience.jackcess.DatabaseBuilder;
//import com.healthmarketscience.jackcess.Row;
//import com.healthmarketscience.jackcess.Table;
//import com.sinergia.dcargo.client.shared.Cliente;
//import com.sinergia.dcargo.client.shared.Conocimiento;
//import com.sinergia.dcargo.client.shared.Guia;
//import com.sinergia.dcargo.client.shared.Oficina;
//import com.sinergia.dcargo.client.shared.TipoPago;
//import com.sinergia.dcargo.client.shared.Usuario;
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
//public class V5_0__Guias implements JdbcMigration {
//	
//	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("sinergia-dcargo-model" );
//	private static EntityManager em = emf.createEntityManager();
//	
//    public void migrate(final Connection connection) throws Exception {
//    	
//    	System.out.println("--> Migrando GUIA ......" );
//    	
//    	List<Conocimiento> conocimientos = UtilORM.buscarTodos(Conocimiento.class, em); 
//    	List<Cliente> clientes = UtilORM.buscarTodos(Cliente.class, em);
//    	List<Usuario> usuarios = UtilORM.buscarTodos(Usuario.class, em);
//    	
//    	// 1. Obteniendo datos del origen
//    	Table table = DatabaseBuilder.open(new File("I://Consultorias//Kipustec//DCargo//bd_transportadora2012//BD_TRANSP.mdb")).getTable("GUIAS");
//    	
////    	List<Guia> guias = new ArrayList<Guia>();
//    	
//    	try {
//    		em.getTransaction().begin();
//        	for(Row row : table) {
//        		Guia guia = new Guia();
//        		guia.setNroGuia(row.getInt("nro_guia"));
//        		guia.setFecha(row.getDate("fecha"));
//        		guia.setTotalGuia(row.getDouble("totalguia"));
//        		guia.setTotalPeso(row.getDouble("totalpeso"));
//        		guia.setTotalCantidad(row.getInt("totalcantidad"));
//        		guia.setAdjunto(row.getString("adjunto"));
//        		guia.setActivo(row.getString("estado").equals("AC")?true:false);
//        		guia.setNroFactura(row.getString("nro_factura"));
//        		guia.setFechaRegistro(row.getDate("fec_registro"));
//        		guia.setTotalMinimo(row.getDouble("total_minimo"));
//        		guia.setCiEntrega(row.getString("ci_entrega"));
//        		guia.setNovedadEntrega(row.getString("novedad_entrega"));
//        		guia.setResumenContenido(row.getString("resumen_contenido"));
//        		guia.setSaldoDestino(row.getDouble("saldo_destino"));
//        		guia.setPagoOrigen(row.getDouble("pago_origen"));
//        		guia.setNotaEntrega(row.getString("nota_entrega"));
//        		guia.setFechaEntrega(row.getDate("fec_entrega"));
//        		
//        		// Tipo Pago
//        		TipoPago tipoPago = new TipoPago();
//        		tipoPago.setId(Long.valueOf(row.getInt("tipo_pago")));
//        		tipoPago = em.merge(tipoPago);
//        		
//        		guia.setTipoPago(tipoPago);
//        		
//        		// Conocimiento
//        		Integer  nroConocimiento = row.getInt("nro_conocimiento");
//        		Conocimiento conocimiento = UtilORM.buscarPorNroConocimiento(nroConocimiento, conocimientos); //UtilORM.buscarEntityPorAtributo("nroConocimiento", nroConocimiento, Conocimiento.class);
//        		guia.setConocimiento(conocimiento);
//        		
//        		// Origen
//        		Integer codClienteOrigen = row.getInt("remite");
//        		Cliente clienteOrigen = UtilORM.buscarPorCodigoCliente(codClienteOrigen, clientes); //UtilORM.buscarEntityPorAtributo("codigo", codClienteOrigen, Cliente.class);
//        		Oficina oficinaOrigen = new Oficina();
//        		oficinaOrigen.setId(Long.valueOf(row.getInt("lugar_o")));
//        		String codUsuarioOrigen = row.getInt("cod_usuario") + "";
//        		Usuario usuarioOrigen = UtilORM.buscarPorCodigoUsuario(codUsuarioOrigen, usuarios); //UtilORM.buscarEntityPorAtributo("nombreUsuario", codUsuarioOrigen, Usuario.class);
//        		guia.setRemitente(clienteOrigen);
//        		guia.setOficinaOrigen(oficinaOrigen);
//        		guia.setUsuarioRegistro(usuarioOrigen);
//        		
//        		// Destino
//        		Integer codClienteDestino = row.getInt("consignata");
//        		Cliente clienteDestino = UtilORM.buscarPorCodigoCliente(codClienteDestino, clientes);//UtilORM.buscarEntityPorAtributo("codigo", codClienteDestino, Cliente.class);
//        		Oficina oficinaDestino = new Oficina();
//        		oficinaDestino.setId(Long.valueOf(row.getInt("lugar_d")));
//        		String codUsuarioEntrega = row.getInt("usuario_entrega") + "";
//        		Usuario usuarioDestino = null; UtilORM.buscarPorCodigoUsuario(codUsuarioEntrega, usuarios);// UtilORM.buscarEntityPorAtributo("nombreUsuario", codUsuarioEntrega, Usuario.class);
//        		guia.setConsignatario(clienteDestino);
//        		guia.setOficinaDestino(oficinaDestino);
//        		guia.setUsuarioEntrega(usuarioDestino);
//        		
//        		// Destino otro
//        		
//        		em.persist(guia);
//         	}
//    		em.getTransaction().commit();
//    		em.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			em.getTransaction().rollback();
//		}
//    	
//    }
//}    