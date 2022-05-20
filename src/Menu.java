import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.DataInput;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import SmartDevices.SmartBulb;
import SmartDevices.SmartCamera;
import SmartDevices.SmartDevice;
import SmartDevices.SmartSpeaker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu implements Serializable {
    Scanner scan;

    /**
     * Método que invoca o Menu Principal
     * @param cidade
     * @param s
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public Menu(Cidade cidade, Scanner s) throws IOException, InterruptedException, ClassNotFoundException{
        this.scan = s;
        clearConsole();
        mainMenu(scan);
    }

    /**
     * Método que "limpa" o terminal
     * @throws IOException
     * @throws InterruptedException
     */
    public static void clearConsole() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    /**
     * Método enterToContinue
     * @param scan
     */
    public void enterToContinue(Scanner scan) {
        System.out.println("Press enter to continue!");
        try{scan.nextLine();}
        catch(Exception e){}
    }

    /**
     * Método que salva um Ficheiro
     * @param cidade
     * @param nomeFicheiro
     */
    public void saveState(Cidade cidade, String nomeFicheiro) {
       try {
           FileOutputStream fos = new FileOutputStream(nomeFicheiro);
           ObjectOutputStream oos = new ObjectOutputStream(fos);
           oos.writeObject(cidade);
           oos.flush();
           oos.close();
       } catch(FileNotFoundException e) {
           System.out.println("Error creating file with that name!");
       } catch(IOException e) {
           System.out.println("Error saving state!");
       }
    }

    /**
     * Método
     * @param nomeFicheiro
     * @throws IOException
     * @throws ClassNotFoundException
     * @return c - retorna uma cidade
     */
    public Cidade loadState(String nomeFicheiro) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(nomeFicheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Cidade c = (Cidade) ois.readObject();
        ois.close();
        return c;
    }

    /**
     * Menu Principal
     * @param scan
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void mainMenu(Scanner scan) throws IOException, InterruptedException, ClassNotFoundException {
        clearConsole();
        String choice;

        Map<String, Casa> casas = new HashMap<>();
        Map<String, FornecedorEnergia> fornecedorDaCasa = new HashMap<>();
        Map<String, FornecedorEnergia> fornecedores = new HashMap<>();
        Map<String, List<Fatura>> faturas = new HashMap<>();

        Cidade cidade = new Cidade(casas, fornecedorDaCasa, fornecedores, faturas);

        System.out.println("Menu:");
        System.out.println("1 - Create a city");
        System.out.println("2 - Import a log file");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();

        switch(choice) {
            case("1"):
                clearConsole();
                createCidade(cidade, scan);
                break;

            case("2"):
                Parser parser = new Parser(cidade);
                parser.parse();
                enterToContinue(scan);
                createCidade(cidade, scan);
                break;

            default:
                clearConsole();
                mainMenu(scan);
                break;
        }
    }

    /**
     * Método que cria o menu que cria a cidade
     * @param cidade
     * @param scan
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void createCidade(Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException {
        clearConsole();
        String choice;

        System.out.println("What do you want to do?\n");
        System.out.println("Create, change or remove:");
        System.out.println("1 - Houses");
        System.out.println("2 - Energy suppliers");
        System.out.print("\n");
        System.out.println("4 - Invoices");
        System.out.println("5 - Simulation");
        System.out.print("\n");
        System.out.println("7 - Save state");
        System.out.println("8 - Load state");
        System.out.print("\n");
        System.out.println("9 - Check State");
        System.out.print("\n");
        System.out.println("0 - Go back");
        System.out.print("\n");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();

        switch(choice) {
            case("1"): // House
                clearConsole();
                System.out.println("What do you want to do?");
                System.out.println("1 - Create house");
                System.out.println("2 - Change some house");
                System.out.println("3 - Remove house");
                System.out.println("0 - Go back");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();

                System.out.print("\n");

                switch(choice) {
                    case("1"):
                        clearConsole();
                        createCasa(cidade, scan);
                        break;

                    case("2"):
                        clearConsole();
                        System.out.println("What house?");
                        System.out.print("House id: ");
                        String houseID = scan.nextLine();
                        while(!cidade.getCasas().containsKey(houseID)) {
                            System.out.println("Invalid option!");
                            System.out.print("Try again: ");
                            houseID = scan.nextLine();
                        }
                        clearConsole();
                        changeCasa(cidade.getCasas().get(houseID), cidade, scan);
                        break;

                    case("3"):
                        clearConsole();
                        System.out.print("House's id that you want to remove: ");
                        String idCasaR = scan.nextLine();
                        cidade.getCasas().remove(idCasaR);
                        break;

                    case("0"):
                        clearConsole();
                        break;

                    default:
                        clearConsole();
                        break;
                }
                break;

            case("2"): //Energy suppliers
                clearConsole();
                System.out.println("What do you want to do?");
                System.out.println("1 - Creat an energy supplier");
                System.out.println("2 - Change some energy supplier");
                System.out.println("3 - Remove some energy supplier");
                System.out.println("0 - Go back");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();
                while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
                    System.out.print("Choose a valid option (1/2/3): ");
                    choice = scan.nextLine();
                }

                System.out.print("\n");

                switch(choice) {
                    case("1"):
                        clearConsole();
                        criaFornecedorEnergia(cidade, scan);
                        break;

                    case("2"):
                        clearConsole();
                        System.out.print("Energy supplier name: ");
                        String nomeFornecedor = scan.nextLine();
                        while(!cidade.getFornecedores().containsKey(nomeFornecedor)) {
                            System.out.println("Invalid name, try again!");
                            nomeFornecedor = scan.nextLine();
                        }
                        changeFornecedorEnergia(cidade.getFornecedor(nomeFornecedor), cidade, scan);
                        break;

                    case("3"):
                        clearConsole();
                        System.out.print("Energy supplier name: ");
                        nomeFornecedor = scan.nextLine();
                        int flag = cidade.removeFornecedor(nomeFornecedor);
                        if(flag == 1)
                            System.out.println("Energy supplier removed successfully!");
                        else
                            System.out.println("Error, the supplier was not removed because there are still houses with a contract!");
                        break;

                    case("0"):
                        clearConsole();
                        break;

                    default:
                        clearConsole();
                        break;
                }
                break;

            case("3"):
                clearConsole();

                System.out.print("Teste: ");
                String t = scan.nextLine();
                while(isWord(t)) {
                    System.out.println("Error, try again!");
                    t = scan.nextLine();
                }

                System.out.println("Palavra escrita: " + t);

                enterToContinue(scan);
                break;

            case("5"):
                clearConsole();
                simulation(cidade, scan);
                break;

            case("7"):
                clearConsole();
                System.out.print("Give the file a name: ");
                String nomeFicheiro = scan.nextLine();
                saveState(cidade, nomeFicheiro);
                System.out.println("Saved!");
                break;

            case("8"):
                clearConsole();
                System.out.print("Insert the name file: ");
                nomeFicheiro = scan.nextLine();
                cidade = loadState(nomeFicheiro);
                System.out.println("\nDone");
                break;

            case("9"):
                clearConsole();
                checkStateMenu(cidade, scan);
                break;

            case("0"):
                clearConsole();
                mainMenu(scan);
                break;

            default:
                break;
        }

        createCidade(cidade, scan);
    }

    /**
     * Método que cria uma Casa
     * @param cidade
     * @param scan
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void createCasa(Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException {
        String choice;

        System.out.println("Do you want to continue?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();

        switch(choice) {
            case("1"):
                break;

            case("2"):
                clearConsole();
                createCidade(cidade, scan);
                break;

            default:
                clearConsole();
                createCasa(cidade, scan);
                break;
        }

        System.out.print("\n");

        System.out.print("House's id: ");
        String idCasa = scan.nextLine();
        while(cidade.getCasas().containsKey(idCasa)) {
            System.out.println("This id has already been used, try again!");
            idCasa = scan.nextLine();
        }

        System.out.print("\n");

        System.out.print("Adress: ");
        String morada = scan.nextLine();

        System.out.print("\n");

        System.out.print("House owner: ");
        String nome = scan.nextLine();

        System.out.print("\n");

        System.out.print("NIF: ");
        String NIF = scan.nextLine();
        while(NIF.length() != 9) {
            System.out.println("The NIF must have 9 characters!");
            System.out.print("Try another one: ");
            NIF = scan.nextLine();
        }

        System.out.print("\n");

        Map<String, List<String>> divisoes = new HashMap<>();
        Map<String, SmartDevice> dispositivos = new HashMap<>();

        Casa casa = new Casa(idCasa, morada, nome, NIF, dispositivos, divisoes);
        cidade.add_Casa(idCasa, casa);

        System.out.println("Do you pretend to add a division in this house?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2")) {
            System.out.print("Choose a valid option (1/2): ");
            choice = scan.nextLine();
        }

        System.out.print("\n");

        while(choice.equals("1")) {
            createDivisao(casa, cidade, scan);

            System.out.print("\n");

            System.out.println("Do you pretend to add another division?");
            System.out.println("1 - Yes");
            System.out.println("2 - No");
            System.out.print("Choose an option: ");
            choice = scan.nextLine();
            while(!choice.equals("1") && !choice.equals("2")) {
                System.out.print("Choose a valid option (1/2): ");
                choice = scan.nextLine();
            }

            System.out.print("\n");
        }

        System.out.println("Join an energy supplier");
        System.out.println("1 - Create energy supplier");
        System.out.println("2 - Choose an existing energy supplier");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2")) {
            System.out.print("Choose a valid option (1/2): ");
            choice = scan.nextLine();
        }

        System.out.print("\n");

        switch(choice) {
            case("1"):
                FornecedorEnergia fe = criaFornecedorEnergia(cidade, scan);
                casa.add_Fornecedor(fe);
                cidade.getFornecedorDaCasa().put(casa.getIdCasa(), fe);

                break;

            case("2"):
                System.out.println("Wich one?");
                System.out.print("Energy supplier name: ");
                String nomeFornecedor = scan.nextLine();
                while(!cidade.getFornecedores().containsKey(nomeFornecedor)) {
                    System.out.println("This supplier does not exists, try again!");
                    nomeFornecedor = scan.nextLine();
                }
                casa.add_Fornecedor(cidade.getFornecedor(nomeFornecedor));
                cidade.getFornecedorDaCasa().put(casa.getIdCasa(), cidade.getFornecedor(nomeFornecedor));

                break;
        }

        createCidade(cidade, scan);
    }

    public static boolean isWord(String in) {
        return Pattern.matches("[a-zA-Z]+", in);
    }

    /**
     * Método que cria uma Divisão de uma Casa
     * @param casa
     * @param cidade
     * @param scan
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void createDivisao(Casa casa, Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException {
        String choice;

        System.out.print("Disivion name: ");
        String nomeDivisao = scan.nextLine();
        while(casa.getDivisoes().containsKey(nomeDivisao)) {
            System.out.println("This division already exists, try again!");
            nomeDivisao = scan.nextLine();
        }

        System.out.print("\n");

        casa.add_Divisao(nomeDivisao);

        System.out.println("Do you pretend to add a device?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2")) {
            System.out.print("Choose a valid option (1/2): ");
            choice = scan.nextLine();
        }

        System.out.print("\n");

        while(choice.equals("1")) {
            SmartDevice device = createDevice(casa, cidade, scan);

            if (device != null)
                casa.add_DispositivoNaDivisao(nomeDivisao, device);

            System.out.print("\n");

            System.out.println("Do you pretend to add another device?");
            System.out.println("1 - Yes");
            System.out.println("2 - No");
            System.out.print("Choose an option: ");
            choice = scan.nextLine();
            while(!choice.equals("1") && !choice.equals("2")) {
                System.out.print("Choose a valid option (1/2): ");
                choice = scan.nextLine();
            }

            System.out.print("\n");
        }
    }

    /**
     * Método que cria um Device
     * @param casa
     * @param cidade
     * @param scan
     * @throws IOException
     * @throws InterruptedException
     * @return sd - Retorna um Device
     */
    public SmartDevice createDevice(Casa casa, Cidade cidade, Scanner scan) throws IOException, InterruptedException {
        SmartDevice sd = null;
        String choice;

        System.out.println("Do you want to continue?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();

        switch(choice) {
            case("1"):
                break;

            case("2"):
                return sd;

            default:
                createDevice(casa, cidade, scan);
                break;
        }

        System.out.print("\n");

        System.out.println("What do you want to create?");
        System.out.println("1 - SmartBulb ");
        System.out.println("2 - SmartSpeaker");
        System.out.println("3 - SmartCamera");
        System.out.println("0 - Go back");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();

        System.out.print("\n");

        switch(choice) {
            case("1"):
                sd = createSmartBulb(casa, cidade, scan);
                break;

            case("2"):
                sd = createSmartSpeaker(casa, cidade, scan);
                break;

            case("3"):
                sd = createSmartCamera(casa, cidade, scan);
                break;

            case("0"):
                break;
        }

        return sd;
    }

    /**
     * Método que cria uma Lampada
     * @param casa
     * @param cidade
     * @param scan
     * @return sb - Retorna uma Lampada
     */
    public SmartDevice createSmartBulb(Casa casa, Cidade cidade, Scanner scan) {
        String choice;
        SmartBulb sb = null;

        System.out.println("Do you want to continue?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();

        switch(choice) {
            case("1"):
                break;

            case("2"):
                return sb;

            default:
                createSmartBulb(casa, cidade, scan);
                break;
        }

        System.out.print("\n");

        System.out.print("SmartBulb ID: ");
        String id = scan.nextLine();
        while(casa.getDispositivos().containsKey(id)) {
            System.out.println("This id already exists, try another one!");
            id = scan.nextLine();
        }

        System.out.print("\n");

        System.out.println("Mode: ");
        System.out.println("1 - ON");
        System.out.println("2 - OFF");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2")) {
            System.out.print("Choose a valid option (1/2): ");
            choice = scan.nextLine();
        }

        int estado = 1;
        switch(choice){
            case("1"):
                estado = 1;
                break;

            case("2"):
                estado = 2;
                break;
        }

        System.out.print("\n");

        System.out.println("Tone:");
        System.out.println("1 - Cold");
        System.out.println("2 - Neutral");
        System.out.println("3 - Warm");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
            System.out.print("Choose a valid option (1/2/3): ");
            choice = scan.nextLine();
        }

        System.out.print("\n");

        String tonalidade = "NEUTRAL";
        switch(choice) {
            case("1"):
                tonalidade = "COLD";
                break;

            case("2"):
                tonalidade = "NEUTRAL";
                break;

            case("3"):
                tonalidade = "WARM";
                break;
        }

        System.out.print("Dimension (cm): ");
        double dimension = -1;
        while(dimension <= 0) {
            try {
                dimension = Double.parseDouble(scan.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid option, try again!");
            }
        }

        System.out.print("\n");

        System.out.print("Device base consumption (kWh): ");
        double consumoBase = -1;
        while(consumoBase < 0) {
            try {
                consumoBase = Double.parseDouble(scan.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid option, try again!");
            }
        }

        sb = new SmartBulb(id, estado, tonalidade, dimension, consumoBase);

        switch(sb.getN_estado()) {
            case(1):
                sb.turnOn();
                break;

            case(2):
                sb.turnOff();
                break;
        }

        switch(sb.getString_tone()) {
            case("NEUTRAl"):
                sb.turn_NEUTRAL();
                break;

            case("WARM"):
                sb.turn_WARM();
                break;

            case("COLD"):
                sb.turn_COLD();
                break;
        }

        return sb;
    }

    /**
     * Método que cria uma Coluna
     * @param casa
     * @param cidade
     * @param scan
     * @return ss - Retorna uma Coluna
     */
    public SmartDevice createSmartSpeaker(Casa casa, Cidade cidade, Scanner scan) {
        String choice;
        SmartSpeaker ss = null;

        System.out.println("Do you want to continue?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();

        switch(choice) {
            case("1"):
                break;

            case("2"):
                return ss;

            default:
                createSmartSpeaker(casa, cidade, scan);
                break;
        }

        System.out.print("\n");

        System.out.print("SmartSpeaker ID: ");
        String id = scan.nextLine();
        while(casa.getDispositivos().containsKey(id)) {
            System.out.println("This id already exists, try another one!");
            id = scan.nextLine();
        }

        System.out.print("\n");

        System.out.println("Mode: ");
        System.out.println("1 - ON");
        System.out.println("2 - OFF");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2")) {
            System.out.print("Choose a valid option (1/2): ");
            choice = scan.nextLine();
        }

        int estado = 1;
        switch(choice){
            case("1"):
                estado = 1;
                break;

            case("2"):
                estado = 2;
                break;
        }

        System.out.print("\n");

        System.out.print("Brand: ");
        String marca = scan.nextLine();

        System.out.print("\n");


        System.out.print("Volume (0 to 100): ");
        int volume = -1;
        while(volume < 0 || volume > 100) {
            try {
                volume = Integer.parseInt(scan.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid option, try again!");
            }
        }

        System.out.print("\n");

        System.out.print("Online radio: ");
        String radioOnline = scan.nextLine();

        System.out.print("\n");


        System.out.print("Device base consumption (kWh): ");
        double consumoBase = -1;
        while(consumoBase < 0) {
            try {
                consumoBase = Double.parseDouble(scan.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid option, try again!");
            }
        }

        System.out.print("\n");

        ss = new SmartSpeaker(id, estado, marca, volume, radioOnline, consumoBase);

        switch(ss.getN_estado()) {
            case(1):
                ss.turnOn();
                break;

            case(2):
                ss.turnOff();
                break;
        }

        return ss;
    }

    /**
     * Método que cria uma Camera
     * @param casa
     * @param cidade
     * @param scan
     * @return sc - Retorna uma Camera
     */
    public SmartDevice createSmartCamera(Casa casa, Cidade cidade, Scanner scan){
        String choice;
        SmartCamera sc = null;

        System.out.println("Do you want to continue?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();

        switch(choice) {
            case("1"):
                break;

            case("2"):
                return sc;

            default:
                createSmartCamera(casa, cidade, scan);
                break;
        }

        System.out.print("\n");

        System.out.print("SmartCamera ID: ");
        String id = scan.nextLine();
        while(casa.getDispositivos().containsKey(id)) {
            System.out.println("This id already exists, try another one!");
            id = scan.nextLine();
        }

        System.out.print("\n");

        System.out.println("Mode: ");
        System.out.println("1 - ON: ");
        System.out.println("2 - OFF: ");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();

        int estado = 1;
        switch(choice){
            case("1"):
                estado = 1;
                break;

            case("2"):
                estado = 2;
                break;
        }

        System.out.print("\n");

        System.out.println("Resolution (px): ");
        System.out.print("x: ");
        int x = -1;
        while(x < 0) {
            try {
                x = Integer.parseInt(scan.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid option, try again!");
            }
        }

        System.out.print("y: ");
        int y = -1;
        while(y < 0) {
            try {
                y = Integer.parseInt(scan.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid option, try again!");
            }
        }

        System.out.print("\n");

        System.out.print("File size (sec): ");
        double tamanhoPacote = -1;
        while(tamanhoPacote < 0) {
            try {
                tamanhoPacote = Double.parseDouble(scan.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid option, try again!");
            }
        }

        System.out.print("\n");

        System.out.print("Device base consumption (kWh): ");
        double valorBase = -1;
        while(valorBase <= 0) {
            try {
                valorBase = Double.parseDouble(scan.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid option, try again!");
            }
        }

        sc = new SmartCamera(id, estado, x, y, tamanhoPacote, valorBase);

        switch(sc.getN_estado()) {
            case(1):
                sc.turnOn();
                break;

            case(2):
                sc.turnOff();
                break;
        }

        return sc;
    }

    /**
     * Método que cria um Fornecedor de Energia
     * @param cidade
     * @param scan
     * @return fe - Retorna um Fornecedor de Energia
     */
    public FornecedorEnergia criaFornecedorEnergia(Cidade cidade, Scanner scan) {
        System.out.print("Energy supplier name: ");
        String nomeFornecedor = scan.nextLine();
        while(cidade.getFornecedores().containsKey(nomeFornecedor)) {
            System.out.println("This supplier already exits!");
            nomeFornecedor = scan.nextLine();
        }

        System.out.print("\n");

        System.out.print("Base value: ");
        double valorBase = -1;
        while(valorBase < 0) {
            try {
                valorBase = Double.parseDouble(scan.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid option, try again!");
            }
        }

        System.out.print("\n");

        System.out.print("Discount (%): ");
        double desconto = -1;
        while(desconto < 0) {
            try {
                desconto = Double.parseDouble(scan.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid option, try again!");
            }
        }

        System.out.print("\n");

        FornecedorEnergia fe = new FornecedorEnergia(nomeFornecedor, valorBase, desconto);

        cidade.getFornecedores().put(nomeFornecedor, fe);

        return fe;
    }

    /**
     * Método que muda as informações de uma Casa
     * @param casa
     * @param cidade
     * @param scan
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void changeCasa(Casa casa, Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException {
        System.out.println("What do you want to do?");
        System.out.println("1 - Change house ID");
        System.out.println("2 - Change house adress");
        System.out.println("3 - Change house owner name");
        System.out.println("4 - Add a new division");
        System.out.println("5 - Change division");
        System.out.println("6 - Remove division");
        System.out.println("7 - Change energy supplier");
        System.out.println("8 - Turn On/Off all devices in the house");
        System.out.println("0 - Go back");
        System.out.print("Choose an option: ");
        String choice = scan.nextLine();

        switch(choice) {
            case("1"):
                clearConsole();
                System.out.print("New Id: ");
                String novoID = scan.nextLine();
                while(cidade.getCasas().containsKey(novoID)) {
                    System.out.println("This division is already created!");
                    System.out.print("Try another one: ");
                    novoID = scan.nextLine();
                }
                casa.setIdCasa(novoID);

                System.out.print("\n");

                break;

            case("2"):
                clearConsole();
                System.out.print("New Adress: ");
                String novaMorada = scan.nextLine();
                casa.setMorada(novaMorada);

                System.out.print("\n");

                break;

            case("3"):
                clearConsole();
                System.out.print("New Name: ");
                String novoNome = scan.nextLine();
                casa.setNome(novoNome);
                System.out.print("NIF: ");
                String novoNIF = scan.nextLine();
                casa.setNIF(novoNIF);

                System.out.print("\n");

                break;

            case("4"):
                clearConsole();
                createDivisao(casa, cidade, scan);
                break;

            case("5"):
                clearConsole();
                System.out.println("What division do you want to change?");
                System.out.print("Division name: ");
                String divName = scan.nextLine();
                while(!casa.getDivisoes().containsKey(divName)) {
                    System.out.println("This division does not exists!");
                    divName = scan.nextLine();
                }
                clearConsole();
                changeDivisao(divName, casa, cidade, scan);
                break;

            case("6"):
                clearConsole();
                System.out.println("What division do you want to remove?");
                System.out.print("Division name: ");
                String nomeDivisao = scan.nextLine();
                while(!casa.getDivisoes().containsKey(nomeDivisao)) {
                    System.out.println("This division does not exists!");
                    System.out.println("Do you want to try again?");
                    System.out.println("1 - Yes");
                    System.out.println("2 - No");
                    System.out.print("Choose an option: ");
                    choice = scan.nextLine();
                    while(!choice.equals("1") && !choice.equals("2")) {
                        System.out.print("Choose a valid option (1/2): ");
                        choice = scan.nextLine();
                    }
                    switch(choice) {
                        case("1"):
                            System.out.print("Division name: ");
                            nomeDivisao = scan.nextLine();
                            break;

                        case("2"):
                            changeCasa(casa, cidade, scan);
                            break;
                    }
                }
                casa.remove_Divisao(nomeDivisao);
                System.out.println("\nDone");
                break;

            case("7"):
                clearConsole();
                System.out.println("What do you want to do?");
                System.out.println("1 - Create a new energy supplier");
                System.out.println("2 - Choose an existing energy supplier");
                System.out.println("0 - Go back");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();
                while(!choice.equals("1") && !choice.equals("2") && !choice.equals("0")) {
                    System.out.print("Choose a valid option (1/2/0): ");
                    choice = scan.nextLine();
                }

                System.out.print("\n");

                switch(choice) {
                    case("1"):
                        casa.setFornecedor(criaFornecedorEnergia(cidade, scan));
                        break;

                    case("2"):
                        //Lista de fornecedores
                        System.out.println("Wich one?");
                        System.out.print("Energy supplier name: ");
                        String nomeFornecedor = scan.nextLine();
                        while(!cidade.getFornecedores().containsKey(nomeFornecedor)) {
                            System.out.println("This supplier does not exists!");
                            nomeFornecedor = scan.nextLine();
                            System.out.print("\n");
                        }

                        casa.setFornecedor(cidade.getFornecedores().get(nomeFornecedor));
                        break;

                    case("0"):
                        clearConsole();
                        changeCasa(casa, cidade, scan);
                        break;
                }
                break;

            case("8"):
                clearConsole();
                System.out.println("Turn On/Off");
                System.out.println("1 - On");
                System.out.println("2 - Off");
                System.out.println("0 - Go back");
                System.out.println("Choose an option: ");
                choice = scan.nextLine();
                while(!choice.equals("1") && !choice.equals("2") && !choice.equals("0")) {
                    System.out.print("Choose a valid option (1/2): ");
                    choice = scan.nextLine();
                }

                System.out.print("\n");

                switch(choice) {
                    case("1"):
                        casa.turn_On_Casa();
                        System.out.println("Done!");
                        break;

                    case("2"):
                        casa.turn_Off_Casa();
                        System.out.println("Done!");
                        break;

                    case("0"):
                        clearConsole();
                        changeCasa(casa, cidade, scan);
                        break;
                }
                break;

            case("0"):
                clearConsole();
                createCidade(cidade, scan);
                break;

            default:
                clearConsole();
                changeCasa(casa, cidade, scan);
                break;
        }
    }

    /**
     * Método que muda as informações de uma Divisao
     * @param divName
     * @param casa
     * @param cidade
     * @param scan
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void changeDivisao(String divName, Casa casa, Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException {
        System.out.println("What do you want to do?");
        System.out.println("1 - Change division name");
        System.out.println("2 - Add device");
        System.out.println("3 - Change device");
        System.out.println("4 - Remove device");
        System.out.println("5 - Turn On/Off a division");
        System.out.println("0 - Go back");
        System.out.println("Choose an option:");
        String choice = scan.nextLine();

        switch(choice) {
            case("1"):
                clearConsole();
                System.out.print("New Id: ");
                String newDivName = scan.nextLine();
                while(casa.getDivisoes().containsKey(newDivName)) {
                    System.out.println("This division already exists!");
                    newDivName = scan.nextLine();
                }
                casa.setNomeDaDivisao(divName, newDivName);

                System.out.print("\n");

                break;

            case("2"):
                clearConsole();
                SmartDevice device = createDevice(casa, cidade, scan);

                if(device != null)
                    casa.add_DispositivoNaDivisao(divName, device);

                break;

            case("3"):
                clearConsole();
                System.out.println("Which device do you want to change?");
                System.out.print("SmartDevice ID: ");
                String idDevice = scan.nextLine();
                changeDevice(casa.getDispositivos().get(idDevice), divName, casa, cidade, scan);
                break;

            case("4"):
                clearConsole();
                System.out.print("SmartDevice ID: ");
                String id = scan.nextLine();
                while(casa.getDispositivos().containsKey(id)) {
                    System.out.println("This device does not exists!");
                    newDivName = scan.nextLine();
                }
                casa.remove_DispositivoNaDivisao(id, divName);
                break;

            case("5"):
                clearConsole();
                System.out.println("Turn On/Off");
                System.out.println("1 - On");
                System.out.println("2 - Off");
                System.out.println("Choose an option: ");
                choice = scan.nextLine();
                while(!choice.equals("1") && !choice.equals("2")) {
                    System.out.print("Choose a valid option (1/2): ");
                    choice = scan.nextLine();
                }

                System.out.print("\n");

                switch(choice) {
                    case("1"):
                        casa.turn_On_Divisao(divName);
                        System.out.println("Done!");
                        break;

                    case("2"):
                        casa.turn_Off_Divisao(divName);
                        System.out.println("Done!");
                        break;
                }
                break;

            case("0"):
                clearConsole();
                changeCasa(casa, cidade, scan);
                break;

            default:
                clearConsole();
                changeDivisao(divName, casa, cidade, scan);
                break;
        }
    }

    /**
     * Método que muda as informações de um Device
     * @param sd
     * @param divName
     * @param casa
     * @param cidade
     * @param scan
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void changeDevice(SmartDevice sd, String divName, Casa casa, Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException {
        clearConsole();
        System.out.println("What do you want to do?");
        System.out.println("1 - Change SmartBulb");
        System.out.println("2 - Change SmartSpeaker");
        System.out.println("3 - Change SmartCamera");
        System.out.println("0 - Go back");
        System.out.print("Choose an option: ");
        String choice = scan.nextLine();

        switch(choice) {
            case("1"):
                clearConsole();
                changeSmartBulb(sd, divName, casa, cidade, scan);
                break;

            case("2"):
                clearConsole();
                changeSmartSpeaker(sd, divName, casa, cidade, scan);
                break;

            case("3"):
                clearConsole();
                changeSmartCamera(sd, divName, casa, cidade, scan);
                break;

            case("0"):
                clearConsole();
                changeDivisao(divName, casa, cidade, scan);

            default:
                clearConsole();
                changeDevice(sd, divName, casa, cidade, scan);
        }
    }

    /**
     * Método que muda as informações de uma SmartBulb
     * @param casa
     * @param cidade
     * @param scan
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void changeSmartBulb(SmartDevice sd, String divName, Casa casa, Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException{
        System.out.println("What do you want to do?");
        System.out.println("1 - Change SmartBulb ID");
        System.out.println("2 - Change Tone");
        System.out.println("3 - Change Mode");
        System.out.println(""); // Consumo
        System.out.println("0 - Go back");
        System.out.print("Choose an option: ");
        String choice = scan.nextLine();

        SmartBulb sb = (SmartBulb) sd;
        switch(choice) {
            case("1"):
                clearConsole();
                System.out.print("New Id: ");
                String novoID = scan.nextLine();
                while(casa.getDispositivos().containsKey(novoID)){
                    System.out.println("This id has already been used, try again!");
                    novoID = scan.nextLine();
                }
                sd.setId(novoID);
                break;

            case("2"):
                clearConsole();
                System.out.println("New Tone:");
                System.out.println("1 - Cold");
                System.out.println("2 - Neutral");
                System.out.println("3 - Warm");
                System.out.println("0 - Go back");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();

                System.out.print("\n");

                switch(choice){
                    case("1"):
                        sb.turn_COLD();
                        break;

                    case("2"):
                        sb.turn_NEUTRAL();
                        break;

                    case("3"):
                        sb.turn_WARM();
                        break;

                    default:
                        clearConsole();
                        changeSmartBulb(sd, divName, casa, cidade, scan);
                        break;
                }

                sd = (SmartDevice) sb;
                break;

            case("3"):
                clearConsole();
                System.out.println("Turn On/Off");
                System.out.println("1 - On");
                System.out.println("2 - Off");
                System.out.println("0 - Go back");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();

                System.out.print("\n");

                switch(choice) {
                    case("1"):
                        sd.turnOn();
                        System.out.println("Done!");
                        break;

                    case("2"):
                        sd.turnOff();
                        System.out.println("Done!");
                        break;

                    default:
                        clearConsole();
                        changeSmartBulb(sd, divName, casa, cidade, scan);
                        break;
                }
                break;

            case("0"):
                clearConsole();
                changeDevice(sd, divName, casa, cidade, scan);
                break;

            default:
                clearConsole();
                changeSmartBulb(sd, divName, casa, cidade, scan);
                break;
        }
    }

    /**
     * Método que muda as informações de um SmartSpeaker
     * @param sd
     * @param divName
     * @param casa
     * @param cidade
     * @param scan
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void changeSmartSpeaker(SmartDevice sd, String divName, Casa casa, Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException{
        System.out.println("What do you want to do?");
        System.out.println("1 - Change SmartSpeaker ID");
        System.out.println("2 - Change Brand");
        System.out.println("3 - Change Mode");
        System.out.println("4 - Change Radio");
        System.out.println("0 - Go back");
        System.out.println("Choose an option: ");
        String choice = scan.nextLine();

        SmartSpeaker ss = (SmartSpeaker) sd;
        switch(choice){
            case("1"):
                clearConsole();
                System.out.print("New Id: ");
                String novoID = scan.nextLine();
                while(casa.getDispositivos().containsKey(novoID)){
                    System.out.println("This id has already been used, try again!");
                    novoID = scan.nextLine();
                }
                sd.setId(novoID);
                break;

            case("2"):
                clearConsole();
                System.out.print("New brand: ");
                String NovaMarca = scan.nextLine();
                ss.setMarca(NovaMarca);
                sd = (SmartDevice) ss;
                break;

            case("3"):
                clearConsole();
                System.out.println("Turn On/Off");
                System.out.println("1 - On");
                System.out.println("2 - Off");
                System.out.println("0 - Go back");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();

                System.out.print("\n");

                switch(choice) {
                    case("1"):
                        sd.turnOn();
                        System.out.println("Done!");
                        break;

                    case("2"):
                        sd.turnOff();
                        System.out.println("Done!");
                        break;

                    default:
                        clearConsole();
                        changeSmartSpeaker(sd, divName, casa, cidade, scan);
                        break;
                }
                break;

            case("4"):
                clearConsole();
                System.out.print("New Radio: ");
                String novaRadio = scan.nextLine();
                ss.setRadioOnline(novaRadio);
                sd = (SmartDevice) ss;
                break;

            case("0"):
                clearConsole();
                changeDevice(sd, divName, casa, cidade, scan);
                break;

            default:
                clearConsole();
                changeSmartSpeaker(sd, divName, casa, cidade, scan);
                break;
        }
    }

    /**
     * Método que muda as informações de uma SmartCamera
     * @param sd
     * @param divName
     * @param casa
     * @param cidade
     * @param scan
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void changeSmartCamera(SmartDevice sd, String divName, Casa casa, Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException{
        System.out.println("What do you want to do?");
        System.out.println("1 - Change SmartCamera ID");
        System.out.println("2 - Change Resolution");
        System.out.println("3 - Change Mode");
        System.out.println("4 - Change Package Size");
        System.out.println("0 - Go back");
        System.out.print("Choose an option: ");
        String choice = scan.nextLine();

        SmartCamera sc = (SmartCamera) sd;
        switch(choice){
            case("1"):
                clearConsole();
                System.out.print("New Id: ");
                String novoID = scan.nextLine();
                while(casa.getDispositivos().containsKey(novoID)){
                    System.out.println("This id has already been used, try again!");
                    novoID = scan.nextLine();
                }
                sd.setId(novoID);
                break;

            case("2"):
                clearConsole();
                System.out.print("New X: ");
                int novoX = -1;
                while(novoX < 0) {
                    try {novoX = Integer.parseInt(scan.nextLine());}
                    catch(NumberFormatException e) {System.out.println("Invalid option, try again!");}
                }
                sc.setX(novoX);

                System.out.println("New Y: ");
                int novoY = -1;
                while(novoY < 0) {
                    try {novoY = Integer.parseInt(scan.nextLine());}
                    catch(NumberFormatException e) {System.out.println("Invalid option, try again!");}
                }
                sc.setY(novoY);

                sd = (SmartDevice) sc;
                break;

            case("3"):
                clearConsole();
                System.out.println("Turn On/Off");
                System.out.println("1 - On");
                System.out.println("2 - Off");
                System.out.println("0 - Go back");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();

                System.out.print("\n");

                switch(choice) {
                    case("1"):
                        sd.turnOn();
                        System.out.println("Done!");
                        break;

                    case("2"):
                        sd.turnOff();
                        System.out.println("Done!");
                        break;

                    default:
                        clearConsole();
                        changeSmartCamera(sd, divName, casa, cidade, scan);
                        break;
                }
                break;

            case("0"):
                clearConsole();
                changeDevice(sd, divName, casa, cidade, scan);
                break;

            default:
                clearConsole();
                changeSmartCamera(sd, divName, casa, cidade, scan);
        }
    }

    /**
     * Método que muda as informações de um Fornecedor de Energia
     * @param fe
     * @param cidade
     * @param scan
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void changeFornecedorEnergia(FornecedorEnergia fe, Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException {
        System.out.println("What do you want to do?");
        System.out.println("1 - Change name");
        System.out.println("2 - Change base value");
        System.out.println("3 - Change discount");
        System.out.println("0 - Go back");
        System.out.print("Choose an option: ");
        String choice = scan.nextLine();

        switch(choice) {
            case("1"):
                clearConsole();
                System.out.print("New name: ");
                String novoNome = scan.nextLine();
                while(cidade.getFornecedores().containsKey(novoNome)){
                    System.out.println("This id has already been used, try again!");
                    novoNome = scan.nextLine();
                }
                fe.setNomeEmpresa(novoNome);
                break;

            case("2"):
                clearConsole();
                System.out.print("New base value: ");
                double novoValorBase = -1;
                while(novoValorBase < 0) {
                    try {
                        novoValorBase = Double.parseDouble(scan.nextLine());
                    } catch(NumberFormatException e) {
                        System.out.println("Invalid option, try again!");
                    }
                }
                fe.setValorBase(novoValorBase);
                break;

            case("3"):
                clearConsole();
                System.out.print("New discount: ");
                double novoDesconto = -1;
                while(novoDesconto < 0) {
                    try {
                        novoValorBase = Double.parseDouble(scan.nextLine());
                    } catch(NumberFormatException e) {
                        System.out.println("Invalid option, try again!");
                    }
                }
                fe.setValorBase(novoDesconto);
                break;

            case("0"):
                clearConsole();
                createCidade(cidade, scan);
                break;

            default:
                clearConsole();
                changeFornecedorEnergia(fe, cidade, scan);
                break;
        }
    }

    /**
     * Método que mostra as informações da Cidade
     * @param cidade
     * @param scan
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void checkStateMenu(Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException{
        clearConsole();
        System.out.println("What do you want to check?");
        System.out.println("1 - Houses");
        System.out.println("2 - Devices");
        System.out.println("3 - Energy Suppliers");
        System.out.println("4 - Bills");
        System.out.println("0 - Go back");
        System.out.print("Choose an option: ");
        String choice = scan.nextLine();

        switch(choice){
            case("1"):
                clearConsole();
                System.out.println("What do you want to check?");
                System.out.println("1 - List of Houses");
                System.out.println("2 - Information of specific House");
                System.out.println("0 - Go back");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();

                switch(choice){
                    case("1"):
                        clearConsole();
                        System.out.println(cidade.listaCasas());
                        enterToContinue(scan);
                        break;

                    case("2"):
                        clearConsole();
                        System.out.print("House ID: ");
                        String idCasa = scan.nextLine();
                        System.out.println(cidade.listaInfoCasa(cidade, idCasa));
                        enterToContinue(scan);
                        break;

                    case("0"):
                        clearConsole();
                        checkStateMenu(cidade, scan);
                        break;

                    default:
                        clearConsole();
                        checkStateMenu(cidade, scan);
                        break;
                }
                break;

            case("2"):
                clearConsole();
                System.out.print("House id: ");
                String idCasa = scan.nextLine();
                System.out.println("What do you want to check?");
                System.out.println("1 - List of Devices");
                System.out.println("2 - Information of specific Device");
                System.out.println("0 - Go back");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();

                switch(choice){
                    case("1"):
                        clearConsole();
                        System.out.println(cidade.getCasa(idCasa).listaDevices());
                        enterToContinue(scan);
                        break;

                    case("2"):
                        clearConsole();
                        System.out.print("Device ID: ");
                        String idDevice = scan.nextLine();
                        System.out.println(cidade.getCasa(idCasa).listaInfoDevice(idDevice));
                        enterToContinue(scan);
                        break;

                    case("0"):
                        clearConsole();
                        checkStateMenu(cidade, scan);
                        break;

                    default:
                        clearConsole();
                        checkStateMenu(cidade, scan);
                        break;
                }
                break;

            case("3"):
                clearConsole();
                System.out.println("What do you want to check?");
                System.out.println("1 - List of Energy Suppliers: ");
                System.out.println("2 - Information of specific Energy Supplier: ");
                System.out.println("0 - Go back");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();
                switch(choice){
                    case("1"):
                        clearConsole();
                        System.out.println(cidade.listaFornecedores());
                        enterToContinue(scan);
                        break;

                    case("2"):
                        clearConsole();
                        System.out.print("Energy supplier name: ");
                        String novoNomeF = scan.nextLine();
                        System.out.println(cidade.listaInfoFornecedor(novoNomeF));
                        enterToContinue(scan);
                        break;

                    case("0"):
                        clearConsole();
                        checkStateMenu(cidade, scan);
                        break;

                    default:
                        clearConsole();
                        checkStateMenu(cidade, scan);
                        break;
                }
                break;

            case("4"):
                clearConsole();
                System.out.println("What do you want to check?");
                System.out.println("1 - List of Bills: ");
                System.out.println("2 - Information of specific Bills: ");
                System.out.println("0 - Go back");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();
                switch(choice){
                    case("1"):
                        clearConsole();
                        //cidade.listaFaturas();
                        enterToContinue(scan);
                        break;

                    case("2"):
                        clearConsole();
                        //cidade.infoBills(novoId);
                        enterToContinue(scan);
                        break;

                    case("0"):
                        clearConsole();
                        checkStateMenu(cidade, scan);
                        break;

                    default:
                        clearConsole();
                        checkStateMenu(cidade, scan);
                        break;
                }
                break;

            case("0"):
                clearConsole();
                createCidade(cidade, scan);
                break;

            default:
                clearConsole();
                checkStateMenu(cidade, scan);
                break;
        }
        checkStateMenu(cidade, scan);
    }

    /**
     * Método que avança o tempo
     * @param cidade
     * @param scan
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void simulation(Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException {
        clearConsole();
        String choice;

        System.out.println("Do you want to continue?");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();

        switch(choice) {
            case("1"):
                break;

            case("2"):
                clearConsole();
                createCidade(cidade, scan);
                break;

            default:
                clearConsole();
                simulation(cidade, scan);
                break;
        }

        clearConsole();

        System.out.println("Initial date:");
        System.out.println("1 - Atual");
        System.out.println("2 - Enter a new date");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();

        LocalDateTime dataInicial = LocalDateTime.now();
        switch(choice) {
            case("1"):
                dataInicial = LocalDateTime.now();
                break;

            case("2"):
                System.out.print("Date (dd-MM-yyyy HH:mm): ");
                String dataI = scan.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                dataInicial = LocalDateTime.parse(dataI, formatter);
                break;
        }

        System.out.print("\n");

        System.out.print("Date that you want to go: (dd-MM-yyyy HH:mm): ");
        String dataF = scan.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dataFinal = LocalDateTime.parse(dataF, formatter);
        while(!dataInicial.isBefore(dataFinal)) {
            System.out.println("Invalid date, try again!");
            dataF = scan.nextLine();
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            dataFinal = LocalDateTime.parse(dataF, formatter);
        }

        System.out.print("\n");

        System.out.println("Initial date: " + dataInicial);
        System.out.println("Final date: " + dataFinal);

        System.out.print("\n");

        long periodo = ChronoUnit.DAYS.between(dataInicial, dataFinal);
        System.out.println(periodo);

        enterToContinue(scan);

        clearConsole();
        System.out.println("What do you want to do?");
        System.out.println("1 - What is the house that spent the most in that period");
        System.out.println("2 - Which supplier has the highest invoicing volume");
        System.out.println("3 - Invoices list's issued by a supplier");
        System.out.println("4 - Give an ordering of the largest energy consumers during a period");
        System.out.println("0 - Go back");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();

        System.out.print("\n");

        switch(choice) {
            case("1"):
                clearConsole();
                Casa casaM = cidade.getCasaMaisGastadoraPeriodo(cidade, 10);
                System.out.println("The house that spent the most was: " + casaM.getIdCasa());
                System.out.println("kWh = " + cidade.getConsumoCasaPeriodo(casaM, cidade, 10));

                enterToContinue(scan);

                System.out.println("1 - See more information about the house");
                System.out.println("0 - Go back");
                System.out.print("Choose an option: ");
                String option = scan.nextLine();
                switch(option){
                    case ("1"):
                        clearConsole();
                        System.out.println(cidade.listaInfoCasa(cidade, casaM.getIdCasa()));
                        enterToContinue(scan);
                        break;

                    case ("0"):
                        clearConsole();
                        simulation(cidade, scan);
                        break;

                    default:
                        clearConsole();
                        simulation(cidade, scan);
                        break;
                }
                break;

            case("2"):
                clearConsole();

                System.out.println(cidade.listaFornecedores());
                System.out.print("Choose an Energy Supllier: ");
                String nomeF = scan.nextLine();
                FornecedorEnergia fornecedor = cidade.getFornecedor(nomeF);

                System.out.print("Invoicing volume = " + cidade.volumeFatFornecedor(fornecedor, 10) + "€");

                enterToContinue(scan);
                break;

            case("3"):
                System.out.println(cidade.listaFornecedores());
                System.out.print("Choose an Energy Supllier: ");
                nomeF = scan.nextLine();
                fornecedor = cidade.getFornecedor(nomeF);


        }
    }
}
