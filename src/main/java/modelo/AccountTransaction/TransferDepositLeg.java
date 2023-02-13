package modelo.AccountTransaction;

import java.util.concurrent.atomic.AtomicInteger;

import modelo.Accounts.ReceptiveAccount;
import modelo.Transfer.Transfer;
import modelo.Reports.Report;
public class TransferDepositLeg extends TransferLeg{

    public static TransferDepositLeg createOfATransferOn(Transfer aTransfer, ReceptiveAccount anAccount){
        TransferDepositLeg transaction = new TransferDepositLeg(aTransfer);
        anAccount.register(transaction);
        return transaction;
    }
    public TransferDepositLeg(Transfer aTransfer) {
        super(aTransfer);
    }

    public TransferLeg withdrawLeg(){
        return transfer.withdrawLeg();
    }
    //BALANCE
    @Override
    public void affectBalance(AtomicInteger aBalance) {
        aBalance.addAndGet(this.getValue());
    }
    //DD
    @Override
    public void acceptReport(Report aReport) {
        aReport.processTransferDepositLeg(this);
    }
}
