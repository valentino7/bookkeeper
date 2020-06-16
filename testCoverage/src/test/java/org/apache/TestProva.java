package test.java.org.apache;


import org.apache.bookkeeper.feature.Feature;
import org.apache.bookkeeper.feature.FixedValueFeature;
import org.junit.Assert;
import org.junit.Test;

public class TestProva {
    @Test
    public void testProva() {
        Feature h = new FixedValueFeature("prova",Boolean.TRUE);

        Assert.assertEquals(Boolean.TRUE, h.isAvailable());
    }
}
