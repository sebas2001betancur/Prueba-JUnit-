package com.app.productos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PproductoTests {

    @Autowired
    private ProductoRepositorio repositorio;

    @Test
    @Rollback(false)
    @Order(1)
    public void testguardarProducto() {
        Producto producto = new Producto("Televisor Samsung HD", 3000);
        Producto productoGuardado = repositorio.save(producto);

        assertNotNull(productoGuardado);
    }

    @Test
    @Order(2)
    public void testbuscarproductoPorNombre() {
      String nombre = "Televisor Samsung HD";
      Producto producto = repositorio.findByNombre(nombre);

      assertThat(producto.getNombre()).isEqualTo(nombre);

    }
    
    @Test
    @Order(3)
    public void testBuscarProductoPorNombreNoExistente() {
        String nombre = "IPhone 11";
        Producto producto = repositorio.findByNombre(nombre);

        assertNull(producto);
    }

    @Test
    @Rollback(false)
    @Order(4)
    public void TestActualizarProducto() {
        String nombreProducto = "Televisor HD"; //el nuevo valor
        Producto producto = new Producto(nombreProducto, 2000); //valores nuevos
        producto.setId(1); //ID del producto a modificar

        repositorio.save(producto);

        Producto productoActualizado = repositorio.findByNombre(nombreProducto);
        assertThat(productoActualizado.getNombre()).isEqualTo(nombreProducto);

    }

    @Test
    @Order(5)
    public void testListarProductos() {
        List<Producto> productos = (List<Producto>) repositorio.findAll();

        for (Producto producto : productos) {
            System.out.println(producto);
        }

        assertThat(productos).size().isGreaterThan(0);

    }

    @Test
    @Rollback(false)
    @Order(6)
    public void testEliminarProducto() {
        Integer id = 1;

        boolean esExistenteAntesDeEliminar = repositorio.findById(id).isPresent(); //si existe me va a dar un true

        repositorio.deleteById(id);

        boolean noExisteDespuesDeEliminar = repositorio.findById(id).isPresent();//me va a dar false por que ya no existe

        assertTrue(esExistenteAntesDeEliminar);
        assertFalse(noExisteDespuesDeEliminar);

    }

}
