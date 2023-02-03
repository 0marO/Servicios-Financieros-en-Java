package modelo.Accounts;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;


public class PortfolioAccount extends Account{

    private final LinkedList<Account> accounts;
    private final LinkedList<PortfolioAccount> parents;
    private final String name;

    //STATIC CREATING METHODS
    public static PortfolioAccount createNamed(String name,Account anAccount, Account anotherAccount){
        return new PortfolioAccount(name,anAccount,anotherAccount);
    }
    public static PortfolioAccount createNamed(String name, Account anAccount){
        return new PortfolioAccount(name,anAccount,null);
    }
    public static PortfolioAccount createNamed(String name){
        return new PortfolioAccount(name,null,null);
    }
    public PortfolioAccount(){
        this("",null,null);
    }
    public PortfolioAccount(String aName, Account anAccount, Account anotherAccount){
        accounts = new LinkedList<Account>();
        parents = new LinkedList<PortfolioAccount>();
        name = aName;
        if (anAccount != null){accounts.add(anAccount);}
        if (anotherAccount != null){accounts.add(anotherAccount);}
    }


    public String getName() {
        return "name";
    }
    @Override
    public int balance() {
        int sum = 0;
        Iterator<Account> it = accounts.iterator();
        while(it.hasNext()){
            sum += it.next().balance();
        }
        return sum;
    }

    @Override
    public boolean hasRegistered(AccountTransaction aTransaction) {
        return accounts.stream().anyMatch(transaction -> transaction.hasRegistered(aTransaction));
    }

    @Override
    public void iterateElementsForReport(Report aReport) {
        accounts.forEach(anAccount -> anAccount.accept(aReport));
    }

    @Override
    public LinkedList<AccountTransaction> transactions() {
        LinkedList<AccountTransaction> transactions= new LinkedList<AccountTransaction>();
        accounts.forEach(anAccount -> anAccount.addTransactionsTo(transactions));
        return transactions;
    }


    @Override
    void accept(Report aReport) {
        aReport.processProtfolio(this);
    }

    @Override
    void addTransactionsTo(LinkedList<AccountTransaction> aListOfTransactions) {
        accounts.forEach(anAccount -> anAccount.addTransactionsTo(aListOfTransactions));
    }

    // ACCOUNTS MANAGEMENT
    public boolean accountsIncludes(Account anAccount){
        // si == no funciona debo definir .equals().
        // Igualmente la logica es que pregunte por una cuenta existente en memoria por lo tanto
        // si "anAccount" y account apuntan a la misma posición de memoria este método funciona.
        return accounts.stream().anyMatch(account -> account == anAccount);
    }

    public boolean accountsIsEmpty(){return accounts.isEmpty();}
    public  int accountsSize(){return accounts.size();}

    public void add(Account accountToAdd) throws Exception{
        if (assertCanAdd(accountToAdd)){
            accounts.add(accountToAdd);
            accountToAdd.addedTo(this);
        }else {
            throw new Exception("Cannot add already registered account to the portfolio");
        }
    }

    // COMPOSITION ///////////////////////////////////////////////////////////
    @Override
    public boolean addedTo(PortfolioAccount aPortfolio) {
        // THIS MIGHT NEED TO BE PRIVATE IN THE FUTURE;
        parents.add(aPortfolio);
        return true;
    }

    @Override
    public boolean isComposedBy(Account anAccount) {
        return this == anAccount ||
                accounts.stream()
                        .anyMatch(composedAccount -> composedAccount.isComposedBy(anAccount)
                                || anAccount.isComposedBy(composedAccount));
    }

    void addRootParentsTo(Set<Account> rootParents){
        if(parents.isEmpty())
            rootParents.add(this);
        else
            parents.forEach(aParent -> aParent.addRootParentsTo(rootParents));
    }
    Set<Account> getRootParents(){
        Set<Account> rootParents = new HashSet<Account>();
        this.addRootParentsTo(rootParents);
        return rootParents;
    }
    boolean anyRootParentsIsComposedBy(Account accountToAdd){
        return this.getRootParents().stream().anyMatch(aParent -> aParent.isComposedBy(accountToAdd));
    }

    boolean assertCanAdd(Account accountToAdd){
        if (this.anyRootParentsIsComposedBy(accountToAdd))
            return false;
        return true;
    }
}
