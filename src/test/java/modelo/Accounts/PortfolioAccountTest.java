package modelo.Accounts;

import modelo.AccountTransaction.AccountTransaction;
import modelo.AccountTransaction.Deposit;
import modelo.AccountTransaction.Withdraw;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
class PortfolioAccountTest {

    @Test
    void test01balanceOfPortfoliowithoutAccountsisZero(){
        assertEquals(new PortfolioAccount().balance(),0);
    }

    @Test
    void test02balanceOfPortfolioWithOneAccountIsAccountBalance() {
        ReceptiveAccount account = new ReceptiveAccount();
        Deposit.createAndRegisterOn(100, account);
        PortfolioAccount portfolio = new PortfolioAccount("",account,null);

        assertEquals(account.balance(), portfolio.balance());
    }

    @Test
    void test03balanceOfPortfolioIsCalculatedRecursivelyOnPortfolios() throws Exception {
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
    void test09PortfolioTransactionsAreCalculatedRecursively() {
        ReceptiveAccount simpleReceptiveAccount = new ReceptiveAccount();
        Deposit simpleReceptiveAccountDeposit = Deposit.createAndRegisterOn(100, simpleReceptiveAccount);
        PortfolioAccount simplePortfolio = PortfolioAccount.createNamed("", simpleReceptiveAccount);

        ReceptiveAccount composedReceptiveAccount = new ReceptiveAccount();
        Deposit composedReceptiveAccountDeposit = Deposit.createAndRegisterOn(50, composedReceptiveAccount);
        PortfolioAccount composedPortfolio = PortfolioAccount.createNamed("", simplePortfolio
                                                                    ,composedReceptiveAccount);

        LinkedList<AccountTransaction> transactions = composedPortfolio.transactions();

        assertEquals(2, transactions.size());
        assertTrue(transactions.contains(simpleReceptiveAccountDeposit));
        assertTrue(transactions.contains(composedReceptiveAccountDeposit));
    }

    @Test
    void test10PortfolioCanNotIncludeTheSameAccountMoreThanOnce() {
        ReceptiveAccount account = new ReceptiveAccount();
        PortfolioAccount portfolio = PortfolioAccount.createNamed("", account);

        try{
            portfolio.add(account);
            fail();
        }catch (Exception e) {
            assertEquals("Cannot add already registered account to the portfolio", e.getMessage());
            assertEquals(1, portfolio.accountsSize());
            assertTrue(portfolio.accountsIncludes(account));
        }
    }

    @Test
    void test11PortfolioCanNotIncludeAccountOfItsPortfolios() {
        ReceptiveAccount account = new ReceptiveAccount();
        PortfolioAccount simplePortfolio = PortfolioAccount.createNamed("", account);
        PortfolioAccount composedPortfolio = PortfolioAccount.createNamed("", simplePortfolio);

        try{
            composedPortfolio.add(account);
            fail();
        }catch (Exception e) {
            assertEquals("Cannot add already registered account to the portfolio", e.getMessage());
            assertEquals(1, composedPortfolio.accountsSize());
            assertTrue(composedPortfolio.accountsIncludes(simplePortfolio));
        }
    }

    @Test
    void test12PortfolioCanNotIncludeItself() {
        ReceptiveAccount account = new ReceptiveAccount();
        PortfolioAccount portfolio = PortfolioAccount.createNamed("", account);

        try{
            portfolio.add(portfolio);
            fail();
        }catch (Exception e) {
            assertEquals("Cannot add already registered account to the portfolio", e.getMessage());
            assertEquals(1, portfolio.accountsSize());
            assertTrue(portfolio.accountsIncludes(account));
        }
    }

    @Test
    void test13ComposedPortfolioCanNotHaveParentPortfolioAccount() {
        ReceptiveAccount account = new ReceptiveAccount();
        PortfolioAccount simplePortfolio = new PortfolioAccount();
        PortfolioAccount composedPortfolio = PortfolioAccount.createNamed("", simplePortfolio, account);

        try{
            simplePortfolio.add(account);
            fail();
        }catch (Exception e) {
            assertEquals("Cannot add already registered account to the portfolio", e.getMessage());
            assertTrue(simplePortfolio.accountsIsEmpty());
        }
    }

    @Test
    void test14ComposedPortfolioCanNotHaveAccountOfAnyRootParentRecursively() {
        ReceptiveAccount account = new ReceptiveAccount();
        PortfolioAccount portfolio = new PortfolioAccount();

        PortfolioAccount leftParentPortfolio = PortfolioAccount.createNamed("",portfolio);
        PortfolioAccount leftRootParentPortfolio = PortfolioAccount.createNamed("",leftParentPortfolio, account);

        PortfolioAccount RightParentPortfolio = PortfolioAccount.createNamed("",portfolio);
        PortfolioAccount RightRootParentPortfolio = PortfolioAccount.createNamed("",leftParentPortfolio, account);

        try {
            portfolio.add(account);
        }catch (Exception e){
            assertEquals("Cannot add already registered account to the portfolio", e.getMessage());
            assertTrue(portfolio.accountsIsEmpty());
        }

    }

    @Test
    void test15PortfolioCanNotIncludeAnyOfTheComposedAccountOfPortfolioToAdd() {

        ReceptiveAccount sharedAccount = new ReceptiveAccount();
        PortfolioAccount portfolioToModify = new PortfolioAccount();
        PortfolioAccount rootPortfolio = PortfolioAccount.createNamed("", sharedAccount,portfolioToModify);
        PortfolioAccount portfolioToAdd = PortfolioAccount.createNamed("", sharedAccount);
        try {
            portfolioToModify.add(portfolioToAdd);
        }catch (Exception e){
            assertEquals("Cannot add already registered account to the portfolio", e.getMessage());
            assertTrue(portfolioToModify.accountsIsEmpty());
        }
    }
}