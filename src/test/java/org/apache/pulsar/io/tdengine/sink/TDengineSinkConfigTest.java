
package org.apache.pulsar.io.tdengine.sink;

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.io.FileUtils.getFile;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * TDengineSinkConfig test
 */
public class TDengineSinkConfigTest {

    @Test
    public final void loadFromYamlFileTest() throws IOException {
        File yamlFile = getFile("sinkConfig.yaml");
        String path = yamlFile.getAbsolutePath();
        TDengineSinkConfig config = TDengineSinkConfig.load(path);
        System.out.println(config.getJdbcUrl());
        assertNotNull(config);
        assertEquals("jdbc:TAOS://localhost:6030/db", config.getJdbcUrl());
        assertEquals("root", config.getUserName());
        assertEquals("taosdata", config.getPassword());
        assertEquals("test_jdbc", config.getTableName());
    }

    @Test
    public final void loadFromMapTest() throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jdbcUrl", "jdbc:TAOS://localhost:6030/db");
        map.put("userName", "root");
        map.put("password", "taosdata");
        map.put("tableName", "test_jdbc");


        TDengineSinkConfig config = TDengineSinkConfig.load(map);
        assertNotNull(config);
        assertEquals("jdbc:TAOS://localhost:6030/db", config.getJdbcUrl());
        assertEquals("root", config.getUserName());
        assertEquals("taosdata", config.getPassword());
        assertEquals("test_jdbc", config.getTableName());
    }

    @Test
    public final void validValidateTest() throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jdbcUrl", "jdbc:TAOS://localhost:6030/db");
        map.put("userName", "root");
        map.put("password", "taosdata");
        map.put("tableName", "test_jdbc");

        TDengineSinkConfig config = TDengineSinkConfig.load(map);
        config.validate();
    }

    @Test
    public final void missingValidValidateTableNameTest() throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jdbcUrl", "jdbc:TAOS://localhost:6030/db");
        map.put("userName", "root");
        map.put("password", "taosdata");
        map.put("tableName", "test_jdbc");

        TDengineSinkConfig config = TDengineSinkConfig.load(map);
        config.validate();
    }


    private File getFile(String name) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(name).getFile());
    }

    @Test
    public void testCreate() throws IOException, SQLException, ClassNotFoundException {
        File yamlFile = getFile("sinkConfig.yaml");
        String path = yamlFile.getAbsolutePath();
        TDengineSinkConfig config = TDengineSinkConfig.load(path);

        TSDBSession tsdbSession = new TSDBSession();
        tsdbSession.create(config);
    }
}
