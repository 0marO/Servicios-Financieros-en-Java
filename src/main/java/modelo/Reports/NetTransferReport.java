package modelo.Reports;

import modelo.AccountTransaction.Deposit;
import modelo.AccountTransaction.TransferDepositLeg;
import modelo.AccountTransaction.TransferWithdrawLeg;
import modelo.AccountTransaction.Withdraw;
import modelo.Accounts.Account;
import modelo.Accounts.PortfolioAccount;
import modelo.Accounts.ReceptiveAccount;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class NetTransferReport extends Report{

    AtomicInteger result;

    //INSTANCE CREATION
    public static NetTransferReport generateFor(Account anAccount){
        return new NetTransferReport(anAccount);
    }

    private NetTransferReport(Account anAccount){
        result = new AtomicInteger();
        registerInResultTheTransfersValuesOf(anAccount);
    }

    //RESULT
    public int yieldResult(){
        return result.get();
    }

    //PRIVATE METHODS
    private void registerInResultTheTransfersValuesOf(Account anAccount){
        anAccount.iterateElementsForReport(this);
    }

    //DD
    @Override
    public void processDeposit(Deposit aDeposit) {
        return;
    }

    @Override
    public void processTransferDepositLeg(TransferDepositLeg aDepositLeg) {
        aDepositLeg.affectBalance(result);
    }

    @Override
    public void processWithdraw(Withdraw aWithdraw) {
        return;
    }

    @Override
    public void processTransferWithdrawLeg(TransferWithdrawLeg aWithdrawLeg) {
        aWithdrawLeg.affectBalance(result);
    }

    @Override
    public void processPortfolio(PortfolioAccount aPortfolio) {
        aPortfolio.iterateElementsForReport(this);
    }

    @Override
    public void processReceptiveAccount(ReceptiveAccount aReceptiveAccount) {
        aReceptiveAccount.iterateElementsForReport(this);
    }
}
