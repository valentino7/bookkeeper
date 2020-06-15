package org.apache.bookkeeper.test;

import org.apache.bookkeeper.client.LedgerFragmentReplicator;
import org.apache.bookkeeper.discover.BookieServiceInfo;
import org.apache.bookkeeper.feature.Feature;
import org.apache.bookkeeper.feature.FixedValueFeature;
import org.apache.bookkeeper.stats.AlertStatsLogger;
import org.apache.bookkeeper.util.HardLink;
import org.junit.Assert;
import org.junit.Test;

public class TestBookkeeperServer {
    @Test
    public void testProva() {
        Feature h = new FixedValueFeature("prova",Boolean.TRUE);

        Assert.assertEquals(Boolean.TRUE, h.isAvailable());
    }
}