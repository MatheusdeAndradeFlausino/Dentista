/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author vanderlei
 */
@Named(value = "temas")
@ApplicationScoped
public class Temas implements Serializable {

    private String temaAtual="humanity";

    public String getTemaAtual() {
        return temaAtual;
    }

    public void setTemaAtual(String temaAtual) {
        this.temaAtual = temaAtual;
    }
    
    private static final String[] POSSIBLE_THEMES = {"afterdark", "afternoon",
        "afterwork", "aristo", "black-tie", "blitzer", "bluesky", "casablanca", "cruze",
        "cupertino", "dark-hive", "dot-luv", "eggplant", "excite-bike", "flick", "glass-x",
        "home", "hot-sneaks", "humanity", "le-frog", "midnight", "mint-choc", "overcast    ", "pepper    -grinder    ",
        "redmond", "rocket","sam", "smoothness","south-street", "start", "sunny", "swanky-purse",
        "trontastic", "twitter bootstrap", "ui-darkness",
        "ui-lightness", "vader"};

    public String[] getThemes() {
        return (POSSIBLE_THEMES);
    }

    public Temas() {
    }

    public void saveTheme(){
        temaAtual = temaAtual;
    }
    
}
