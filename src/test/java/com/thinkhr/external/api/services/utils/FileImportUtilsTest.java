package com.thinkhr.external.api.services.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.thinkhr.external.api.exception.ApplicationException;
import com.thinkhr.external.api.exception.MessageResourceHandler;
import com.thinkhr.external.api.response.APIMessageUtil;

/**
 * Junit Tests to verify all methods of FileImportUtils.java
 * @author Admin
 *
 */
@RunWith(SpringRunner.class)
public class FileImportUtilsTest {
    @Mock
    private MessageResourceHandler resourceHandler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * To verify getMissingHeaders when presentHeaders and requiredHeaders with 
     * values should return some missing headers
     *  
     */
    @Test
    public void testGetMissingHeaders_1() {
        String[] headersInCSV = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "PRODUCER", "INDUSTRY" };
        String[] requiredHeaders = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "ADDRESS", "CITY", "PRODUCER", "INDUSTRY" };

        String[] missingHeaders = FileImportUtil.getMissingHeaders(headersInCSV, requiredHeaders);

        assertEquals(2, missingHeaders.length);
    }

    /**
     * To verify getMissingHeaders when presentHeaders and requiredHeaders
     *  with values returning no missing headers
     *  
     */
    @Test
    public void testGetMissingHeaders_2() {
        String[] headersInCSV = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "PRODUCER", "INDUSTRY", "ADDRESS", "CITY" };
        String[] requiredHeaders = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "ADDRESS", "CITY", "PRODUCER", "INDUSTRY" };

        String[] missingHeaders = FileImportUtil.getMissingHeaders(headersInCSV, requiredHeaders);

        assertEquals(0, missingHeaders.length);
    }

    /**
     * To verify getMissingHeaders when presentHeaders = null , 
     * and requiredHeaders with some values
     *  
     */
    @Test
    public void testGetMissingHeaders_3() {
        String[] headersInCSV = null;
        String[] requiredHeaders = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "ADDRESS", "CITY", "PRODUCER", "INDUSTRY" };

        String[] missingHeaders = FileImportUtil.getMissingHeaders(headersInCSV, requiredHeaders);

        assertEquals(requiredHeaders.length, missingHeaders.length);
    }

    /**
     * To verify getMissingHeaders when presentHeaders 
     * with some values , requiredHeaders = null
     *  
     */
    @Test
    public void testGetMissingHeaders_4() {
        String[] headersInCSV = { "CLIENT_NAME", "DISPLAY_NAME", "PRODUCER", "INDUSTRY", "ADDRESS", "CITY" };
        String[] requiredHeaders = null;

        String[] missingHeaders = FileImportUtil.getMissingHeaders(headersInCSV, requiredHeaders);

        assertEquals(0, missingHeaders.length);
    }

    /**
     * To verify getMissingHeaders when presentHeaders 
     * = null , requiredHeaders = null
     *  
     */
    @Test
    public void testGetMissingHeaders_5() {
        String[] headersInCSV = null;
        String[] requiredHeaders = null;

        String[] missingHeaders = FileImportUtil.getMissingHeaders(headersInCSV, requiredHeaders);

        assertEquals(0, missingHeaders.length);
    }

    /**
     * To verify hasValidExtension when fileName has extension from validExtention
     *  
     */
    @Test
    public void testHasValidExtension_1() {
        String fileName = "testFile.exe";
        String[] validExtensions = new String[] { "txt", "exe" };

        assertTrue(FileImportUtil.hasValidExtension(fileName, validExtensions));
    }

    /**
     * To verify hasValidExtension when fileName does not have extension from validExtention
     *  
     */
    @Test
    public void testHasValidExtension_2() {
        String fileName = "testFile.exe";
        String[] validExtensions = new String[] { "txt", "csv" };

        assertFalse(FileImportUtil.hasValidExtension(fileName, validExtensions));
    }

    /**
     * To verify hasValidExtension when fileName is null
     *  
     */
    @Test
    public void testHasValidExtension_3() {
        String fileName = null;
        String[] validExtensions = new String[] { "txt", "csv" };

        assertFalse(FileImportUtil.hasValidExtension(fileName, validExtensions));
    }

    /**
     * To verify hasValidExtension when validExtention is null
     *  
     */
    @Test
    public void testHasValidExtension_4() {
        String fileName = "testFile.csv";
        String[] validExtensions = null;

        assertFalse(FileImportUtil.hasValidExtension(fileName, validExtensions));
    }

    /**
     * To verify populateColumnValues 
     * when all values in columns have mapping in columnToHeaderMap
     *  
     */
    @Test
    public void testPopulateColumnValues_1() {

        String[] columns = new String[] { "companyName", "companyPhone", "address", "city", "state" };
        Object[] columnValues = new Object[columns.length];
        ;
        Map<String, String> columnToHeaderMap = new HashMap<String, String>();
        columnToHeaderMap.put("companyName", "CLIENT_NAME");
        columnToHeaderMap.put("companyPhone", "CLIENT_PHONE");
        columnToHeaderMap.put("address", "ADDRESS");
        columnToHeaderMap.put("city", "CITY");
        columnToHeaderMap.put("state", "STATE");

        String[] splitValues = new String[] { "92134567", "Plasia", "MP", "Pepcus", "Indore" };
        Map<String, Integer> headerIndexMap = new HashMap<String, Integer>();
        headerIndexMap.put("CLIENT_NAME", 3);
        headerIndexMap.put("CLIENT_PHONE", 0);
        headerIndexMap.put("ADDRESS", 1);
        headerIndexMap.put("CITY", 4);
        headerIndexMap.put("STATE", 2);

        FileImportUtil.populateColumnValues(columnValues, columns, columnToHeaderMap, splitValues, headerIndexMap);
        assertEquals("Pepcus", columnValues[0]);
        assertEquals("92134567", columnValues[1]);
        assertEquals("Plasia", columnValues[2]);
        assertEquals("Indore", columnValues[3]);
        assertEquals("MP", columnValues[4]);
    }

    /**
     * To verify populateColumnValues 
     * when some values in columns not having mapping in columnToHeaderMap
     *  
     */
    @Test
    public void testPopulateColumnValues_2() {

        String[] columns = new String[] { "companyName", "companyPhone", "address", "city", "state" };
        Object[] columnValues = new Object[columns.length];
        ;
        Map<String, String> columnToHeaderMap = new HashMap<String, String>();
        columnToHeaderMap.put("companyName", "CLIENT_NAME");
        columnToHeaderMap.put("companyPhone", "CLIENT_PHONE");
        columnToHeaderMap.put("city", "CITY");
        columnToHeaderMap.put("state", "STATE");

        String[] splitValues = new String[] { "92134567", "Plasia", "MP", "Pepcus", "Indore" };
        Map<String, Integer> headerIndexMap = new HashMap<String, Integer>();
        headerIndexMap.put("CLIENT_NAME", 3);
        headerIndexMap.put("CLIENT_PHONE", 0);
        headerIndexMap.put("ADDRESS", 1);
        headerIndexMap.put("CITY", 4);
        headerIndexMap.put("STATE", 2);

        FileImportUtil.populateColumnValues(columnValues, columns, columnToHeaderMap, splitValues, headerIndexMap);
        assertEquals("Pepcus", columnValues[0]);
        assertEquals("92134567", columnValues[1]);
        assertNull("Plasia", columnValues[2]);
        assertEquals("Indore", columnValues[3]);
        assertEquals("MP", columnValues[4]);
    }

    /**
     * To verify populateColumnValues 
     * when All headers in columnToHeaderMap have entry in headerIndexMap
     *  
     */
    @Test
    public void testPopulateColumnValues_3() {

        String[] columns = new String[] { "companyName", "companyPhone", "address", "city", "state" };
        Object[] columnValues = new Object[columns.length];
        ;
        Map<String, String> columnToHeaderMap = new HashMap<String, String>();
        columnToHeaderMap.put("companyName", "CLIENT_NAME");
        columnToHeaderMap.put("companyPhone", "CLIENT_PHONE");
        columnToHeaderMap.put("address", "ADDRESS");
        columnToHeaderMap.put("city", "CITY");
        columnToHeaderMap.put("state", "STATE");

        String[] splitValues = new String[] { "92134567", "Plasia", "MP", "Pepcus", "Indore" };
        Map<String, Integer> headerIndexMap = new HashMap<String, Integer>();
        headerIndexMap.put("CLIENT_NAME", 3);
        headerIndexMap.put("CLIENT_PHONE", 0);
        headerIndexMap.put("ADDRESS", 1);
        headerIndexMap.put("CITY", 4);
        headerIndexMap.put("STATE", 2);

        FileImportUtil.populateColumnValues(columnValues, columns, columnToHeaderMap, splitValues, headerIndexMap);
        assertEquals("Pepcus", columnValues[0]);
        assertEquals("92134567", columnValues[1]);
        assertEquals("Plasia", columnValues[2]);
        assertEquals("Indore", columnValues[3]);
        assertEquals("MP", columnValues[4]);
    }

    /**
     * To verify populateColumnValues 
     * when A header in columnToHeaderMap does not have entry in headerIndexMap
     *  
     */
    @Test
    public void testPopulateColumnValues_4() {

        String[] columns = new String[] { "companyName", "companyPhone", "address", "city", "state" };
        Object[] columnValues = new Object[columns.length];
        ;
        Map<String, String> columnToHeaderMap = new HashMap<String, String>();
        columnToHeaderMap.put("companyName", "CLIENT_NAME");
        columnToHeaderMap.put("companyPhone", "CLIENT_PHONE");
        columnToHeaderMap.put("address", "ADDRESS");
        columnToHeaderMap.put("city", "CITY");
        columnToHeaderMap.put("state", "STATE");

        String[] splitValues = new String[] { "92134567", "Plasia", "MP", "Pepcus", "Indore" };
        Map<String, Integer> headerIndexMap = new HashMap<String, Integer>();
        headerIndexMap.put("CLIENT_PHONE", 0);
        headerIndexMap.put("ADDRESS", 1);
        headerIndexMap.put("CITY", 4);
        headerIndexMap.put("STATE", 2);

        FileImportUtil.populateColumnValues(columnValues, columns, columnToHeaderMap, splitValues, headerIndexMap);

        assertNull(columnValues[0]);
        assertEquals("92134567", columnValues[1]);
        assertEquals("Plasia", columnValues[2]);
        assertEquals("Indore", columnValues[3]);
        assertEquals("MP", columnValues[4]);
    }

    /**
     * To verify populateColumnValues 
     * splitValues does not have value at index for all indexes in headerIndexMap
     * (throwing ArrayIndexOutOfBound exception)
     *  
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testPopulateColumnValues_5() {

        String[] columns = new String[] { "companyName", "companyPhone", "address", "city", "state" };
        Object[] columnValues = new Object[columns.length];
        ;
        Map<String, String> columnToHeaderMap = new HashMap<String, String>();
        columnToHeaderMap.put("companyName", "CLIENT_NAME");
        columnToHeaderMap.put("companyPhone", "CLIENT_PHONE");
        columnToHeaderMap.put("address", "ADDRESS");
        columnToHeaderMap.put("city", "CITY");
        columnToHeaderMap.put("state", "STATE");

        String[] splitValues = new String[] { "92134567", "Plasia", "MP", "Pepcus" };
        Map<String, Integer> headerIndexMap = new HashMap<String, Integer>();
        headerIndexMap.put("CLIENT_NAME", 3);
        headerIndexMap.put("CLIENT_PHONE", 0);
        headerIndexMap.put("ADDRESS", 1);
        headerIndexMap.put("CITY", 4);
        headerIndexMap.put("STATE", 2);

        FileImportUtil.populateColumnValues(columnValues, columns, columnToHeaderMap, splitValues, headerIndexMap);
    }

    /**
     * To verify getCustomFieldHeaders when allHeadersInCSV and requiredHeaders with 
     * values should return some missing headers
     *  
     */
    @Test
    public void testGetCustomFieldHeaders_1() {
        String[] allHeadersInCSV = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "PRODUCER", "INDUSTRY", "BUSINESS_ID" };
        String[] requiredHeaders = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "ADDRESS", "CITY", "PRODUCER", "INDUSTRY" };

        Set<String> customHeaders = FileImportUtil.getCustomFieldHeaders(allHeadersInCSV, requiredHeaders);

        assertTrue(customHeaders.contains("BUSINESS_ID"));
    }

    /**
     * To verify getCustomFieldHeaders when allHeadersInCSV and requiredHeaders
     * with values returning no custom headers
     *  
     */
    @Test
    public void testGetCustomFieldHeaders_2() {
        String[] allHeadersInCSV = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "PRODUCER", "INDUSTRY" };
        String[] requiredHeaders = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "ADDRESS", "CITY", "PRODUCER", "INDUSTRY" };

        Set<String> customHeaders = FileImportUtil.getCustomFieldHeaders(allHeadersInCSV, requiredHeaders);

        assertTrue(customHeaders.isEmpty());
    }

    /**
     * To verify getCustomFieldHeaders when allHeadersInCSV = null , 
     * requiredHeaders with some values
     *  
     */
    @Test
    public void testGetCustomFieldHeaders_3() {
        String[] allHeadersInCSV = null;
        String[] requiredHeaders = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "ADDRESS", "CITY", "PRODUCER", "INDUSTRY" };

        Set<String> customHeaders = FileImportUtil.getCustomFieldHeaders(allHeadersInCSV, requiredHeaders);

        assertTrue(customHeaders.isEmpty());
    }

    /**
     * To verify getCustomFieldHeaders when allHeadersInCSV with some values 
     * , requiredHeaders = null
     *  
     */
    @Test
    public void testGetCustomFieldHeaders_4() {
        String[] allHeadersInCSV = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "PRODUCER", "INDUSTRY" };
        String[] requiredHeaders = null;

        Set<String> customHeaders = FileImportUtil.getCustomFieldHeaders(allHeadersInCSV, requiredHeaders);

        assertEquals(allHeadersInCSV.length, customHeaders.size());
    }

    /**
     * To verify checkCustomHeaders when 
     * All custom field Headers are there in allMappedHeaders
     *  
     */
    @Test
    public void testCheckCustomHeaders_1() {
        String[] allHeadersInCsv = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "PRODUCER", "INDUSTRY", "BUSINESS_ID" };// In this "BUSINESS_ID is custom header"
        List<String> allMappedHeaders = new ArrayList<String>();
        allMappedHeaders.add("CLIENT_NAME");
        allMappedHeaders.add("PRODUCER");
        allMappedHeaders.add("BUSINESS_ID");

        when(APIMessageUtil.getMessageFromResourceBundle(resourceHandler, "FAILURE_CAUSE")).thenReturn("Faulure Cause");
        FileImportUtil.checkCustomHeaders(allHeadersInCsv, allMappedHeaders, resourceHandler);

        assertTrue(true);
    }

    /**
     * To verify checkCustomHeaders when 
     * One custom field header is not in allMappedHeaders
     *  
     */
    @Test(expected = ApplicationException.class)
    public void testCheckCustomHeaders_2() {
        String[] allHeadersInCsv = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "PRODUCER", "INDUSTRY", "BUSINESS_ID" };// In this "BUSINESS_ID is custom header"
        List<String> allMappedHeaders = new ArrayList<String>();
        allMappedHeaders.add("CLIENT_NAME");
        allMappedHeaders.add("PRODUCER");

        FileImportUtil.checkCustomHeaders(allHeadersInCsv, allMappedHeaders, resourceHandler);
    }

    /**
     * To verify checkCustomHeaders when 
     * two custom field header is not in allMappedHeaders
     *  
     */
    @Test(expected = ApplicationException.class)
    public void testCheckCustomHeaders_3() {
        String[] allHeadersInCsv = new String[] { "CLIENT_NAME", "DISPLAY_NAME", "PRODUCER", "INDUSTRY", "BUSINESS_ID", "CLIENT_TYPE" };// In this "BUSINESS_ID is custom header"
        List<String> allMappedHeaders = new ArrayList<String>();
        allMappedHeaders.add("CLIENT_NAME");
        allMappedHeaders.add("PRODUCER");

        FileImportUtil.checkCustomHeaders(allHeadersInCsv, allMappedHeaders, resourceHandler);
    }

    /**
     * To verify readFileContents when 
     * file as Multipart instance of csv file
     *  
     */
    @Test
    public void testReadFileContents_1() {

        File file = new File("src/test/resources/testdata/testReadFileContent.csv");
        FileInputStream input;
        try {
            input = new FileInputStream(file);

            MultipartFile multipartFile;

            multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
            List<String> fileContents = FileImportUtil.readFileContent(multipartFile);

            assertEquals("Line1", fileContents.get(0));
            assertEquals("Line2", fileContents.get(1));
        } catch (IOException e1) {
            fail();
        }

    }

    //    /**
    //     * To test createResponseFile 
    //     * when fileImportResult has a failed records
    //     */
    //    @Test
    //    public void testCreateResponseFile_1() {
    //        FileImportResult fileImportResult = new FileImportResult();
    //        fileImportResult.setTotalRecords(3);
    //        fileImportResult.setNumSuccessRecords(2);
    //        fileImportResult.setNumFailedRecords(1);
    //
    //        fileImportResult.addFailedRecord(2, "Pepcus,PEP,true,Software,PEP123,Indore", "Dupllicate Company Name - Pepcus", "Skipped");
    //        try {
    //            File responseFile = FileImportUtil.createReponseFile(fileImportResult, resourceHandler);
    //            assertTrue(responseFile.exists());
    //        } catch (IOException e) {
    //            fail();
    //        }
    //    }
    //
    //    /**
    //     * To test createResponseFile 
    //     * when fileImportResult has no failed records
    //     */
    //
    //    @Test
    //    public void testCreateResponseFile_2() {
    //        FileImportResult fileImportResult = new FileImportResult();
    //        fileImportResult.setTotalRecords(3);
    //        fileImportResult.setNumSuccessRecords(3);
    //        fileImportResult.setNumFailedRecords(0);
    //
    //        try {
    //            File responseFile = FileImportUtil.createReponseFile(fileImportResult, resourceHandler);
    //            assertFalse(responseFile.exists());
    //        } catch (IOException e) {
    //            fail();
    //        }
    //
    //    }

}
