package org.szunigap.webapp.jsf3.repositories;

import org.szunigap.webapp.jsf3.models.Producto;

import java.util.List;

public interface ProductoRepository extends CrudRepository<Producto> {
    List<Producto> buscarPorNombre(String nombre);
}
