package org.es.leipl.tercafeira.grupob.pojos;

/**
 * @author GRUPO_B_LEI_PL
 * @version 0.0
 */

/**
 * Java Object for representation of the classroom
 */
public class Sala {

    /**
     * designacao is the name of the classroom
     */
    private String designacao;

    /**
     * lotacao is the classroom student capacity
     */
    private int lotacao;

    /**
     * Creates a new instance of the Sala class with the specified designation and capacity.
     *
     * @param designacao a string representing the designation of the room.
     * @param lotacao    a string representing the capacity of the room.
     */
    public Sala (String designacao, String lotacao) {
        if (designacao == null || designacao.trim().isEmpty() || lotacao == null) {
            this.designacao = "Aguarda atribuição de sala";
            this.lotacao=0;

        }
        else {
            this.designacao = designacao;
            this.lotacao = parseNumeroLotacao(lotacao);
        }
    }

    /**
     * Tries to parse a string to Integer
     *
     * @param lotacao
     *
     * @return The Integer value of the param or -1 if the String is not parsable
     */
    private int parseNumeroLotacao(String lotacao) {
        try{
            return Integer.parseInt(lotacao);
        }catch(NumberFormatException e) {
            System.out.println("lotacao is not parsable ");
            return -1;
        }
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public int getLotacao() {
        return lotacao;
    }

    public void setLotacao(int lotacao) {
        this.lotacao = lotacao;
    }

    /**
     * @return a string representation of the object.
     * In general, the toString method returns a string that "textually represents" this object.
     */
    @Override
    public String toString() {
        //To Do
        return "Sala: " + designacao + " Capacidade: " + lotacao +"\n";
    }


}
