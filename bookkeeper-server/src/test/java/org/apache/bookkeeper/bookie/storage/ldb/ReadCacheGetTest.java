package org.apache.bookkeeper.bookie.storage.ldb;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import org.apache.bookkeeper.bookie.storage.ldb.ReadCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ReadCacheGetTest {

    private ReadCache cache = null;
    private ByteBuf entry;
    private long ledgerId;
    private long entryId;


    @Parameterized.Parameters
    public static Collection<?> getParameter() {

        return Arrays.asList(new Object[][] {
                //grandezza buffer per numero entry
                { 0, 1, Unpooled.wrappedBuffer(new byte[64])}
        });
    }

    @After
    public void after(){
        cache.close();
    }

    @Before
    public void before(){
        this.cache = new ReadCache(UnpooledByteBufAllocator.DEFAULT, 10 * 1024);
        this.cache.put(this.entryId, this.entryId, this.entry);
    }

    public ReadCacheGetTest(long ledgerId, long entryId, ByteBuf entry){
        this.ledgerId = ledgerId;
        this.entryId = entryId;
        this.entry = entry;
    }



    @Test
    public void get(){
        assertEquals(this.entry, this.cache.get(this.entryId, this.entryId));
    }

}