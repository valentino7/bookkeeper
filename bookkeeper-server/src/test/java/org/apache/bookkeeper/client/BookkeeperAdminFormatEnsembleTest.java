package org.apache.bookkeeper.client;

import com.google.common.collect.Sets;
import org.apache.bookkeeper.conf.ServerConfiguration;
import org.apache.bookkeeper.net.BookieSocketAddress;
import org.apache.bookkeeper.test.BookKeeperClusterTestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.print.Book;
import java.net.UnknownHostException;
import java.util.*;

import static org.apache.bookkeeper.client.BookKeeperAdmin.*;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BookkeeperAdminFormatEnsembleTest extends BookKeeperClusterTestCase {

    private char marker;
    private Set<BookieSocketAddress> bookiesSrc;
    private List<BookieSocketAddress> ensemble;
    private static final int numOfBookies = 2;
    private final int lostBookieRecoveryDelayInitValue = 1800;
    private String expected;

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void after() throws Exception {
        super.stopZKCluster();
    }

    /*


    public BookAdminTest(Object o, Object o2) {
    }*/


    @Parameterized.Parameters
    public static Collection<?> getParameter() throws UnknownHostException {
        BookieSocketAddress bookie0 = new BookieSocketAddress("bookie0:3181");
        BookieSocketAddress bookie1 = new BookieSocketAddress("bookie1:3181");
        BookieSocketAddress bookie2 = new BookieSocketAddress("bookie2:3181");
        BookieSocketAddress bookie3 = new BookieSocketAddress("ciao:2323");



        List<BookieSocketAddress> ensembleOfSegment1 = new ArrayList<BookieSocketAddress>();
        ensembleOfSegment1.add(bookie0);
        ensembleOfSegment1.add(bookie1);
        ensembleOfSegment1.add(bookie2);

        List<BookieSocketAddress> ensembleOfSegment2 = new ArrayList<BookieSocketAddress>();
        ensembleOfSegment2.add(bookie3);
/*
        bookieSrc
                *            Source bookie that had a failure. We want to replicate the
                *            ledger fragments that were stored there.*/
        Set<BookieSocketAddress> bookiesSrc = Sets.newHashSet(bookie0);
        return Arrays.asList(new Object[][] {
                { ensembleOfSegment1, bookiesSrc, '*', "[bookie0:3181*, bookie1:3181 , bookie2:3181 ]"},
                { null, null, '*', ""},

        });
    }


    public BookkeeperAdminFormatEnsembleTest(List<BookieSocketAddress> ensemble, Set<BookieSocketAddress> bookiesSrc, char marker,
                                             String expected) {
        super(numOfBookies, 480);
        baseConf.setLostBookieRecoveryDelay(lostBookieRecoveryDelayInitValue);
        baseConf.setOpenLedgerRereplicationGracePeriod(String.valueOf(30000));
        setAutoRecoveryEnabled(true);

        this.ensemble = ensemble;
        this.bookiesSrc = bookiesSrc;
        this.marker = marker;
        this.expected = expected;
    }

    @Test
    public void formatEnsemble() {
        String result;
        try {
            result = BookKeeperAdmin.formatEnsemble(this.ensemble, this.bookiesSrc, this.marker);

        }catch(Exception e){
            e.printStackTrace();
            result = "";
        }
        assertEquals(this.expected,result);

    }

}