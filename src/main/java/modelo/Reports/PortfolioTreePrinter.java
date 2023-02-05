package modelo.Reports;

import modelo.AccountTransaction.Deposit;
import modelo.AccountTransaction.TransferDepositLeg;
import modelo.AccountTransaction.TransferWithdrawLeg;
import modelo.AccountTransaction.Withdraw;
import modelo.Accounts.PortfolioAccount;
import modelo.Accounts.ReceptiveAccount;

import java.util.LinkedList;

public class PortfolioTreePrinter extends Report {

    LinkedList<String> result;

    //INSTANCE CREATION
    public static PortfolioTreePrinter generateFor(PortfolioAccount aPortfolio) {
        return new PortfolioTreePrinter(aPortfolio);
    }

    private PortfolioTreePrinter(PortfolioAccount aPortfolio) {
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
        result.add(aPortfolio.getName());
    }

    @Override
    public void processReceptiveAccount(ReceptiveAccount aReceptiveAccount) {
        result.add(aReceptiveAccount.getName());
    }

    //PRIVATE

    private void indentTemporaryResult(){
        LinkedList<String> indentedNames = new LinkedList<>();
        result.forEach(aName -> indentedNames.add(" "+aName));
        result = indentedNames;
    }
}
