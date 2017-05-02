/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author valderlei
 */
public class Email {

    private String assunto;
    private String mensagem;   
    private String destinatario_email;
    private String destinatario_nome;
    private final String HOST = "smtp.gmail.com";
    private final String EMAIL= "a12032@bcc.unifal-mg.edu.br";
    private final String SENHA= "a88384718";
    private final String REMETENTE_NOME= "Matheus Flausino(Dentist Development)";
    private final String REMETENTE_EMAIL= "a12032@bcc.unifal-mg.edu.br";
    
    public void enviarEmail() {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName(HOST); // o servidor SMTP para envio do e-mail
            email.addTo(destinatario_email); //destinatário
            email.setFrom(REMETENTE_EMAIL, REMETENTE_NOME); // remetente
            email.setSubject(assunto); // assunto do e-mail
            email.setAuthentication(EMAIL, SENHA); // autenticacao no servidor gmail
            email.setSSLOnConnect(true);
            email.setMsg(mensagem); //conteudo do e-mail
            email.send(); //envia o e-mail
        } catch (EmailException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Email setDestinatario(String destinatario_email, String destinatario_nome) {
        this.destinatario_email = destinatario_email;
        this.destinatario_nome = destinatario_nome;
        return this;
    }
    
    public Email setDestinatario(String destinatario_email) {
        this.destinatario_email = destinatario_email;
        return this;
    }

    public Email setMensagem(String mensagem) {
        this.mensagem = mensagem;
        return this;
    }

    public Email setAssunto(String assunto) {
        this.assunto = assunto;
        return this;
    }

    public String getAssunto() {
        return assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getDestinatario_email() {
        return destinatario_email;
    }

    public String getDestinatario_nome() {
        return destinatario_nome;
    }

    public String getHOST() {
        return HOST;
    }  

}
