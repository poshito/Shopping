/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.persistence.mybatis;

import edu.eci.pdsw.samples.entities.Producto;
import edu.eci.pdsw.samples.mybatis.mappers.ProductoMapper;
import edu.eci.pdsw.samples.persistence.DaoProducto;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.util.List;

/**
 *
 * @author 2101738
 */
public class MybatisProducto implements DaoProducto {
    private ProductoMapper pm;
    
    public MybatisProducto(ProductoMapper pm){
        this.pm=pm;
    }
   

    @Override
    public Producto load(int idProducto) throws PersistenceException {
        return pm.load(idProducto);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(Producto p) throws PersistenceException {
        pm.save(p);
    }

    @Override
    public List<Producto> loadAll() throws PersistenceException {       
        return pm.loadAll();
    }
    
}
