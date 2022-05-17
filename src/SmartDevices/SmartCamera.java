package SmartDevices;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;

public class SmartCamera extends SmartDevice {
    public enum Estado {
        ON,
        OFF
    }
    public Estado estado;
    public int x;
    public int y;
    public long tempoLigada;
    public double tamanhoPacote;
    public double consumoPorHora;
    public LocalDateTime dataInicial;

    /**
     * Construtor por omissão.
     */
    public SmartCamera() {
        super();
        this.estado = Estado.OFF;
        this.x = 0;
        this.y = 0;
        this.tempoLigada = 0;
        this.tamanhoPacote = 0;
        this.consumoPorHora = 0;
        this.dataInicial = LocalDateTime.now();
    }

    /**
     * Construtor parametrizado
     * @param id
     * @param estado
     * @param resolucao
     * @param dataAtual
     */
    public SmartCamera(String id, Estado estado, int x, int y, LocalDateTime dataAtual){
        super(id);
        this.estado = estado;
        this.x = x;
        this.y = y;
        this.dataInicial = dataAtual;
    }

    /**
     * Construtor de cópia.
     * @param sc SmartCamera passada como parâmetro.
     */
    public SmartCamera(SmartCamera sc) { // SC de SmartCamera
        super();
        this.estado = sc.getEstado();
        this.x = sc.getX();
        this.y = sc.getY();
        this.tempoLigada = sc.getTempoLigada();
        this.tamanhoPacote = sc.getTamanhoPacote();
        this.consumoPorHora = sc.getConsumoPorHora();
        this.dataInicial = sc.getDataInicial();
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
                sc.getX() == this.x &&
                sc.getY() == this.y &&
                sc.getTamanhoPacote() == this.tamanhoPacote &&
                sc.getConsumoPorHora() == this.consumoPorHora &&
                sc.getDataInicial().equals(this.dataInicial));
    }

    /**
     * Método que representa a SmartCamera em formato de texto.
     * @return String com as características da SmartCamera.
     */
    public String toString() {

        String sc = "\n" + "id: " + this.id + "\n" +
                    "Estado: " + this.estado + "\n" +
                    "Resolucao x: " + this.x + "\n" +
                    "Resolucao y: " + this.y + "\n" +
                    "Tempo Ligada " + this.tempoLigada + "\n" +
                    "Tamanho do Pacote: " + this.tamanhoPacote + "\n" +
                    "Consumo por hora: " + this.consumoPorHora + " \n" +
                    "Data atual: " + this.dataInicial + "\n";

        return sc;
    }

    /**
     * Método que cria um clone da SmartCamera.
     * @return Clone da SmartCamera.
     */
    public SmartCamera clone() {
        return new SmartCamera(this);
    }

    public void turnOn() {
        this.estado = Estado.ON;
    }

    public void turnOff() {
        this.estado = Estado.OFF;
    }

    /**
     * Método que atualiza a camêra quando uma nova data for introduzida.
     * @param dataPretendida
     */
    public void atualizarCamera(LocalDateTime dataPretendida) {
        this.tempoLigada = dataInicial.until(dataInicial.plusDays(dataPretendida.getHour()), ChronoUnit.HOURS);
        this.tamanhoPacote = tempoLigada * 0.5;
        this.consumoPorHora = (x * y) * 0.2 * this.tamanhoPacote/tempoLigada;
    }






    // Getters and Setters
    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public long getTempoLigada() {
        return this.tempoLigada;
    }

    public double getTamanhoPacote() {
        return tamanhoPacote;
    }

    public double getConsumoPorHora() {
        return consumoPorHora;
    }

    public void setConsumoPorHora(double consumoPorHora) {
        this.consumoPorHora = consumoPorHora;
    }

    public LocalDateTime getDataInicial() {
        return this.dataInicial;
    }

    public void setDataAtual(LocalDateTime dataInicial) {
        this.dataInicial = dataInicial;
    }
}
