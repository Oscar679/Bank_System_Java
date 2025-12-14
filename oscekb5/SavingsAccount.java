package src.oscekb5;

import java.util.ArrayList;
import java.util.List;

/**
 * Klassen skapar en instans av ett konto som associeras till kund (Customer).
 * Kontot är av typen sparkonto
 * 
 * @author Oscar Ekberg, oscekb-5
 */
public class SavingsAccount extends Account {
    private final float INTEREST_RATE = 1.02f;
    private final float CLOSING_INTEREST_RATE = 0.024f;
    private int withdrawalCounter = 0;

    private final List<String> history = new ArrayList<>();

    /**
     * 
     * @param accountNumber - Unikt kontonummer för varje bankkund
     * @param accountType   - Kontotypen, i detta fall 'sparkonto'
     */
    public SavingsAccount(int accountNumber, String accountType) {
        super(accountNumber, accountType);
    }

    @Override
    protected boolean withdraw(int amount) {
        float balance = getBalance();
        float total = amount;

        if (withdrawalCounter >= 1) {
            total = amount * INTEREST_RATE;
        }

        if (balance >= total) {
            balance -= total;
            setBalance(balance);
            withdrawalCounter++;
            history.add(getCurrentFormattedDateTime() + " -" + getFormattedAmount(total) + " Saldo: "
                    + getFormattedAmount(getBalance()));
            return true;
        }
        return false;
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
        return getBalance() * CLOSING_INTEREST_RATE;
    }

    @Override
    protected String getFormattedInterest() {
        return " 2,4 %";
    }
}
