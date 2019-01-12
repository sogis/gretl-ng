package ch.so.agi.gretl.jobs;

import ch.so.agi.gretl.util.GradleVariable;
import ch.so.agi.gretl.util.IntegrationTestUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class CsvValidatorTest {
    @Test
    public void validationOk() throws Exception {
        GradleVariable[] gvs = null; // {GradleVariable.newGradleProperty(TestUtilSql.VARNAME_PG_CON_URI, TestUtilSql.PG_CON_URI)};
        IntegrationTestUtil.runJob("jobs/CsvValidator", gvs);
    }
    @Test
    public void validationFail() throws Exception {
        GradleVariable[] gvs = null; // {GradleVariable.newGradleProperty(TestUtilSql.VARNAME_PG_CON_URI, TestUtilSql.PG_CON_URI)};
        assertEquals(1,IntegrationTestUtil.runJob("jobs/CsvValidatorFail", gvs,new StringBuffer(),new StringBuffer()));
    }

}
