package weatherstyle.gestionesuggerimentiia.applicationlogic.control;


import Model.RecoveryGuardaroba;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import weatherstyle.gestionecitta.applicationlogic.logic.beans.Citta;
import weatherstyle.gestionecitta.applicationlogic.logic.service.CittaLogicImpl;
import weatherstyle.gestionecitta.applicationlogic.logic.service.CittaLogicService;
import weatherstyle.gestionecitta.storage.dao.CittaDAOImpl;
import weatherstyle.gestionecitta.storage.service.InfoCittaImpl;
import weatherstyle.gestioneguardaroba.applicationlogic.logic.beans.Guardaroba;
import weatherstyle.gestioneguardaroba.applicationlogic.logic.beans.Maglia;
import weatherstyle.gestioneguardaroba.applicationlogic.logic.beans.Pantaloni;
import weatherstyle.gestioneguardaroba.applicationlogic.logic.beans.Scarpe;
import weatherstyle.gestioneguardaroba.applicationlogic.logic.service.CapoAbbigliamentoService;
import weatherstyle.gestioneguardaroba.storage.dao.CapoAbbigliamentoDAOImpl;
import weatherstyle.gestioneguardaroba.storage.dao.GuardarobaDAOImpl;
import weatherstyle.gestionemeteo.applicationlogic.logic.beans.MeteoDailyMin;
import weatherstyle.gestionemeteo.applicationlogic.logic.service.MeteoLogicService;
import weatherstyle.gestionemeteo.storage.dao.MeteoDAOImpl;
import weatherstyle.gestionesuggerimentiia.applicationlogic.logic.algorithms.ImplementorAlgorithm;
import weatherstyle.gestionesuggerimentiia.applicationlogic.logic.beans.Suggerimento;
import weatherstyle.gestionesuggerimentiia.applicationlogic.logic.service.SuggerimentoLogicImpl;
import weatherstyle.gestionesuggerimentiia.storage.dao.OutfitDAOImpl;
import weatherstyle.gestionesuggerimentiia.storage.dao.SuggerimentoDAOImpl;
import weatherstyle.gestioneutenti.applicationlogic.logic.beans.Utente;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * classe che raccoglie tutte le informazioni necessarie per fornire suggerimenti all'utente
 */
@WebServlet(name = "RichiestaSuggerimentoServlet",value = "/RichiestaSuggerimentoServlet")
public class RichiestaSuggerimentoServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

        // parametri meteo presi dalla request
        String meteo = request.getParameter("meteo");
        double temperaturaPercepitaMedia = Double.parseDouble(request.getParameter("temperaturaPercepita"));
        String stagionePrevisione = request.getParameter("stagionePrevisione");

        MeteoDailyMin meteoDailyMin = new MeteoDailyMin();
        meteoDailyMin.setMeteo(meteo);
        meteoDailyMin.setStagionePrevisione(stagionePrevisione);
        meteoDailyMin.setTemperaturaPercepitaMedia(temperaturaPercepitaMedia);

        // parametri citta presi dalla request
        String nomeCitta = request.getParameter("nomeCitta");
        String lat = request.getParameter("latitudine");
        String lon = request.getParameter("longitudine");

        Citta citta = new Citta();
        citta.setNome(nomeCitta);
        citta.setLat(lat);
        citta.setLon(lon);

        // prende l'utente dalla sessione
        HttpSession session = request.getSession(false);
        if (session == null)
            response.sendRedirect("index.html");
        else {
            // Si prende l'Utente dalla sessione
            Utente utente = (Utente) session.getAttribute("utente");

            // Si prende il guardaroba dell'Utente
//            Guardaroba guardaroba = new CapoAbbigliamentoService(new CapoAbbigliamentoDAOImpl(),
//                    new GuardarobaDAOImpl()).ottieniGuardarobaUtente(utente.getId());
            Guardaroba guardaroba = (Guardaroba) this.getServletContext().getAttribute("guardaroba");

            // Si prendono le varie liste di capi d'abbigliamento presenti nel guardaroba
            List<Maglia> listaMaglie = guardaroba.getMaglie();
            List<Pantaloni> listaPantaloni = guardaroba.getPantaloni();
            List<Scarpe> listaScarpe = guardaroba.getScarpe();

            //  Si istanziano le liste che conterranno i capi suggeriti
            List<Maglia> maglieSuggerite;
            List<Pantaloni> pantaloniSuggeriti;
            List<Scarpe> scarpeSuggerite;

            // Si utilizza il design pattern per ottenere un istanza di una implementazione
            // basata sul machine learning

            @SuppressWarnings("unchecked")
            ImplementorAlgorithm<Maglia> magliaImplementorAlgorithmML = (ImplementorAlgorithm<Maglia>)
                    getServletContext().getAttribute("magliaML");

            @SuppressWarnings("unchecked")
            ImplementorAlgorithm<Pantaloni> pantaloniImplementorAlgorithmML = (ImplementorAlgorithm<Pantaloni>)
                    getServletContext().getAttribute("pantaloniML");

            @SuppressWarnings("unchecked")
            ImplementorAlgorithm<Scarpe> scarpeImplementorAlgorithmML = (ImplementorAlgorithm<Scarpe>)
                    getServletContext().getAttribute("scarpeML");

            //  Si ottengono le migliori maglie, pantaloni e scarpe con i rispettivi punteggi

            SuggerimentoLogicImpl suggerimentoLogicImpl = new SuggerimentoLogicImpl(
                    new SuggerimentoDAOImpl(), new OutfitDAOImpl());

            maglieSuggerite = suggerimentoLogicImpl.ottieniSuggerimentiCapi(magliaImplementorAlgorithmML,
                    listaMaglie, meteoDailyMin);

            pantaloniSuggeriti = suggerimentoLogicImpl.ottieniSuggerimentiCapi(pantaloniImplementorAlgorithmML,
                    listaPantaloni, meteoDailyMin);

            scarpeSuggerite = suggerimentoLogicImpl.ottieniSuggerimentiCapi(scarpeImplementorAlgorithmML,
                    listaScarpe, meteoDailyMin);

            // Si utilizza il design pattern per ottenere un istanza di una implementazione
            // basata su algoritmo genetico

            /*
                @SuppressWarnings("unchecked")
                ImplementorAlgorithm<Maglia> magliaImplementorAlgorithmGA = (ImplementorAlgorithm<Maglia>)
                        getServletContext().getAttribute("magliaGA");

                @SuppressWarnings("unchecked")
                ImplementorAlgorithm<Pantaloni> pantaloniImplementorAlgorithmGA = (ImplementorAlgorithm<Pantaloni>)
                        getServletContext().getAttribute("pantaloniGA");

                @SuppressWarnings("unchecked")
                ImplementorAlgorithm<Scarpe> scarpeImplementorAlgorithmGA = (ImplementorAlgorithm<Scarpe>)
                        getServletContext().getAttribute("scarpeGA");

                //  Si ottengono le migliori maglie, pantaloni e scarpe con i rispettivi punteggi
                SuggerimentoLogicImpl suggerimentoLogicImpl = new SuggerimentoLogicImpl(
                        new SuggerimentoDAOImpl(), new OutfitDAOImpl());

                maglieSuggerite = suggerimentoLogicImpl.ottieniSuggerimentiCapi(magliaImplementorAlgorithmGA,
                        listaMaglie, meteoDailyMin);

                pantaloniSuggeriti = suggerimentoLogicImpl.ottieniSuggerimentiCapi(pantaloniImplementorAlgorithmGA,
                        listaPantaloni, meteoDailyMin);

                scarpeSuggerite = suggerimentoLogicImpl.ottieniSuggerimentiCapi(scarpeImplementorAlgorithmGA,
                        listaScarpe, meteoDailyMin);
            */

            // salvo nella request i suggerimenti dati dal sistema
            request.setAttribute("maglieSuggerite", maglieSuggerite);
            request.setAttribute("pantaloniSuggeriti", pantaloniSuggeriti);
            request.setAttribute("scarpeSuggerite", scarpeSuggerite);

            Suggerimento suggerimento = new Suggerimento();
            suggerimento.setUtente(utente);
            suggerimento.setDate(new Date(new GregorianCalendar().getTimeInMillis()));
            suggerimento.setCitta(citta);
            suggerimento.setMeteoDailyMin(meteoDailyMin);

            // salvo nella request il suggerimento
            request.setAttribute("suggerimento", suggerimento);

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/visualizzaSuggerimenti.jsp");
            dispatcher.forward(request, response);

            }
    }

    @Override
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        doGet(request,response);
    }

}
