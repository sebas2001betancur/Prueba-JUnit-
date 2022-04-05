package com.app.productos;

import org.springframework.data.repository.CrudRepository;

public interface ProductoRepositorio extends CrudRepository<Producto, Integer> {

    public Producto findByNombre(String nombre);
    public Producto save(Producto producto);
}
