package modelo.AccountTransaction;


import modelo.Accounts.ReceptiveAccount;
import modelo.Reports.Report;

import java.util.concurrent.atomic.AtomicInteger;

public class Deposit extends AccountTransaction{

    // INITIALIZATION
    public static Deposit createFor(int aValue){
        return new Deposit(aValue);
    }
    public static Deposit createAndRegisterOn(int aValue, ReceptiveAccount anAccount){
        Deposit aDeposit = Deposit.createFor(aValue);
        anAccount.register(aDeposit);
        return aDeposit;
    }
    public Deposit(int aValue){
        value = aValue;
    }

    //VALUE
    @Override
    public int getValue() {
        return value;
    }

    //BALANCE
    @Override
    public void affectBalance(AtomicInteger aBalance) {
        aBalance.addAndGet(value);
    }

    //DD
    @Override
    public void acceptReport(Report aReport) {
        aReport.processDeposit(this);
    }
}
