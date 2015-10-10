/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.persistence.mybatis;

import edu.eci.pdsw.samples.entities.DetallePedido;
import edu.eci.pdsw.samples.entities.Pedido;
import edu.eci.pdsw.samples.mybatis.mappers.PedidoMapper;
import edu.eci.pdsw.samples.persistence.DaoPedido;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author 2101738
 */
public class MybatisPedido implements DaoPedido{
    private PedidoMapper pm;
    
    public MybatisPedido (PedidoMapper pm){
        this.pm=pm;
    }
    @Override
    public void save(Pedido p) throws PersistenceException {
        pm.insertPedido(p);
        Set<DetallePedido> det= p.getDetallesPedido();
        for (DetallePedido dp : det) {
            pm.insertDetallePedido(p.getCodigo(),dp);
        }
    }

    @Override
    public Pedido load(int id) throws PersistenceException{
        Pedido p = pm.getPedido(id);
        Set<DetallePedido> st = pm.getDetallePedido(id);
        p.setDetallesPedido(st);
        return p;
    }

    @Override
    public void update(Pedido p) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // falta el update en pedidomapper
    }

    @Override
    public List<Pedido> loadAll() throws PersistenceException {
        return pm.getAllPedidos();
    }
}
