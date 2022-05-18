import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import SmartDevices.SmartBulb;
import SmartDevices.SmartCamera;
import SmartDevices.SmartDevice;
import SmartDevices.SmartSpeaker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu implements Serializable {
    Scanner scan;

    public Menu(Cidade cidade, Scanner s) throws IOException, InterruptedException, ClassNotFoundException{
        this.scan = s;
        clearConsole();
        mainMenu(scan);
    }

    public static void clearConsole() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

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

    public Cidade loadState(String nomeFicheiro) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(nomeFicheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Cidade c = (Cidade) ois.readObject();
        ois.close();
        return c;
    }

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
                createCidade(cidade, scan);
                break;

            default:
                clearConsole();
                mainMenu(scan);
                break;
        }
    }

    public void createCidade(Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException {
        clearConsole();
        String choice;

        System.out.println("What do you want to do?\n");
        System.out.println("Create, change or remove:");
        System.out.println("1 - Houses");
        System.out.println("2 - Energy suppliers");
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
                        while(!cidade.casas.containsKey(houseID)) {
                            System.out.println("Invalid option!");
                            System.out.print("Try again: ");
                            houseID = scan.nextLine();
                        }
                        clearConsole();
                        changeCasa(cidade.casas.get(houseID), cidade, scan);
                        break;

                    case("3"):
                        clearConsole();
                        System.out.print("House's id that you want to remove: ");
                        String idCasaR = scan.nextLine();
                        cidade.casas.remove(idCasaR);
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
                        while(!cidade.fornecedores.containsKey(nomeFornecedor)) {
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
                //System.out.println(cidade.toString());
                System.out.println(cidade.listaInfoCasa("casa1"));

                System.out.println("Press enter to continue");
                try{System.in.read();}
                catch(Exception e){}

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

    public void createCasa(Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException {
        String choice;

        System.out.print("House's id: ");
        String idCasa = scan.nextLine();
        while(cidade.casas.containsKey(idCasa)) {
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
        while(NIF.length() != 9 /* && onlyDigits(NIF, NIF.length())*/) {
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
                cidade.fornecedorDaCasa.put(casa.getIdCasa(), fe);

                break;

            case("2"):
                System.out.println("Wich one?");
                System.out.print("Energy supplier name: ");
                String nomeFornecedor = scan.nextLine();
                while(!cidade.fornecedores.containsKey(nomeFornecedor)) {
                    System.out.println("This supplier does not exists, try again!");
                    nomeFornecedor = scan.nextLine();
                }
                casa.add_Fornecedor(cidade.getFornecedor(nomeFornecedor));
                cidade.fornecedorDaCasa.put(casa.getIdCasa(), cidade.getFornecedor(nomeFornecedor));

                break;
        }

        createCidade(cidade, scan);
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

    public void createDivisao(Casa casa, Cidade cidade, Scanner scan) throws IOException, InterruptedException {
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

    public SmartDevice createDevice(Casa casa, Cidade cidade, Scanner scan) {
        SmartDevice sd = null;
        String choice;

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


    public SmartDevice createSmartBulb(Casa casa, Cidade cidade, Scanner scan) {
        String choice;

        System.out.print("SmartBulb ID: ");
        String id = scan.nextLine();
        while(casa.dispositivos.containsKey(id)) {
            System.out.println("This id already exists, try another one!");
            id = scan.nextLine();
        }

        System.out.print("\n");

        SmartBulb.Estado estado = SmartBulb.Estado.OFF;

        System.out.println("Mode: ");
        System.out.println("1 - ON");
        System.out.println("2 - OFF");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2")) {
            System.out.print("Choose a valid option (1/2): ");
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

        SmartBulb.Tonalidade tonalidade = SmartBulb.Tonalidade.NEUTRAL;
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

        System.out.print("Dimension: ");
        double dimension = -1;
        while(dimension <= 0) {
            try {
                dimension = Double.parseDouble(scan.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid option, try again!");
            }
        }

        System.out.print("\n");

        System.out.print("Base value: ");
        double consumoBase = -1;
        while(consumoBase < 0) {
            try {
                consumoBase = Double.parseDouble(scan.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid option, try again!");
            }
        }

        SmartDevice sb = new SmartBulb(id, estado, tonalidade, dimension, consumoBase);

        return sb;
    }

    public SmartDevice createSmartSpeaker(Casa casa, Cidade cidade, Scanner scan) {
        String choice;

        System.out.print("SmartSpeaker ID: ");
        String id = scan.nextLine();
        while(casa.dispositivos.containsKey(id)) {
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

        SmartSpeaker.Estado estado = SmartSpeaker.Estado.OFF;
        switch(choice) {
            case("1"):
                estado = SmartSpeaker.Estado.ON;
                break;

            case("2"):
                estado = SmartSpeaker.Estado.OFF;
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


        System.out.print("Device base consumption: ");
        double consumoBase = -1;
        while(consumoBase < 0) {
            try {
                consumoBase = Double.parseDouble(scan.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid option, try again!");
            }
        }

        System.out.print("\n");

        SmartDevice ss = new SmartSpeaker(id, estado, marca, volume, radioOnline, consumoBase);

        return ss;
    }

    public SmartDevice createSmartCamera(Casa casa, Cidade cidade, Scanner scan){
        String choice;

        System.out.print("SmartCamera ID: ");
        String id = scan.nextLine();
        while(casa.dispositivos.containsKey(id)) {
            System.out.println("This id already exists, try another one!");
            id = scan.nextLine();
        }

        System.out.print("\n");

        SmartCamera.Estado estado = SmartCamera.Estado.OFF;

        System.out.println("Mode: ");
        System.out.println("1 - ON: ");
        System.out.println("2 - OFF: ");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();

        switch(choice) {
            case("1"):
                estado = SmartCamera.Estado.ON;
                break;

            case("2"):
                estado = SmartCamera.Estado.OFF;
                break;

            default:
                break;
        }

        System.out.print("\n");

        System.out.println("Resolution: ");
        System.out.print("x: ");
        int x = scan.nextInt();
        scan.nextLine();
        System.out.print("y: ");
        int y = scan.nextInt();
        scan.nextLine();

        System.out.print("\n");

        System.out.print("File size: ");
        double tamanhoPacote = -1;
        while(tamanhoPacote <= 0) {
            try {
                tamanhoPacote = Double.parseDouble(scan.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid option, try again!");
            }
        }

        System.out.print("\n");

        System.out.print("Base value: ");
        double valorBase = -1;
        while(valorBase <= 0) {
            try {
                valorBase = Double.parseDouble(scan.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid option, try again!");
            }
        }


        SmartDevice sc = new SmartCamera(id, estado, x, y, tamanhoPacote, valorBase);

        return sc;
    }

    public FornecedorEnergia criaFornecedorEnergia(Cidade cidade, Scanner scan) {
        System.out.print("Energy supplier name: ");
        String nomeFornecedor = scan.nextLine();
        while(cidade.fornecedores.containsKey(nomeFornecedor)) {
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

        System.out.print("Discount: ");
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

        cidade.fornecedores.put(nomeFornecedor, fe);

        return fe;
    }

    public void changeCasa(Casa casa, Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException {
        System.out.println("What do you want to do?");
        System.out.println("1 - Change house ID");
        System.out.println("2 - Change house adress");
        System.out.println("3 - Change house owner name");
        System.out.println("4 - Change house NIF");
        System.out.println("5 - Add a new division");
        System.out.println("6 - Change division");
        System.out.println("7 - Remove division");
        System.out.println("8 - Change energy supplier");
        System.out.println("9 - Turn On/Off all devices in the house");
        System.out.println("0 - Go back");
        System.out.print("Choose an option: ");
        String choice = scan.nextLine();

        switch(choice) {
            case("1"):
                clearConsole();
                System.out.print("New Id: ");
                String novoID = scan.nextLine();
                while(cidade.casas.containsKey(novoID)) {
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

                System.out.print("\n");

                break;

            case("4"):
                clearConsole();
                System.out.print("New NIF: ");
                String novoNIF = scan.nextLine();
                casa.setNIF(novoNIF);

                System.out.print("\n");

                break;

            case("5"):
                clearConsole();
                createDivisao(casa, cidade, scan);
                break;

            case("6"):
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

            case("7"):
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

            case("8"):
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
                        while(!cidade.fornecedores.containsKey(nomeFornecedor)) {
                            System.out.println("This supplier does not exists!");
                            nomeFornecedor = scan.nextLine();
                            System.out.print("\n");
                        }

                        casa.setFornecedor(cidade.fornecedores.get(nomeFornecedor));
                        break;

                    case("0"):
                        clearConsole();
                        changeCasa(casa, cidade, scan);
                        break;
                }
                break;

            case("9"):
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
                changeDevice(casa.dispositivos.get(idDevice), divName, casa, cidade, scan);
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

    public void changeSmartBulb(SmartDevice sd, String divName, Casa casa, Cidade cidade, Scanner scan) throws IOException, InterruptedException, ClassNotFoundException{
        System.out.println("What do you want to do?");
        System.out.println("1 - Change SmartBulb ID");
        System.out.println("2 - Change Tone");
        System.out.println("3 - Change Mode");
        System.out.println("");
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
                int novoX = scan.nextInt();
                scan.nextLine();
                sc.setX(novoX);

                System.out.println("New Y: ");
                int novoY = scan.nextInt();
                scan.nextLine();
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

}
