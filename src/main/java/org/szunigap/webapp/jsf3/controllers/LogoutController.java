package org.szunigap.webapp.jsf3.controllers;

import jakarta.enterprise.inject.Model;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Model
public class LogoutController {
    @Inject
    private FacesContext facesContext;
    public String logout() throws ServletException {
        //Obtenemos el request del facecontext del get, hacemos un cast
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        /*cerramos sesión y todo lo relacionado con la autenticación */
        request.logout();
        // Invalidamos la sesión
        request.getSession().invalidate();
        //Mensaje de confirmación
        facesContext.addMessage(null, new FacesMessage("Haz cerrado sesión con éxito!!"));
        //retornamos al login xhtml
        return "login.xhtml";
    }

}
