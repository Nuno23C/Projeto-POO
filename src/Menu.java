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
    private Map<String, List<String>> fornecedores = new HashMap<>(); // Todos os fornecedores e as casas ---> nome do Fornecedor - Lista de casas
    private Map<String, List<String>> faturas = new HashMap<>(); // Todas as faturas de todas as casas ---> nome da Casas - Lista de faturas

    public Menu(){
        mainMenu();
    }

    public void mainMenu(){
        Scanner scan = new Scanner(System.in);
        String choice;

        System.out.println("Menu:");
        System.out.println("1 - House");
        System.out.println("2 - Devices");
        System.out.println("3 - Divisions");
        System.out.println("4 - Energy supplier");
        System.out.println("5 - Check");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5")) {
            System.out.print("Chose a valid option (1/2/3/4/5): ");
            choice = scan.nextLine();
        }

        switch(choice) {
            case("1"):
                System.out.println("1 - Create house");
                System.out.println("2 - Change house");
                System.out.println("3 - Remove house");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();
                while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
                    System.out.print("Chose a valid option (1/2/3): ");
                    choice = scan.nextLine();
                }

                switch(choice) {
                    case("1"):
                        createCasa();
                        break;

                    case("2"):
                        System.out.print("House's id that you want to change: ");
                        String idCasaC = scan.next();
                        alteraCasa(casas.get(idCasaC));
                        break;

                    case("3"):
                        System.out.print("House's id that you want to remove: ");
                        String idCasaR = scan.next();
                        casas.remove(idCasaR);
                        break;
                }

                break;

            case("2"):
                System.out.println("1 - Create device");
                System.out.println("2 - Change device");
                System.out.println("3 - Remove device");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();
                while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
                    System.out.print("Chose a valid option (1/2/3): ");
                    choice = scan.nextLine();
                }

                switch(choice) {
                    case("1"):
                        // createDevice();
                        // adicionar a uma divisao
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
                System.out.println("1 - Creat division");
                System.out.println("2 - Change division");
                System.out.println("3 - Remove division");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();
                while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
                    System.out.print("Chose a valid option (1/2/3): ");
                    choice = scan.nextLine();
                }

                switch(choice) {
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
        }
    }

    public void createCasa() {
        Scanner scan = new Scanner(System.in);
        String choice;

        System.out.print("House's id: ");
        String idCasa = scan.nextLine();
        while(casas.containsKey(idCasa)) {
            System.out.println("This division is already created!");
            System.out.print("Try another one: ");
            idCasa = scan.nextLine();
        }

        System.out.print("Adress: ");
        String morada = scan.nextLine();

        System.out.print("House owner: ");
        String nome = scan.nextLine();

        System.out.print("NIF: ");
        String NIF = scan.nextLine();
        while(NIF.length() != 9 /* && onlyDigits(NIF, NIF.length())*/) {
            System.out.println("The NIF must have 9 characters!");
            System.out.print("Try another one:");
            NIF = scan.nextLine();
        }

        Map<String, List<String>> divisoes = new HashMap<>();
        Map<String, SmartDevice> dispositivos = new HashMap<>();
        Casa casa = new Casa(idCasa, morada, nome, NIF, dispositivos, divisoes);
        casas.put(idCasa, casa);

        System.out.println("Do you pretend to add a division in this house?");
        System.out.println("1 - Yes");
        System.out.println("2 - No -> go back to menu");
        System.out.print("Choose an option:");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2")) {
            System.out.print("Chose a valid option (1/2): ");
            choice = scan.nextLine();
        }

        switch(choice) {
            case("1"):
                createDivisao(casa);
                System.out.println("Do you pretend to add another division?");
                System.out.println("1 - Yes");
                System.out.println("2 - No");
                System.out.print("Choose an option:");
                choice = scan.nextLine();
                while(!choice.equals("1") && !choice.equals("2")) {
                    System.out.print("Chose a valid option (1/2): ");
                    choice = scan.nextLine();
                }

                while(choice.equals("1")) {
                    createDivisao(casa);
                    System.out.println("Do you pretend to add another division?");
                    System.out.println("1 - Yes");
                    System.out.println("2 - No");
                    System.out.print("Choose an option:");
                    choice = scan.nextLine();
                }

                casa.add_Fornecedor(criaFornecedorEnergia());

                break;

            case("2"):
                break;
        }

        mainMenu();
    }

/*
    public boolean onlyDigits(String str, int n) {
        int i;
        for (i = 0; i < n; i++) {
            if (Character.isDigit(str.charAt(i)))
                return true;
            else
                return false;
        }

        return false;
    }
*/

    public void createDivisao(Casa casa) {
        Scanner scan = new Scanner(System.in);
        String choice;

        System.out.print("Disivion: ");
        String nomeDivisao = scan.nextLine();
        while(casa.getDivisoes().containsKey(nomeDivisao)) {
            System.out.println("This division already exists!");
            nomeDivisao = scan.nextLine();
        }

        casa.add_Divisao(nomeDivisao);

        System.out.println("Do you pretend to add a device?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2")) {
            System.out.print("Chose a valid option (1/2): ");
            choice = scan.nextLine();
        }

        switch(choice) {
            case("1"):
                SmartDevice device = createDevice(casa);

                if (device != null)
                    casa.add_DispositivoNaDivisao(nomeDivisao, device);

                System.out.println("Do you pretend to add another device?");
                System.out.println("1 - Yes");
                System.out.println("2 - No");
                System.out.print("Choose an option: ");
                choice = scan.next();
                while(choice.equals("1")) {
                    device = createDevice(casa);

                    if (device != null)
                        casa.add_DispositivoNaDivisao(nomeDivisao, device);

                    System.out.println("Do you pretend to add another device?");
                    System.out.println("1 - Yes");
                    System.out.println("2 - No");
                    System.out.print("Choose an option: ");
                    choice = scan.next();
                }
                break;

            case("2"):
                //mainMenu();
                break;
        }

        //mainMenu();
    }

    public SmartDevice createDevice(Casa casa) {
        Scanner scan = new Scanner(System.in);
        SmartDevice sd = null;
        String choice;

        System.out.println("1 - SmartBulb ");
        System.out.println("2 - SmartSpeaker");
        System.out.println("3 - SmartCamera");
        System.out.println("0 - Go back");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("0")) {
            System.out.print("Chose a valid option (1/2/3/0): ");
            choice = scan.nextLine();
        }

        switch(choice) {
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
                break;
        }

        return sd;
    }


    public SmartDevice createSmartBulb(Casa casa) {
        Scanner scan = new Scanner(System.in);
        String choice;

        System.out.print("SmartBulb ID: ");
        String id = scan.nextLine();
        while(casa.dispositivos.containsKey(id)) {
            System.out.println("This id already exists, try another one!");
            id = scan.nextLine();
        }

        SmartBulb.Estado estado = SmartBulb.Estado.OFF;

        System.out.println("Mode: ");
        System.out.println("1 - ON");
        System.out.println("2 - OFF");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2")) {
            System.out.print("Chose a valid option (1/2): ");
            choice = scan.nextLine();
        }

        switch(choice){
            case("1"):
                estado = SmartBulb.Estado.ON;
                break;

            case("2"):
                estado = SmartBulb.Estado.OFF;
                break;
        }

        SmartBulb.Tonalidade tonalidade = SmartBulb.Tonalidade.NEUTRAL;

        System.out.println("Tone:");
        System.out.println("1 - Cold");
        System.out.println("2 - Neutral");
        System.out.println("3 - Warm");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
            System.out.print("Chose a valid option (1/2/3): ");
            choice = scan.nextLine();
        }

        switch(choice) {
            case("1"):
                tonalidade = SmartBulb.Tonalidade.COLD;
                break;

            case("2"):
                tonalidade = SmartBulb.Tonalidade.NEUTRAL;
                break;

            case("3"):
                tonalidade = SmartBulb.Tonalidade.WARM;
                break;
        }

        System.out.print("Dimension:");
        double dimensões = scan.nextDouble();

        SmartDevice sb = new SmartBulb(id, estado, tonalidade, dimensões);

        return sb;
    }

    public SmartDevice createSmartSpeaker(Casa casa) {
        Scanner scan = new Scanner(System.in);
        String choice;

        System.out.print("SmartSpeaker ID: ");
        String id = scan.nextLine();
        while(casa.dispositivos.containsKey(id)) {
            System.out.println("This id already exists, try another one!");
            id = scan.nextLine();
        }

        SmartSpeaker.Estado estado = SmartSpeaker.Estado.OFF;
        System.out.println("Mode: ");
        System.out.println("1 - ON");
        System.out.println("2 - OFF");
        System.out.print("Choose an option: ");
        choice = scan.next();
        while(!choice.equals("1") && !choice.equals("2")){
            System.out.println("Chose a valid option (1/2):");
            choice=scan.next();
        }

        switch(choice) {
            case("1"):
                estado = SmartSpeaker.Estado.ON;
                break;

            case("2"):
                estado = SmartSpeaker.Estado.OFF;
                break;
        }

        System.out.println("SmartSpeaker brand: ");
        String marca = scan.nextLine();

        System.out.println("Volume: ");
        int volume = scan.nextInt();

        System.out.println("Online radio: ");
        String radioOnline = scan.nextLine();

        System.out.println("Device base consumption: ");
        double consumoBase = scan.nextDouble();

        SmartDevice ss = new SmartSpeaker(id, estado, marca, volume, radioOnline, consumoBase);

        return ss;
    }

    public SmartDevice createSmartCamera(Casa casa){
        Scanner scan = new Scanner(System.in);
        String choice;

        System.out.print("SmartCamera ID: ");
        String id = scan.nextLine();
        while(casa.dispositivos.containsKey(id)) {
            System.out.println("This id already exists, try another one!");
            id = scan.nextLine();
        }

        SmartCamera.Estado estado = SmartCamera.Estado.OFF;

        System.out.println("Mode: ");
        System.out.println("1 - ON: ");
        System.out.println("2 - OFF: ");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2")) {
            System.out.print("Chose a valid option (1/2): ");
            choice = scan.nextLine();
        }

        switch(choice) {
            case("1"):
                estado = SmartCamera.Estado.ON;
                break;

            case("2"):
                estado = SmartCamera.Estado.OFF;
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

        System.out.print("Energy supplier name: ");
        String nomeFornecedor = scan.nextLine();
        while(fornecedores.containsKey(nomeFornecedor)) {
            System.out.println("This supplier already exits!");
            nomeFornecedor = scan.nextLine();
        }

        System.out.print("Base value: ");
        double valorBase = scan.nextDouble();

        System.out.print("Discount: ");
        double desconto = scan.nextDouble();

        FornecedorEnergia fe = new FornecedorEnergia(nomeFornecedor);

        return fe;
    }

    public void alteraCasa(Casa casa){
        Scanner scan = new Scanner(System.in);
        String choice;

        System.out.println("What do you to change?");
        System.out.println("1 - Id");
        System.out.println("2 - Adress");
        System.out.println("3 - Name");
        System.out.println("4 - NIF");
        System.out.println("0 - Go back");
        System.out.println("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("0")) {
            System.out.print("Chose a valid option (1/2/3/4/0): ");
            choice = scan.nextLine();
        }

        switch(choice){
            case("1"):
                System.out.print("New Id: ");
                String novoid = scan.next();
                casa.setIdCasa(novoid);
                break;

            case("2"):
                System.out.print("New Adress: ");
                String novoMorada = scan.next();
                casa.setMorada(novoMorada);
                break;

            case("3"):
                System.out.print("New Name: ");
                String novoNome = scan.next();
                casa.setNome(novoNome);
                break;

            case("4"):
                System.out.print("New NIF: ");
                String novoNIF = scan.next();
                casa.setNIF(novoNIF);
                break;

            case("0"):
                break;
        }

        mainMenu();
    }
}
