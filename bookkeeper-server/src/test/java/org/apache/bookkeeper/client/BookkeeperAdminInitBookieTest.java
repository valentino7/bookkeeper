package org.apache.bookkeeper.client;

import org.apache.bookkeeper.conf.ServerConfiguration;
import org.apache.bookkeeper.test.ZooKeeperCluster;
import org.apache.bookkeeper.test.ZooKeeperClusterUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.IOException;
import java.util.*;


@RunWith(Parameterized.class)
public class BookkeeperAdminInitBookieTest{



    private ZooKeeperCluster zk;
    private boolean expected;
    private ServerConfiguration confTest;
    String path = "/test";

    @Before
    public void setUp() throws Exception {
        //creo cluster di zookkeeper
//        super.setUp();
        //E' necessario inizializzare un cluster prima di testare il suo nuke
        System.out.println("Inizializzazione");
        //Il cluster di metadati è gestito con ZooKeeper, instanziamo quindi un cluster Zookeeper
        try {
            zk = new ZooKeeperClusterUtil(5);
            zk.startCluster();
            System.out.println("Cluster Zookeeper inizializzato e running");
            //E' necessario specificare lo URI dove vengono salvati i ledgers
            if(confTest != null) confTest.setMetadataServiceUri(zk.getMetadataServiceUri());
            //Inizializazione del nuovo cluster
            System.out.println("Cluster inizializzato");
        }
        catch (Exception e) { System.out.println("Failure nell'inizializzazione del cluster"); e.printStackTrace();
        }

    }


    @After
    public void after() throws Exception {
        zk.killCluster();
    }


    @Parameterized.Parameters
    public static Collection<?> getParameter() throws IOException {
//        ServerConfiguration conf = new ServerConfiguration();


//        ServerConfiguration conf = new ServerConfiguration();
//        conf.setMetadataServiceUri("zk://127.0.0.1/path/to/ledgers");

        ServerConfiguration conf = new ServerConfiguration();
//        conf.setJournalFlushWhenQueueEmpty(true);
        conf.setAllowLoopback(true);

//        conf.setJournalFormatVersionToWrite(5);
//        conf.setAllowEphemeralPorts(true);
//        conf.setBookiePort(0);
//        conf.setGcWaitTime(1000);
//        conf.setDiskUsageThreshold(0.999f);
//        conf.setDiskUsageWarnThreshold(0.99f);
//        conf.setAllocatorPoolingPolicy(PoolingPolicy.UnpooledHeap);
//        conf.setProperty(DbLedgerStorage.WRITE_CACHE_MAX_SIZE_MB, 4);
//        conf.setProperty(DbLedgerStorage.READ_AHEAD_CACHE_MAX_SIZE_MB, 4);

//        FileUtils.cleanDirectory(new File("/tmp/bk-txn"));


        return Arrays.asList(new Object[][] {
                {conf, true},
                {null, false}
        });
    }


    public BookkeeperAdminInitBookieTest(ServerConfiguration conf, boolean expected) {
//        super(numOfBookies, 480);
//        baseConf.setLostBookieRecoveryDelay(lostBookieRecoveryDelayInitValue);
//        baseConf.setOpenLedgerRereplicationGracePeriod(String.valueOf(30000));
//        setAutoRecoveryEnabled(true);
        this.confTest = conf;
        this.expected = expected;
    }

    /**
     * Initializes bookie, by making sure that the journalDir, ledgerDirs and
     * indexDirs are empty and there is no registered Bookie with this BookieId.
     */
    @Test
    public void initBookie()  {
        boolean valid;
        try{
            valid = BookKeeperAdmin.initBookie(confTest);
        }catch(Exception e){
            e.printStackTrace();
            valid = false;
        }
        Assert.assertEquals(this.expected, valid);


    }

}
