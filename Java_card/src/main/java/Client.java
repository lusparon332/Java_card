import java.util.ArrayList;

public class Client {
    String name;
    String birthDate;
    ArrayList<Card> cards;

    public Client(String name, String birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.cards = new ArrayList<>() { };
    }

    @Override
    public String toString() {
        String result = "Name: " + name + "\nDate of birth: " + birthDate + "\nCards count: " + cards.size();
        if (cards.size() != 0) {
            for (int i = 0; i < cards.size(); i++) {
                Card card = cards.get(i);
                result += "\nCard #" + i + ": " + card.cardNum +
                        "; Issue date: " + card.issueDate.getTime() +
                        "; Expiration date: " + card.expDate.getTime();
            }
        }
        return result;
    }
}
