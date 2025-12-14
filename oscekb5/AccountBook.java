package src.oscekb5;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Klassen representerar en adressbok som kan spara och läsa personer från
 * fil.
 * 
 * @author Oscar Ekberg, oscekb-5
 */
public class AccountBook {
    public ArrayList<String> bankData = new ArrayList<>();

    /**
     * Lägger till transaktioner för en kund i adressboken.
     * 
     * @param customer aktuell kund
     */
    public void addTransactions(Customer customer) {
        bankData.add("Transaktioner för kund " + customer.toString() + ": ");

        for (Account account : customer.getAccounts()) {
            List<String> allTransactions = account.getTransactions();
            bankData.add("Kontonummer: " + account.getAccountNumber());

            if (allTransactions.isEmpty()) {
                bankData.add(" Inga transaktioner hittades för detta konto.");
                continue;
            }

            for (String transaction : allTransactions) {
                bankData.add(" " + transaction);
            }
        }

    }

    /**
     * Sparar adressbokens innehåll till en fil.
     * 
     * @param filename sökväg till fil
     */
    public void save(String filename) {
        try (PrintWriter out = new PrintWriter("src/oscekb5/files/" + filename)) {
            for (String bd : bankData) {
                out.println(bd);
            }
            System.out.println("Saved to: " + "src/oscekb5/files/" + filename);
        } catch (IOException e) {
            throw new RuntimeException("Could not save file");
        }
    }
}
