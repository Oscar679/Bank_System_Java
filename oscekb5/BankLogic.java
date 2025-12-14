package src.oscekb5;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Klassen representerar det publika gränssnittet som hanterar kunder
 * och konton.
 * 
 * Klassen hanterar en lista innehållande samtliga bankkunder.
 * 
 * @author Oscar Ekberg, oscekb-5
 */
public class BankLogic {
    private final ArrayList<Customer> customers = new ArrayList<>();
    private int nextAccountNumber = 1000;

    /**
     * Skapar kund med angivet förnamn, efternamn och personnummer.
     * Ifall det angivna personnumret redan finns registrerat hos banken, misslyckas
     * skapandet.
     * 
     * @param name    kundens förnamn
     * @param surname kundens efternamn
     * @param pNo     kundens personnummer
     * @return ifall kunden redan finns registrerad misslyckas skapandet, annars
     *         true
     */
    public boolean createCustomer(String name, String surname, String pNo) {
        for (Customer customer : customers) {
            if (customer.getpNo().equals(pNo)) {
                return false;
            }
        }
        customers.add(new Customer(pNo, name, surname));
        return true;
    }

    /**
     * Returnerar en lista med information om samtliga bankkunder.
     * 
     * @return lista med bankkunder
     */
    public List<String> getAllCustomers() {
        List<String> list = new ArrayList<>();
        for (Customer customer : customers) {
            list.add(customer.toString());
        }
        return list;
    }

    /**
     * Returnerar information om den enskilda kunden och samtliga konton kopplade
     * till kunden.
     * 
     * @param pNo bankkundens personnummer
     * @return lista med kund- och kontoinformation
     */
    public List<String> getCustomer(String pNo) {
        for (Customer customer : customers) {
            if (customer.getpNo().equals(pNo)) {
                List<String> list = new ArrayList<>();
                list.add(customer.toString());
                for (Account account : customer.getAccounts()) {
                    list.add(account.toString());
                }
                return list;
            }
        }
        return null;
    }

    /**
     * Ändrar befintlig kunds för- och efternamn.
     * 
     * @param name    nytt förnamn
     * @param surname nytt efternamn
     * @param pNo     kundens personnummer
     * @return true vid lyckad ändring, annars false
     */
    public boolean changeCustomerName(String name, String surname, String pNo) {
        for (Customer customer : customers) {
            if (customer.getpNo().equals(pNo)) {
                return customer.setName(name, surname);
            }
        }
        return false;
    }

    /**
     * Skapar sparkonto för aktuell kund.
     * 
     * @param pNo kund som kontot ska kopplas till
     * @return returnerar kontonumret ifall kontot skapades, annars -1
     */
    public int createSavingsAccount(String pNo) {
        for (Customer customer : customers) {
            if (customer.getpNo().equals(pNo)) {
                nextAccountNumber++;
                return customer.addSavingsAccount(nextAccountNumber);
            }
        }
        return -1;
    }

    /**
     * Skapar sparkonto för aktuell kund.
     * 
     * @param pNo kund som kontot ska kopplas till
     * @return returnerar kontonumret ifall kontot skapades, annars -1
     */
    public int createCreditAccount(String pNo) {
        for (Customer customer : customers) {
            if (customer.getpNo().equals(pNo)) {
                nextAccountNumber++;
                return customer.addCreditAccount(nextAccountNumber);
            }
        }
        return -1;
    }

    /**
     * Returnerar sträng med kontoinformation för angivet konto.
     * 
     * @param pNo       kundens personnummer
     * @param accountId det önskade kontot
     * @return sträng med kontoinformation, null ifall kunden eller kontot ej finns.
     */
    public String getAccount(String pNo, int accountId) {
        for (Customer customer : customers) {
            if (customer.getpNo().equals(pNo)) {
                return customer.getAccountInfo(accountId);
            }
        }
        return null;
    }

    /**
     * Returnerar ett Customer objekt
     * 
     * @param pNo personnummer för aktuell kund
     * @return Customer objekt
     */
    public Customer getCustomerObj(String pNo) {
        for (Customer customer : customers) {
            if (customer.getpNo().equals(pNo)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Returnerar alla Customer objekt
     * 
     * @return Customer objekt
     */
    public List<Customer> getAllCustomerObjs() {
        List<Customer> list = new ArrayList<>();
        for (Customer customer : customers) {
            list.add(customer);
        }
        return list;
    }

    /**
     * Returnerar en lista av Integer innehållande kontonummer för aktuell kund
     * 
     * @param pNo personnummer för aktuell kund
     * @return lista med kontonummer för aktuell kund
     */
    public List<Integer> getAccountId(String pNo) {
        List<Integer> accountIds = new ArrayList<>();

        for (Customer customer : customers) {
            if (customer.getpNo().equals(pNo)) {
                for (Account account : customer.getAccounts()) {
                    accountIds.add(account.getAccountNumber());
                }
                return accountIds;
            }
        }
        return null;
    }

    /**
     * Adderar vald summa till saldot.
     * 
     * Metoden säkerställer att den angivna kunden finns.
     * 
     * @param pNo       kundens personnummer
     * @param accountId önskat bankkonto
     * @param amount    summa
     * @return true ifall kontot hittats hos kunden och ifall summan är giltig,
     *         annars false
     */
    public boolean deposit(String pNo, int accountId, float amount) {
        for (Customer customer : customers) {
            if (customer.getpNo().equals(pNo)) {
                return customer.deposit(accountId, amount);
            }
        }
        return false;
    }

    /**
     * Subtraherar vald summa från saldot.
     * 
     * Metoden säkerställer att den angivna kunden finns.
     * 
     * @param pNo       kundens personnummer
     * @param accountId önskat bankkonto
     * @param amount    summa
     * @return true ifall kontot hittats hos kunden och ifall summan är giltig,
     *         annars false
     */
    public boolean withdraw(String pNo, int accountId, int amount) {
        for (Customer customer : customers) {
            if (customer.getpNo().equals(pNo)) {
                return customer.withdraw(accountId, amount);
            }
        }
        return false;
    }

    /**
     * Stänger angivet konto hos angiven bankkund.
     * 
     * @param pNo       kundens personnummer
     * @param accountId önskat bankkonto
     * @return sträng med information om det avslutade kontot, vid misslyckande
     *         returneras en tom sträng
     */
    public String closeAccount(String pNo, int accountId) {
        for (Customer customer : customers) {
            if (customer.getpNo().equals(pNo)) {
                return customer.closeAccount(accountId);
            }
        }
        return null;
    }

    /**
     * Raderar angiven bankkund från registret, tillsammans med dess konton.
     * 
     * @param pNo kundens personnummer
     * @return sträng med information om bankkund och avslutade konton
     */
    public List<String> deleteCustomer(String pNo) {
        for (Customer customer : customers) {
            if (customer.getpNo().equals(pNo)) {
                List<String> list = new ArrayList<>();
                list.add(customer.toString());
                list.addAll(customer.closeAllAccounts());
                customers.remove(customer);
                return list;
            }
        }
        return null;
    }

    /**
     * Säkerställer att önskat konto finns hos aktuell kund
     *
     * @param pNo       aktuell kund
     * @param accountId önskat kontonummer
     * @return lista med transaktioner i strängformat
     */
    public List<String> getTransactions(String pNo, int accountId) {
        List<String> list = new ArrayList<>();
        for (Customer customer : customers) {
            if (pNo.equals(customer.getpNo()) && customer.getAccountInfo(accountId) != null) {
                list.addAll(customer.getTransactions(accountId));
                if (list != null) {
                    return list;
                }
            }
        }
        return null;
    }

    /**
     * Sparar bankens kunder och konton till fil.
     * 
     * @param fileName filnamn
     */
    public void saveBankToFile(String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/oscekb5/files/" + fileName))) {
            out.writeObject(customers);
            out.writeInt(nextAccountNumber);
        } catch (Exception e) {
            throw new RuntimeException("Could not save bank data to file");
        }
    }

    /**
     * Läser in bankens kunder och konton från fil.
     * 
     * @param path sökväg till fil
     */
    public void loadBankToFile(String path) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/oscekb5/files/" + path))) {
            customers.clear();
            customers.addAll((ArrayList<Customer>) in.readObject());
            nextAccountNumber = in.readInt();
            System.out.println("Bank data loaded from file: " + "src/oscekb5/files/" + path);
        } catch (IOException e) {
            throw new RuntimeException("Could not load bank data from file");
        } catch (Exception e) {
            throw new RuntimeException("Unknown error occurred while loading bank data from file");
        }
    }
}