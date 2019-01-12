package ch.so.agi.gretl.jobs;

import ch.so.agi.gretl.util.GradleVariable;
import ch.so.agi.gretl.util.IntegrationTestUtil;

import org.junit.Test;
import static org.junit.Assert.*;

public class IliValidatorTest {
    @Test
    public void validationOk() throws Exception {
        GradleVariable[] gvs = null;
        IntegrationTestUtil.runJob("src/integrationTest/jobs/iliValidator", gvs);
    }

    @Test
    public void validationFail() throws Exception {
        GradleVariable[] gvs = null;
        assertEquals(1,
                IntegrationTestUtil.runJob("jobs/iliValidatorFail", gvs, new StringBuffer(), new StringBuffer()));
    }
}
