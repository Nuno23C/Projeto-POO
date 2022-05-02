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
    private Map<String, Casa> casas = new HashMap<>(); // Id da casa - Casa
    private Map<String, SmartDevice> dispositivos = new HashMap<>(); // Id do device - Device
    private Map<String, List<String>> divisoes; // Divisão - Lista de Ids dos Devices

    public Menu(){
        mainMenu();
    }

    public void mainMenu(){
        Scanner scan = new Scanner(System.in);

        System.out.println("Menu:");
        System.out.println("1 - Casa");
        System.out.println("2 - Dispositivos");
        System.out.println("3 - Divisões");
        System.out.println("4 - Fornecedor de energia");
        System.out.println("0 - Consultar");
        System.out.print("Escolhe uma opção: ");

        switch(scan.next()){
            case("1"):
                System.out.println("1 - Criar casa");
                System.out.println("2 - Alterar casa");
                System.out.println("3 - Remover casa");
                switch(scan.next()) {
                    case("1"):
                        createCasa();
                        break;

                    case("2"):
                        //alteraDevice();
                        break;

                    case("3"):
                        //removeDevice();
                        break;
                }
                break;

            case("2"):
                System.out.println("1 - Criar dispositivo");
                System.out.println("2 - Alterar dispositivo");
                System.out.println("3 - Remover dispositivo");
                switch(scan.next()) {
                    case("1"):
                        createDevice();
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
                        //removeDisisao();
                        break;

                }
                break;


            case("0"):
                System.out.println(dispositivos.toString());

            default:
                System.out.println("Opção invalida!");
                break;
        }

        mainMenu();

        //scan.close();
    }


    public void createCasa(){
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

        Casa casa = new Casa(idCasa, morada, nome, NIF);
        casas.put(idCasa, casa);

        //scan.close();
    }

    public void createDevice() {
        Scanner scan = new Scanner(System.in);

        System.out.println("1 - SmartBulb ");
        System.out.println("2 - SmartSpeaker");
        System.out.println("3 - SmartCamera");
        System.out.println("0 - Voltar ao menu anterior");
        System.out.print("Escolhe uma opção: ");
        switch(scan.next()) {
            case("1"):
                createSmartBulb();
                break;

            case("2"):
                createSmartSpeaker();
                break;

            case("3"):
                createSmartCamera();
                break;

            case("0"):
                mainMenu();
                break;

            default:
                System.out.println("Opção invalida!");
                break;
        }
        //scan.close();
    }


    public void createSmartBulb() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Id da SmartBulb: ");
        String id = scan.nextLine();
        while(dispositivos.containsKey(id)) {
            System.out.println("Este id já foi utilizado, por favor introduza outro!");
            id = scan.nextLine();
        }

        System.out.println("Estado: ");
        System.out.println("1 - ON");
        System.out.println("2 - OFF");
        System.out.println("0 - Voltar");
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
        dispositivos.put(id, sb);
    }

    public void createSmartSpeaker() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Id do SmartSpeaker: ");
        String id = scan.nextLine();
        while(dispositivos.containsKey(id)) {
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
        dispositivos.put(id, ss);

        //scan.close();
    }


    public void createSmartCamera(){
        Scanner scan = new Scanner(System.in);

        System.out.print("Id da SmartCamera: ");
        String id = scan.nextLine();
        while(dispositivos.containsKey(id)) {
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

        System.out.println("Resoluçao: ");
        System.out.println("1 - 4k ");
        System.out.println("2 - 1080 ");
        System.out.println("3 - 720 ");
        System.out.println("4 - 480 ");
        System.out.println("5 - 144 ");
        System.out.print("Escolhe uma opção: ");
        double resolucao = 1080;
        switch(scan.next()) {
            case("1"):
                resolucao = 4000;
                break;

            case("2"):
                resolucao = 1080;
                break;

            case("3"):
                resolucao = 720;
                break;

            case("4"):
                resolucao = 480;
                break;

            case("5"):
                resolucao = 144;
                break;

            default:
                break;
        }

        System.out.print("Data: ");
        LocalDateTime dataAtual = LocalDateTime.now();
        System.out.println(dataAtual);

        SmartDevice sc = new SmartCamera(id, estado, resolucao, dataAtual);
        dispositivos.put(id, sc);
    }

}
