import java.io.*;
import java.util.*;
import javax.swing.*;

public class Main {
    public static HashMap<Long, Client> clients = new HashMap<Long, Client>();

    public static void main(String[] args) {
        readClientsFromFile("clients.txt");
        (new NotificationThread()).start();

        Scanner in = new Scanner(System.in);

        String answer = "";
        while (true) {
            System.out.print("\nList of possible actions:\n" +
                    "1 - Create client;\n" +
                    "2 - Show list of clients;\n" +
                    "3 - Get a new card for the client;\n" +
                    "4 - Close client card;\n" +
                    "5 - Exit the program.\n\n" +
                    "Enter a number: ");
            answer = in.nextLine();
            System.out.println(handleAnswer(answer));
        }
    }

    public static String handleAnswer(String answer) {
        Scanner in = new Scanner(System.in);
        String handleResult = "";

        switch (answer) {
            // Create client
            case "1":
                String name = "";
                while (name == "") {
                    System.out.print("Enter client full name: ");
                    name = in.nextLine();
                }
                String birthDate = "";
                while (!isCorrectBirthDate(birthDate)) {
                    System.out.print("Enter client's date of birth (MM.DD.YYYY): ");
                    birthDate = in.nextLine();
                }
                Long entry = thereWasAlready(name, birthDate);
                if (entry == Long.valueOf(-1)) {
                    clients.put(Long.valueOf(clients.size()), new Client(name, birthDate));
                    handleResult = "Client with ID " + (clients.size() - 1) + " named " + name + " with date of birth " + birthDate + " created";
                }
                else
                    handleResult = "A client with the same name and date of birth already exists (ID: " + entry + ")";

                break;
            // Show list of clients
            case "2":
                if (clients.size() == 0)
                    handleResult = "There are no clients";
                for (Long clientID: clients.keySet()) {
                    handleResult += "\nID: " + clientID + "\n" + clients.get(clientID) + "\n";
                }
                break;
            // Get a new card for the client
            case "3":
                Long clientID = Long.MAX_VALUE;
                while (clientID > clients.size() - 1 || clientID < 0) {
                    System.out.print("Enter client ID: ");
                    clientID = Long.valueOf(in.nextInt());
                }
                addCard(clientID, 5);
                handleResult = "Client with ID " + clientID + " get a new card";
                break;
            // Close client card
            case "4":
                Long clientID1 = Long.MAX_VALUE;
                while (clientID1 > clients.size() - 1 || clientID1 < 0) {
                    System.out.print("Enter client ID: ");
                    clientID1 = Long.valueOf(in.nextInt());
                }
                if (clients.get(clientID1).cards.size() == 0)
                    handleResult = "The client with ID " + clientID1 + " has no cards";
                else {
                    Integer cardID = Integer.MAX_VALUE;
                    while (cardID > clients.get(clientID1).cards.size() - 1 || cardID < 0) {
                        System.out.print("Enter the card number (#): ");
                        cardID = in.nextInt();
                    }
                    clients.get(clientID1).cards.remove(cardID.intValue());
                    handleResult = "Card #" + cardID + " closed. Client now has " + clients.get(clientID1).cards.size() + " cards";
                }
                break;
            // Exit the program
            case "5":
                writeClientsIntoFile("clients.txt");
                System.exit(0);
                break;
            default:
                handleResult = "Unknown number entered.";
                break;
        }

        return handleResult;
    }

    public static boolean isCorrectBirthDate(String birthDate) {
        if (birthDate == "")
            return false;
        String[] digits = birthDate.split("\\.");
        if (digits.length != 3)
            return false;
        try {
            if (digits[0].length() != 2 || digits[1].length() != 2 || digits[2].length() != 4)
                return false;
            Integer month = Integer.valueOf(digits[0]);
            Integer day = Integer.valueOf(digits[1]);
            Integer year = Integer.valueOf(digits[2]);
            if (year < 1900 || year > 2008)
                return false;
            if (month <= 0 || month > 12)
                return false;
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10|| month == 12 ) {
                if (day <= 0 || day > 31)
                    return false;
            }
            else if (month == 2) {
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    if (day <= 0 || day > 29)
                        return false;
                }
                else if (day <= 0 || day > 28)
                    return false;
            }
            else if (day <= 0 || day > 30)
                return false;
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }
    public static Long thereWasAlready(String name, String birthDate) {
        for (Long clientID : clients.keySet()) {
            Client client = clients.get(clientID);
            if (name.equalsIgnoreCase(client.name) && birthDate.equalsIgnoreCase(client.birthDate))
                return clientID;
        }
        return Long.valueOf(-1);
    }

    public static void addCard(Long clientID, int duration) {
        clients.get(clientID).cards.add(new Card(clientID, duration));
    }

    public static void writeClientsIntoFile(String path) {
        try(FileWriter writer = new FileWriter(path, false))
        {
            String text = clients.size() + "\n";
            for (Long clientID: clients.keySet()) {
                Client client = clients.get(clientID);
                text += clientID + "\n";
                text += client.name + "\n";
                text += client.birthDate + "\n";
                text += client.cards.size() + "\n";
                for (int i = 0; i < client.cards.size(); i++)
                    text += client.cards.get(i).cardNum + "\n" +
                            client.cards.get(i).issueDate.get(Calendar.MONTH) + "\n" +
                            client.cards.get(i).issueDate.get(Calendar.DATE) + "\n" +
                            client.cards.get(i).issueDate.get(Calendar.YEAR) + "\n" +
                            client.cards.get(i).expDate.get(Calendar.MONTH) + "\n" +
                            client.cards.get(i).expDate.get(Calendar.DATE) + "\n" +
                            client.cards.get(i).expDate.get(Calendar.YEAR) + "\n";
            }
            writer.append(text);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void readClientsFromFile(String path) {
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();
            if (line == null)
                return;;
            int clientsCount = Integer.parseInt(line);
            if (clientsCount == 0)
                return;
            for (int i = 0; i < clientsCount; i++) {
                Long clientID = Long.parseLong(reader.readLine());
                String name = reader.readLine();
                String birthDate = reader.readLine();
                Client client = new Client(name, birthDate);

                int cardsCount = Integer.parseInt(reader.readLine());
                for (int j = 0; j < cardsCount; j++) {
                    Card card = new Card(clientID, 1);
                    card.cardNum = reader.readLine();

                    int month = Integer.parseInt(reader.readLine());
                    int day = Integer.parseInt(reader.readLine());
                    int year = Integer.parseInt(reader.readLine());
                    card.issueDate = new GregorianCalendar(year, month, day);

                    month = Integer.parseInt(reader.readLine());
                    day = Integer.parseInt(reader.readLine());
                    year = Integer.parseInt(reader.readLine());
                    card.expDate = new GregorianCalendar(year, month, day);

                    client.cards.add(card);
                }
                clients.put(clientID, client);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

