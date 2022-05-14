import java.io.IOException;
import java.nio.charset.*;
import java.nio.file.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SmartDevices.SmartBulb;
import SmartDevices.SmartSpeaker;

public class Parser {
    public void parse(){
        Path path = Path.of("");
        String conteudo = Files.lerFicheiro(path);
        //SmartCity city = new SmartCity(ouseID, deviceID);
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
                    if (casa == null) System.out.println("Linha inválida.");
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
        try {
            List<String> lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8);
            return lines;
        }
        catch(IOException exc) {
            List<String> lines = new ArrayList<>();
            return lines;
        }
    }

    public Casa parseCasa(String input, Casa casa) {
        String[] campos = input.split(",");
        String nome = campos[0];
        int NIF = Integer.parseInt(campos[1]);
        String fornecedor = campos[2];
        FornecedorEnergia fornecedorEnergia = FornecedorEnergia.getComercializador(fornecedor);
        if (fornecedorEnergia == null){
            fornecedorEnergia = new FornecedorEnergia(fornecedor);
        }
        return new Casa(casa.getIdCasa(),nome,NIF,fornecedorEnergia);
    }

    public SmartBulb parseSmartBulb(String input, Casa casa){
        String[] campos = input.split(",");
        String estado = campos[0];
        int dimensoes = Integer.parseInt(campos[1]);
        double consumo = Double.parseDouble(campos[2]);
        return new SmartBulb(.giveDeviceId(), estado,dimensoes,consumo);
    }

    public FornecedorEnergia parseFornecedorEnergia(String input) {
        String[] campos = input.split(",");
        String nome = campos[0];
        return new ComercializadoresEnergia(nome);
    }

    public SmartCamera parseSmartCamera(String input, Casa casa) {
        String[] campos = input.split(",");
        String resolucao = campos[0];
        resolucao = resolucao.replace("(","");
        resolucao = resolucao.replace(")","");
        String[] larguraAltura = resolucao.split("x");
        int tamanho = Integer.parseInt(campos[1]);
        float largura = Float.parseFloat(larguraAltura[0]);
        float altura = Float.parseFloat(larguraAltura[1]);
        float consumo = Float.parseFloat(campos[2]);
        return new SmartCamera(.giveDeviceId(), largura,altura,tamanho,SmartCamera.state.OFF, consumo);
    }

    

    public SmartSpeaker parseSmartSpeaker(String input, Casa casa) {
        String[] campos = input.split(",");
        int volume = Integer.parseInt(campos[0]);
        String estacao = campos[1];
        double consumo = Double.parseDouble(campos[3]);
        return new SmartSpeaker(.giveDeviceId(), volume,estacao,consumo);
    }

    

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
