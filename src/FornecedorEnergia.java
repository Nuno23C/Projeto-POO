import SmartDevices.SmartDevice;
import java.util.Map;

public class FornecedorEnergia {
    public String nomeEmpresa;
    public double imposto;
    public double valorBase; //de cada dispositivo
    public double desconto;

    /**
     * Construtor por omissão
     */
    public FornecedorEnergia(){
        this.nomeEmpresa = "";
        this.imposto = 1.23;
        this.valorBase = 0;
        this.desconto = 0;
    }

    /**
     * Contrutor parametrizado
     * @param nomeEmpresa
     * @param imposto
     * @param valorBase
     * @param desconto
     */
    public FornecedorEnergia(String nomeEmpresa, double imposto, double valorBase, double desconto){
        this.nomeEmpresa = nomeEmpresa;
        this.imposto = 1.23;
        this.valorBase = valorBase;
        this.desconto = desconto;
    }

    /**
     * Constutor por cópia
     * @param fe
     */
    public FornecedorEnergia (FornecedorEnergia fe){
        this.nomeEmpresa = fe.getNomeEmpresa();
        this.imposto = fe.getImposto();
        this.valorBase = fe.getValorBase();
        this.desconto = fe.getDesconto();
    }

    public boolean equals (Object o) {
        if (this == o)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        FornecedorEnergia fe = (FornecedorEnergia) o;
        return (fe.getNomeEmpresa().equals(this.nomeEmpresa) &&
                fe.getImposto() == this.imposto &&
                fe.getValorBase() == this.valorBase &&
                fe.getDesconto() == this.desconto);
    }

    public FornecedorEnergia clone() {
        return new FornecedorEnergia(this);
    }

    public String toString() {

        String sb = "\n" + "Nome da Empresa: " + this.nomeEmpresa + "\n" +
                "Imposto: " + this.imposto + "\n" +
                "Valor Base: " + this.valorBase + "\n" +
                "Desconto: " + this.desconto + "\n";

        return sb;
    }


    public double getPrecoPorDispositivo(SmartDevice sd){
        return (this.valorBase * sd.getConsumoPorHora() * this.imposto) * (1 - (this.desconto/100));
    }

    public double getConsumoDivisao(Map<String, SmartDevice> div){
        double consumoDivisao = 0;

        for(SmartDevice disp: div.values()) {
            consumoDivisao += disp.getConsumoPorHora();
        }

        return consumoDivisao;
    }

    public double getConsumoDaCasa(Casa c){
        double consumoDaCasa = 0;

        for(SmartDevice device: c.getDispositivos().values()){
            consumoDaCasa += getPrecoPorDispositivo(device);
        }

        return consumoDaCasa;
    }



    // Getters and Setters
    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public double getImposto() {
        return imposto;
    }

    public void setImposto(double imposto) {
        this.imposto = imposto;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
}
