package modelo.AccountTransaction;
import modelo.Accounts.ReceptiveAccount;
import java.util.concurrent.atomic.AtomicInteger;
import modelo.Reports.Report;
public class Withdraw extends AccountTransaction{

    // INITIALIZATION
    public static Withdraw createFor(int aValue){
        return new Withdraw(aValue);
    }
    public static Withdraw createAndRegisterOn(int aValue, ReceptiveAccount anAccount){
        Withdraw aWithdraw = Withdraw.createFor(aValue);
        anAccount.register(aWithdraw);
        return aWithdraw;
    }
    public Withdraw(int aValue){
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
        aBalance.addAndGet(-value);
    }

    //DD
    @Override
    public void acceptReport(Report aReport) {
        aReport.processWithdraw(this);
    }
}
