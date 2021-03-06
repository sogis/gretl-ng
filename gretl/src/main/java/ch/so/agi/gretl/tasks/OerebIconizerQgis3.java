package ch.so.agi.gretl.tasks;

import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

import ch.so.agi.gretl.api.Connector;
import ch.so.agi.gretl.logging.GretlLogger;
import ch.so.agi.gretl.logging.LogEnvironment;
import ch.so.agi.gretl.util.TaskUtil;
import ch.so.agi.oereb.LegendEntry;
import ch.so.agi.oereb.OerebIconizer;

/**
 * Creates icons for OEREB-Rahmenmodell and saves them in a database table as bytea.
 * @deprecated
 * <p> Use {@link OerebIconizer} instead.
 *
 */
@Deprecated
public class OerebIconizerQgis3 extends DefaultTask {
    protected GretlLogger log;

    @Input
    public String sldUrl = null;
    
    @Input 
    public String legendGraphicUrl = null;
    
    @Input
    public Connector database = null;
    
    @Input
    public String dbQTable = null;
    
    @Input
    public String typeCodeAttrName = null;
    
    @Input
    public String typeCodeListAttrName = null;

    @Input
    public String typeCodeListValue = null;

    @Input 
    public String symbolAttrName = null;
    
    @Input @Optional
    public String legendTextAttrName = null;
    
    @Input
    @Optional
    public boolean useCommunalTypeCodes = false;

    @TaskAction
    public void createAndSaveSymbols() {
        log = LogEnvironment.getLogger(OerebIconizerQgis3.class);

        if (sldUrl == null) {
            throw new IllegalArgumentException("sldUrl must not be null");
        }
        if (legendGraphicUrl == null) {
            throw new IllegalArgumentException("legendGraphicUrl must not be null");
        }
        if (database == null) {
            throw new IllegalArgumentException("database must not be null");
        }
        if (dbQTable == null) {
            throw new IllegalArgumentException("dbQTable must not be null");
        }
        if (typeCodeAttrName == null) {
            throw new IllegalArgumentException("typeCodeAttrName must not be null");
        }
        if (typeCodeListAttrName == null) {
            throw new IllegalArgumentException("typeCodeListAttrName must not be null");
        }
        if (typeCodeListValue == null) {
            throw new IllegalArgumentException("typeCodeListValue must not be null");
        }
        if (symbolAttrName == null) {
            throw new IllegalArgumentException("symbolAttrName must not be null");
        }
                            
        try {
            String qualifiedTableName[] = dbQTable.split("\\."); // since oereb2-iconizer
            
            OerebIconizer iconizer = new OerebIconizer();
            List<LegendEntry> legendEntries = iconizer.getSymbols("QGIS3", sldUrl, legendGraphicUrl);
            int count = iconizer.updateSymbols(legendEntries, database.getDbUri(), database.getDbUser(), database.getDbPassword(), qualifiedTableName[0], qualifiedTableName[1], typeCodeAttrName, typeCodeListAttrName, typeCodeListValue, symbolAttrName, useCommunalTypeCodes);
            log.info("Updated " + String.valueOf(count) + " record(s).");
        } catch (Exception e) {
            log.error("Exception in OerebIconizerQgis3 task.", e);
            GradleException ge = TaskUtil.toGradleException(e);
            throw ge;
        }
    }
}
