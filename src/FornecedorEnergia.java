import SmartDevices.SmartDevice;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FornecedorEnergia implements Serializable {
    public String nomeEmpresa;
    public double imposto;
    public double valorBase;
    public double desconto;
    private Map<String, Casa> casasDoFornecedor; //Id da casa - Casa
    private Map<String, List<Fatura>> faturas;  //Id da casa - Lista de faturas

    /**
     * Construtor por omissão
     */
    public FornecedorEnergia(){
        this.nomeEmpresa = "";
        this.imposto = 1.23;
        this.valorBase = 0;
        this.desconto = 0;
        this.casasDoFornecedor = new HashMap<>();
        this.faturas = new HashMap<>();
    }

    /**
     * Contrutor parametrizado
     * @param nomeEmpresa
     * @param imposto
     * @param valorBase
     * @param desconto
     * @param faturas
     */
    public FornecedorEnergia(String nomeEmpresa, double valorBase, double desconto, HashMap<String, Casa> casas, Map<String, List<Fatura>> faturas){
        this.nomeEmpresa = nomeEmpresa;
        this.imposto = 1.23;
        this.valorBase = valorBase;
        this.desconto = desconto;
        this.casasDoFornecedor = casas;
        this.faturas = faturas;
    }

    public FornecedorEnergia(String nomeEmpresa, double valorBase, double desconto){
        this.nomeEmpresa = nomeEmpresa;
        this.imposto = 1.23;
        this.valorBase = valorBase;
        this.desconto = desconto;
        this.casasDoFornecedor = new HashMap<String, Casa>();
        this.faturas = new HashMap<String, List<Fatura>>();
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
        this.casasDoFornecedor = fe.getCasasDoFornecedor();
        this.faturas = fe.getFaturas();
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
                fe.getDesconto() == this.desconto &&
                fe.getCasasDoFornecedor().equals(this.casasDoFornecedor) &&
                fe.getFaturas().equals(this.faturas));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Nome da Empresa: " + this.nomeEmpresa + "\n");
        sb.append("Tax: " + this.imposto + "\n");
        sb.append("Base value: " + this.valorBase + "\n");
        sb.append("Discount: " + this.desconto + "\n");
        sb.append("\n");

        //sb.append("Casas: " + this.casas + "\n");
        //sb.append("Bill: " + this.faturas + "\n");

        return sb.toString();
    }

    public FornecedorEnergia clone() {
        return new FornecedorEnergia(this);
    }

    public double getPrecoDispositivoPorHora(SmartDevice sd){
        return (this.valorBase * sd.getConsumoPorHora() * this.imposto) * (1 - (this.desconto/100));
    }

    // Quando recebe a casa
    public double getPrecoCasaPorHora(Casa casa) {
        double precoTotal = 0;

        for(SmartDevice sd: casa.getDispositivos().values()) {
            precoTotal += getPrecoDispositivoPorHora(sd);
        }

        return precoTotal;
    }

    // Quando recebe os dispositivos da casa
    public double getPrecoCasaPorHora(Map<String, SmartDevice> dispositivos) {
        double precoTotal = 0;

        for(SmartDevice sd: dispositivos.values()) {
            precoTotal += getPrecoDispositivoPorHora(sd);
        }

        return precoTotal;
    }

    // Quando recebe a casa
    public double getConsumoCasaPorHora(Casa casa) {
        double consumoTotal = 0;

        for(SmartDevice sd: casa.getDispositivos().values()) {
            consumoTotal += getPrecoDispositivoPorHora(sd);
        }

        return consumoTotal;
    }

    // Quando recebe os dispositivos da casa
    public double getConsumoCasaPorHora(Map<String, SmartDevice> dispositivos) {
        double consumoTotal = 0;

        for(SmartDevice sd: dispositivos.values()) {
            consumoTotal += sd.getConsumoPorHora();
        }

        return consumoTotal;
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

    public Map<String, Casa> getCasasDoFornecedor() {
        return this.casasDoFornecedor;
    }

    private void setCasasDoFornecedor(HashMap<String, Casa> casasDoFornecedor){
        this.casasDoFornecedor = casasDoFornecedor;
    }

    public Map<String, List<Fatura>> getFaturas() {
        return this.faturas;
    }

    public void setFatura(Map<String, List<Fatura>> faturas) {
        this.faturas = faturas;
    }
}
