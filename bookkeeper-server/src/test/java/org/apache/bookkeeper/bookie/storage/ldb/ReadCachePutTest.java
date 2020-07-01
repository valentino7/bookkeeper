package org.apache.bookkeeper.bookie;

import com.google.common.collect.Sets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import org.apache.bookkeeper.bookie.storage.ldb.ReadCache;
import org.apache.bookkeeper.net.BookieSocketAddress;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.UnknownHostException;
import java.util.*;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class    ReadCachePutTest {

    private ReadCache cache = null;
    private ByteBuf entry;
    private long ledgerId;
    private long entryId;


    @Parameterized.Parameters
    public static Collection<?> getParameter() {

        return Arrays.asList(new Object[][] {
                //grandezza buffer per numero entry
                { -1, 0, null},
                { 0, -1, Unpooled.wrappedBuffer(new byte[64])},
                { 0, 0, Unpooled.wrappedBuffer(new byte[64])}
        });
    }

    @After
    public void after(){
        cache.close();
    }

    @Before
    public void before(){
        this.cache = new ReadCache(UnpooledByteBufAllocator.DEFAULT, 10 * 1024);
    }

    public ReadCachePutTest(long ledgerId, long entryId, ByteBuf entry){
        this.ledgerId = ledgerId;
        this.entryId = entryId;
        this.entry = entry;
    }



    @Test
    public void put(){
        try {
            this.cache.put(this.entryId, this.entryId, this.entry);
            assertEquals(1, this.cache.count());
            assertEquals(this.entry, cache.get(this.ledgerId, this.entryId));
            return;
        }
        catch(Exception e) {
            assertTrue(Boolean.TRUE);
        }
    }

}