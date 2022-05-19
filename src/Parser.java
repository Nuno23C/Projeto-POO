import java.io.IOException;
import java.nio.charset.*;
import java.nio.file.*;

import java.util.ArrayList;
import java.util.List;

import SmartDevices.SmartBulb;
import SmartDevices.SmartCamera;
import SmartDevices.SmartSpeaker;
import SmartDevices.SmartBulb.Tonalidade;

public class Parser {
    private int houseID = 0;
    private int deviceID = 0;
    private Cidade cidade;

    public Parser(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<String> lerFicheiro(String nomeFich) {
        List<String> lines;

        try {lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8);}
        catch(IOException exc) {lines = new ArrayList<>();}

        return lines;
    }

    public void parse() throws IOException{
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
                    cidade.add_Casa(casa.getIdCasa(), casa);

                    break;

                case "Divisao":
                    if (casa == null)
                        System.out.println("Invalid line!");
                    divisao = linhaPartida[1];
                    casa.add_Divisao(divisao);
                    break;

                case "SmartBulb":
                    if (divisao == null)
                        System.out.println("Invalid line!");
                    SmartBulb sb = parseSmartBulb(linhaPartida[1], casa);
                    casa.add_DispositivoNaDivisao(divisao, sb);
                    break;

                case "SmartSpeaker":
                    if (divisao == null)
                        System.out.println("Invalid line!");
                    SmartSpeaker ss = parseSmartSpeaker(linhaPartida[1], casa);
                    casa.add_DispositivoNaDivisao(divisao, ss);
                    break;

                case "SmartCamera":
                    if (divisao == null)
                        System.out.println("Invalid line!");
                    SmartCamera sc = parseSmartCamera(linhaPartida[1], casa);
                    casa.add_DispositivoNaDivisao(divisao, sc);
                    break;

                case "Fornecedor":
                    //FornecedorEnergia fe = parseFornecedorEnergia(linhaPartida[1]);
                    //System.out.println(fe);
                    //cidade.fornecedores.put(fe.getNomeEmpresa(), fe);
                    break;

                default:
                    System.out.println("Linha inválida.");
                    break;
            }
        }
        System.out.println("Feito");
    }

    public Casa parseCasa(String input) {
        String[] campos = input.split(",");
        String nome = campos[0];
        String NIF = campos[1];
        String nomeFornecedor = campos[2];
        FornecedorEnergia fe = cidade.getFornecedor(nomeFornecedor);
        String idCasa = Integer.toString(houseID);
        cidade.fornecedorDaCasa.put(idCasa, fe);
        this.houseID++;

        return new Casa(idCasa, nome, NIF, fe);
    }

    public SmartBulb parseSmartBulb(String input, Casa casa){
        String[] campos = input.split(",");
        String tonalidade = campos[0];
        Tonalidade tone = Tonalidade.NEUTRAL;
        switch(tonalidade) {
            case("NEUTRAl"):
                tone = Tonalidade.NEUTRAL;
                break;

            case("WARM"):
                tone = Tonalidade.WARM;
                break;

            case("COLD"):
                tone = Tonalidade.COLD;
                break;
        }
        int dimensoes = Integer.parseInt(campos[1]);
        double consumo = Double.parseDouble(campos[2]);
        this.deviceID++;

        return new SmartBulb(Integer.toString(deviceID), SmartBulb.Estado.OFF, tone, dimensoes, consumo);
    }

    public SmartSpeaker parseSmartSpeaker(String input, Casa casa) {
        String[] campos = input.split(",");
        int volume = Integer.parseInt(campos[0]);
        String radioOnline = campos[1];
        String marca = campos[2];
        double consumo = Double.parseDouble(campos[3]);
        this.deviceID++;

        return new SmartSpeaker(Integer.toString(deviceID), SmartSpeaker.Estado.OFF, marca, volume, radioOnline, consumo);
    }

    public SmartCamera parseSmartCamera(String input, Casa casa) {
        String[] campos = input.split(",");
        String resolucao = campos[0];
        resolucao = resolucao.replace("(","");
        resolucao = resolucao.replace(")","");
        String[] larguraAltura = resolucao.split("x");
        int largura = Integer.parseInt(larguraAltura[0]);
        int altura = Integer.parseInt(larguraAltura[1]);
        double tamanho = Double.parseDouble(campos[1]);
        double consumo = Double.parseDouble(campos[2]);
        this.deviceID++;

        return new SmartCamera(Integer.toString(deviceID), SmartCamera.Estado.OFF, largura, altura, tamanho, consumo);
    }

    public FornecedorEnergia parseFornecedorEnergia(String input) {
        String[] campos = input.split(",");
        String nome = campos[0];
        double valorBase = Double.parseDouble(campos[1]);
        double desconto = Double.parseDouble(campos[2]);

        return new FornecedorEnergia(nome, valorBase, desconto);
        //FornecedorEnergia fe = cidade.getFornecedor(nome);
        //fe.setValorBase(valorBase);
        //fe.setDesconto(desconto);
    }
}
