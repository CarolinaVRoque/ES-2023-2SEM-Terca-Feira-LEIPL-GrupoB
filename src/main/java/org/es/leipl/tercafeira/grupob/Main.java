package org.es.leipl.tercafeira.grupob;

import net.fortuna.ical4j.data.ParserException;
import org.es.leipl.tercafeira.grupob.pojos.Horario;
import org.es.leipl.tercafeira.grupob.tools.gui.GUI;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;

import static org.es.leipl.tercafeira.grupob.tools.ImportFiles.*;

/**
 * @author GRUPO_B_LEI_PL
 * @version 0.0
 */
public class Main {
    public static void main(String[] args) throws IOException, ParserException {
             GUI.createGUI();
            //Horario horario = CSVImport("arquivo/Horario.csv");
            JSONArray json = CSVtoJSon("arquivo/Horario.csv");
            System.out.println(json);
            saveJSONtoFile(json);
    }
}