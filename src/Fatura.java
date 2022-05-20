import java.io.Serializable;
import java.time.LocalDateTime;

public class Fatura implements Serializable {
    private String idCasa;
    private double consumo;
    private double custo;
    private LocalDateTime dataInicial;
    private LocalDateTime dataFinal;

    /**
     * Construtor por omissão
     */
    public Fatura() {
        this.idCasa = "";
        this.consumo = 0;
        this.custo = 0;
        this.dataInicial = LocalDateTime.now();
        this.dataFinal = LocalDateTime.now();
    }

    /**
     * Construtor parametrizado
     * @param idCsa
     * @param consumo
     * @param custo
     * @param dataInicial
     * @param dataFinal
     */
    public Fatura(String idCasa, double consumo, double custo, LocalDateTime dataInicial, LocalDateTime dataFinal) {
        this.idCasa = idCasa;
        this.consumo = consumo;
        this.custo = custo;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    /**
     * Construtor por cópia
     */
    public Fatura(Fatura f) {
        this.idCasa = f.getIdCasa();
        this.consumo = f.getConsumo();
        this.custo = f.getCusto();
        this.dataInicial = f.getDataInicial();
        this.dataFinal = f.getDataFinal();
    }

    /**
     * Método que verifica se duas faturas são iguais.
     * @param o fatura passada como parâmetro
     * @return True se as faturas forem iguais | False se as faturas forem diferentes
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if(o == null || this.getClass() != o.getClass())
            return false;

        Fatura f = (Fatura) o;
        return (this.idCasa.equals(f.getIdCasa()) &&
                this.consumo == f.getConsumo() &&
                this.custo == f.getCusto() &&
                this.dataInicial.equals(f.getDataInicial()) &&
                this.dataFinal.equals(f.getDataFinal()));
    }

    /**
     * Método que representa uma fatura em formato de texto.
     * @return String com as características de uma fatura.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("House ID: " + this.idCasa + "\n");
        sb.append("Initial date: " + this.dataInicial + "\n");
        sb.append("Final date: " + this.dataFinal + "\n");
        sb.append("Consumption during the time period: " + this.consumo + "kwh" + "\n");
        sb.append("Cost: " + this.custo + "€" + "\n");

        return sb.toString();
    }

    /**
     * Método que cria um clone da fatura.
     * @return Clone da fatura.
     */
    public Fatura clone() {
        return new Fatura(this);
    }



    // Getters and Setters

    /**
     * Método get que dá o id da casa
     * @return idCasa
     */
    public String getIdCasa() {
        return this.idCasa;
    }

    /**
     * Método set que altera o id da casa
     * @param idCasa
     */
    public void setIdCasa(String idCasa) {
        this.idCasa = idCasa;
    }

    /**
     * Método get que dá o consumo da casa
     * @return consumo
     */
    public double getConsumo() {
        return this.consumo;
    }

    /**
     * Método set que altera o consumo da casa
     * @param consumo
     */
    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    /**
     * Método get que dá o id da casa
     * @return idCasa
     */
    public double getCusto() {
        return this.custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public LocalDateTime getDataInicial() {
        return this.dataInicial;
    }

    public void setDataInicial(LocalDateTime dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDateTime getDataFinal() {
        return this.dataFinal;
    }

    public void setDataFinal(LocalDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }
}
