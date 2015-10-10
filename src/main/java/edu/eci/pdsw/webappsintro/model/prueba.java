/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.webappsintro.model;

import edu.eci.pdsw.samples.entities.DetallePedido;
import edu.eci.pdsw.samples.entities.Pedido;
import edu.eci.pdsw.samples.entities.Producto;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author usuario
 */
public class prueba {
    
    public static void main(String args[]){
        ServicesFacade sf = ServicesFacade.getInstance();
        List<Producto> lista;
        lista=sf.getProductos();
        for (Producto p : lista) {
            System.out.println(p.getNombre());
        }
        System.out.println("__________________________________");
        Pedido p = sf.loadPedido(2);
        Set<DetallePedido> det;
        det= p.getDetallesPedido();
        for (DetallePedido dp: det) {
            System.out.println(dp.getProducto().getNombre());
        }
        
        p = new Pedido(4,new Timestamp(new Date().getTime()));
        Producto pr = new Producto(415165,"elProducto",4842115);
        p.addDetalle(new DetallePedido(5,pr));
        sf.SavePedido(p);
    }
}
