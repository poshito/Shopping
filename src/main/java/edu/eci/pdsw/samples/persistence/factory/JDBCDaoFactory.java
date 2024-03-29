/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.persistence.factory;

import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.DaoPedido;
import edu.eci.pdsw.samples.persistence.DaoProducto;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import edu.eci.pdsw.samples.persistence.jdbcimpl.JDBCDaoPedido;
import edu.eci.pdsw.samples.persistence.jdbcimpl.JDBCDaoProducto;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class JDBCDaoFactory extends DaoFactory {

    Connection con;
    Properties prop = new Properties();
    InputStream input = null;
    
    
    private Connection openConnection() throws PersistenceException{
        try {
            input = new FileInputStream(new File("./target/classes/config.properties"));
            //input = ClassLoader.getSystemResourceAsStream("config.properties");
            prop.load(input);
            String url=prop.getProperty("url");//"jdbc:mysql://desarrollo.is.escuelaing.edu.co:3306/bdprueba";
            String driver=prop.getProperty("driver");//"com.mysql.jdbc.Driver";
            String user=prop.getProperty("user");//"bdprueba";
            String pwd=prop.getProperty("pwd");//"bdprueba";
            
            Class.forName(driver);
            Connection _con=DriverManager.getConnection(url,user,pwd);
            _con.setAutoCommit(false);
            return _con;
        } catch (ClassNotFoundException | SQLException ex) {
            throw new PersistenceException("Error on connection opening.",ex);
        }catch (FileNotFoundException ex) {
                Logger.getLogger(DaoFactory.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("Invalid factory configuration.",ex);
        } catch (IOException ex) {
                Logger.getLogger(DaoFactory.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("Invalid factory configuration.",ex);
        }

    }
    
    @Override
    public void beginSession() throws PersistenceException {
        try {
            if (con==null || con.isClosed()){            
                con=openConnection();
            }
            else{
                throw new PersistenceException("Session was already opened.");
            }
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while opening a JDBC connection.",ex);
        }
        
    }

    @Override
    public DaoProducto getDaoProducto() {        
        return new JDBCDaoProducto(con);
    }

    @Override
    public DaoPedido getDaoPedido() {
        return new JDBCDaoPedido(con);
    }

    @Override
    public void endSession() throws PersistenceException {
        try {
            if (con==null || con.isClosed()){
                throw new PersistenceException("Conection is null or is already closed.");
            }
            else{
                con.close();
            }            
        } catch (SQLException ex) {
            throw new PersistenceException("Error on connection closing.",ex);
        }
    }

    @Override
    public void commitTransaction() throws PersistenceException {
        try {
            if (con==null || con.isClosed()){
                throw new PersistenceException("Conection is null or is already closed.");
            }
            else{
                con.commit();
            }            
        } catch (SQLException ex) {
            throw new PersistenceException("Error on connection closing.",ex);
        }        
    }

    @Override
    public void rollbackTransaction() throws PersistenceException {
                try {
            if (con==null || con.isClosed()){
                throw new PersistenceException("Conection is null or is already closed.");
            }
            else{
                con.rollback();
            }            
        } catch (SQLException ex) {
            throw new PersistenceException("Error on connection closing.",ex);
        }
    }
    
}
