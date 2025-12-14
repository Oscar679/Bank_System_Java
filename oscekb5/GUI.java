package src.oscekb5;

import javax.swing.*;
import java.util.List;

/**
 * Klassen skapar en instans av ett GUI som tillhandahåller ett grafisk
 * användargränssnitt för banksystemet.
 * 
 * @author Oscar Ekberg, oscekb-5
 */
public class GUI extends JFrame {
    // Banklogik som GUI:t ska använda sig av
    private BankLogic bankLogic;

    // Bok för hantering av filinläsning och filskrivning
    private AccountBook accountBook;

    /**
     * Instansierar GUI
     * Gör GUI:t synligt
     * 
     * @param args
     */
    public static void main(String[] args) {
        GUI frame = new GUI();
        frame.setVisible(true);
    }

    /**
     * Konstruktor som kallar på buildFrame() för att bygga GUI:t
     */
    public GUI() {
        buildFrame();
    }

    /**
     * Bygger samtliga komponenter för GUI:t
     * Append:ar samtliga komponenter till rotmenyn
     * Initierar händelsehanterare till samtliga menyval
     */
    private void buildFrame() {
        bankLogic = new BankLogic();
        accountBook = new AccountBook();

        // Menyns dimensioner
        final int FRAME_WIDTH = 400;
        final int FRAME_HEIGHT = 400;

        // Skapa grundmenyn
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Skapa alla menyerna
        JMenu customerMenu = new JMenu("Kundmeny");
        JMenu accountMenu = new JMenu("Kontomeny");
        JMenu bankMenu = new JMenu("Bankmeny");

        // Lägg till kund-, konto- och bankmeny till rotmenyn
        menuBar.add(customerMenu);
        menuBar.add(accountMenu);
        menuBar.add(bankMenu);

        // Lägg till alla menyval till kundmenyn
        JMenuItem createCustomer = new JMenuItem("Skapa kund");
        customerMenu.add(createCustomer);
        createCustomer.addActionListener(event -> createCustomer());

        JMenuItem getCustomer = new JMenuItem("Hämta kund");
        customerMenu.add(getCustomer);
        getCustomer.addActionListener(event -> getCustomer());

        JMenuItem getAllCustomers = new JMenuItem("Hämta alla kunder");
        customerMenu.add(getAllCustomers);
        getAllCustomers.addActionListener(event -> getAllCustomers());

        JMenuItem changeCustomerName = new JMenuItem("Ändra kundnamn");
        customerMenu.add(changeCustomerName);
        changeCustomerName.addActionListener(event -> changeCustomerName());

        JMenuItem deleteCustomer = new JMenuItem("Radera kund");
        customerMenu.add(deleteCustomer);
        deleteCustomer.addActionListener(event -> deleteCustomer());

        // Lägg till alla menyval till kontomenyn
        JMenuItem createSavingsAccount = new JMenuItem("Skapa sparkonto");
        accountMenu.add(createSavingsAccount);
        createSavingsAccount.addActionListener(e -> createSavingsAccount());

        JMenuItem createCreditAccount = new JMenuItem("Skapa kreditkonto");
        accountMenu.add(createCreditAccount);
        createCreditAccount.addActionListener(e -> createCreditAccount());

        JMenuItem getAccount = new JMenuItem("Hämta konto");
        accountMenu.add(getAccount);
        getAccount.addActionListener(e -> getAccount());

        JMenuItem deposit = new JMenuItem("Sätt in pengar");
        accountMenu.add(deposit);
        deposit.addActionListener(e -> deposit());

        JMenuItem withdraw = new JMenuItem("Ta ut pengar");
        accountMenu.add(withdraw);
        withdraw.addActionListener(e -> withdraw());

        JMenuItem closeAccount = new JMenuItem("Stäng konto");
        accountMenu.add(closeAccount);
        closeAccount.addActionListener(e -> closeAccount());

        JMenuItem getTransactions = new JMenuItem("Hämta transaktioner");
        accountMenu.add(getTransactions);
        getTransactions.addActionListener(e -> getTransactions());

        // Lägg till menyval till bankmeny (uppgift 4)
        JMenuItem saveBank = new JMenuItem("Spara bank till fil");
        bankMenu.add(saveBank);
        saveBank.addActionListener(e -> saveBank());

        JMenuItem loadBank = new JMenuItem("Ladda in bank från fil");
        bankMenu.add(loadBank);
        loadBank.addActionListener(e -> loadBank());

        JMenuItem saveTransactions = new JMenuItem("Spara transaktioner till fil");
        bankMenu.add(saveTransactions);
        saveTransactions.addActionListener(e -> saveTransactions());

        // Ange dimensioner, title, skapa stäng-knapp, skapa layout
        setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Start");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centrera ramen
        setLocationRelativeTo(null);
    }

    /**
     * Visar ett inputfält där användaren kan välja önskat konto, utan att således
     * behöva minnas varje kontonummer
     * 
     * @param pNo personnummer för aktuell kund
     * @return kontonummer som valts ur rullgardinsmenyn
     */
    public Integer chooseAccount(String pNo) {
        List<Integer> result = bankLogic.getAccountId(pNo);
        if (result != null) {
            return (Integer) JOptionPane.showInputDialog(
                    this,
                    "Välj konto",
                    "Konton",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    result.toArray(),
                    result.get(0));
        }
        return null;
    }

    /**
     * Bygger 3 input-fält
     * Kallar på aktuell metod inom BankLogic
     */
    public void createCustomer() {
        String forename = JOptionPane.showInputDialog(this, "Förnamn");

        String surname = JOptionPane.showInputDialog(this, "Efternamn");

        String pno = JOptionPane.showInputDialog(this, "Personnummer");

        if (forename != null && surname != null && pno != null) {
            boolean result = bankLogic.createCustomer(forename, surname, pno);
            if (result) {
                JOptionPane.showMessageDialog(this, result);
            }
        }
    }

    /**
     * Returnerar lista med samtliga kunder
     * Kallar på aktuell metod inom BankLogic
     */
    public void getAllCustomers() {
        List<String> customers = bankLogic.getAllCustomers();

        if (customers == null || customers.size() == 0) {
            JOptionPane.showMessageDialog(this, "Det finns inga kunder");
            return;
        }

        JOptionPane.showMessageDialog(this, String.join("\n", customers));
    }

    /**
     * Returnerar aktuell kund
     * Kallar på aktuell metod inom BankLogic
     */
    public void getCustomer() {
        String pNo = JOptionPane.showInputDialog(this, "Personnummer");

        if (pNo != null) {
            List<String> customer = bankLogic.getCustomer(pNo);
            if (customer == null || customer.size() == 0) {
                JOptionPane.showMessageDialog(this, "Det finns inga kunder");
                return;
            }
            JOptionPane.showMessageDialog(this, String.join("\n", customer));
        }
    }

    /**
     * Bygger 3 input-fält
     * Kallar på aktuell metod inom BankLogic
     */
    public void changeCustomerName() {
        String pNo = JOptionPane.showInputDialog(this, "Personnummer");

        String name = JOptionPane.showInputDialog(this, "Nytt förnamn");

        String surname = JOptionPane.showInputDialog(this, "Nytt efternamn");

        if (pNo != null && name != null && surname != null) {
            if (bankLogic.changeCustomerName(name, surname, pNo)) {
                JOptionPane.showMessageDialog(this, "Namnbyte lyckades");
            }
        }

    }

    /**
     * Bygger 1 input-fält
     * Kallar på aktuell metod inom BankLogic för att skapa sparkonto
     */
    public void createSavingsAccount() {
        String pNo = JOptionPane.showInputDialog(this, "Personnummer");

        if (pNo != null) {
            if (bankLogic.createSavingsAccount(pNo) != -1) {
                JOptionPane.showMessageDialog(this, "Sparkonto har skapats");
            }
        }
    }

    /**
     * Bygger 1 input-fält
     * Kallar på aktuell metod inom BankLogic för att skapa kreditkonto
     */
    public void createCreditAccount() {
        String pNo = JOptionPane.showInputDialog(this, "Personnummer");

        if (pNo != null) {
            if (bankLogic.createCreditAccount(pNo) != -1) {
                JOptionPane.showMessageDialog(this, "Kreditkonto har skapats");
            }
        }
    }

    /**
     * Bygger 2 input-fält
     * Kallar på aktuell metod inom BankLogic för att returnera konto för aktuell
     * kund
     */
    public void getAccount() {
        String pNo = JOptionPane.showInputDialog(this, "Personnummer");

        if (pNo != null) {
            Integer accountId = chooseAccount(pNo);
            if (accountId == null) {
                return;
            }
            String result = bankLogic.getAccount(pNo, accountId);
            if (result != null) {
                JOptionPane.showMessageDialog(this, result);
            } else {
                JOptionPane.showMessageDialog(this, "Kontot hittades ej");
            }
        }

    }

    /**
     * Bygger 3 input-fält
     * Kallar på aktuell metod inom BankLogic för att genomföra en insättning för
     * aktuell kund och konto
     */
    public void deposit() {
        String pNo = JOptionPane.showInputDialog(this, "Personnummer");

        Integer accountId = chooseAccount(pNo);

        if (accountId == null) {
            return;
        }

        String amount = JOptionPane.showInputDialog(this, "Belopp");
        try {
            float parsedAmount = Float.parseFloat(amount);

            if (pNo != null && amount != null) {
                if (bankLogic.deposit(pNo, accountId, parsedAmount)) {
                    JOptionPane.showMessageDialog(this, "Insättning lyckades");
                }
            }
        } catch (NumberFormatException e) {
            e.getMessage();
            JOptionPane.showMessageDialog(this, "Ogiltigt belopp");
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Okänt fel");
            return;
        }
    }

    /**
     * Bygger 3 input-fält
     * Kallar på aktuell metod inom BankLogic för att genomföra ett uttag för
     * aktuell kund och konto
     */
    public void withdraw() {
        String pNo = JOptionPane.showInputDialog(this, "Personnummer");

        Integer accountId = chooseAccount(pNo);

        if (accountId == null) {
            return;
        }

        String amount = JOptionPane.showInputDialog(this, "Belopp");
        try {
            int parsedAmount = Integer.parseInt(amount);

            if (pNo != null && amount != null) {
                if (bankLogic.withdraw(pNo, accountId, parsedAmount)) {
                    JOptionPane.showMessageDialog(this, "Uttag lyckades");
                }
            }
        } catch (NumberFormatException e) {
            e.getMessage();
            JOptionPane.showMessageDialog(this, "Ogiltigt belopp");
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Okänt fel");
            return;
        }
    }

    /**
     * Bygger 2 input-fält
     * Kallar på aktuell metod inom BankLogic för att stänga aktuellt konto för
     * aktuell kund
     */
    public void closeAccount() {
        String pNo = JOptionPane.showInputDialog(this, "Personnummer");

        Integer accountId = chooseAccount(pNo);

        if (accountId == null) {
            return;
        }

        Customer customer = bankLogic.getCustomerObj(pNo);

        Account account = customer.getAccountFromId(accountId);

        float finalInterest = account.calcFinalInterest();

        if (pNo != null && customer != null && account != null) {
            String result = bankLogic.closeAccount(pNo, accountId);
            if (result != null) {
                JOptionPane.showMessageDialog(this, result);
                JOptionPane.showMessageDialog(this, "Slutlig ränta: " + finalInterest);
            }
        }
        return;
    }

    /**
     * Bygger 1 input-fält
     * Kallar på aktuell metod inom BankLogic för att avsluta aktuellt kundkonto
     */
    public void deleteCustomer() {
        String pNo = JOptionPane.showInputDialog(this, "Personnummer");

        if (pNo != null) {
            List<String> result = bankLogic.deleteCustomer(pNo);

            if (result != null) {
                JOptionPane.showMessageDialog(this, String.join("\n", result));
            } else {
                JOptionPane.showMessageDialog(this, "Kunden hittades ej");
            }
        }
    }

    /**
     * Bygger 2 input-fält
     * Kallar på aktuell metod inom BankLogic för att returnera och visa samtliga
     * transaktioner för kund och konto
     */
    public void getTransactions() {
        String pNo = JOptionPane.showInputDialog(this, "Personnummer");

        if (pNo != null) {

            Integer accountId = chooseAccount(pNo);

            if (accountId == null) {
                return;
            }

            List<String> result = bankLogic.getTransactions(pNo, accountId);

            if (result != null && !result.isEmpty()) {
                JOptionPane.showMessageDialog(this, String.join("\n", result));
            } else {
                JOptionPane.showMessageDialog(this, "Kund och konto hittades ej");
            }
        }
    }

    /* Metoder för inlämningsuppgift 4 */
    public void saveBank() {
        List<Customer> allCustomers = bankLogic.getAllCustomerObjs();

        if (allCustomers == null || allCustomers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Det finns inga kunder att spara");
            return;
        }

        String fileName = JOptionPane.showInputDialog(this, "Ange filnamn");

        if (fileName == null || fileName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ogiltigt filnamn");
            return;
        }

        try {
            bankLogic.saveBankToFile(fileName);
            JOptionPane.showMessageDialog(this, "Bank sparad");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Kunde inte spara bank från fil");
        }
    }

    /**
     * Bygger 1 input-fält
     * Kallar på aktuell metod inom BankLogic för att ladda in bank från fil
     */
    public void loadBank() {
        String path = JOptionPane.showInputDialog(this, "Ange filnamn");

        if (path == null || path.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ogiltigt filnamn");
            return;
        }

        try {
            bankLogic.loadBankToFile(path);
            JOptionPane.showMessageDialog(this, "Bank laddad från fil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Kunde inte ladda in bank från fil");
        }
    }

    /**
     * Bygger 2 input-fält
     * Kallar på aktuell metod inom AccountBook för att spara transaktioner till fil
     */
    public void saveTransactions() {
        String pNo = JOptionPane.showInputDialog(this, "Personnummer");

        Customer customer = bankLogic.getCustomerObj(pNo);

        if (customer == null) {
            JOptionPane.showMessageDialog(this, "Kunden hittades ej");
            return;
        }

        accountBook.addTransactions(customer);

        String fileName = JOptionPane.showInputDialog(this, "Ange filnamn");

        if (fileName == null || fileName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ogiltigt filnamn");
            return;
        } else {
            if (!fileName.endsWith(".txt")) {
                fileName = fileName + ".txt";
            }
            try {
                accountBook.save(fileName);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Kunde inte spara transaktioner till fil");
            }
        }
    }
}
