import java.time.LocalDateTime;

public class Fatura {
    private String idCasa;
    private double consumo;
    private double custo;
    private LocalDateTime dataInicial;
    private LocalDateTime dataFinal;

    public Fatura() {
        this.idCasa = "";
        this.consumo = 0;
        this.custo = 0;
        this.dataInicial = LocalDateTime.now();
        this.dataFinal = LocalDateTime.now();
    }

    public Fatura(String idCasa, double consumo, double custo, LocalDateTime dataInicial, LocalDateTime dataFinal) {
        this.idCasa = idCasa;
        this.consumo = consumo;
        this.custo = custo;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public Fatura(Fatura f) {
        this.idCasa = f.getIdCasa();
        this.consumo = f.getConsumo();
        this.custo = f.getCusto();
        this.dataInicial = f.getDataInicial();
        this.dataFinal = f.getDataFinal();
    }

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

    public String toString() {
        String sb = "Id da casa: " + this.idCasa + "\n" +
                    "Data inicial: " + this.dataInicial + "\n" +
                    "Data final: " + this.dataFinal + "\n" +
                    "Consumo da casa durante o período de tempo: " + this.consumo + "W" + "\n" +
                    "Custo: " + this.custo + "€" + "\n";

        return sb;
    }

    public Fatura clone() {
        return new Fatura(this);
    }

    // Getters and Setters
    public String getIdCasa() {
        return this.idCasa;
    }

    public void setIdCasa(String idCasa) {
        this.idCasa = idCasa;
    }

    public double getConsumo() {
        return this.consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

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