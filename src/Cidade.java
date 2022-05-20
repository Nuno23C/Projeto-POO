import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SmartDevices.SmartDevice;

import java.io.*;

public class Cidade implements Serializable {
    private Map<String, Casa> casas = new HashMap<>(); // Id da casa - Casa (Todas as casas)
    private Map<String, FornecedorEnergia> fornecedorDaCasa = new HashMap<>(); // idCasa - Fornecedor (Fornecedor de cada casa)
    private Map<String, FornecedorEnergia> fornecedores = new HashMap<>(); // Nome do fornecedor - Fornecedor (Todos os fornecedores existentes)
    private Map<String, List<Fatura>> faturas = new HashMap<>(); // Nome da Casas - Lista de faturas (Todas as faturas de todas as casas)

    public Cidade() {
        this.casas = new HashMap<>();
        this.fornecedorDaCasa = new HashMap<>();
        this.fornecedores = new HashMap<>();
        this.faturas = new HashMap<>();
    }

    public Cidade(Map<String, Casa> casas, Map<String, FornecedorEnergia> fornecedorDaCasa, Map<String, FornecedorEnergia> fornecedores, Map<String, List<Fatura>> faturas) {
        this.casas = casas;
        this.fornecedorDaCasa = fornecedorDaCasa;
        this.fornecedores = fornecedores;
        this.faturas = faturas;
    }

    public Cidade(Cidade cidade) {
        this.casas = cidade.getCasas();
        this.fornecedorDaCasa = cidade.getFornecedorDaCasa();
        this.fornecedores = cidade.getFornecedores();
        this.faturas = cidade.getFaturas();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Casas:\n");
        for (Casa casa: this.casas.values()){
            sb.append(casa.toString());
            sb.append("\n");
        }
       sb.append("\nFornecedores:\n");
       for (FornecedorEnergia fe: this.fornecedores.values()){
            sb.append(fe.toString());
            sb.append("\n");
        }

        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if(o == null || this.getClass() != o.getClass())
            return false;

        Cidade c = (Cidade) o;
        return (this.casas.equals(c.getCasas()) &&
                this.fornecedorDaCasa.equals(c.getFornecedorDaCasa()) &&
                this.fornecedores.equals(c.getFornecedores()) &&
                this.faturas.equals(c.getFaturas()));
    }

    public Cidade clone() {
        return new Cidade(this);
    }

    public void add_Casa(String idCasa, Casa casa){
        this.casas.put(idCasa, casa);
    }

    public void remove_Casa(String idCasa){
        this.casas.remove(idCasa);
    }

    public Casa getCasa(String idCasa) {
        return this.casas.get(idCasa);
    }

    public FornecedorEnergia getFornecedorDaCasa(String idCasa) {
        return this.fornecedorDaCasa.get(idCasa);
    }

    public FornecedorEnergia getFornecedor(String nomeFornecedor) {
        return this.fornecedores.get(nomeFornecedor);
    }

    public void add_Fornecedor(FornecedorEnergia fe) {
        this.fornecedores.put(fe.getNomeEmpresa(), fe);
    }

    public int removeFornecedor(String nomeFornecedor) {
        int flag = 1;

        for(String idCasa: this.fornecedorDaCasa.keySet()) {
            if(this.fornecedorDaCasa.get(idCasa).getNomeEmpresa().equals(nomeFornecedor))
                flag = 0;
        }
        if(flag == 1)
            this.fornecedores.remove(nomeFornecedor);

        return flag;
    }

    public double getConsumoCasaPeriodo(Casa casa, Cidade cidade, long periodo) {
        double consumoTotal = 0;

        for(SmartDevice sd: casa.getDispositivos().values()) {
            consumoTotal += sd.getConsumoF();
        }

        return consumoTotal*periodo;
    }

    public Casa getCasaMaisGastadoraPeriodo(Cidade cidade, long period) {
        double maiorValor = 0;
        Casa casaM = null;

        for(Casa c: cidade.getCasas().values()) {
            double consumo = getConsumoCasaPeriodo(c, cidade, period);
            if(consumo > maiorValor) {
                maiorValor = consumo;
                casaM = c;
            }
        }

        return casaM;
    }

    public double getPrecoCasaPeriodo(Casa casa, long periodo) {
        double precoTotal = 0;

        for(SmartDevice sd: casa.getDispositivos().values()) {
            precoTotal += getPrecoDispositivoPeriodo(casa, sd, periodo);
        }

        return precoTotal;
    }

    public double getPrecoDispositivoPeriodo(Casa casa, SmartDevice sd, long periodo){
        return (casa.getFornecedor().getValorBase() * sd.getConsumoF() * casa.getFornecedor().getImposto()) * (1 - (casa.getFornecedor().getDesconto()/100)) * periodo;
    }


    public double volumeFatFornecedor (FornecedorEnergia fornecedor, long periodo){
        double volumeFaturacao = 0;
        for(String idCasa: this.fornecedorDaCasa.keySet()){
            if(this.fornecedorDaCasa.get(idCasa).getNomeEmpresa().equals(fornecedor.getNomeEmpresa())){
                volumeFaturacao += getPrecoCasaPeriodo(getCasa(idCasa), periodo);
            }
        }
        return volumeFaturacao;
    }







    public String listaCasas() {
        StringBuilder sb = new StringBuilder();

        sb.append("Houses ids:\n");
        for(String idCasa: this.casas.keySet()) {
            sb.append(idCasa);
            sb.append("\n");
        }

        return sb.toString();
    }

    public String listaInfoCasa(Cidade cidade, String idCasa) {
        StringBuilder sb = new StringBuilder();

        sb.append(getCasa(idCasa).toString());

        return sb.toString();
    }


    public String listaFornecedores(){
        StringBuilder sb = new StringBuilder();

        sb.append("List of energy suppliers:\n");
        for(String nomeF: this.fornecedores.keySet()){
            sb.append(nomeF);
            sb.append("\n");
        }

        return sb.toString();
    }

    public String listaInfoFornecedor(String nomeF){
        StringBuilder sb = new StringBuilder();

        sb.append(fornecedores.get(nomeF).toString());

        return sb.toString();
    }

/*
    private Map<String, Casa> casas = new HashMap<>(); // Id da casa - Casa (Todas as casas)
    private Map<String, FornecedorEnergia> fornecedorDaCasa = new HashMap<>(); // idCasa - Fornecedor (Fornecedor de cada casa)
    private Map<String, FornecedorEnergia> fornecedores = new HashMap<>(); // Nome do fornecedor - Fornecedor (Todos os fornecedores existentes)
    private Map<String, List<Fatura>> faturas = new HashMap<>(); // Nome da Casas - Lista de faturas (Todas as faturas de todas as casas)
*/














    // Getters and Setters
    public Map<String, Casa> getCasas() {
        return this.casas;
    }

    public void setCasas(HashMap<String, Casa> casas){
        this.casas = casas;
    }

    public Map<String, FornecedorEnergia> getFornecedorDaCasa(){
        return this.fornecedorDaCasa;
    }

    public void setFornecedoresCasas(Map<String, FornecedorEnergia> fornecedorDaCasa){
        this.fornecedorDaCasa = fornecedorDaCasa;
    }

    public Map<String, FornecedorEnergia> getFornecedores() {
        return this.fornecedores;
    }

    public void setFornecedores(Map<String, FornecedorEnergia> fornecedores) {
        this.fornecedores = fornecedores;
    }

    public Map<String, List<Fatura>> getFaturas(){
        return this.faturas;
    }

    public void setFatura(Map<String, List<Fatura>> faturas){
        this.faturas = faturas;
    }
}
