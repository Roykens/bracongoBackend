package com.royken.bracongo.survey.web.beans.util;

import com.royken.bracongo.survey.entities.Action;
import com.royken.bracongo.survey.entities.Reponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class ActionParseUtil {

    private static String getPdvZoneReponse(Reponse reponse) {
        StringBuilder stb = new StringBuilder();
        stb.append(reponse.getPointDeVente().getNom());
        stb.append(" ");
        stb.append(reponse.getPointDeVente().getCircuit().getZone().getSecteur().getCode());
        stb.append("-");
        stb.append(reponse.getPointDeVente().getCircuit().getCode());
        return stb.toString();
    }

    private static String getMaoReponse(Reponse reponse) {
        return reponse.getPointDeVente().getCircuit().getZone().getSecteur().getEnqueteur().getNom();
    }

    public static List<String> besoinContrat(List<Action> actions) {
        List<String> result = new ArrayList<String>();
        StringBuilder stbpdv = new StringBuilder();
        StringBuilder stbmao = new StringBuilder();
        Set<String> noms = new TreeSet<String>();
        for (Action action : actions) {
            if (action.isBesoinDeContrat()) {
                stbpdv.append(getPdvZoneReponse(action.getReponse()));
                stbpdv.append(", ");
                noms.add(getMaoReponse(action.getReponse()));
          
            }
        }
        for (String nom : noms) {
            stbmao.append(nom);
                stbmao.append(", ");
        }
        result.add(stbpdv.toString());
        result.add(stbmao.toString());
        return result;
    }

    public static List<String> besoinRenouvellementContrat(List<Action> actions) {
        List<String> result = new ArrayList<String>();
        StringBuilder stbpdv = new StringBuilder();
        StringBuilder stbmao = new StringBuilder();
        Set<String> noms = new TreeSet<String>();
        for (Action action : actions) {
            if (action.isBesoinRenouvellementContrat()) {
                stbpdv.append(getPdvZoneReponse(action.getReponse()));
                stbpdv.append(", ");
                noms.add(getMaoReponse(action.getReponse()));
              //  stbmao.append(getMaoReponse(action.getReponse()));
               // stbmao.append(", ");
            }
        }
         for (String nom : noms) {
            stbmao.append(nom);
                stbmao.append(", ");
        }
        result.add(stbpdv.toString());
        result.add(stbmao.toString());
        return result;
    }

    public static List<String> contratPartiel(List<Action> actions) {
        List<String> result = new ArrayList<String>();
        Set<String> noms = new TreeSet<String>();
        StringBuilder stbpdv = new StringBuilder();
        StringBuilder stbmao = new StringBuilder();
        for (Action action : actions) {
            if (action.isContratPartiel()) {
                stbpdv.append(getPdvZoneReponse(action.getReponse()));
                stbpdv.append(", ");
                 noms.add(getMaoReponse(action.getReponse()));
            }
        }
        for (String nom : noms) {
            stbmao.append(nom);
                stbmao.append(", ");
        }
        result.add(stbpdv.toString());
        result.add(stbmao.toString());
        return result;
    }

    public static List<String> ReclamationRemise(List<Action> actions) {
        List<String> result = new ArrayList<String>();
        StringBuilder stbpdv = new StringBuilder();
        Set<String> noms = new TreeSet<String>();
        StringBuilder stbmao = new StringBuilder();
        for (Action action : actions) {
            if (action.isReclamationRemise()) {
                stbpdv.append(getPdvZoneReponse(action.getReponse()));
                stbpdv.append(", ");
                noms.add(getMaoReponse(action.getReponse()));
            }
        }
        for (String nom : noms) {
            stbmao.append(nom);
                stbmao.append(", ");
        }
        result.add(stbpdv.toString());
        result.add(stbmao.toString());
        return result;
    }

    public static List<String> pdvFerme(List<Action> actions) {
        List<String> result = new ArrayList<String>();
        StringBuilder stbpdv = new StringBuilder();
        Set<String> noms = new TreeSet<String>();
        StringBuilder stbmao = new StringBuilder();
        for (Action action : actions) {
            if (action.isFermeNonOperationel()) {
                stbpdv.append(getPdvZoneReponse(action.getReponse()));
                stbpdv.append(", ");
                noms.add(getMaoReponse(action.getReponse()));
            }
        }
        for (String nom : noms) {
            stbmao.append(nom);
                stbmao.append(", ");
        }
        result.add(stbpdv.toString());
        result.add(stbmao.toString());
        return result;
    }

    public static List<String> pdvSollicitantConversion(List<Action> actions) {
        List<String> result = new ArrayList<String>();
         Set<String> noms = new TreeSet<String>();
        StringBuilder stbpdv = new StringBuilder();
        StringBuilder stbmao = new StringBuilder();
        for (Action action : actions) {
            if (action.isMixteSollicitantCoversion()) {
                stbpdv.append(getPdvZoneReponse(action.getReponse()));
                stbpdv.append(", ");
                noms.add(getMaoReponse(action.getReponse()));
            }
        }
        for (String nom : noms) {
            stbmao.append(nom);
                stbmao.append(", ");
        }
        result.add(stbpdv.toString());
        result.add(stbmao.toString());
        return result;
    }

    public static List<String> besoin3BacContre1(List<Action> actions) {
        List<String> result = new ArrayList<String>();
        Set<String> noms = new TreeSet<String>();
        StringBuilder stbpdv = new StringBuilder();
        StringBuilder stbmao = new StringBuilder();
        for (Action action : actions) {
            if (action.isBesoinOperation3Bac1()) {
                stbpdv.append(action.getReponse().getPointDeVente().getNom());
                stbpdv.append(" ");
                stbpdv.append(action.getNombreBacs());
                stbpdv.append(" ");
                stbpdv.append("bacs ");
                stbpdv.append(action.getReponse().getPointDeVente().getCircuit().getZone().getSecteur().getCode());
                stbpdv.append("-");
                stbpdv.append(action.getReponse().getPointDeVente().getCircuit().getCode());
                stbpdv.append(", ");
                noms.add(getMaoReponse(action.getReponse()));
            }
        }
        for (String nom : noms) {
            stbmao.append(nom);
                stbmao.append(", ");
        }
        result.add(stbpdv.toString());
        result.add(stbmao.toString());
        return result;
    }

    public static List<String> pdvDemenage(List<Action> actions) {
        List<String> result = new ArrayList<String>();
        Set<String> noms = new TreeSet<String>();
        StringBuilder stbpdv = new StringBuilder();
        StringBuilder stbmao = new StringBuilder();
        for (Action action : actions) {
            if (action.isDemenageSansPrevenir()) {
                stbpdv.append(getPdvZoneReponse(action.getReponse()));
                stbpdv.append(", ");
                 noms.add(getMaoReponse(action.getReponse()));
            }
        }
        for (String nom : noms) {
            stbmao.append(nom);
                stbmao.append(", ");
        }
        result.add(stbpdv.toString());
        result.add(stbmao.toString());
        return result;
    }

    public static List<String> pdvRenforcer(List<Action> actions) {
        List<String> result = new ArrayList<String>();
        Set<String> noms = new TreeSet<String>();
        StringBuilder stbpdv = new StringBuilder();
        StringBuilder stbmao = new StringBuilder();
        for (Action action : actions) {
            if (action.isRenforcerEnCapacite()) {
                stbpdv.append(getPdvZoneReponse(action.getReponse()));
                stbpdv.append(", ");
                noms.add(getMaoReponse(action.getReponse()));
            }
        }
        for (String nom : noms) {
            stbmao.append(nom);
                stbmao.append(", ");
        }
        result.add(stbpdv.toString());
        result.add(stbmao.toString());
        return result;
    }

    public static List<String> pdvBesoinPlv(List<Action> actions) {
        List<String> result = new ArrayList<String>();
        Set<String> noms = new TreeSet<String>();
        StringBuilder stbpdv = new StringBuilder();
        StringBuilder stbmao = new StringBuilder();
        for (Action action : actions) {
            if (action.isBesoinPlv()) {
                stbpdv.append(getPdvZoneReponse(action.getReponse()));
                stbpdv.append(", ");
                noms.add(getMaoReponse(action.getReponse()));
            }
        }
        for (String nom : noms) {
            stbmao.append(nom);
                stbmao.append(", ");
        }
        result.add(stbpdv.toString());
        result.add(stbmao.toString());
        return result;
    }

    public static List<String> pdvConsignation(List<Action> actions) {
        List<String> result = new ArrayList<String>();
        Set<String> noms = new TreeSet<String>();
        StringBuilder stbpdv = new StringBuilder();
        StringBuilder stbmao = new StringBuilder();
        for (Action action : actions) {
            if (action.isBesoinConsignationEmballage()) {
                stbpdv.append(getPdvZoneReponse(action.getReponse()));
                stbpdv.append(", ");
                noms.add(getMaoReponse(action.getReponse()));
            }
        }
        for (String nom : noms) {
            stbmao.append(nom);
                stbmao.append(", ");
        }
        result.add(stbpdv.toString());
        result.add(stbmao.toString());
        return result;
    }

    public static List<String> pdvAdresseErrone(List<Action> actions) {
        List<String> result = new ArrayList<String>();
        Set<String> noms = new TreeSet<String>();
        StringBuilder stbpdv = new StringBuilder();
        StringBuilder stbmao = new StringBuilder();
        for (Action action : actions) {
            if (action.isAdresseErronee()) {
                stbpdv.append(getPdvZoneReponse(action.getReponse()));
                stbpdv.append(", ");
                noms.add(getMaoReponse(action.getReponse()));
            }
        }
        for (String nom : noms) {
            stbmao.append(nom);
                stbmao.append(", ");
        }
        result.add(stbpdv.toString());
        result.add(stbmao.toString());
        return result;
    }

    public static List<String> pdvBesoin5Chaises(List<Action> actions) {
        List<String> result = new ArrayList<String>();
        Set<String> noms = new TreeSet<String>();
        StringBuilder stbpdv = new StringBuilder();
        StringBuilder stbmao = new StringBuilder();
        for (Action action : actions) {
            if (action.isBesoin5ChaisesContre1()) {
                stbpdv.append(getPdvZoneReponse(action.getReponse()));
                stbpdv.append(", ");
                noms.add(getMaoReponse(action.getReponse()));
            }
        }
        for (String nom : noms) {
            stbmao.append(nom);
                stbmao.append(", ");
        }
        result.add(stbpdv.toString());
        result.add(stbmao.toString());
        return result;
    }

    public static List<String> pdvPhnCapsule(List<Action> actions) {
        List<String> result = new ArrayList<String>();
        Set<String> noms = new TreeSet<String>();
        StringBuilder stbpdv = new StringBuilder();
        StringBuilder stbmao = new StringBuilder();
        for (Action action : actions) {
            if (action.isPhnCapsule()) {
                stbpdv.append(getPdvZoneReponse(action.getReponse()));
                stbpdv.append(", ");
                noms.add(getMaoReponse(action.getReponse()));
            }
        }
        for (String nom : noms) {
            stbmao.append(nom);
                stbmao.append(", ");
        }
        result.add(stbpdv.toString());
        result.add(stbmao.toString());
        return result;
    }
}
