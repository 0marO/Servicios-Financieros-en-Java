package modelo.AccountTransaction;

import java.util.concurrent.atomic.AtomicInteger;
import modelo.Reports.Report;
public abstract class AccountTransaction {
    int value;

    // VALUE
    public abstract int getValue();

    // BALANCE
    public abstract void affectBalance(AtomicInteger aBalance);

    // DOUBLE DISPATCH
    public abstract void acceptReport(Report aReport);
}
