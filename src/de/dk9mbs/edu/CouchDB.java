/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.edu;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

/**
 *
 * @author buehler
 */
public class CouchDB {
    private static final Logger logger = Logger.getLogger(CouchDB.class.getName());
    private String _url="";
    private String _database="";
    
    
    public CouchDB() {
        this._url="http://127.0.0.1:5984";
        this._database="phl";
    }
    
    public String addDocument(String json) {
        try {
            HttpURLConnection con = (HttpURLConnection) this._getURL().openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            java.io.OutputStream str = con.getOutputStream();
            str.write(json.getBytes());
            str.flush();
            str.close();            
            
            int httpCode=con.getResponseCode();
            return this._getResponse(con) ;
            
        } catch(java.io.IOException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return "";
    }

    
    public java.util.ArrayList getStatsTags() {
        try {
            java.util.ArrayList<String> status = new java.util.ArrayList<>();
            HttpURLConnection con = (HttpURLConnection) this._getURL("_design/phl/_view/status_tags?group=true").openConnection() ;
            con.setRequestMethod("GET");
            con.connect();
            
            String json = this._getResponse(con);
            JSONObject jobj = new JSONObject(json);
            JSONArray ary = jobj.getJSONArray("rows");
            
            JSONObject item;
            for(int x=0;x<ary.length();x++ ) {
                item=ary.getJSONObject(x);
                status.add(item.getString("key"));
            }
            
            return status;
        } catch (java.io.IOException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        
        return null;
    }

    private String _getResponse(HttpURLConnection con) {
        try{
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(con.getInputStream()));

            String line;
            String out="";
            while ((line = in.readLine()) != null)  {
                out+=line;
            }
            return out ;
            
        } catch(java.io.IOException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        
        return "";
    }
    
    private URL _getURL() { 
        return this._getURL("");
    }

    private URL _getURL(String uri) {
        try {
            if(!uri.startsWith("/") && !uri.equals("") ) {
                uri="/"+uri;
            }
            URL url;
            url = new URL(this._url+"/"+this._database+uri);
            return url;
        } catch (java.io.IOException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return null;
    }
}
