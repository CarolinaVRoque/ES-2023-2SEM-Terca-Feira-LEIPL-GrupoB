package org.es.leipl.tercafeira.grupob;

import com.opencsv.exceptions.CsvValidationException;
import net.fortuna.ical4j.data.ParserException;
import org.es.leipl.tercafeira.grupob.tools.gui.GUI;
import java.io.IOException;


/**
 * @author GRUPO_B_LEI_PL
 * @version 0.0
 */
public class Main {
    public static void main(String[] args) throws IOException, ParserException, CsvValidationException {
        GUI.createGUI();
        //GUI.addEvent(LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), "Teste teste");
        GUI.showGUI();
    }
}