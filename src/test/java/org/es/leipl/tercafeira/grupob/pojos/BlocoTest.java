package org.es.leipl.tercafeira.grupob.pojos;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;

/**
 Represents a class block of a subject in a specific time slot.
 */

public class BlocoTest {

    /**
     Creates a new block.
     @param curso the course(s) for which the block is being offered
     @param uc the academic unit to which the block belongs
     @param turno the code of the time slot
     @param turma the class(es) for which the block is being offered
     @param inscritos the number of students enrolled in the block
     @param diaSemana the day of the week in which the block occurs
     @param horaIni the start time of the block
     @param horaFim the end time of the block
     @param data the date on which the block occurs
     @param sala the room in which the block occurs
     @param lotacaoSala the capacity of the room in which the block occurs
     */
    Bloco bloco = new Bloco("LEI,MEI", "POO", "TP1", "A", 30, "Segunda", LocalTime.of(13, 00, 00), LocalTime.of(14, 30, 00), LocalDate.of(2022, 12, 02), "Sala 1", 40);


    /**
     Tests the toString method of the Bloco class.
     Checks if the string representation of the Bloco object is correct, including its course, unit, class and turn information,
     the number of enrolled students, the day of the week, the start and end time, the date, the assigned room, and the room capacity.
     @return void
     */
    @Test
    public void testToString() {

        String expected = "Bloco: [LEI, MEI] POO TP1 30 Segunda " + "13:00:00" + " " + "14:30:00" + " " + "02/12/2022" + " " + " Sala 1 40\n";
        String actual = bloco.toString();

        assertEquals(expected, actual);
    }

    /**
     This method tests the toJson() method of the Bloco class. It verifies if the JSON string
     generated by the method is correct and matches the expected output.
     The expected output is a JSON string containing information about the Bloco object, including
     the course, unit, class type, room, number of enrolled students, day of the week, start and end
     time of the class, date of the class, and the maximum capacity of the classroom.
     The actual output is obtained by calling the toJson() method of the Bloco object and then
     converting it to a JSON string using the toJSONString() method of the JSON.simple library.
     If the expected and actual JSON strings match, the test passes. Otherwise, it fails.
     */
    @Test
    public void toJson() {

        String expected = "{\"Lotação da sala\":40,\"Inscritos no turno\":30,\"Sala atribuída à aula\":\"Sala 1\",\"Hora início da aula\":\"08:30\",\"Unidade Curricular\":\"POO\",\"Hora fim da aula\":\"10:00\",\"Data da aula\":\"2023-04-24\",\"Turma\":[\"A\"],\"Turno\":\"TP1\",\"Curso\":[\"LEI\",\"MEI\"],\"Dia da semana\":\"Segunda\"}";
        String actual = bloco.toJson().toJSONString();

        assertEquals(expected, actual);

    }

    /**
     Tests the method getUc() of the class Bloco.
     Checks if the UC returned by the method is according to the expected one,
     which is the value passed as argument in the constructor.
     */
    @Test
    public void testGetUC() {
        Bloco bloco = new Bloco("LEI,MEI", "POO", "TP1", "A", 30, "Segunda", LocalTime.of(13, 00, 00), LocalTime.of(14, 30, 00), LocalDate.of(2022, 12, 02), "Sala 1", 40);
        assertEquals("POO", bloco.getUc());
    }

    /**
     Tests the getTurno() method of the Bloco class.
     Verifies if the turn returned by the method is equal to the expected one,
     which is the value passed as argument in the constructor.
     */
    @Test
    public void testGetTurno() {
        Bloco bloco = new Bloco("LEI,MEI", "POO", "TP1", "A", 30, "Segunda", LocalTime.of(13, 00, 00), LocalTime.of(14, 30, 00), LocalDate.of(2022, 12, 02), "Sala 1", 40);
        assertEquals("TP1", bloco.getTurno());
    }

    /**
     Tests the getTurma() method of the Bloco class.
     Checks if the turma returned by the method matches the expected one,
     which is the value passed as argument in the constructor.
     */
    @Test
    public void testGetTurma() {
        Bloco bloco = new Bloco("LEI,MEI", "POO", "TP1", "A", 30, "Segunda", LocalTime.of(13, 00, 00), LocalTime.of(14, 30, 00), LocalDate.of(2022, 12, 02), "Sala 1", 40);
        assertEquals("A", bloco.getTurma());
    }

    /**
     Tests the getInscritos() method of the Bloco class.
     Checks if the number of enrolled students returned by the method is the expected one,
     which is the value passed as an argument in the constructor.
     */
    @Test
    public void testGetInscritos() {
        Bloco bloco = new Bloco("LEI,MEI", "POO", "TP1", "A", 30, "Segunda", LocalTime.of(13, 00, 00), LocalTime.of(14, 30, 00), LocalDate.of(2022, 12, 02), "Sala 1", 40);
        assertEquals(30, bloco.getInscritos());
    }

    /**
     Test the getDiaSemana() method of the Bloco class.
     Verify if the day of the week returned by the method is according to the expected,
     which is the value passed as argument in the constructor.
     */
    @Test
    public void testGetDiaSemana() {
        Bloco bloco = new Bloco("LEI,MEI", "POO", "TP1", "A", 30, "Segunda", LocalTime.of(13, 00, 00), LocalTime.of(14, 30, 00), LocalDate.of(2022, 12, 02), "Sala 1", 40);
        assertEquals("Segunda", bloco.getDiaSemana());
    }

    /**
     Tests the getHoraIni() method of the Bloco class.
     Checks if the start time returned by the method matches the expected value,
     which is the value passed as argument in the constructor.
     */
    @Test
    public void testGetHoraInicio() {
        Bloco bloco = new Bloco("LEI,MEI", "POO", "TP1", "A", 30, "Segunda", LocalTime.of(13, 00, 00), LocalTime.of(14, 30, 00), LocalDate.of(2022, 12, 02), "Sala 1", 40);
        assertEquals(LocalTime.of(13, 00, 00), bloco.getHoraIni());
    }

    /**
     Tests the getHoraFim() method of the Bloco class.
     Verifies if the end time returned by the method is the expected one,
     which is the value passed as an argument in the constructor.
     */
    @Test
    public void testGetHoraFim() {
        Bloco bloco = new Bloco("LEI,MEI", "POO", "TP1", "A", 30, "Segunda", LocalTime.of(13, 00, 00), LocalTime.of(14, 30, 00), LocalDate.of(2022, 12, 02), "Sala 1", 40);
        assertEquals(LocalTime.of(14, 30, 00), bloco.getHoraFim());
    }

    /**
     Tests the getData() method of the Bloco class.
     Checks if the date returned by the method is the expected one,
     which is the value passed as an argument in the constructor.
     */
    @Test
    public void testGetData() {
        assertEquals(LocalDate.of(2022, 12, 02), bloco.getData());
    }
    /**
     Tests the method getSala() of the Bloco class.
     Verifies if the room returned by the method is the expected one,
     which is the value passed as an argument in the constructor.
     */
    @Test
    public void testGetSala() {
        assertEquals("Sala 1", bloco.getSala());
    }
    /**
     Tests the getLotacao() method of the Bloco class.
     Checks if the capacity returned by the method is according to the expected value,
     which is the value passed as an argument in the constructor.
     */
    @Test
    public void testGetLotacao() {
        assertEquals(40, bloco.getLotacao());
    }

    /**
     Tests the setHoraIni() method of the Bloco class.
     Verifies if the method correctly changes the start time of the block.
     */
    @Test
    public void testSetHorarioIni() {
        bloco.setHoraIni(LocalTime.of(14, 00, 00));
        assertEquals(LocalTime.of(14, 00, 00), bloco.getHoraIni());
    }
}