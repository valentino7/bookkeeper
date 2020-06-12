import org.apache.bookkeeper.client.BookKeeper;
import org.apache.bookkeeper.client.LedgerEntry;
import org.junit.Test;

public class ModulesTest {

    @Test
    public void integrationTest1() {
        LedgerEntryntryImpl module1 = new LedgerEntry();
        module1.coveredByUnitTest();    }

}