package src.oscekb5;

import java.util.ArrayList;
import java.util.List;

/**
 * Klassen skapar en instans av ett konto som associeras till kund (Customer).
 * Kontot är av typen kreditkonto
 * 
 * @author Oscar Ekberg, oscekb-5
 */
public class CreditAccount extends Account {
    private final int MAX_CREDIT = -5000;
    private final float INTEREST_IF_POS = 0.011f;
    private final float INTEREST_IF_NEG = 0.05f;

    private final List<String> history = new ArrayList<>();

    /**
     * Konstruktor för klassen
     * 
     * @param accountNumber - Unikt kontonummer för varje bankkund
     * @param accountType   - Kontotypen, i detta fall 'kreditkonto'
     */
    public CreditAccount(int accountNumber, String accountType) {
        super(accountNumber, accountType);
    }

    @Override
    protected boolean withdraw(int amount) {
        if (getBalance() - amount < MAX_CREDIT) {
            return false;
        }
        setBalance(getBalance() - amount);
        history.add(getCurrentFormattedDateTime() + " -" + getFormattedAmount(amount) + " Saldo: "
                + getFormattedAmount(getBalance()));
        return true;
    }

    @Override
    protected void deposit(float amount) {
        setBalance(getBalance() + amount);
        history.add(getCurrentFormattedDateTime() + " " + getFormattedAmount(amount) + " Saldo: "
                + getFormattedAmount(getBalance()));
    }

    @Override
    protected List<String> getTransactions() {
        return history;
    }

    @Override
    protected float calcFinalInterest() {
        if (getBalance() >= 0) {
            return getBalance() * INTEREST_IF_POS;
        } else {
            return getBalance() * INTEREST_IF_NEG;
        }

    }

    @Override
    protected String getFormattedInterest() {
        if (getBalance() >= 0) {
            return " 1,1 %";
        } else {
            return " 5 %";
        }
    }
}
