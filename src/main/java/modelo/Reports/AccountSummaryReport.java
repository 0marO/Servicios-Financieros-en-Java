package modelo.Reports;

import modelo.AccountTransaction.*;
import modelo.Accounts.Account;
import modelo.Accounts.PortfolioAccount;
import modelo.Accounts.ReceptiveAccount;

import java.util.LinkedList;

public class AccountSummaryReport extends Report{

    private LinkedList<String> result;

    //INSTANCE CREATION
    public static AccountSummaryReport generateFor(Account anAccount){
        return new AccountSummaryReport(anAccount);
    }
    private AccountSummaryReport(Account anAccount){
        result = new LinkedList<String>();
        registerInResultTheTransactionStringsOf(anAccount);
    }

    //AUX TO INSTANCE CREATION
    private void registerInResultTheTransactionStringsOf(Account anAccount){
        anAccount.iterateElementsForReport(this);
        result.add(parsedStringForBalanceOf(anAccount));
    }

    private String parsedStringForBalanceOf(Account anAccount){
        return new String("Balance = "+ anAccount.balance());
    }

    //RESULT
    public LinkedList<String> yieldResult(){
        return result;
    }

    //DD
    @Override
    public void processDeposit(Deposit aDeposit) {
        result.add(parsedDepositStringFor(aDeposit));
    }

    @Override
    public void processTransferDepositLeg(TransferDepositLeg aDepositLeg) {
        result.add(parsedTransferDepositLegStringFor(aDepositLeg));
    }

    @Override
    public void processWithdraw(Withdraw aWithdraw) {
        result.add(parsedWithdrawStringFor(aWithdraw));
    }

    @Override
    public void processTransferWithdrawLeg(TransferWithdrawLeg aWithdrawLeg) {
        result.add(parsedTransferWithdrawLegStringFor(aWithdrawLeg));
    }

    @Override
    public void processPortfolio(PortfolioAccount aPortfolio) {
        aPortfolio.iterateElementsForReport(this);
    }

    @Override
    public void processReceptiveAccount(ReceptiveAccount aReceptiveAccount) {
        aReceptiveAccount.iterateElementsForReport(this);
    }

    //PRIVATE PARSING STRINGS METHODS
    private String parsedDepositStringFor(Deposit aDeposit){
        return new String("Depósito por "+ aDeposit.getValue()+ "\n");
    }
    private String parsedWithdrawStringFor(Withdraw aWithdraw){
        return new String("Extracción por "+ aWithdraw.getValue()+ "\n");
    }
    private String parsedTransferWithdrawLegStringFor(TransferWithdrawLeg aWithdrawLeg){
        return new String("Salida por transferencia de "+ aWithdrawLeg.getValue()+ "\n");
    }
    private String parsedTransferDepositLegStringFor(TransferDepositLeg aDepositLeg){
        return new String("Entrada por transferencia de "+ aDepositLeg.getValue()+ "\n");
    }
}
