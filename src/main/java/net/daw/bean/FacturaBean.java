/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;




/**
 *
 * @author a044879742a
 */
public class FacturaBean {
  	private int id;
        private date fecha; 
        private float iva;
        private int id_usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public date getFecha() {
        return fecha;
    }

    public void setFecha(date fecha) {
        this.fecha = fecha;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
	


	
    
}
