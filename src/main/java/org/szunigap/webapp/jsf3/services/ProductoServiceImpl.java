package org.szunigap.webapp.jsf3.services;

import jakarta.annotation.Resource;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.szunigap.webapp.jsf3.models.Categoria;
import org.szunigap.webapp.jsf3.models.Producto;
import org.szunigap.webapp.jsf3.repositories.CrudRepository;
import org.szunigap.webapp.jsf3.repositories.ProductoRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
@Stateless
@DeclareRoles({"USER", "ADMIN"})
public class ProductoServiceImpl implements ProductoService{

    @Inject CrudRepository<Categoria> repositoryCategoria;
    @Inject
    private ProductoRepository repository;
    @Resource
    private SessionContext ctx;
    @Override
    @PermitAll
    public List<Producto> listar()  {
        Principal usuario = ctx.getCallerPrincipal();
        String username = usuario.getName();
        System.out.println("username: " + username);
        //Invocando el metodo del contexto
        if (ctx.isCallerInRole("ADMIN")){
            System.out.println("Hola soy un administrador");
        } else if (ctx.isCallerInRole("USER")){
            System.out.println("Hola soy un usuario normal");
        } else {
            System.out.println("Hola soy un usuario anonimo");
         //   throw new SecurityException("Losentimos no tiens permiso para acceder a esta página");
        }
        return repository.listar();
    }

    @RolesAllowed({"USER", "ADMIN"})
    @Override
    public Optional<Producto> porId(Long id) {
            return Optional.ofNullable(repository.porId(id));
    }

    @Override
    @RolesAllowed({"ADMIN"})
    public void guardar(Producto producto) {
        repository.guardar(producto);
    }

    @Override
    @RolesAllowed({"ADMIN"})
    public void eliminar(Long id) {
        repository.eliminar(id);
    }

    @Override
    @RolesAllowed({"USER", "ADMIN"})
    public List<Categoria> listarCategoria() {
        return repositoryCategoria.listar();
    }

    @Override
    @RolesAllowed({"USER", "ADMIN"})
    public Optional<Categoria> porIdCategoria(Long id) {
        return Optional.ofNullable(repositoryCategoria.porId(id));
    }

    @Override
    @RolesAllowed({"USER", "ADMIN"})
    public List<Producto> buscarPorNombre(String nombre) {
        return repository.buscarPorNombre(nombre);
    }
}
