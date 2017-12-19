/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.edu;

import java.time.LocalTime;

/**
 *
 * @author buehler
 */
public class JCouchDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CouchDB couch = new CouchDB();

        //couch.getStatsTags();
        
        String test = new java.sql.Timestamp(System.currentTimeMillis()).toString() ;
        String json=couch.addDocument("{\"status\":\"002\",\"value_type\":\"scalar\",\"value\":\"2\",\"timestamp\":\""+test+"\"}");
        System.out.println(json);
    }
    
}
