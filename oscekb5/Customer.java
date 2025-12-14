package src.oscekb5;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Klassen skapar en instans av en kund.
 * Objektet innehåller variabler för förnamn, efternamn, personnummer,
 * kontoräknare och en lista med aktiva konton.
 * 
 * @author Oscar Ekberg, oscekb-5
 */
public class Customer implements Serializable {
    private String name;
    private String surName;
    private final String pNo;
    private final ArrayList<Account> accounts = new ArrayList<>();

    /**
     * Konstruktor för klassen Customer.
     * Initierar värde för personnummer, förnamn och efternamn.
     * 
     * @param pNo     kundens personnummer
     * @param name    kundens förnamn
     * @param surName kundens efternamn
     */
    public Customer(String pNo, String name, String surName) {
        this.pNo = pNo;
        this.name = name;
        this.surName = surName;
    }

    /**
     * Överskriver metoden toString().
     * 
     * @return en sträng innehållande aktuell kunds personnummer, förnamn och
     *         efternamn
     */
    @Override
    public String toString() {
        return pNo + " " + name + " " + surName;
    }

    /**
     * Skapar ett nytt konto för aktuell kund.
     * 
     * @param accountNumber numret för kontonumret
     * @return kontonumret som det nyöppnade kontot skapats med
     */
    public int addCreditAccount(int accountNumber) {
        Account temp = new CreditAccount((accountNumber), "Kreditkonto");
        accounts.add(temp);
        return accountNumber;
    }

    /**
     * Skapar ett nytt konto för aktuell kund.
     * 
     * @param accountNumber numret för kontonumret
     * @return kontonumret som det nyöppnade kontot skapats med
     */
    public int addSavingsAccount(int accountNumber) {
        Account temp = new SavingsAccount((accountNumber), "Sparkonto");
        accounts.add(temp);
        return accountNumber;
    }

    /**
     * Returnerar konto-objekt för aktuellt kontonummer
     * 
     * @param accountId
     * @return
     */
    public Account getAccountFromId(int accountId) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountId) {
                return account;
            }
        }
        return null;
    }

    /**
     * Avslutar/stänger ett befintligt konto.
     * Flyttar samtliga konton i listan.
     * Minskar på räknaren för antalet konton som kunden har aktiva.
     * 
     * @param accountId kontonumret på det konto som önskas tas bort
     * @return sträng som skriver ut det stängda kontots detaljer
     */
    public String closeAccount(int accountId) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountId) {
                NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("sv", "SE"));

                nf.setMinimumFractionDigits(2);
                nf.setMaximumFractionDigits(2);

                String nfBalance = nf.format(account.getBalance());
                float interest = account.calcFinalInterest();
                String nfInterest = nf.format(interest);

                accounts.remove(account);

                return accountId + " " + nfBalance + " " + account.getAccountType() + " " + nfInterest;
            }
        }
        return null;
    }

    /**
     * Listar de konton som aktuell kund har.
     * 
     * @return lista med information om konton
     */
    public List<Account> getAccounts() {
        List<Account> list = new ArrayList<>();
        for (Account account : accounts) {
            list.add(account);
        }
        return list;
    }

    /**
     * Returnerar personnumret för aktuell kund.
     * 
     * @return personnummer
     */
    public String getpNo() {
        return pNo;
    }

    /**
     * Adderar vald summa till saldot.
     * Summan är ogiltig ifall den är <= 0.
     * 
     * Metoden säkerställer att det angivna kontot finns och kallar sedan på
     * verkställande deposit-metod.
     * 
     * @param accountId kontonummer för önskat konto
     * @param amount    summa som önskas sättas in
     * @return true vid lyckad insättning, annars false
     */
    public boolean deposit(int accountId, float amount) {
        if (amount <= 0) {
            return false;
        }
        for (Account account : accounts) {
            if (account != null && account.getAccountNumber() == accountId) {
                account.deposit(amount);
                return true;
            }
        }
        return false;
    }

    /**
     * Subtraherar vald summa från saldot.
     * Summan är ogiltig ifall den är <= 0.
     * 
     * Metoden säkerställer att det angivna kontot finns och kallar sedan på
     * verkställande withdraw-metod.
     * 
     * @param accountId kontonummer för önskat konto
     * @param amount    summa som önskas tas ut
     * @return true vid lyckat uttag, annars false
     */
    public boolean withdraw(int accountId, int amount) {
        if (amount <= 0) {
            return false;
        }

        for (Account account : accounts) {
            if (account.getAccountNumber() == accountId) {
                return account.withdraw(amount);
            }
        }
        return false;
    }

    /**
     * Sätter det aktuella objektets namn-variablar till det som skickas med till
     * metoden.
     * 
     * Metoden säkerställer att de nya namnen inte är tomma.
     * 
     * @param name    det nya förnamnet
     * @param surname det nya efternamnet
     * @return en flagga som returnerar ett booleskt värde, true vid lyckad ändring,
     *         annars false
     */
    public boolean setName(String name, String surname) {
        boolean isChanged = false;
        if (name != null & !name.isEmpty()) {
            this.name = name;
            isChanged = true;
        }
        if (surname != null & !surname.isEmpty()) {
            this.surName = surname;
            isChanged = true;
        }
        return isChanged;
    }

    /**
     * Avslutar och stänger samtliga konton för aktuell kund.
     * 
     * @return lista med info om stängda konton
     */
    public List<String> closeAllAccounts() {
        List<String> closedAccounts = new ArrayList<>();

        List<Account> temp = new ArrayList<>(accounts);
        for (Account account : temp) {
            closedAccounts.add(closeAccount(account.getAccountNumber()));
        }
        return closedAccounts;
    }

    /**
     * Hämtar information om aktuellt konto.
     * 
     * @param accountId det aktuella kontot
     * @return sträng med kontoinformation
     */
    public String getAccountInfo(int accountId) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountId) {
                return account.toString();
            }
        }
        return null;
    }

    /**
     * Hämtar transaktionshistorik för aktuellt konto
     * 
     * @param accountId kontonummer för aktuellt konto
     * @return lista med transaktioner i strängformat
     */
    public List<String> getTransactions(int accountId) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountId) {
                return account.getTransactions();
            }
        }
        return null;
    }
}