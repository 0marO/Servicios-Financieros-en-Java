package modelo.Reports;


import modelo.Accounts.PortfolioAccount;
import modelo.Accounts.ReceptiveAccount;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioTreePrinterTest {

    @Test
    void test01CanCreateReceptiveAccountWithAName() {
        ReceptiveAccount account = ReceptiveAccount.newNamed("Cuenta de Juan");
        assertEquals("Cuenta de Juan", account.getName());
    }

    @Test
    void test02CanCreateReceptiveAccountWithADifferentName() {
        ReceptiveAccount account = ReceptiveAccount.newNamed("Cuenta de Angeles");
        assertEquals("Cuenta de Angeles", account.getName());
    }

    @Test
    void test03CanCreatePortfolioWithAName() {
        PortfolioAccount portfolioAccount = PortfolioAccount.createNamed("Portfolio de hijos");
        assertEquals("Portfolio de hijos", portfolioAccount.getName());
    }

    @Test
    void test04CanCreatePortfolioWithANameAndAChildAccount() {

        ReceptiveAccount account = ReceptiveAccount.newNamed("Cuenta de Angeles");
        PortfolioAccount portfolioAccount = PortfolioAccount.createNamed("Portfolio de hijos", account);
        assertEquals("Portfolio de hijos", portfolioAccount.getName());
        assertTrue(portfolioAccount.accountsIncludes(account));
    }

    @Test
    void test05CanCreatePortfolioWithANameWithAChildAccountAndAChildPorfolio() {
        ReceptiveAccount account = ReceptiveAccount.newNamed("Cuenta de Angeles");
        PortfolioAccount portfolioAccount = PortfolioAccount.createNamed("Portfolio de hijos");
        PortfolioAccount parentPortfolioAccount = PortfolioAccount.createNamed("Portfolio de Familia", account,portfolioAccount);

        assertEquals("Portfolio de hijos", portfolioAccount.getName());
        assertTrue(parentPortfolioAccount.accountsIncludes(account));
        assertTrue(parentPortfolioAccount.accountsIncludes(portfolioAccount));
    }

    @Test
    void test06CanCreateAReceptiveAccountWithoutANameAndTheNameIsEmpty() {
        ReceptiveAccount account = ReceptiveAccount.newNamed("");
        assertEquals("", account.getName());
    }

    @Test
    void test07CanCreateAPortfolioWithoutANameAndTheNameIsEmpty() {
        PortfolioAccount portfolioAccount = PortfolioAccount.createNamed("");
        assertEquals("", portfolioAccount.getName());
    }

    @Test
    void test08CanCreateAPortfolioTreePrinterForAEmptyPortfolioAndTheResultIsTheExpected() {
        PortfolioAccount portfolioAccount = PortfolioAccount.createNamed("");
        PortfolioTreePrinter report = PortfolioTreePrinter.generateFor(portfolioAccount);

        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("");
        assertEquals(expectedResult, report.yieldResult());
    }

    @Test
    void test09CanCreateAPortfolioTreePrinterForAEmptyPortfolioWithANameAndTheResultIsTheExpected() {
        PortfolioAccount portfolioAccount = PortfolioAccount.createNamed("Portfolio de Familia");
        PortfolioTreePrinter report = PortfolioTreePrinter.generateFor(portfolioAccount);

        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("Portfolio de Familia");
        assertEquals(expectedResult, report.yieldResult());
    }

    @Test
    void test10CanCreateAPortfolioTreePrinterForAPortfolioWithChildReceptiveAccountAndTheResultIsTheExpected() {
        ReceptiveAccount account = ReceptiveAccount.newNamed("Cuenta de Juan");
        PortfolioAccount portfolioAccount = PortfolioAccount.createNamed("Portfolio de Familia", account);
        PortfolioTreePrinter report = PortfolioTreePrinter.generateFor(portfolioAccount);

        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("Portfolio de Familia");
        expectedResult.add(" Cuenta de Juan");
        assertEquals(expectedResult, report.yieldResult());
    }

    @Test
    void test11CanCreateAPortfolioTreePrinterForAPortfolioWithTwoChildReceptiveAccountsAndTheResultIsTheExpected() {
        ReceptiveAccount account = ReceptiveAccount.newNamed("Cuenta de Juan");
        ReceptiveAccount account2 = ReceptiveAccount.newNamed("Cuenta de Angeles");
        PortfolioAccount portfolioAccount = PortfolioAccount.createNamed("Portfolio de Familia", account, account2);
        PortfolioTreePrinter report = PortfolioTreePrinter.generateFor(portfolioAccount);

        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("Portfolio de Familia");
        expectedResult.add(" Cuenta de Juan");
        expectedResult.add(" Cuenta de Angeles");
        assertEquals(expectedResult, report.yieldResult());
    }

    @Test
    void test12CanCreateAPortfolioTreePrinterForAPortfolioWithChildReceptiveAccountAndChildPortfolioAndTheResultIsTheExpected() {
        ReceptiveAccount account = ReceptiveAccount.newNamed("Cuenta de Juan");
        PortfolioAccount portfolioAccount = PortfolioAccount.createNamed("Portfolio de Hijos", account);
        PortfolioAccount parentPortfolioAccount = PortfolioAccount.createNamed("Portfolio de Familia",portfolioAccount);
        PortfolioTreePrinter report = PortfolioTreePrinter.generateFor(parentPortfolioAccount);

        LinkedList<String> expectedResult = new LinkedList<>();
        expectedResult.add("Portfolio de Familia");
        expectedResult.add(" Portfolio de Hijos");
        expectedResult.add("  Cuenta de Juan");

        assertEquals(expectedResult, report.yieldResult());
    }

}

