import java.io.IOException;
import java.nio.charset.*;
import java.nio.file.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SmartDevices.SmartBulb;
import SmartDevices.SmartCamera;
import SmartDevices.SmartDevice;
import SmartDevices.SmartSpeaker;

public class Parser {
    private int houseID = 0;
    private int deviceID = 0;

    public void parse(){
        Path path = Path.of("logs.txt");
        String conteudo = Files.readString(path);
        String[] conteudoPartido = conteudo.split("\n");
        String[] linhaPartida;
        String divisao = "";
        Casa casa = null;

        for (String linha : conteudoPartido) {

            linhaPartida = linha.split(":", 2);

            switch(linhaPartida[0]) {
                case "Casa":
                    casa = parseCasa(linhaPartida[1]);
                    break;

                case "Divisao":
                    if (casa == null)
                        System.out.println("Linha inválida.");
                    divisao = linhaPartida[1];
                    casa.add_Divisao(divisao);
                    break;

                case "SmartBulb":
                    if (divisao == null) System.out.println("Linha inválida.");
                    SmartBulb sb = parseSmartBulb(linhaPartida[1]);
                    casa.add_DispositivoNaDivisao(divisao, sb);
                    break;

                case "SmartSpeaker":
                    if (divisao == null) System.out.println("Linha inválida.");
                    SmartSpeaker ss = parseSmartSpeaker(linhaPartida[1]); //não percebo o erro
                    casa.add_DispositivoNaDivisao(divisao, ss);
                    break;
                case "SmartCamera":
                    if (divisao == null) System.out.println("Linha inválida.");
                    SmartSpeaker sc = parseSmartCamera(linhaPartida[1]); //não percebo o erro
                    casa.add_DispositivoNaDivisao(divisao, sc);
                    break;
                case "Fornecedor":
                    Menu.criaFornecedorEnergia(parseComercializadoresEnergia(linhaPartida[1]));
                    break;
                default:
                    System.out.println("Linha inválida.");
                    break;
            }
        }
        System.out.println("Feito");
    }

    public List<String> lerFicheiro(String nomeFich) {
        List<String> lines;

        try {lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8);}
        catch(IOException exc) {lines = new ArrayList<>();}

        return lines;
    }

    public Casa parseCasa(String input) {
        String[] campos = input.split(",");
        String nome = campos[0];
        String NIF = campos[1];
        String fornecedor = campos[2];
        this.houseID++;
        return new Casa(Integer.toString(houseID), nome, NIF, fornecedor);
    }

    public SmartBulb parseSmartBulb(String input, Casa casa){
        String[] campos = input.split(",");
        String estado = campos[0];
        int dimensoes = Integer.parseInt(campos[1]);
        double consumo = Double.parseDouble(campos[2]);
        return new SmartBulb(id,estado, dimensoes,consumo);
        //Esta Sara nao faz um caralho
        //O choca parece a Sara
    }

    public SmartSpeaker parseSmartSpeaker(String input, Casa casa) {
        String[] campos = input.split(",");
        int volume = Integer.parseInt(campos[0]);
        String estacao = campos[1];
        double consumo = Double.parseDouble(campos[3]);
        return new SmartSpeaker(id, volume,estacao,consumo); //same problem
    }

    public SmartCamera parseSmartCamera(String input, Casa casa) {
        String[] campos = input.split(",");
        String resolucao = campos[0];
        //não percebi estas duas linhas
        resolucao = resolucao.replace("(","");
        resolucao = resolucao.replace(")","");
        String[] larguraAltura = resolucao.split("x");
        int tamanho = Integer.parseInt(campos[1]);
        float largura = Float.parseFloat(larguraAltura[0]);
        float altura = Float.parseFloat(larguraAltura[1]);
        float consumo = Float.parseFloat(campos[2]);
        return new SmartCamera(id, largura,altura,tamanho,SmartCamera.Estado.OFF, consumo);
        //o mesmo problema que a função anterior
    }

    public FornecedorEnergia parseFornecedorEnergia(String input) {
        String[] campos = input.split(",");
        String nome = campos[0];
        return new FornecedorEnergia(nome); //está a dar mas no meu pede mais argumentos -> VER
    }

    // O nelson tem um parse da Marca mas não temos nenum classe para marca

    // Ele tem uma simulação, não sei se é para fazer

    //Que é isto?
    /*
    public CasaInteligente parseCasa(String input){
        String[] campos = input.split(",");
        String nome = campos[0];
        int nif = Integer.parseInt(campos[1]);
        (...)
        return new CasaInteligente(...);
    }
    */

}
