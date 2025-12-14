package src.oscekb5;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.List;

/**
 * Klassen skapar en instans av ett konto som associeras till kund (Customer).
 * Kontot innehåller medlemsvariabler för saldo, kontonummer, räntesats &
 * kontotyp.
 * 
 * @author Oscar Ekberg, oscekb-5
 */
public abstract class Account implements Serializable {
    private float balance;
    private final int accountNumber;
    /* private static final float INTEREST_RATE = 0.024f; */
    private final String accountType;

    /**
     * Konstruktor för klassen Account.
     * Initierar värdet för aktuella objekts kontonummer och kontotyp.
     * 
     * @param accountNumber - Unikt kontonummer för varje bankkund
     * @param accountType   - Kontotypen, kreditkonto eller sparkonto
     */
    public Account(int accountNumber, String accountType) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

    /**
     * Överskriver funktionen toString() och returnerar i detta fallet en formaterad
     * sträng.
     * Strängen innehåller kontoinformation.
     * 
     * @return formaterad sträng med kontoinformation
     */
    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("sv", "SE"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        String nfBalance = nf.format(balance);
        return accountNumber + " " + nfBalance + " " + accountType + getFormattedInterest();
    }

    /**
     * Returnerar aktuella kontots saldo.
     * 
     * @return saldot i form av float
     */
    public float getBalance() {
        return balance;
    }

    /**
     * Sätter kontots saldo till önskat värde
     * 
     * @param balance kontots saldo
     */
    public void setBalance(float balance) {
        this.balance = balance;
    }

    /**
     * Returnerar aktuella kontots kontonummer.
     * 
     * @return kontonumret
     */
    public int getAccountNumber() {
        return accountNumber;
    }

    /**
     * Returnerar det aktuella kontots kontotyp.
     * 
     * @return kontotypen i form av en String
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Returnerar datum och tid i specificerat format
     * 
     * @return formaterat datum och tid
     */
    protected String getCurrentFormattedDateTime() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String date = LocalDateTime.now().format(format);
        return date;
    }

    /**
     * Formatterar summan till svensk standard samt med två decimaler
     * 
     * @param amount summan som önskas formatteras
     * @return
     */
    protected String getFormattedAmount(float amount) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("sv", "SE"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        return nf.format(amount);
    }

    /**
     * Räknar ut och returnerar den slutliga räntan
     * Beroende på kontotyp fungerar uträkningen olika
     * 
     * @return flyttal i form av en float för slutlig ränta
     */
    protected abstract float calcFinalInterest();

    /**
     * Implementerar olika räntor för uttag beroende på kontotyp
     * 
     * @param amount summan för uttag
     * @return booleskt värde, false för misslyckat uttag, annars true
     */
    protected abstract boolean withdraw(int amount);

    /**
     * Implementerar insättning för vardera subklass
     * 
     * @param amount summan för insättning
     */
    protected abstract void deposit(float amount);

    /**
     * Returnerar transaktionshistoriken för det aktuella kontot
     * 
     * @return lista med transaktioner
     */
    protected abstract List<String> getTransactions();

    /**
     * Returnerar en formatterad sträng med räntan för vardera kontotyp
     * 
     * @return
     */
    protected abstract String getFormattedInterest();
}