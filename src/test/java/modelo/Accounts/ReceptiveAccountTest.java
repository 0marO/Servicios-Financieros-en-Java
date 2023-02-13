package modelo.Accounts;

import modelo.AccountTransaction.Deposit;
import modelo.AccountTransaction.Withdraw;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReceptiveAccountTest {

    @Test
    void test01ReceptiveAccountHaveZeroAsBalanceWhenCreated() {
        ReceptiveAccount account = new ReceptiveAccount();
        assertEquals(0,account.balance());
    }

    @Test
    void test02DepositIncreasesBalanceOnTransactionValue() {
        ReceptiveAccount account = new ReceptiveAccount();
        Deposit.createAndRegisterOn(100, account);

        assertEquals(100,account.balance());
    }

    @Test
    void test03WithdrawDecreasesBalanceOnTransactionValue() {
        ReceptiveAccount account = new ReceptiveAccount();
        Deposit.createAndRegisterOn(100, account);
        Withdraw.createAndRegisterOn(100, account);

        assertEquals(0,account.balance());
    }

    @Test
    void test04WithdrawValueMustBePositive() {
        ReceptiveAccount account = new ReceptiveAccount();

        int withdrawValue = 50;

        assertEquals(withdrawValue,Withdraw.createAndRegisterOn(withdrawValue,account).getValue());
    }

    @Test
    void test05ReceptiveAccountKnowsRegisteredTransactions() {
        ReceptiveAccount account = new ReceptiveAccount();
        Deposit deposit = Deposit.createAndRegisterOn(100, account);
        Withdraw withdraw = Withdraw.createAndRegisterOn(50, account);

        assertTrue(account.hasRegistered(deposit));
        assertTrue(account.hasRegistered(withdraw));
    }

    @Test
    void test06ReceptiveAccountDoNotKnowNotRegisteredTransactions() {
        ReceptiveAccount account = new ReceptiveAccount();
        Deposit deposit = Deposit.createFor(100);
        Withdraw withdraw = Withdraw.createFor(50);

        assertFalse(account.hasRegistered(deposit));
        assertFalse(account.hasRegistered(withdraw));
    }

    @Test
    void test07AccountKnowsItsTransactions() {
        ReceptiveAccount account = new ReceptiveAccount();
        Deposit deposit = Deposit.createAndRegisterOn(100, account);


        assertEquals(1, account.transactions().size());
        assertTrue(account.transactions().contains(deposit));
    }
}