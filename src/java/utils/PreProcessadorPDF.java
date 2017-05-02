/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import java.io.File;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author valderlei
 */
public class PreProcessadorPDF {
    
    public static void preProcessPDF(Object document, String title) throws IOException, BadElementException, DocumentException {
        //cria o documento
        Document pdf = (Document) document;
        Font tituloListaFont = new Font(Font.COURIER,20,Font.BOLD);
        //seta a margin e página, precisa estar antes da abertura do documento, ou seja da linha: pdf.open()
        pdf.setMargins(2f, 2f, 2f, 2f);
        pdf.setPageSize(PageSize.A4);
        pdf.open();
        //aqui pega o contexto para formar a url da imagem
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "resources/images" + File.separator + "logo.jpg";
        //cria a imagem e passando a url
        Image image = Image.getInstance(logo);
        //alinha ao centro
        image.setAlignment(Image.ALIGN_CENTER);
        //adciona a img ao pdf
        pdf.add(image);
        //adiciona um paragrafo ao pdf, alinha também ao centro
        Paragraph p = new Paragraph(title, tituloListaFont);
        p.setAlignment("center");
        pdf.add(p);
        pdf.add(new Paragraph(" "));
    }
    
}
