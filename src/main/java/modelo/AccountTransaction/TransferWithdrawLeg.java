package modelo.AccountTransaction;

import java.util.concurrent.atomic.AtomicInteger;

import modelo.Accounts.ReceptiveAccount;
import modelo.Transfer.Transfer;
import modelo.Reports.Report;
public class TransferWithdrawLeg extends TransferLeg{

    public static TransferWithdrawLeg createOfATransferOn(Transfer aTransfer, ReceptiveAccount anAccount){
        TransferWithdrawLeg transaction = new TransferWithdrawLeg(aTransfer);
        anAccount.register(transaction);
        return transaction;
    }
    public TransferWithdrawLeg(Transfer aTransfer) {
        super(aTransfer);
    }

    public TransferLeg depositLeg(){
        return transfer.depositLeg();
    }
    //BALANCE
    @Override
    public void affectBalance(AtomicInteger aBalance) {
        aBalance.addAndGet(-value);
    }
    //DD
    @Override
    public void acceptReport(Report aReport) {
        aReport.processTransferWithdrawLeg(this);
    }
}
