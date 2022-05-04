import java.util.Scanner;
import java.time.LocalDateTime;
import SmartDevices.SmartBulb;
import SmartDevices.SmartCamera;
import SmartDevices.SmartDevice;
import SmartDevices.SmartSpeaker;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private Map<String, Casa> casas = new HashMap<>(); // Todas as casas ---> Id da casa - Casa
    private Map<String, FornecedorEnergia> fornecedores = new HashMap<>(); // Todos os fornecedores ---> nome do Fornecedor - Fornecedor

    public Menu(){
        mainMenu();
    }

    public void mainMenu(){
        Scanner scan = new Scanner(System.in);
        String choice;

        do {
            System.out.println("Menu:");
            System.out.println("1 - Casa");
            System.out.println("2 - Dispositivos");
            System.out.println("3 - Divisões");
            System.out.println("4 - Fornecedor de energia");
            System.out.println("5 - Consultar");
            System.out.print("Escolhe uma opção: ");
            choice = scan.next();
            switch(choice) {
                case("1"):
                    System.out.println("1 - Criar casa");
                    System.out.println("2 - Alterar casa");
                    System.out.println("3 - Remover casa");
                    switch(scan.next()) {
                        case("1"):
                            createCasa();
                            break;

                        case("2"):
                            //alteraCasa();
                            break;

                        case("3"):
                            //removeCasa();
                            break;
                    }
                    break;

                case("2"):
                    System.out.println("1 - Criar dispositivo");
                    System.out.println("2 - Alterar dispositivo");
                    System.out.println("3 - Remover dispositivo");
                    switch(scan.next()) {
                        case("1"):
                            //createDevice();
                            break;

                        case("2"):
                            //alteraDevice();
                            break;

                        case("3"):
                            //removeDevice();
                            break;

                    }
                    break;

                case("3"):
                    System.out.println("1 - Criar divisão");
                    System.out.println("2 - Alterar divisão");
                    System.out.println("3 - Remover divisão");
                    switch(scan.next()) {
                        case("1"):
                            //createDivisao();
                            break;

                        case("2"):
                            //alteraDivisao();
                            break;

                        case("3"):
                            //removeDivisao();
                            break;
                    }
                    break;


                case("5"):
                    System.out.println(casas.toString());
                    break;

                default:
                    System.out.println("Opção invalida!");
                    break;
            }
        } while(!choice.equals("1") || !choice.equals("2") || !choice.equals("3") || !choice.equals("4") || !choice.equals("5"));
    }


    public void createCasa() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Id da casa: ");
        String idCasa = scan.nextLine();
        while(casas.containsKey(idCasa)) {
            System.out.println("Este id já foi utilizado, por favor introduza outro!");
            idCasa = scan.nextLine();
        }

        System.out.print("Morada: ");
        String morada = scan.nextLine();

        System.out.print("Nome do proprietário: ");
        String nome = scan.nextLine();

        System.out.print("NIF: ");
        String NIF = scan.nextLine();

        Map<String, List<String>> divisoes = new HashMap<>();
        Map<String, SmartDevice> dispositivos = new HashMap<>();
        Casa casa = new Casa(idCasa, morada, nome, NIF, dispositivos, divisoes);
        casas.put(idCasa, casa);

        String choice;
        do {
            System.out.println("Do you pretend to add a division in this house?");
            System.out.println("1 - Yes");
            System.out.println("2 - No -> go back to menu");
            System.out.print("Choose an option:");
            choice = scan.next();
            switch(choice) {
                case("1"):
                    createDivisao(casa);
                    System.out.println("Do you pretend to add another division?");
                    System.out.println("1 - Yes");
                    System.out.println("2 - No");
                    choice = scan.next();
                    while(choice.equals("1")) {
                        createDivisao(casa);
                    }
                    casa.add_Fornecedor(criaFornecedorEnergia());
                    break;

                case("2"):
                    mainMenu();
                    break;

                default:
                    System.out.println("Invalid option!");
                    break;
            }
        } while(!choice.equals("1") || !choice.equals("2"));
    }

    public void createDivisao(Casa casa) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Disivion: ");
        String nomeDivisao = scan.nextLine();
        while(casas.containsKey(nomeDivisao)) {
            System.out.println("This division is already created!");
            nomeDivisao = scan.nextLine();
        }

        casa.add_Divisao(nomeDivisao);

        String choice;
        do {
            System.out.println("Do you pretend to add a device?");
            System.out.println("1 - Yes");
            System.out.println("2 - No");
            System.out.print("Choose an option:");
            choice = scan.next();
            switch(choice) {
                case("1"):
                    casa.add_DispositivoNaDivisao(nomeDivisao, createDevice(casa));
                    System.out.println("Do you pretend to add another device?");
                    System.out.println("1 - Yes");
                    System.out.println("2 - No");
                    choice = scan.next();
                    while(choice.equals("1")) {
                        casa.add_DispositivoNaDivisao(nomeDivisao, createDevice(casa));
                        System.out.println("Do you pretend to add another device?");
                        System.out.println("1 - Yes");
                        System.out.println("2 - No");
                        choice = scan.next();
                    }
                    break;

                case("2"):
                    mainMenu();
                    break;

                default:
                    System.out.println("Invalid option!");
                    break;
            }
        } while(!choice.equals("1") || !choice.equals("2"));
    }

    public SmartDevice createDevice(Casa casa) {
        Scanner scan = new Scanner(System.in);
        SmartDevice sd = null;

        System.out.println("1 - SmartBulb ");
        System.out.println("2 - SmartSpeaker");
        System.out.println("3 - SmartCamera");
        System.out.println("0 - Go back");
        System.out.print("Choose an option: ");
        switch(scan.next()) {
            case("1"):
                sd = createSmartBulb(casa);
                break;

            case("2"):
                sd = createSmartSpeaker(casa);
                break;

            case("3"):
                sd = createSmartCamera(casa);
                break;

            case("0"):
                mainMenu();
                break;

            default:
                System.out.println("Opção invalida!");
                break;
        }

        return sd;
    }


    public SmartDevice createSmartBulb(Casa casa) {
        Scanner scan = new Scanner(System.in);

        System.out.print("ID da SmartBulb: ");
        String id = scan.nextLine();
        while(casa.dispositivos.containsKey(id)) {
            System.out.println("Este id já foi utilizado, por favor introduza outro!");
            id = scan.nextLine();
        }

        System.out.println("Estado: ");
        System.out.println("1 - ON");
        System.out.println("2 - OFF");
        System.out.print("Escolhe uma opção: ");
        SmartBulb.Estado estado = SmartBulb.Estado.OFF;
        switch(scan.next()){
            case("1"):
                estado = SmartBulb.Estado.ON;
                break;

            case("2"):
                estado = SmartBulb.Estado.OFF;
                break;

            default:
                System.out.println("Invalid option!");
                break;
        }

        System.out.println("Tonalidade:");
        System.out.println("1 - Cold");
        System.out.println("2 - Neutral");
        System.out.println("3 - Warm");
        System.out.println("0 - Voltar");
        System.out.print("Escolhe uma opção: ");
        SmartBulb.Tonalidade tonalidade = SmartBulb.Tonalidade.NEUTRAL;
        switch(scan.next()) {
            case("1"):
                tonalidade = SmartBulb.Tonalidade.COLD;
                break;

            case("2"):
                tonalidade = SmartBulb.Tonalidade.NEUTRAL;
                break;

            case("3"):
                tonalidade = SmartBulb.Tonalidade.WARM;
                break;

            default:
                break;
        }

        System.out.print("Dimensões:");
        double dimensões = scan.nextDouble();

        SmartDevice sb = new SmartBulb(id, estado, tonalidade, dimensões);

        return sb;
    }

    public SmartDevice createSmartSpeaker(Casa casa) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Id do SmartSpeaker: ");
        String id = scan.nextLine();
        while(casa.dispositivos.containsKey(id)) {
            System.out.println("Este id já foi utilizado, por favor introduza outro!");
            id = scan.nextLine();
        }

        System.out.println("Estado: ");
        System.out.println("1 - ON");
        System.out.println("2 - OFF");
        System.out.println("0 - Voltar");
        System.out.print("Escolhe uma opção: ");
        SmartSpeaker.Estado estado = SmartSpeaker.Estado.OFF;
        switch(scan.next()) {
            case("1"):
                estado = SmartSpeaker.Estado.ON;
                break;

            case("2"):
                estado = SmartSpeaker.Estado.OFF;
                break;
        }

        System.out.print("Marca da SmartSpeaker: ");
        String marca = scan.nextLine();

        System.out.print("Volume: ");
        int volume = scan.nextInt();

        System.out.print("Radio online: ");
        String radioOnline = scan.nextLine();

        System.out.print("Consumo base do dispositivo: ");
        double consumoBase = scan.nextDouble();

        SmartDevice ss = new SmartSpeaker(id, estado, marca, volume, radioOnline, consumoBase);

        return ss;
    }


    public SmartDevice createSmartCamera(Casa casa){
        Scanner scan = new Scanner(System.in);

        System.out.print("Id da SmartCamera: ");
        String id = scan.nextLine();
        while(casa.dispositivos.containsKey(id)) {
            System.out.println("Este id já foi utilizado, por favor introduza outro!");
            id = scan.nextLine();
        }

        System.out.println("Estado: ");
        System.out.println("1 - ON: ");
        System.out.println("2 - OFF: ");
        System.out.println("0 - Voltar: ");
        System.out.print("Escolhe uma opção: ");
        SmartCamera.Estado estado = SmartCamera.Estado.OFF;
        switch(scan.next()) {
            case("1"):
                estado = SmartCamera.Estado.ON;
                break;

            case("2"):
                estado = SmartCamera.Estado.OFF;
                break;

            default:
                break;
        }


        System.out.println("Resolution: ");
        System.out.println("x: ");
        int x = scan.nextInt();
        System.out.println("y: ");
        int y = scan.nextInt();

        System.out.print("Data: ");
        LocalDateTime dataAtual = LocalDateTime.now();
        System.out.println(dataAtual);

        SmartDevice sc = new SmartCamera(id, estado, x, y, dataAtual);

        return sc;
    }

    public FornecedorEnergia criaFornecedorEnergia() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Energy supplier: ");
        String nomeFornecedor = scan.nextLine();

        while(fornecedores.containsKey(nomeFornecedor)) {
            System.out.println("This supplier already exits!");
            nomeFornecedor = scan.nextLine();
        }

        FornecedorEnergia fe = new FornecedorEnergia(nomeFornecedor);

        return fe;
    }
}
