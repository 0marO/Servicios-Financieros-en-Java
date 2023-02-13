package modelo.Transfer;

import modelo.AccountTransaction.Deposit;
import modelo.Accounts.ReceptiveAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransferTest {

    @Test
    void test01ATransferDecreasesBalanceFromOriginAccountAndIncreasesItForDestinationAccount() {
        ReceptiveAccount originAccount = new ReceptiveAccount();
        Deposit.createAndRegisterOn(10,originAccount);

        ReceptiveAccount destinationAccount = new ReceptiveAccount();
        Deposit.createAndRegisterOn(10,destinationAccount);

        try {
            Transfer.createFor(2,originAccount,destinationAccount);
        } catch (Exception e) {
            fail();
        }

        assertEquals(8,originAccount.balance());
        assertEquals(12,destinationAccount.balance());
    }

    @Test
    void test02ATransferKnowsItsValue() {
        ReceptiveAccount originAccount = new ReceptiveAccount();
        Deposit.createAndRegisterOn(10,originAccount);

        ReceptiveAccount destinationAccount = new ReceptiveAccount();
        Deposit.createAndRegisterOn(10,destinationAccount);

        Transfer transfer = null;
        try {
            transfer = Transfer.createFor(2,originAccount,destinationAccount);
        } catch (Exception e) {
            fail();
        }


        assertEquals(2,transfer.getValue());
    }

    @Test
    void test03DepositLegKnowsItsWithdrawCounterpart() {
        ReceptiveAccount originAccount = new ReceptiveAccount();
        Deposit.createAndRegisterOn(10,originAccount);

        ReceptiveAccount destinationAccount = new ReceptiveAccount();
        Deposit.createAndRegisterOn(10,destinationAccount);

        Transfer transfer = null;
        try {
            transfer = Transfer.createFor(2,originAccount,destinationAccount);
        } catch (Exception e) {
            fail();
        }


        assertEquals(transfer.withdrawLeg(),transfer.depositLeg().withdrawLeg());
    }

    @Test
    void test04WithdrawLegKnowsItsDepositCounterpart() {
        ReceptiveAccount originAccount = new ReceptiveAccount();
        Deposit.createAndRegisterOn(10,originAccount);

        ReceptiveAccount destinationAccount = new ReceptiveAccount();
        Deposit.createAndRegisterOn(10,destinationAccount);

        Transfer transfer = null;
        try {
            transfer = Transfer.createFor(2,originAccount,destinationAccount);
        } catch (Exception e) {
            fail();
        }

        assertEquals(transfer.depositLeg(),transfer.withdrawLeg().depositLeg());
    }

    @Test
    void test05OriginAndDestinationAccountsCannotBeTheSame() {
        ReceptiveAccount originAccount = new ReceptiveAccount();
        Deposit.createAndRegisterOn(10,originAccount);

        ReceptiveAccount destinationAccount = new ReceptiveAccount();
        Deposit.createAndRegisterOn(10,destinationAccount);
        try {
            Transfer.createFor(2,originAccount,originAccount);
        } catch (Exception e) {
            assertEquals("Cannot make this transaction!",e.getMessage());
            assertEquals(10,originAccount.balance());
        }

    }

    @Test
    void test06CannotTransferAZeroAmount() {
        ReceptiveAccount originAccount = new ReceptiveAccount();
        Deposit.createAndRegisterOn(10,originAccount);

        try {
            Transfer.createFor(0,originAccount,originAccount);
        } catch (Exception e) {
            assertEquals("Cannot make this transaction!",e.getMessage());
            assertEquals(10,originAccount.balance());
        }

    }
}