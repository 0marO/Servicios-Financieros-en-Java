package modelo.Reports;

import modelo.AccountTransaction.Deposit;
import modelo.AccountTransaction.TransferDepositLeg;
import modelo.AccountTransaction.TransferWithdrawLeg;
import modelo.AccountTransaction.Withdraw;
import modelo.Accounts.Account;
import modelo.Accounts.PortfolioAccount;
import modelo.Accounts.ReceptiveAccount;

// (VISITOR CLASS)
public abstract class Report {

    // como no puedo poner un return type generico para "yieldResult" este método será implementado
    // con tipos de dato de retorno diferentes en cada subclase de reporte.


    //DD
    public abstract void processDeposit(Deposit aDeposit);
    public abstract void processTransferDepositLeg(TransferDepositLeg aDepositLeg);
    public abstract void processWithdraw(Withdraw aWithdraw);
    public abstract void processTransferWithdrawLeg(TransferWithdrawLeg aWithdrawLeg);
    public abstract void processPortfolio(PortfolioAccount aPortfolio);
    public abstract void processReceptiveAccount(ReceptiveAccount aReceptiveAccount);


}
