import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDateTime;

import SmartDevices.SmartBulb;
import SmartDevices.SmartCamera;
import SmartDevices.SmartDevice;
import SmartDevices.SmartSpeaker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu implements Serializable {
    Scanner scan;

    public Menu(Cidade cidade, Scanner s) throws IOException, InterruptedException{
        this.scan = s;
        clearConsole();
        mainMenu(scan);
    }

    public static void clearConsole() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    public void saveState(Cidade cidade, Scanner scan) {
       try {
           System.out.println("Give the file a name:");
           String nomeDoFicheiro = scan.nextLine();
           File f = new File("./output/" + nomeDoFicheiro);
           while(f.exists()){
                System.out.println("Give a valid name!");
                nomeDoFicheiro = scan.nextLine();
                f = new File("./output/" + nomeDoFicheiro);
           }
           cidade.saveState(nomeDoFicheiro);
       } catch(FileNotFoundException e) {
           System.out.println("Error creating file with that name!");
       } catch(IOException e) {
           System.out.println("Error saving state!");
       }
    }

    public void loadState(Cidade cidade, Scanner scan) {
        System.out.println("Insira o path para o ficheiro que pretende carregar:");
        StringBuilder path = new StringBuilder();
        path.append(scan.nextLine());
        cidade = (Cidade) ReadObjectFromFile(path.toString()); //createMenuLine devolve a cidade guardada no ficheiro
    }

    public Object ReadObjectFromFile(String filepath) {
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();

            System.out.println("Estado carregado com sucesso!");
            objectIn.close();
            return obj;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void mainMenu(Scanner scan) throws IOException, InterruptedException {
        clearConsole();
        String choice;

        Map<String, Casa> casas = new HashMap<>();
        Map<String, List<String>> fornecedores = new HashMap<>();
        Map<String, List<String>> faturas = new HashMap<>();

        Cidade cidade = new Cidade(casas, fornecedores, faturas);

        System.out.println("Menu:");
        System.out.println("1 - Create a city");
        System.out.println("2 - Import a log file");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2")) {
            System.out.print("Choose a valid option (1/2): ");
            choice = scan.nextLine();
        }

        switch(choice) {
            case("1"):
                clearConsole();
                createCidade(cidade, scan);
                break;
        }
    }

    public void createCidade(Cidade cidade, Scanner scan) throws IOException, InterruptedException {
        String choice;

        System.out.println("What do you want to do?\n");
        System.out.println("Create, change or remove:");
        System.out.println("1 - Houses");
        System.out.println("2 - Devices");
        System.out.println("3 - Energy suppliers");
        System.out.print("\n");
        System.out.println("7 - Save state");
        System.out.println("8 - Load state");
        System.out.print("\n");
        System.out.println("9 - Check");
        System.out.print("\n");
        System.out.println("0 - Go back");
        System.out.print("\n");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("7") && !choice.equals("8") && !choice.equals("9") && !choice.equals("0")) {
            System.out.print("Choose a valid option (1/2/3/7/8/9/0): ");
            choice = scan.nextLine();
        }

        clearConsole();

        switch(choice) {
            case("1"): // House
                System.out.println("What do you want to do?");
                System.out.println("1 - Create house");
                System.out.println("2 - Change some house");
                System.out.println("3 - Remove house");
                System.out.println("0 - Go back");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();
                while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("0")) {
                    System.out.print("Choose a valid option (1/2/3/0): ");
                    choice = scan.nextLine();
                }

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
                        changeCasa(cidade.casas.get(houseID), cidade, scan);
                        break;

                    case("3"):
                        System.out.print("House's id that you want to remove: ");
                        String idCasaR = scan.next();
                        cidade.casas.remove(idCasaR);
                        break;

                    case("0"):
                        break;
                }

                break;

            case("2"): // Devices
                System.out.println("What do you want to do?");
                System.out.println("1 - Create device");
                System.out.println("2 - Change some device");
                System.out.println("3 - Remove device");
                System.out.println("0 - Go back");
                System.out.print("Choose an option: ");
                choice = scan.nextLine();
                while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("0")) {
                    System.out.print("Choose a valid option (1/2/3/0): ");
                    choice = scan.nextLine();
                }

                System.out.print("\n");

                switch(choice) {
                    case("1"):
                        //createDevice();
                        //adicionar a uma divisao
                        break;

                    case("2"):
                        //changeDevice();
                        //Turn On/Off a specific device
                        break;

                    case("3"):
                        //removeDevice();
                        break;

                    }

                break;

            case("3"): //Energy suppliers
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
                        //create();
                        break;

                    case("2"):
                        //change();
                        break;

                    case("3"):
                        //remove();
                        break;
                }
                break;

            case("7"):
                saveState(cidade, scan);
                System.out.println("Saved!");
                break;

            case("8"):
                loadState(cidade, scan);
                System.out.println("\nDone");
                break;

            case("9"):
                System.out.println(cidade.toString());
                break;

            case("0"):
                mainMenu(scan);
                break;
        }

        createCidade(cidade, scan);
    }

    public void createCasa(Cidade cidade, Scanner scan) throws IOException, InterruptedException {
        String choice;

        System.out.print("House's id: ");
        String idCasa = scan.nextLine();
        while(cidade.casas.containsKey(idCasa)) {
            System.out.println("This division is already created!");
            System.out.print("Try another one: ");
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

        switch(choice) {
            case("1"):
                createDivisao(casa, cidade, scan);
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

                while(choice.equals("1")) {
                    createDivisao(casa, cidade, scan);

                    System.out.print("\n");

                    System.out.println("Do you pretend to add another division?");
                    System.out.println("1 - Yes");
                    System.out.println("2 - No");
                    System.out.print("Choose an option: ");
                    choice = scan.nextLine();

                    System.out.print("\n");
                }
                break;

            case("2"):
                break;
        }

        casa.add_Fornecedor(criaFornecedorEnergia(cidade, scan));
        clearConsole();
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
            System.out.println("This division already exists!");
            System.out.print("Try another one:");
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
            choice = scan.next();
            while(!choice.equals("1") && !choice.equals("2")) {
                System.out.print("Choose a valid option (1/2): ");
                choice = scan.nextLine();
            }

            System.out.print("\n");
        }
    }

    public SmartDevice createDevice(Casa casa, Cidade cidade, Scanner scan) {
        //Scanner scan = new Scanner(System.in);
        SmartDevice sd = null;
        String choice;

        System.out.println("What do you want to create?");
        System.out.println("1 - SmartBulb ");
        System.out.println("2 - SmartSpeaker");
        System.out.println("3 - SmartCamera");
        System.out.println("0 - Go back");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("0")) {
            System.out.print("Choose a valid option (1/2/3/0): ");
            choice = scan.nextLine();
        }

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
        //Scanner scan = new Scanner(System.in);
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

        SmartBulb.Tonalidade tonalidade = SmartBulb.Tonalidade.NEUTRAL;

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
        double dimensões = scan.nextDouble();

        System.out.print("\n");

        SmartDevice sb = new SmartBulb(id, estado, tonalidade, dimensões);

        return sb;
    }

    public SmartDevice createSmartSpeaker(Casa casa, Cidade cidade, Scanner scan) {
        //Scanner scan = new Scanner(System.in);
        String choice;

        System.out.print("SmartSpeaker ID: ");
        String id = scan.nextLine();
        while(casa.dispositivos.containsKey(id)) {
            System.out.println("This id already exists, try another one!");
            id = scan.nextLine();
        }

        System.out.print("\n");

        SmartSpeaker.Estado estado = SmartSpeaker.Estado.OFF;
        System.out.println("Mode: ");
        System.out.println("1 - ON");
        System.out.println("2 - OFF");
        System.out.print("Choose an option: ");
        choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2")){
            System.out.println("Choose a valid option (1/2):");
            choice=scan.nextLine();
        }

        if(choice.equals("1")) {
            estado = SmartSpeaker.Estado.ON;
        }

/*
        switch(choice) {
            case("1"):
                estado = SmartSpeaker.Estado.ON;
                break;

            case("2"):
                estado = SmartSpeaker.Estado.OFF;
                break;
        }
*/

        System.out.print("\n");

        System.out.println("Only one word please! ");
        System.out.print("SmartSpeaker brand: ");
        String marca = scan.next();

        System.out.print("\n");

        System.out.print("Volume (0 to 100): ");
        int volume = scan.nextInt();
        if(volume < 0 && volume > 100) {
            System.out.print("Choose a valid option: ");
            volume = scan.nextInt();
        }

        System.out.print("\n");

        System.out.println("Only one word please! ");
        System.out.print("Online radio: ");
        String radioOnline = scan.next();

        System.out.print("\n");

        System.out.print("Device base consumption: ");
        double consumoBase = scan.nextDouble();

        System.out.print("\n");

        SmartDevice ss = new SmartSpeaker(id, estado, marca, volume, radioOnline, consumoBase);

        return ss;
    }

    public SmartDevice createSmartCamera(Casa casa, Cidade cidade, Scanner scan){
        //Scanner scan = new Scanner(System.in);
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
        while(!choice.equals("1") && !choice.equals("2")) {
            System.out.print("Choose a valid option (1/2): ");
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

        System.out.print("\n");

        System.out.println("Resolution: ");
        System.out.print("x: ");
        int x = scan.nextInt();
        System.out.print("y: ");
        int y = scan.nextInt();

        System.out.print("\n");

        System.out.print("Data: ");
        LocalDateTime dataAtual = LocalDateTime.now();
        System.out.println(dataAtual);

        System.out.print("\n");

        SmartDevice sc = new SmartCamera(id, estado, x, y, dataAtual);

        return sc;
    }

    public FornecedorEnergia criaFornecedorEnergia(Cidade cidade, Scanner scan) {
        //Scanner scan = new Scanner(System.in);

        System.out.print("Energy supplier name: ");
        String nomeFornecedor = scan.nextLine();
        while(cidade.fornecedores.containsKey(nomeFornecedor)) {
            System.out.println("This supplier already exits!");
            nomeFornecedor = scan.nextLine();
        }

        System.out.print("\n");

        System.out.print("Base value: ");
        double valorBase = scan.nextDouble();

        System.out.print("\n");

        System.out.print("Discount: ");
        double desconto = scan.nextDouble();

        System.out.print("\n");

        FornecedorEnergia fe = new FornecedorEnergia(nomeFornecedor, valorBase, desconto);

        return fe;
    }

    public void changeCasa(Casa casa, Cidade cidade, Scanner scan) throws IOException, InterruptedException {
        System.out.print("\n");

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
        System.out.println("Choose an option:");
        String choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5") && !choice.equals("6") && !choice.equals("7") && !choice.equals("8") && !choice.equals("9") && !choice.equals("0")) {
            System.out.print("Choose a valid option (1/2/3/4/5/6/7/8/9/0): ");
            choice = scan.nextLine();
        }

        System.out.print("\n");

        switch(choice) {
            case("1"):
                System.out.print("New Id: ");
                String novoID = scan.next();
                casa.setIdCasa(novoID);

                System.out.print("\n");

                break;

            case("2"):
                System.out.print("New Adress: ");
                String novoMorada = scan.next();
                casa.setMorada(novoMorada);

                System.out.print("\n");

                break;

            case("3"):
                System.out.print("New Name: ");
                String novoNome = scan.next();
                casa.setNome(novoNome);

                System.out.print("\n");

                break;

            case("4"):
                System.out.print("New NIF: ");
                String novoNIF = scan.next();
                casa.setNIF(novoNIF);

                System.out.print("\n");

                break;

            case("5"):
                clearConsole();
                createDivisao(casa, cidade, scan);
                break;

            case("6"):
                clearConsole();
                changeDivisao(casa, cidade, scan);
                break;

            case("7"):
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

                break;

            case("9"):
                System.out.println("Turn On/Off");
                System.out.println("1 - On");
                System.out.println("2 - Off");
                System.out.println("Choose an option: ");
                choice = scan.nextLine();
                while(!choice.equals("1") && !choice.equals("2")) {
                    System.out.print("Choose a valid option (1/2): ");
                    choice = scan.nextLine();
                }
                switch(choice) {
                    case("1"):
                        casa.turn_On_Casa();
                        System.out.println("\nDone!");
                        break;

                    case("2"):
                        casa.turn_Off_Casa();
                        System.out.println("\nDone!");
                        break;
                }
                break;

            case("0"):
                break;

        }

        //createCidade(cidade, scan);
    }

    public void changeDivisao(Casa casa, Cidade cidade, Scanner scan) {
        System.out.print("\n");

        System.out.println("What do you want to do?");
        System.out.println("1 - Change division name");
        System.out.println("3 - Add device");
        System.out.println("2 - Change device");
        System.out.println("4 - Remove device");
        System.out.println("6 - Turn On/Off a division");
        System.out.println("6 - Turn On/Off a specific device");
        System.out.println("0 - Go back");
        System.out.println("Choose an option:");
        String choice = scan.nextLine();
        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5") && !choice.equals("6") && !choice.equals("7") && !choice.equals("8") && !choice.equals("0")) {
            System.out.print("Choose a valid option (1/2/0): ");
            choice = scan.nextLine();
        }
    }
}
