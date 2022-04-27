package SmartDevices;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;

public class SmartCamera extends SmartDevice {
    public double resolucao;
    public long tempoLigada;
    public double tamanhoPacote;
    public double consumoPorHora;
    public LocalDateTime dataAtual;
    public LocalDateTime dataPretendida;

    /**
     * Construtor por omissão.
     */
    public SmartCamera() {
        super();
        this.resolucao = 0;
        this.tempoLigada = 0;
        this.tamanhoPacote = 0;
        this.consumoPorHora = 0;
        this.dataAtual = LocalDateTime.now();
        this.dataPretendida = LocalDateTime.now();
    }

    /**
     * Construtor com parâmetros.
     * @param id identificação da SmartCamera.
     * @param estado Estado da SmartCamera.
     * @param resolucao Resolucao da SmartCamera.
     * @param tamanhoPacote Tamanho do Pacote transferido
     */
    public SmartCamera(String id, Estado estado, double resolucao, LocalDateTime dataAtual, LocalDateTime dataPretendida){
        super(id,estado);
        this.resolucao = resolucao;
        this.tempoLigada = dataAtual.until(dataAtual.plusDays(dataPretendida.getHour()), ChronoUnit.HOURS);
        this.tamanhoPacote = tempoLigada * 0.5;
        if (resolucao > 0 && this.tamanhoPacote > 0)
            this.consumoPorHora = resolucao * this.tamanhoPacote/tempoLigada;
        else
            this.consumoPorHora = 0;
        this.dataAtual = dataAtual;
        this.dataPretendida = dataPretendida;
    }

    /**
     * Construtor de cópia.
     * @param sc SmartCamera passada como parâmetro.
     */
    public SmartCamera(SmartCamera sc) { // SC de SmartCamera
        super();
        this.resolucao = sc.getResolucao();
        this.tempoLigada = sc.getTempoLigada();
        this.tamanhoPacote = sc.getTamanhoPacote();
        this.consumoPorHora = sc.getConsumoPorHora();
        this.dataAtual = sc.getDataAtual();
        this.dataPretendida = sc.getDataPretendida();
    }

    /**
     * Método que verifica se os duas SmartCameras são iguais.
     * @param o SmartCamera passada como parâmetro.
     * @return True se as SmartCamera forem iguais | False se as SmartCamera forem diferentes.
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        SmartCamera sc = (SmartCamera) o;
        return (sc.getId().equals(this.id) &&
                sc.getEstado() == this.estado &&
                sc.getResolucao() == this.resolucao &&
                sc.getTamanhoPacote() == this.tamanhoPacote &&
                sc.getConsumoPorHora() == this.consumoPorHora &&
                sc.getDataAtual().equals(this.dataAtual) &&
                sc.getDataPretendida().equals(this.dataPretendida));
    }

    /**
     * Método que cria um clone da SmartCamera.
     * @return Clone da SmartCamera.
     */
    public SmartCamera clone() {
        return new SmartCamera(this);
    }

    /**
     * Método que representa a SmartCamera em formato de texto.
     * @return String com as características da SmartCamera.
     */
    public String toString() {

        String sc = "\n" + "id: " + this.id + "\n" +
                    "Estado: " + this.estado + "\n" +
                    "Resolucao: " + this.resolucao + "\n" +
                    "Tempo Ligada " + this.tempoLigada + "\n" +
                    "Tamanho do Pacote: " + this.tamanhoPacote + "\n" +
                    "Consumo por hora: " + this.consumoPorHora + " \n" +
                    "Data atual: " + this.dataAtual + "\n" +
                    "Data pretendida: " + this.dataPretendida + "\n";

        return sc;
    }







    // Getters and Setters
    public double getResolucao() {
        return resolucao;
    }

    public void setResolucao(double resolucao) {
        this.resolucao = resolucao;
    }

    public long getTempoLigada() {
        return this.tempoLigada;
    }

    public void setTempoLigada(long tempoLigada) {
        this.tempoLigada = tempoLigada;
    }

    public double getTamanhoPacote() {
        return tamanhoPacote;
    }

    public void setTamanhoPacote(double tamanhoPacote) {
        this.tamanhoPacote = tamanhoPacote;
    }

    public double getConsumoPorHora() {
        return consumoPorHora;
    }

    public void setConsumoPorHora(double consumoPorHora) {
        this.consumoPorHora = consumoPorHora;
    }

    public LocalDateTime getDataAtual() {
        return this.dataAtual;
    }

    public void setDataAtual(LocalDateTime dataAtual) {
        this.dataAtual = dataAtual;
    }

    public LocalDateTime getDataPretendida() {
        return this.dataPretendida;
    }

    public void setDataPretendida(LocalDateTime dataPretendida) {
        this.dataPretendida = dataPretendida;
    }

}
