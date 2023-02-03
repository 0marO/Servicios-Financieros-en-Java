package modelo.Accounts;

import java.util.LinkedList;

public abstract class Account {

    // MAIN PROTOCOL
    public abstract int balance();
    public abstract boolean hasRegistered(AccountTransaction aTransaction);
    public abstract void iterateElementsForReport(Report aReport);
    public abstract LinkedList<AccountTransaction> transactions();

    // COMPOSITION
    public abstract boolean addedTo(PortfolioAccount aPortfolio);
    public abstract boolean isComposedBy(Account anAccount);

    // PRIVATE FOR PACKAGE
    void accept(Report aReport) {
        return;
    }
    void addTransactionsTo(LinkedList<AccountTransaction> aListOfTransactions){
        return;
    }
}
