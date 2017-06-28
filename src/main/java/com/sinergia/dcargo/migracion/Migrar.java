package com.sinergia.dcargo.migracion;

import org.flywaydb.core.Flyway;

/**
 * 
 * @author Willy Hurtado Vela 
 *         willysystems@gmail.com
 */
public class Migrar {
	
    public static void main(String[] args) {
    	
    	// 1. Conexion origen
    	
        // Create the Flyway instance
        Flyway flyway = new Flyway();

        // Point it to the database
        flyway.setDataSource("jdbc:mysql://localhost:3306/dcargo", "root", "control123!");
//        flyway.setDataSource("jdbc:mysql://ec2-54-214-97-192.us-west-2.compute.amazonaws.com:3306/dcargo","root", "Monamis_123!");
        // Start the migration
        flyway.baseline();
        
//        flyway.clean();
//        flyway.info();
        flyway.migrate();
        
        
    }
}