package org.szunigap.webapp.jsf3.services;

import jakarta.ejb.Local;
import org.szunigap.webapp.jsf3.models.Categoria;
import org.szunigap.webapp.jsf3.models.Producto;

import java.util.List;
import java.util.Optional;

@Local
public interface ProductoService {
    List<Producto> listar();
    Optional<Producto> porId(Long id);
    void guardar(Producto producto);
    void eliminar(Long id);
    List<Categoria> listarCategoria();
    Optional<Categoria> porIdCategoria(Long id);
    List<Producto> buscarPorNombre(String nombre);

}
