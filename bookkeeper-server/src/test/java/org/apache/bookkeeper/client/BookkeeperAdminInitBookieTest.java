package org.apache.bookkeeper.client;

import org.apache.bookkeeper.conf.BookKeeperClusterTestCase;
import org.apache.bookkeeper.conf.ServerConfiguration;
import org.apache.bookkeeper.test.ZooKeeperCluster;
import org.apache.bookkeeper.test.ZooKeeperClusterUtil;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@RunWith(Parameterized.class)
public class BookkeeperAdminInitBookieTest {


    private boolean journalDir;
    private boolean ledgeDir;
    private boolean indexDir;
    private ZooKeeperCluster zk;
    private boolean expected;
    private ServerConfiguration confTest;
    String path = "/test";


    private static final int numOfBookies = 2;
    private final int lostBookieRecoveryDelayInitValue = 1800;

    @Before
    public void setUp() throws Exception {


        //creo cluster di zookkeeper
//        super.setUp();
        //E' necessario inizializzare un cluster prima di testare il suo nuke
//        System.out.println("Inizializzazione");
        //Il cluster di metadati è gestito con ZooKeeper, instanziamo quindi un cluster Zookeeper
        try {
            zk = new ZooKeeperClusterUtil(3);
            zk.startCluster();

            System.out.println("Cluster Zookeeper inizializzato e running");
            //E' necessario specificare lo URI dove vengono salvati i ledgers
            if(confTest != null) confTest.setMetadataServiceUri(zk.getMetadataServiceUri());
            //Inizializazione del nuovo cluster
            System.out.println("Cluster inizializzato");
        }
        catch (Exception e) { System.out.println("Failure nell'inizializzazione del cluster"); e.printStackTrace();
        }

//        deleteDirs(confTest.getLedgerDirs());
//        deleteDirs(confTest.getIndexDirs());



    }

//
   @After
    public void after() throws IOException {

       if(this.journalDir == false)
           deleteDirs(confTest.getJournalDirs());

        try {
            zk.killCluster();

        }catch (Exception e){
            e.printStackTrace();
        }


//       super.tearDown();


   }

   public void deleteDirs(File[] dirs) throws IOException {
//       for (File dir : dirs) {
//           dir.setWritable(true);
////            System.out.println(new File("/home/valentino/IdeaProjects/bookkeeper"+dir.getCanonicalPath()+"/ciao.txt"));
//
//           //file name only
//           File file=null;
//           try {
//               System.out.println(dir.getAbsolutePath());
//               System.out.println(dir.length());
//               Files.delete(Paths.get(dir.getAbsolutePath() ));
//               file = new File(dir.getAbsolutePath() + "/fill.txt");
//               file.delete();
//
//           }catch(Exception e){
//               e.printStackTrace();
//           }
//
//       }
       for (File dir : dirs) {
           FileUtils.deleteDirectory(dir);

       }

   }

    @Parameterized.Parameters
    public static Collection<?> getParameter() throws IOException {
//        ServerConfiguration conf = new ServerConfiguration();


//        ServerConfiguration conf = new ServerConfiguration();
//        conf.setMetadataServiceUri("zk://127.0.0.1/path/to/ledgers");

        ServerConfiguration conf = new ServerConfiguration();
        ServerConfiguration confJournalNotEMpty = new ServerConfiguration();
//        conf.setJournalFlushWhenQueueEmpty(true);
        conf.setAllowLoopback(true);
        confJournalNotEMpty.setAllowLoopback(true);

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
        boolean journalEmpty = true;
        boolean journalNotEmpty = false;





        return Arrays.asList(new Object[][] {
                {conf, journalEmpty, true},
                {null, journalEmpty, false},
                {conf, journalNotEmpty, false},

        });
    }


    public BookkeeperAdminInitBookieTest(ServerConfiguration conf, boolean journalDir, boolean expected) throws IOException {
//        super(numOfBookies, 480);
//        baseConf.setLostBookieRecoveryDelay(lostBookieRecoveryDelayInitValue);
//        baseConf.setOpenLedgerRereplicationGracePeriod(String.valueOf(30000));
//        setAutoRecoveryEnabled(true);


//        super(numOfBookies, 480);
//        baseConf.setLostBookieRecoveryDelay(lostBookieRecoveryDelayInitValue);
//        baseConf.setOpenLedgerRereplicationGracePeriod(String.valueOf(30000));
//        setAutoRecoveryEnabled(true);


        this.confTest = conf;
        this.journalDir = journalDir;
        this.expected = expected;
        if(this.journalDir == false)
            deleteDirs(confTest.getJournalDirs());


    }

    /**
     * Initializes bookie, by making sure that the journalDir, ledgerDirs and
     * indexDirs are empty and there is no registered Bookie with this BookieId.
     */
    @Test
    public void initBookie() {
        boolean valid;


        try{
            if(this.journalDir == false) {
                addFileFileDir(this.confTest.getJournalDirs());
            }
            this.confTest.setIndexDirName(new String[]{"/testIndex/fill.txt"});
//
            valid = BookKeeperAdmin.initBookie(confTest);
        }catch(Exception e){
            e.printStackTrace();
            valid = false;
        }
        Assert.assertEquals(this.expected, valid);
    }

    public void addFileFileDir(File[] dirs) throws IOException {
        for (File dir : dirs) {
            dir.setWritable(true);
            dir.setExecutable(true);
            //file name only
            File file = new File(dir.getAbsolutePath()+"/prova.txt");
            System.out.println(file.canWrite());
            if(file.createNewFile()){
                System.out.println("file.txt File Created in Project root directory");
            }else System.out.println("File file.txt already exists in the project root directory");

        }


    }

}
