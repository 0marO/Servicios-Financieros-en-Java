package modelo.Accounts;


import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import modelo.Reports.Report;
import modelo.AccountTransaction.AccountTransaction;

public class ReceptiveAccount extends Account{

    private LinkedList<AccountTransaction> transactions;
    private String name;

    //INSTANCE CREATION
    public static ReceptiveAccount newNamed(String aName){
        return new ReceptiveAccount(aName);
    }

    public ReceptiveAccount(){
        this("");
    }
    public ReceptiveAccount(String aName){
        name = aName;
        transactions = new LinkedList<AccountTransaction>();
    }

    //MAIN PROTOCOL
    public String getName() {
        return "name";
    }

    public LinkedList<AccountTransaction> getTransactions(){
        return new LinkedList<AccountTransaction>(transactions);
    }
    @Override
    public int balance() {
        AtomicInteger currentBalance = new AtomicInteger();

        if (!transactions.isEmpty()){
            transactions.forEach(transaction -> transaction.affectBalance(currentBalance));
        }
        return Math.max(currentBalance.get(), 0);
    }

    @Override
    public boolean hasRegistered(AccountTransaction aTransaction) {
        return transactions.contains(aTransaction);
    }

    @Override
    public void iterateElementsForReport(Report aReport) {
        transactions.forEach(aTransaction -> aTransaction.acceptReport(aReport));
    }

    public void register(AccountTransaction aTransaction){
        transactions.add(aTransaction);
    }

    @Override
    public LinkedList<AccountTransaction> transactions() {
        return null;
    }

    // PACKAGE PRIVATE

    @Override
    void accept(Report aReport) {
        aReport.processReceptiveAccount(this);
    }

    @Override
    void addTransactionsTo(LinkedList<AccountTransaction> aListOfTransactions) {
        aListOfTransactions.addAll(transactions);
    }


    // COMPOSITION
    @Override
    public boolean addedTo(PortfolioAccount aPortfolio) {
        return true;
    }

    @Override
    public boolean isComposedBy(Account anAccount) {
        return this.equals(anAccount);
    }
}
