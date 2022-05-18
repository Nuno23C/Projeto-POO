import java.nio.file.Files;

public class n {

        public List<String> readFile(String nomeFich) {
        try {
            return Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8);

        }
        catch(IOException exc) {
            return new ArrayList<>();
        }

    }

    /**
     * Função que dá parse às logs dadas em ficheiro
     */
    public void parse() throws IOException{
        Path path = Path.of("./logs/logs.txt");
        //Path path = FileSystems.getDefault().getPath("logs", "logs.txt");
        String content = Files.readString(path);
        //List<String> linhas = readFile("dados.csv");
        String[] contentSplited = content.split("\n");
        String[] linhaPartida;
        String divisao = "";

        for (String linha : contentSplited) {

            linhaPartida = linha.split(":", 2);

            switch (linhaPartida[0]) {
                case "Casa" -> parseCasa(linhaPartida[1]);
                case "Divisao" -> {
                    //if (casaMaisRecente == null) System.out.println("Linha inválida.");
                    divisao = linhaPartida[1];
                    this.city.criaDivisao(divisao);
                }
                case "SmartBulb" -> {
                    if (divisao == null) System.out.println("Linha inválida.");
                    parseSmartBulb(divisao, linhaPartida[1]);
                }
                case "SmartCamera" -> {
                    if (divisao == null) System.out.println("Linha inválida.");
                    parseSmartCamera(divisao, linhaPartida[1]);
                }
                case "SmartSpeaker" -> {
                    if (divisao == null) System.out.println("Linha inválida.");
                    parseSmartSpeaker(divisao, linhaPartida[1]);

                }
                case "Fornecedor" -> parseComercializadoresEnergia(linhaPartida[1]);
                case "Marca" -> parseMarca(linhaPartida[1]);
                default -> {
                }
                //System.out.println("Linha inválida.");
            }
        }
        System.out.println("Ficheiro carregado com sucesso.");
    }

    /**
     * Função que dá parse às casas em uma SmartCity
     * @param input String atual a ser lida das logs
     */
    public void parseCasa(String input){
        String[] campos = input.split(",");
        String nome = campos[0];
        int nif = Integer.parseInt(campos[1]);
        String fornecedor = campos[2];
        this.city.createHouse(nome, nif, "", fornecedor);
    }

    /**
     * Função que dá parse às lampadas em uma SmartCity
     * @param divisao Divisao em que o dispositivo vai ser adicionado
     * @param input String atual a ser lida das logs
     */
    public void parseSmartBulb(String divisao,String input){
        String[] campos = input.split(",");
        String mode = campos[0];
        int dimensions = Integer.parseInt(campos[1]);
        double consumo = Double.parseDouble(campos[2]);
        this.city.addDeviceToDivisaoL(divisao, this.city.giveDeviceId(), mode, dimensions,consumo);
    }

    /**
     * Função que dá parse às camaras desegurança em uma SmartCity
     * @param divisao Divisao em que o dispositivo vai ser adicionado
     * @param input String atual a ser lida das logs
     */
    public void parseSmartCamera(String divisao, String input){
        String[] campos = input.split(",");
        String resolucao = campos[0]; //resolução 0000x0000?
        resolucao = resolucao.replace("(","");
        resolucao = resolucao.replace(")","");
        String[] widthHeight = resolucao.split("x");
        int tamanho = Integer.parseInt(campos[1]);
        float width = Float.parseFloat(widthHeight[0]);
        float heigth = Float.parseFloat(widthHeight[1]);
        double consumo = Double.parseDouble(campos[2]);
		this.city.addDeviceToDivisaoC(divisao, this.city.giveDeviceId(),width,heigth,tamanho,consumo);
    }

    /**
     * Função que dá parse às colunas em uma SmartCity
     * @param divisao Divisao em que o dispositivo vai ser adicionado
     * @param input String atual a ser lida das logs
     */
    public void parseSmartSpeaker(String divisao, String input){
        String[] campos = input.split(",");
        int vol = Integer.parseInt(campos[0]);
        String estacao = campos[1];
        double consumo = Double.parseDouble(campos[3]);
        this.city.addDeviceToDivisaoS(divisao, this.city.getDeviceId(), vol, estacao, campos[2], consumo);
    }

    /**
     * Função que dá parse aos fornecedores em uma SmartCity
     * @param input String atual a ser lida das logs
     */
    public void parseComercializadoresEnergia(String input){
        String[] campos = input.split(",");
        String nome = campos[0];
        this.city.createComercializadorEnergia(nome);
    }

}
