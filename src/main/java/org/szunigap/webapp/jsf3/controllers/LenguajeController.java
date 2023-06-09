package org.szunigap.webapp.jsf3.controllers;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Named // Para poder hacer referencia aeste componente
@SessionScoped
public class LenguajeController implements Serializable { //Se tiene que serializar entre los request, se guarda en una estructura de texto
    private static final long serialVersionUID = 1L; // PAra persistir en la sesión
    private Locale locale;
    private String lenguaje;
    private Map<String,String> lenguajesSoportados;
// metodo de inicialización del beans, cada vez que se crea el objeto en el Httprequest se debe inicializar, debe ser en el postconstruct
    // y no en el constructor para que tenga acceso a ciertos recursos que no han sido inicializados
    @PostConstruct
    public void init(){
        locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        lenguajesSoportados = new HashMap<>();
        lenguajesSoportados.put("Ingles", "en");
        lenguajesSoportados.put("Español", "es");
    }
    public void seleccionar(ValueChangeEvent event){
        String nuevo = event.getNewValue().toString();
        lenguajesSoportados.values().forEach(v -> {
            if (v.equals(nuevo)){
                this.locale = new Locale(nuevo);
                FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locale);
            }
        });
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Map<String, String> getLenguajesSoportados() {
        return lenguajesSoportados;
    }

    public void setLenguajesSoportados(Map<String, String> lenguajesSoportados) {
        this.lenguajesSoportados = lenguajesSoportados;
    }
}
