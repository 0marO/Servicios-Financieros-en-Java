package modelo.Transfer;

import modelo.AccountTransaction.TransferDepositLeg;
import modelo.AccountTransaction.TransferWithdrawLeg;
import modelo.Accounts.ReceptiveAccount;

public class Transfer {

    private int value;
    private TransferDepositLeg depositLeg;
    private TransferWithdrawLeg withdrawLeg;

    private static boolean assertTheAmountCanBeTransferedToDestinationFromOrigin(int anAmount,
                                                                              ReceptiveAccount destinationAccount,
                                                                              ReceptiveAccount originAccount) {
        return assertCanTransferToDestinationFromOrigin(destinationAccount,originAccount)
                && assertCanTransferTheAmount(anAmount);
    }

    private static boolean assertCanTransferTheAmount(int anAmount) {
        return anAmount != 0;
    }

    private static boolean assertCanTransferToDestinationFromOrigin(ReceptiveAccount destinationAccount, ReceptiveAccount originAccount) {
        return originAccount != destinationAccount;
    }

    public static Transfer createFor(int anAmount,
                                     ReceptiveAccount originAccount,
                                     ReceptiveAccount destinationAccount) throws Exception{

        if (!assertTheAmountCanBeTransferedToDestinationFromOrigin(anAmount, destinationAccount,originAccount))
            throw new Exception("Cannot make this transaction!"); // Falta especificación de errores

        Transfer transfer = new Transfer();
        TransferWithdrawLeg withdrawLeg = TransferWithdrawLeg.createOfATransferOn(transfer,originAccount);
        TransferDepositLeg depositLeg = TransferDepositLeg.createOfATransferOn(transfer,destinationAccount);
        transfer.conformTransfer(anAmount,withdrawLeg,depositLeg);
        return transfer;
    }



    private Transfer(){
    }
    private void conformTransfer(int initialValue,
                                 TransferWithdrawLeg aTransferWithdrawLeg,
                                 TransferDepositLeg aTransferDepositLeg){
        value = initialValue;
        withdrawLeg = aTransferWithdrawLeg;
        depositLeg = aTransferDepositLeg;
    }

    public int getValue() {
        return value;
    }
    public TransferDepositLeg depositLeg() {
        return depositLeg;
    }
    public TransferWithdrawLeg withdrawLeg() {
        return withdrawLeg;
    }



}
