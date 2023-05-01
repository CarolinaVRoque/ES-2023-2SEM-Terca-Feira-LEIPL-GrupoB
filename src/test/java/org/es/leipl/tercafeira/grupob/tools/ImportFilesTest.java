package org.es.leipl.tercafeira.grupob.tools;

import com.opencsv.CSVReader;
import org.es.leipl.tercafeira.grupob.pojos.Aula;
import org.es.leipl.tercafeira.grupob.pojos.Horario;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class ImportFilesTest {

    /**
     This method tests the functionality of saving a JSONArray to a file by calling the static method saveJSONtoFile of the ImportFiles class.
     A JSONArray object is created with one element, "test", and then passed to the saveJSONtoFile method which returns a String with the path of the file created.
     The method then verifies that the file exists, is a file, and has a non-zero length.
     */
    @Test
    @DisplayName("Tests save a JSON to file")
    void saveJSONtoFile() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("test");
        String filePath = ImportFiles.saveJSONtoFile(jsonArray);

        File file = new File(filePath);
        //em vez disto, usar o file 1 de fora
        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertTrue(file.length() > 0 );
    }

    /**
     Converts a CSV file to a JSON array, where each object in the array represents a row in the CSV file.
     Each field in the CSV file becomes a key-value pair in the corresponding JSON object.
     The first row of the CSV file is assumed to contain the header, which becomes the keys of the JSON objects.
     @param filePath the path of the CSV file to be converted
     @return a JSON array representing the CSV data
     */
    @Test
    @DisplayName("Tests converting a CSV file to JSON")
    void CSVtoJSon() {

        File file = new File("horario.csv");
        JSONArray jsonArray = ImportFiles.CSVtoJSon(file.getAbsolutePath());

        //verifica se é capaz de retornar um objeto JSON não nulo e não vazio.
        assertNotNull(jsonArray);
        assertTrue(jsonArray.size() > 0);
        assertFalse(jsonArray.isEmpty());


        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        assertNotNull(jsonObject.get("Curso"));
        assertNotNull(jsonObject.get("Unidade Curricular"));
        assertNotNull(jsonObject.get("Turno"));
        assertNotNull(jsonObject.get("Turma"));
        assertNotNull(jsonObject.get("Inscritos no turno"));
        assertNotNull(jsonObject.get("Dia da semana"));
        assertNotNull(jsonObject.get("Hora início da aula"));
        assertNotNull(jsonObject.get("Hora fim da aula"));
        assertNotNull(jsonObject.get("Data da aula"));
        assertNotNull(jsonObject.get("Sala atribuída à aula"));
        assertNotNull(jsonObject.get("Lotação da sala"));
    }

    /**
     * Test method to verify that the CSVImport method is able to import a CSV file and return a non-null Horario object.
     * The method also checks that the number of Aulas objects in the Horario object returned by the CSVImport method
     * matches the expected value.
     */
    @Test
    @DisplayName("Tests importing a CSV file")
    void CSVImport() {

        Horario horario = ImportFiles.CSVImport("Horario.csv");
        assertNotNull(horario, "Horario object should not be null");
        assertEquals(26001, horario.getAulasList().size());
    }
}
