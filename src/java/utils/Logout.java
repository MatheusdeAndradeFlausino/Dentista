/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import classes.Usuario;
import enums.Afirmacao;
import enums.StatusPadrao;
import facades.UsuarioFacade;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author valderlei
 */
@Named(value = "Logout")
@Dependent
public class Logout {

    @EJB
    private UsuarioFacade usuarioFacade;

    /**
     * Creates a new instance of Logout
     */
    public Logout() {
    }

    private UsuarioFacade getUsuarioFacade() {
        return usuarioFacade;
        
    }

    public void permitirAcesso() {
        String login = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        Usuario usuario = getUsuarioFacade().findByLogin(login);
        if (usuario != null ) {
            if (!usuario.getStatus().equals(StatusPadrao.ATIVO.getStatus())) {
                deslogar();
            } else if(usuario.getIsRestricaoHorario().equals(Afirmacao.SIM.getValor())){
                Date data = new Date();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(data);
                String diaSemana = DatasEHoras.weekDay(calendar);
                String horaInicio="", horaTermino="";
                
                switch (diaSemana) {
                    case "Domingo":
                        horaInicio = usuario.getHoraInicioDomingo();
                        horaTermino = usuario.getHoraFinalDomingo();
                        break;
                    case "Segunda-feira":
                        horaInicio = usuario.getHoraInicioSegunda();
                        horaTermino = usuario.getHoraFinalSegunda();
                        break;
                    case "Terça-feira":
                        horaInicio = usuario.getHoraInicioTerca();
                        horaTermino = usuario.getHoraFinalTerca();
                        break;
                    case "Quarta-feira":
                        horaInicio = usuario.getHoraInicioQuarta();
                        horaTermino = usuario.getHoraFinalQuarta();
                        break;
                    case "Quinta-feira":
                        horaInicio = usuario.getHoraInicioQuinta();
                        horaTermino = usuario.getHoraFinalQuinta();
                        break;
                    case "Sexta-feira":
                        horaInicio = usuario.getHoraInicioSexta();
                        horaTermino = usuario.getHoraFinalSexta();
                        break;
                    case "Sábado":
                        horaInicio = usuario.getHoraInicioSabado();
                        horaTermino = usuario.getHoraFinalSabado();
                        break;
                }
                
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String horaAtual = sdf.format(data);
                if(!DatasEHoras.isHorarioNoIntervalo(horaInicio, horaTermino, horaAtual)){
                    deslogar();
                }
            }
        }
    }

    public String usuarioLogado(){
       return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getUserPrincipal().getName();
    }
    
    public void deslogar() {
        String base = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            request.logout();
            FacesContext.getCurrentInstance().getExternalContext().redirect(base + "/webapp/index.xhtml?faces-redirect=true");
        } catch (Exception e) {
        }
    }

}
