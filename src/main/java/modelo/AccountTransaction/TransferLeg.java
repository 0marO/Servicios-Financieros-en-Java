package modelo.AccountTransaction;

import modelo.Accounts.Account;
import modelo.Accounts.ReceptiveAccount;
import modelo.Transfer.Transfer;
import modelo.Reports.Report;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class TransferLeg extends AccountTransaction{

    Transfer transfer;

    public static AccountTransaction createOfATransferOn(Transfer aTransfer, ReceptiveAccount anAccount){
        return  null;
    }
    public TransferLeg(Transfer aTransfer){
        transfer = aTransfer;
    }

    //VALUE
    @Override
    public int getValue(){
        return transfer.getValue();
    }

}
