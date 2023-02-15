package modelo.Reports;

import modelo.AccountTransaction.Deposit;
import modelo.AccountTransaction.Withdraw;
import modelo.Accounts.PortfolioAccount;
import modelo.Accounts.ReceptiveAccount;
import modelo.Transfer.Transfer;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class AccountSummaryReportTest {

    @Test
    void test01SummaryOfAccountWithNoTransactionsHasBalanceOf0Pesos() {
        ReceptiveAccount account = new ReceptiveAccount();
        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("Balance = 0\n");
        AccountSummaryReport aReport = AccountSummaryReport.generateFor(account);

        assertEquals(expectedResult, aReport.yieldResult());
    }

    @Test
    void test02SummaryOfAccountWithOneDepositHasBalanceAndDeposit() {
        ReceptiveAccount account = new ReceptiveAccount();
        Deposit.createAndRegisterOn(100,account);

        LinkedList<String> expectedReport = new LinkedList<>();
        expectedReport.add("Depósito por 100\n");
        expectedReport.add("Balance = 100\n");

        AccountSummaryReport report = AccountSummaryReport.generateFor(account);

        assertEquals(expectedReport,report.yieldResult());
    }

    @Test
    void test03SummaryOfAccountWithMultipleDepositsHasAllDeposits() {
        ReceptiveAccount account = new ReceptiveAccount();
        Deposit.createAndRegisterOn(100,account);
        Deposit.createAndRegisterOn(10,account);

        LinkedList<String> expectedReport = new LinkedList<>();
        expectedReport.add("Depósito por 100\n");
        expectedReport.add("Depósito por 10\n");
        expectedReport.add("Balance = 110\n");
        AccountSummaryReport report = AccountSummaryReport.generateFor(account);

        assertEquals(expectedReport,report.yieldResult());
    }

    @Test
    void test04SummaryOfAccountWithOneWithdrawalHasBalanceAndWithdraw() {
        ReceptiveAccount account = new ReceptiveAccount();
        Withdraw.createAndRegisterOn(100,account);


        LinkedList<String> expectedReport = new LinkedList<>();
        expectedReport.add("Extracción por 100\n");
        expectedReport.add("Balance = -100\n");
        AccountSummaryReport report = AccountSummaryReport.generateFor(account);

        assertEquals(expectedReport,report.yieldResult());
    }

    @Test
    void test05SummaryOfAccountWithMultipleWithdrawsHasAllWithdraws() {
        ReceptiveAccount account = new ReceptiveAccount();
        Withdraw.createAndRegisterOn(100,account);
        Withdraw.createAndRegisterOn(50,account);


        LinkedList<String> expectedReport = new LinkedList<>();
        expectedReport.add("Extracción por 100\n");
        expectedReport.add("Extracción por 50\n");
        expectedReport.add("Balance = -150\n");
        AccountSummaryReport report = AccountSummaryReport.generateFor(account);

        assertEquals(expectedReport,report.yieldResult());
    }

    @Test
    void test06SummaryOfAccountThatTransferedHasTransferLegAndBalance() {
        ReceptiveAccount account = new ReceptiveAccount();
        ReceptiveAccount recipientAccount = new ReceptiveAccount();

        try {
            Transfer.createFor(100,account,recipientAccount);
        } catch (Exception e) {
            fail();
        }

        LinkedList<String> expectedReport = new LinkedList<>();
        expectedReport.add("Salida por transferencia de 100\n");
        expectedReport.add("Balance = -100\n");
        AccountSummaryReport report = AccountSummaryReport.generateFor(account);

        assertEquals(expectedReport,report.yieldResult());
    }

    @Test
    void test07SummaryOfAccountThatTransferedMultipleTimesHasTransferLegsAndBalance() {
        ReceptiveAccount account = new ReceptiveAccount();
        ReceptiveAccount recipientAccount = new ReceptiveAccount();

        try {
            Transfer.createFor(100,account,recipientAccount);
            Transfer.createFor(200,account,recipientAccount);
        } catch (Exception e) {
            fail();
        }

        LinkedList<String> expectedReport = new LinkedList<>();
        expectedReport.add("Salida por transferencia de 100\n");
        expectedReport.add("Salida por transferencia de 200\n");
        expectedReport.add("Balance = -300\n");
        AccountSummaryReport report = AccountSummaryReport.generateFor(account);

        assertEquals(expectedReport,report.yieldResult());
    }

    @Test
    void test08SummaryOfAccountThatReceivedATransferHasTransferLegAndBalance() {
        ReceptiveAccount account = new ReceptiveAccount();
        ReceptiveAccount recipientAccount = new ReceptiveAccount();

        try {
            Transfer.createFor(100,account,recipientAccount);
        } catch (Exception e) {
            fail();
        }

        LinkedList<String> expectedReport = new LinkedList<>();
        expectedReport.add("Entrada por transferencia de 100\n");
        expectedReport.add("Balance = 100\n");
        AccountSummaryReport report = AccountSummaryReport.generateFor(recipientAccount);

        assertEquals(expectedReport,report.yieldResult());
    }

    @Test
    void test09SummaryOfAccountThatReceivedMultipleTransferencesHasTransferLegsAndBalance() {
        ReceptiveAccount account = new ReceptiveAccount();
        ReceptiveAccount recipientAccount = new ReceptiveAccount();

        try {
            Transfer.createFor(100,account,recipientAccount);
            Transfer.createFor(200,account,recipientAccount);
        } catch (Exception e) {
            fail();
        }

        LinkedList<String> expectedReport = new LinkedList<>();
        expectedReport.add("Entrada por transferencia de 100\n");
        expectedReport.add("Entrada por transferencia de 200\n");
        expectedReport.add("Balance = 300\n");
        AccountSummaryReport report = AccountSummaryReport.generateFor(recipientAccount);

        assertEquals(expectedReport,report.yieldResult());
    }

    @Test
    void test10SummaryOfPortfolioWithNoAccountsHasCeroBalance() {
        ReceptiveAccount account = new ReceptiveAccount();
        ReceptiveAccount recipientAccount = new ReceptiveAccount();

        try {
            Transfer.createFor(100,account,recipientAccount);
            Transfer.createFor(200,account,recipientAccount);
        } catch (Exception e) {
            fail();
        }

        LinkedList<String> expectedReport = new LinkedList<>();
        expectedReport.add("Entrada por transferencia de 100\n");
        expectedReport.add("Entrada por transferencia de 200\n");
        expectedReport.add("Balance = 300\n");
        AccountSummaryReport report = AccountSummaryReport.generateFor(recipientAccount);

        assertEquals(expectedReport,report.yieldResult());
    }

    @Test
    void test11SummaryOfPortfolioWithAnAccountWithDepositHasDepositAndBalance() {
        ReceptiveAccount account = new ReceptiveAccount();
        PortfolioAccount portfolio = PortfolioAccount.createNamed("",account);

        Deposit.createAndRegisterOn(100,account);


        LinkedList<String> expectedReport = new LinkedList<>();
        expectedReport.add("Depósito por 100\n");
        expectedReport.add("Balance = 100\n");

        AccountSummaryReport report = AccountSummaryReport.generateFor(portfolio);

        assertEquals(expectedReport,report.yieldResult());
    }

    @Test
    void test12SummaryOfPortfolioWithMultipleAccountsHasDepositsOfAccountsAndBalance() {
        ReceptiveAccount account = new ReceptiveAccount();
        ReceptiveAccount account2 = new ReceptiveAccount();
        PortfolioAccount portfolio = PortfolioAccount.createNamed("",account, account2);

        Deposit.createAndRegisterOn(200,account2);
        Deposit.createAndRegisterOn(100,account);


        LinkedList<String> expectedReport = new LinkedList<>();
        expectedReport.add("Depósito por 100\n");
        expectedReport.add("Depósito por 200\n");
        expectedReport.add("Balance = 300\n");

        AccountSummaryReport report = AccountSummaryReport.generateFor(portfolio);

        assertEquals(expectedReport,report.yieldResult());
    }

    @Test
    void test13SummaryOfParentPortfolioWithChildPortfolioAndAccountHasDepositOfAccountAndBalance() {
        ReceptiveAccount account = new ReceptiveAccount();
        PortfolioAccount portfolio = PortfolioAccount.createNamed("",account);
        PortfolioAccount parentPortfolio = PortfolioAccount.createNamed("",portfolio);

        Deposit.createAndRegisterOn(100,account);

        LinkedList<String> expectedReport = new LinkedList<>();
        expectedReport.add("Depósito por 100\n");
        expectedReport.add("Balance = 100\n");

        AccountSummaryReport report = AccountSummaryReport.generateFor(parentPortfolio);

        assertEquals(expectedReport,report.yieldResult());
    }

}