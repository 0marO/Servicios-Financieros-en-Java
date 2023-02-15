package modelo.Reports;

import modelo.AccountTransaction.Deposit;
import modelo.Accounts.PortfolioAccount;
import modelo.Accounts.ReceptiveAccount;
import modelo.Transfer.Transfer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NetTransferReportTest {

    @Test
    void test01ReceptiveAccountWithNoTransfersHas0pesosNetTransferReport() {
        ReceptiveAccount account = new ReceptiveAccount();
        NetTransferReport report = NetTransferReport.generateFor(account);
        assertEquals(0,report.yieldResult());
    }

    @Test
    void test02NetTransferReportFromOriginOfTransferAccountWithOnlyOneTransferIsTheExpected() {
        ReceptiveAccount account = new ReceptiveAccount();
        ReceptiveAccount recipientAccount = new ReceptiveAccount();

        try {
            Transfer.createFor(10,account,recipientAccount);
        } catch (Exception e) {
            fail();
        }

        NetTransferReport report = NetTransferReport.generateFor(account);
        assertEquals(-10,report.yieldResult());
    }

    @Test
    void test03NetTransferReportFromRecipientOfTransferAccountWithOnlyOneTransferIsTheExpected() {
        ReceptiveAccount account = new ReceptiveAccount();
        ReceptiveAccount recipientAccount = new ReceptiveAccount();

        try {
            Transfer.createFor(10,account,recipientAccount);
        } catch (Exception e) {
            fail();
        }

        NetTransferReport report = NetTransferReport.generateFor(recipientAccount);
        assertEquals(10,report.yieldResult());
    }

    @Test
    void test04NetTransferReportFromRecipientOfTransferAccountWithTwoTransfersIsTheExpected() {
        ReceptiveAccount account = new ReceptiveAccount();
        ReceptiveAccount recipientAccount = new ReceptiveAccount();

        try {
            Transfer.createFor(10,account,recipientAccount);
            Transfer.createFor(10,account,recipientAccount);
        } catch (Exception e) {
            fail();
        }

        NetTransferReport report = NetTransferReport.generateFor(account);
        assertEquals(-20,report.yieldResult());
    }

    @Test
    void test05NetTransferReportIsNotAffectedByOtherTypesOfTransacctions() {
        ReceptiveAccount account = new ReceptiveAccount();
        ReceptiveAccount recipientAccount = new ReceptiveAccount();

        try {
            Transfer.createFor(10,account,recipientAccount);
            Transfer.createFor(10,account,recipientAccount);
        } catch (Exception e) {
            fail();
        }
        Deposit.createAndRegisterOn(100,account);

        NetTransferReport report = NetTransferReport.generateFor(account);
        assertEquals(-20,report.yieldResult());
        assertEquals(80,account.balance());
    }

    @Test
    void test06NetTransferReportResultIsCeroPesosForAPortfolioWithNoTransfers() {
        PortfolioAccount portfolioAccount = new PortfolioAccount();
        NetTransferReport report = NetTransferReport.generateFor(portfolioAccount);
        assertEquals(0,report.yieldResult());
    }

    @Test
    void test07CanGenerateReportForPortfolioWithChildAccountWithARegisteredOriginOfTransfer() {
        ReceptiveAccount originAccount = new ReceptiveAccount();
        ReceptiveAccount recipientAccount = new ReceptiveAccount();
        PortfolioAccount portfolioAccount = PortfolioAccount.createNamed("",originAccount);

        try {
            Transfer.createFor(10,originAccount,recipientAccount);
        } catch (Exception e) {
            fail();
        }

        NetTransferReport report = NetTransferReport.generateFor(portfolioAccount);
        assertEquals(-10,report.yieldResult());
    }

    @Test
    void test08CanGenerateReportForPortfolioWithChildAccountWithARegisteredEndOfTransfer() {
        ReceptiveAccount originAccount = new ReceptiveAccount();
        ReceptiveAccount recipientAccount = new ReceptiveAccount();
        PortfolioAccount portfolioAccount = PortfolioAccount.createNamed("",recipientAccount);

        try {
            Transfer.createFor(10,originAccount,recipientAccount);
        } catch (Exception e) {
            fail();
        }

        NetTransferReport report = NetTransferReport.generateFor(portfolioAccount);
        assertEquals(10,report.yieldResult());
    }

    @Test
    void test09CanGenerateReportForPortfolioWithChildAccountWithTwoRegisteredEndOfTransfers() {
        ReceptiveAccount originAccount = new ReceptiveAccount();
        ReceptiveAccount recipientAccount = new ReceptiveAccount();
        PortfolioAccount portfolioAccount = PortfolioAccount.createNamed("",recipientAccount);

        try {
            Transfer.createFor(10,originAccount,recipientAccount);
            Transfer.createFor(10,originAccount,recipientAccount);
        } catch (Exception e) {
            fail();
        }

        NetTransferReport report = NetTransferReport.generateFor(portfolioAccount);
        assertEquals(20,report.yieldResult());
    }

    @Test
    void test10CanGenerateReportForPortfolioWithChilPortfolioThatHasAChildAccountWithOneRegisteredTransfer() {
        ReceptiveAccount originAccount = new ReceptiveAccount();
        ReceptiveAccount recipientAccount = new ReceptiveAccount();
        PortfolioAccount portfolioAccount = PortfolioAccount.createNamed("",
                                                            PortfolioAccount.createNamed("",recipientAccount));

        try {
            Transfer.createFor(10,originAccount,recipientAccount);
        } catch (Exception e) {
            fail();
        }

        NetTransferReport report = NetTransferReport.generateFor(portfolioAccount);
        assertEquals(10,report.yieldResult());
    }
}