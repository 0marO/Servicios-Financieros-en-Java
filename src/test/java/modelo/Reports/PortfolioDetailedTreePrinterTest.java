package modelo.Reports;

import modelo.AccountTransaction.Deposit;
import modelo.AccountTransaction.Withdraw;
import modelo.Accounts.PortfolioAccount;
import modelo.Accounts.ReceptiveAccount;
import modelo.Transfer.Transfer;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioDetailedTreePrinterTest {

    @Test
    void test01CanCreatePortfolioDetailedTreePrinterReportForAnEmpyPortfolio() {

        PortfolioAccount portfolio = PortfolioAccount.createNamed("");
        PortfolioDetailedTreePrinter report = PortfolioDetailedTreePrinter.generateFor(portfolio);

        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("");
        expectedResult.add(" Balance = 0");

        assertEquals(expectedResult,report.yieldResult());
    }

    @Test
    void test02CanCreatePortfolioDetailedTreePrinterReportForAnEmpyNamedPortfolio() {
        PortfolioAccount portfolio = PortfolioAccount.createNamed("Portfolio de Ana Gutson");
        PortfolioDetailedTreePrinter report = PortfolioDetailedTreePrinter.generateFor(portfolio);

        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("Portfolio de Ana Gutson");
        expectedResult.add(" Balance = 0");

        assertEquals(expectedResult,report.yieldResult());
    }

    @Test
    void test03CanCreatePortfolioDetailedTreePrinterReportForAnPortolioWithAChildReceptiveAccount() {
        ReceptiveAccount account = ReceptiveAccount.newNamed("Cuenta de Ana Gutson");
        PortfolioAccount portfolio = PortfolioAccount.createNamed("Portfolio de Ana Gutson",account);
        PortfolioDetailedTreePrinter report = PortfolioDetailedTreePrinter.generateFor(portfolio);

        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("Portfolio de Ana Gutson");
        expectedResult.add(" Cuenta de Ana Gutson");
        expectedResult.add("  Balance = 0");
        expectedResult.add(" Balance = 0");

        assertEquals(expectedResult,report.yieldResult());
    }

    @Test
    void test04CanCreatePortfolioDetailedTreePrinterReportForAPortolioWithTwoChildReceptiveAccount() {

        ReceptiveAccount account = ReceptiveAccount.newNamed("Cuenta de Ana Gutson");
        ReceptiveAccount account2 = ReceptiveAccount.newNamed("Cuenta de Joaquín Singer");

        PortfolioAccount portfolio = PortfolioAccount.createNamed("Portfolio de Ana Gutson",account,account2);

        PortfolioDetailedTreePrinter report = PortfolioDetailedTreePrinter.generateFor(portfolio);

        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("Portfolio de Ana Gutson");
        expectedResult.add(" Cuenta de Ana Gutson");
        expectedResult.add("  Balance = 0");
        expectedResult.add(" Cuenta de Joaquín Singer");
        expectedResult.add("  Balance = 0");
        expectedResult.add(" Balance = 0");

        assertEquals(expectedResult,report.yieldResult());
    }

    @Test
    void test05CanCreatePortfolioDetailedTreePrinterReportForAPortolioWithChildReceptiveAccountWithARegisteredDeposit() {
        ReceptiveAccount account = ReceptiveAccount.newNamed("Cuenta de Ana Gutson");
        PortfolioAccount portfolio = PortfolioAccount.createNamed("Portfolio de Ana Gutson",account);

        Deposit.createAndRegisterOn(1000000000, account);

        PortfolioDetailedTreePrinter report = PortfolioDetailedTreePrinter.generateFor(portfolio);

        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("Portfolio de Ana Gutson");
        expectedResult.add(" Cuenta de Ana Gutson");
        expectedResult.add("  Depósito por 1000000000\n");
        expectedResult.add("  Balance = 1000000000");
        expectedResult.add(" Balance = 1000000000");

        assertEquals(expectedResult,report.yieldResult());
    }

    @Test
    void test06CanCreatePortfolioDetailedTreePrinterReportForAPortolioWithChildReceptiveAccountWithARegisteredDepositAndWithdraw() {
        ReceptiveAccount account = ReceptiveAccount.newNamed("Cuenta de Ana Gutson");
        PortfolioAccount portfolio = PortfolioAccount.createNamed("Portfolio de Ana Gutson",account);

        Deposit.createAndRegisterOn(1000000000, account);
        Withdraw.createAndRegisterOn(1000000000, account);

        PortfolioDetailedTreePrinter report = PortfolioDetailedTreePrinter.generateFor(portfolio);

        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("Portfolio de Ana Gutson");
        expectedResult.add(" Cuenta de Ana Gutson");
        expectedResult.add("  Depósito por 1000000000\n");
        expectedResult.add("  Extracción por 1000000000\n");
        expectedResult.add("  Balance = 0");
        expectedResult.add(" Balance = 0");

        assertEquals(expectedResult,report.yieldResult());
    }

    @Test
    void test07CanCreatePortfolioDetailedTreePrinterReportForAPortolioWithChildReceptiveAccountWithARegisteredDepositAndTransfer() {
        ReceptiveAccount account = ReceptiveAccount.newNamed("Cuenta de Ana Gutson");
        ReceptiveAccount recipientAccount = ReceptiveAccount.newNamed("Cuenta de Pepito");
        PortfolioAccount portfolio = PortfolioAccount.createNamed("Portfolio de Ana Gutson",account);

        Deposit.createAndRegisterOn(1000000000, account);

        try {
            Transfer.createFor(1000000000,account,recipientAccount);
        } catch (Exception e) {
            fail();
        }

        PortfolioDetailedTreePrinter report = PortfolioDetailedTreePrinter.generateFor(portfolio);

        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("Portfolio de Ana Gutson");
        expectedResult.add(" Cuenta de Ana Gutson");
        expectedResult.add("  Depósito por 1000000000\n");
        expectedResult.add("  Salida por transferencia de 1000000000\n");
        expectedResult.add("  Balance = 0");
        expectedResult.add(" Balance = 0");

        assertEquals(expectedResult,report.yieldResult());
    }

    @Test
    void test08CanCreatePortfolioDetailedTreePrinterReportForAPortfolioWithChildPortfolio() {

        PortfolioAccount portfolio = PortfolioAccount.createNamed("Portfolio de Ana Gutson");
        PortfolioAccount fatherPortfolio = PortfolioAccount.createNamed("Portfolio de Ayudantes",portfolio);

        PortfolioDetailedTreePrinter report = PortfolioDetailedTreePrinter.generateFor(fatherPortfolio);

        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("Portfolio de Ayudantes");
        expectedResult.add(" Portfolio de Ana Gutson");
        expectedResult.add("  Balance = 0");
        expectedResult.add(" Balance = 0");
        assertEquals(expectedResult,report.yieldResult());
    }

    @Test
    void test09CanCreatePortfolioDetailedTreePrinterReportForAPortfolioWithChildPortfoliowithChildReceptiveAccount() {
        ReceptiveAccount account = ReceptiveAccount.newNamed("Cuenta de Ana Gutson");
        PortfolioAccount portfolio = PortfolioAccount.createNamed("Portfolio de Ana Gutson",account);
        PortfolioAccount fatherPortfolio = PortfolioAccount.createNamed("Portfolio de Ayudantes",portfolio);

        PortfolioDetailedTreePrinter report = PortfolioDetailedTreePrinter.generateFor(fatherPortfolio);

        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("Portfolio de Ayudantes");
        expectedResult.add(" Portfolio de Ana Gutson");
        expectedResult.add("  Cuenta de Ana Gutson");
        expectedResult.add("   Balance = 0");
        expectedResult.add("  Balance = 0");
        expectedResult.add(" Balance = 0");
        assertEquals(expectedResult,report.yieldResult());
    }
}