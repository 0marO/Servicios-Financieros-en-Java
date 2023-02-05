package modelo.Reports;

import modelo.AccountTransaction.Deposit;
import modelo.AccountTransaction.TransferDepositLeg;
import modelo.AccountTransaction.TransferWithdrawLeg;
import modelo.AccountTransaction.Withdraw;
import modelo.Accounts.PortfolioAccount;
import modelo.Accounts.ReceptiveAccount;

import java.util.LinkedList;

public class PortfolioDetailedTreePrinter extends Report{

    private LinkedList<String> result;

    //INSTANCE CREATION
    public static PortfolioDetailedTreePrinter generateFor(PortfolioAccount aPortfolio){
        return new PortfolioDetailedTreePrinter(aPortfolio);
    }

    private PortfolioDetailedTreePrinter(PortfolioAccount aPortfolio){
        result = new LinkedList<>();
        aPortfolio.accept(this);
    }

    //DD
    @Override
    public void processDeposit(Deposit aDeposit) {
    }

    @Override
    public void processTransferDepositLeg(TransferDepositLeg aDepositLeg) {
    }

    @Override
    public void processWithdraw(Withdraw aWithdraw) {
    }

    @Override
    public void processTransferWithdrawLeg(TransferWithdrawLeg aWithdrawLeg) {
    }

    @Override
    public void processPortfolio(PortfolioAccount aPortfolio) {
        aPortfolio.iterateElementsForReport(this);
        this.indentTemporaryResult();
        result.addFirst(aPortfolio.getName());
        this.generateAndAddToResultIndentedStringOfBalanceFrom(aPortfolio);
    }

    @Override
    public void processReceptiveAccount(ReceptiveAccount aReceptiveAccount) {
        result.add(aReceptiveAccount.getName());
        this.indentAccountSummaryReportResultOf(aReceptiveAccount);
    }

    //PRIVATE METHODS
    private LinkedList<String> accountSummaryReportResultFor(ReceptiveAccount aReceptiveAccount){
        return AccountSummaryReport.generateFor(aReceptiveAccount).yieldResult();
    }
    private void generateAndAddToResultIndentedStringOfBalanceFrom(PortfolioAccount aPortfolio){
        result.add("  Balance = " + aPortfolio.balance());
    }
    private void indentAccountSummaryReportResultOf(ReceptiveAccount aReceptiveAccount){
        this.accountSummaryReportResultFor(aReceptiveAccount).forEach(aName ->
                                                            result.add(" " + aName));
    }

    private void indentTemporaryResult(){
        LinkedList<String> indentedNamesAndTransactions = new LinkedList<>();
        result.forEach(aName -> indentedNamesAndTransactions.add(" " + aName));
        result = indentedNamesAndTransactions;
    }
}
