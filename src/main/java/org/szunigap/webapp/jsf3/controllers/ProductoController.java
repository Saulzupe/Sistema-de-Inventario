package org.szunigap.webapp.jsf3.controllers;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.szunigap.webapp.jsf3.models.Categoria;
import org.szunigap.webapp.jsf3.models.Producto;
import org.szunigap.webapp.jsf3.services.ProductoService;

import java.util.List;
import java.util.ResourceBundle;

@Model //Componente manejado por el contenedor
public class ProductoController {
    private Producto producto;
    private Long id;

    @Inject
    private ProductoService service;
    @Inject
    private FacesContext facesContext;
    @Inject
    private ResourceBundle bundle;
    private List<Producto> listado;
    private String textoBuscar;
    @PostConstruct
    public void init() {
        this.listado = service.listar(); // Por cada request se genera el componente listado
        producto = new Producto();

    }
    @Produces
    @Model
    public String titulo(){
        return bundle.getString("producto.texto.titulo");
    }
    @Produces
    @Model
    public String tituloForm(){
        return "Formulario Producto";
    }
    /*@Produces
    @RequestScoped
    @Named("listado")
    public List<Producto> findAll(){
        //return Arrays.asList(new Producto("peras"), new Producto("manzanas"), new Producto("mandarinas"));
        return service.listar();
    }*/
   // @Produces
    //@Model
    public Producto producto(){
        this.producto = new Producto();
        if (id != null && id > 0){
            //producto = service.porId(id).orElseThrow();
            service.porId(id).ifPresent(p -> {
                this.producto = p; //Viene desde la consulta JPA o service
            });
        }
        return producto;
    }
    @Produces
    @Model
    public List<Categoria> categorias() {
        return service.listarCategoria();
    }
    // Tenemos un atributo Id en la clase controladora, luego en la vista asignamos el id mediante el boton cuando se hace click
    // pasamos el id ejecutamos el boton editar a su vez cargamos la vista formulario y como existe el id va a poblar los datos
    public void editar(Long id){
        this.id = id;
        producto();
    }
    public void guardar(){ //Metodo de acción (Evento)
        System.out.println(producto);
        if (producto.getId() != null && producto.getId() > 0) {
            facesContext.addMessage(null, new FacesMessage(String.format(bundle.getString("producto.mensaje.editar"), producto.getNombre())));
        } else {
            facesContext.addMessage(null, new FacesMessage(String.format(bundle.getString("producto.mensaje.crear"), producto.getNombre())));
        }
        service.guardar(producto);
        listado = service.listar(); // Refrescamos el listado
        //return "index.xhtml";
        producto = new Producto();
    }
    public void eliminar(Producto producto){
        service.eliminar(producto.getId());
        facesContext.addMessage(null, new FacesMessage(String.format(bundle.getString("producto.mensaje.eliminar"), producto.getNombre())));
        // Eliminamos y actualizamos - eliminar se invoca desde el index
        listado = service.listar();

    }
    public void buscar(){
        this.listado = service.buscarPorNombre(this.textoBuscar);
    }
    public void cerrarDialogo(){
        System.out.println("Cerrando la ventana modal...!");
        producto = new Producto();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Producto> getListado() {
        return listado;
    }

    public void setListado(List<Producto> listado) {
        this.listado = listado;
    }

    public String getTextoBuscar() {
        return textoBuscar;
    }

    public void setTextoBuscar(String textoBuscar) {
        this.textoBuscar = textoBuscar;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
