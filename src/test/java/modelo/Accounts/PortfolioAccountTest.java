package modelo.Accounts;

import modelo.AccountTransaction.AccountTransaction;
import modelo.AccountTransaction.Deposit;
import modelo.AccountTransaction.Withdraw;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
class PortfolioAccountTest {

    @Test
    void balanceOfPortfoliowithoutAccountsisZero(){
        assertEquals(new PortfolioAccount().balance(),0);
    }

    @Test
    void balanceOfPortfolioWithOneAccountIsAccountBalance() {
        ReceptiveAccount account = new ReceptiveAccount();
        Deposit.createAndRegisterOn(100, account);
        PortfolioAccount portfolio = new PortfolioAccount("",account,null);

        assertEquals(account.balance(), portfolio.balance());
    }

    @Test
    void balanceOfPortfolioIsCalculatedRecursivelyOnPortfolios() throws Exception {
        ReceptiveAccount simplePortfolioAccount = new ReceptiveAccount();
        Deposit.createAndRegisterOn(100, simplePortfolioAccount);

        PortfolioAccount simplePortfolio = new PortfolioAccount();
        simplePortfolio.add(simplePortfolioAccount);

        ReceptiveAccount composedReceptiveAccount = new ReceptiveAccount();
        Deposit.createAndRegisterOn(50, composedReceptiveAccount);
        PortfolioAccount composedPortfolio = PortfolioAccount
                                    .createNamed("",simplePortfolio, composedReceptiveAccount);

        assertEquals(composedReceptiveAccount.balance() + simplePortfolio.balance(),
                        composedPortfolio.balance());

    }

    @Test
    void test04PortfolioWithoutAccountsHasNoRegisteredTransaction() {
        assertFalse(new PortfolioAccount().hasRegistered(Deposit.createFor(100)));
    }

    @Test
    void test05PortfolioHasRegisteredItsAccountsTransactions() {
        ReceptiveAccount account = new ReceptiveAccount();
        Deposit deposit = Deposit.createAndRegisterOn(100, account);
        PortfolioAccount portfolio = PortfolioAccount.createNamed("",account);

        assertTrue(portfolio.hasRegistered(deposit));
    }

    @Test
    void test06PortfolioLooksForRegisteredTransactionsRecursively() {
        ReceptiveAccount simpleReceptiveAccount = new ReceptiveAccount();
        Deposit simpleReceptiveAccountDeposit = Deposit.createAndRegisterOn(100, simpleReceptiveAccount);
        PortfolioAccount simplePortfolio = PortfolioAccount.createNamed("",simpleReceptiveAccount);

        ReceptiveAccount composedReceptiveAccount = new ReceptiveAccount();
        Deposit composedReceptiveAccountDeposit = Deposit.createAndRegisterOn(50, composedReceptiveAccount);
        PortfolioAccount composedPortfolio = PortfolioAccount.createNamed("",
                                                                    simplePortfolio,
                                                                    composedReceptiveAccount);


        assertTrue(composedPortfolio.hasRegistered(simpleReceptiveAccountDeposit));
        assertTrue(composedPortfolio.hasRegistered(composedReceptiveAccountDeposit));

    }

    @Test
    void test07PortfolioHasNoTransactionWhenHasNoAccounts() {
        assertTrue(new PortfolioAccount().transactions().isEmpty());
    }

    @Test
    void test08PortfolioTransactionsIncludesAllItsAccountsTransactions() {
        ReceptiveAccount account = new ReceptiveAccount();
        Deposit accountDeposit = Deposit.createAndRegisterOn(100, account);
        Withdraw accountWithdraw = Withdraw.createAndRegisterOn(100, account);

        PortfolioAccount portfolio = PortfolioAccount.createNamed("",account);
        LinkedList<AccountTransaction> portfolioTransactions = portfolio.transactions();

        assertEquals(2, portfolioTransactions.size());
        assertTrue(portfolioTransactions.contains(accountDeposit));
        assertTrue(portfolioTransactions.contains(accountWithdraw));
    }

    @Test
    void accept() {
    }

    @Test
    void addTransactionsTo() {
    }

    @Test
    void accountsIncludes() {
    }

    @Test
    void accountsIsEmpty() {
    }

    @Test
    void accountsSize() {
    }

    @Test
    void add() {
    }

    @Test
    void addedTo() {
    }

    @Test
    void isComposedBy() {
    }

    @Test
    void addRootParentsTo() {
    }

    @Test
    void getRootParents() {
    }

    @Test
    void anyRootParentsIsComposedBy() {
    }

    @Test
    void assertCanAdd() {
    }
}