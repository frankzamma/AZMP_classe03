package Logics.ga;

import Model.CapoAbbigliamento;
import Model.MeteoInformation;
import Model.Pantalone;

import java.util.List;

public class BottomClothesGA{
    private GenericGA genericGA;

    public BottomClothesGA(List<Pantalone> listaPantaloni, MeteoInformation meteoInformation){
        genericGA = new GenericGA(listaPantaloni, meteoInformation, "pantaloniGA");
    }

    public List<CapoAbbigliamento> getBestPantaloni(){
        System.out.println("Avvio Algoritmo Genetico Pantaloni");
        List<CapoAbbigliamento> list = genericGA.getBestResult();

        System.out.println(list);
        return list;
    }
}