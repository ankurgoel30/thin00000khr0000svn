package com.thinkhr.external.api.services.upload;

import static com.thinkhr.external.api.ApplicationConstants.COMMA_SEPARATOR;
import static com.thinkhr.external.api.services.upload.FileImportValidator.validateAndGetFileContent;
import static com.thinkhr.external.api.services.upload.FileImportValidator.validateHeaders;

import java.sql.DataTruncation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.thinkhr.external.api.db.entities.CustomFields;
import com.thinkhr.external.api.exception.APIErrorCodes;
import com.thinkhr.external.api.exception.ApplicationException;
import com.thinkhr.external.api.exception.MessageResourceHandler;
import com.thinkhr.external.api.model.FileImportResult;
import com.thinkhr.external.api.repositories.CompanyRepository;
import com.thinkhr.external.api.repositories.CustomFieldsRepository;
import com.thinkhr.external.api.repositories.FileDataRepository;
import com.thinkhr.external.api.response.APIMessageUtil;
import com.thinkhr.external.api.services.utils.FileImportUtil;

/**
 * @author Surabhi Bhawsar
 * @since 2017-11-26
 *
 */
@Service
public class FileImportService {
    
    private Logger logger = LoggerFactory.getLogger(FileImportService.class);
    
    @Autowired
    FileDataRepository fileDataRepository;
    
    @Autowired
    CompanyRepository companyRepository;
    
    @Autowired
    CustomFieldsRepository customFieldRepository;

    @Autowired
    MessageResourceHandler resourceHandler;
    
    
    /**
     * Imports a CSV file for companies record
     * 
     * @param fileToImport
     * @param brokerId
     * @param resource
     * @throws ApplicationException
     */
    public FileImportResult bulkUpload(MultipartFile fileToImport, int brokerId, String resource) throws ApplicationException {

        // Process files if submitted by a valid broker else throw an exception
        if (null == companyRepository.findOne(brokerId)) {
              throw ApplicationException.createFileImportError(APIErrorCodes.INVALID_BROKER_ID, String.valueOf(brokerId));
        }
        
        List<String> fileContents = validateAndGetFileContent(fileToImport);
        
        validateHeaders(fileContents.get(0), resource, fileToImport.getName());
        
        return importFileDataToDB (fileContents, brokerId, resource);
        
    }
    
    /**
     * Process imported file to save companies records in database
     *  
     * @param fileContents
     * @param brokerId
     * @param resource
     * @throws ApplicationException
     */
    private FileImportResult importFileDataToDB(List<String> fileContents, int brokerId, String resource) throws ApplicationException {

        FileImportResult fileImportResult = new FileImportResult();

        String headerLine = fileContents.get(0);

        //TODO: review to modify
        List<String> records = new ArrayList<String>();
        records.addAll(fileContents);
        records.remove(0);

        fileImportResult.setTotalRecords(records.size());
        fileImportResult.setHeaderLine(headerLine);

        //To keep track of duplicate records in imported file. A duplicate record = duplicate client_name
        Set<String> companyNames = new HashSet<String>();

        String[] headersInCSV = headerLine.split(COMMA_SEPARATOR);

        //DO not assume that CSV file shall contains fixed column position. Let's read and map then with database column
        Map<String, String> companyFileHeaderColumnMap = getCompanyColumnsHeaderMap(brokerId); 

        Map<String, String> locationFileHeaderColumnMap = FileUploadEnum.LOCATION.prepareColumnHeaderMap();

        //Check every custom field from imported file has a corrosponding column in database. If not, retrun error here.
        FileImportUtil.validateAndFilterCustomHeaders(headersInCSV, companyFileHeaderColumnMap.values());

        Map<String, Integer> headerIndexMap = new HashMap<String, Integer>();
        for (int i = 0; i < headersInCSV.length; i++) {
            headerIndexMap.put(headersInCSV[i], i);
        }

        List<String> companyColumnsToInsert = new ArrayList<String>(companyFileHeaderColumnMap.keySet());
        List<String> locationColumnsToInsert = new ArrayList<String>(locationFileHeaderColumnMap.keySet());

        int recCount = 0;
       
        for (String record : records ) {

            if (StringUtils.isBlank(record)) {
                addIntoSkippedRecord(fileImportResult, record, recCount);
                continue;
            }

            String[] rowColValues = record.split(COMMA_SEPARATOR);

            String companyName = rowColValues[0]; 

            if (companyNames.contains(companyName)) {
                addDuplicateRecordToResult(fileImportResult, recCount, record, companyName);
                continue;
            } 
            companyNames.add(companyName);

            List<Object> companyColumnsValues = null;
            List<Object> locationColumnsValues = null;

            try {
                // Populate companyColumnsValues from split record
                companyColumnsValues = FileImportUtil.populateColumnValues(record, companyColumnsToInsert, companyFileHeaderColumnMap,
                        headerIndexMap);

                // Populate locationColumnsValues from split record
                locationColumnsValues = FileImportUtil.populateColumnValues(record, locationColumnsToInsert, locationFileHeaderColumnMap,
                        headerIndexMap);

            } catch (ArrayIndexOutOfBoundsException ex) {
                addFailedRecordToResult(fileImportResult, record, recCount);
                continue;
            } catch (Exception ex) {
                throw ApplicationException.createFileImportError(APIErrorCodes.FILE_READ_ERROR, ex.toString());
            }

            try {

                //Finally save companies one by one
                fileDataRepository.saveCompanyRecord(companyColumnsToInsert, companyColumnsValues, locationColumnsToInsert,
                        locationColumnsValues);

                fileImportResult.increamentSuccessRecords();
            } catch (Exception ex) {
                fileImportResult.increamentFailedRecords();
                Throwable th = ex.getCause();
                String cause = null;
                String info = APIMessageUtil.getMessageFromResourceBundle(resourceHandler, "RECORD_NOT_ADDED");
                if (th instanceof DataTruncation) {
                    DataTruncation dte = (DataTruncation) th;
                    cause = APIMessageUtil.getMessageFromResourceBundle(resourceHandler, "DATA_TRUNCTATION");
                } else {
                    cause = ex.getMessage();
                }
                fileImportResult.addFailedRecord(recCount ++, record, cause, info);
            }
        }
        logger.debug("Total Number of Records: " + fileImportResult.getTotalRecords());
        logger.debug("Total Number of Successful Records: " + fileImportResult.getNumSuccessRecords());
        logger.debug("Total Number of Failure Records: " + fileImportResult.getNumFailedRecords());
        if (fileImportResult.getNumFailedRecords() > 0) {
            logger.debug("List of Failure Records");
            for (FileImportResult.FailedRecord failedRecord : fileImportResult.getFailedRecords()) {
                logger.debug(failedRecord.getRecord() + COMMA_SEPARATOR + failedRecord.getFailureCause());
            }
        }

        return fileImportResult;
    }

    private void addFailedRecordToResult(FileImportResult fileImportResult,
            String record, int recCount) {
        fileImportResult.increamentFailedRecords();
        String causeMissingFields = APIMessageUtil.getMessageFromResourceBundle(resourceHandler, "MISSING_FIELDS");
        String infoSkipped = APIMessageUtil.getMessageFromResourceBundle(resourceHandler, "SKIPPED");
        fileImportResult.addFailedRecord(recCount++ , record, causeMissingFields, infoSkipped);
    }

    private void addDuplicateRecordToResult(FileImportResult fileImportResult,
            int recCount, String record, String companyName) {
        
        fileImportResult.increamentFailedRecords();
        String causeDuplicateName = APIMessageUtil.getMessageFromResourceBundle(resourceHandler, APIErrorCodes.DUPLICATE_RECORD);
        causeDuplicateName = causeDuplicateName + " - " + companyName;
        String infoSkipped = APIMessageUtil.getMessageFromResourceBundle(resourceHandler, APIErrorCodes.SKIPPED_RECORD);
        fileImportResult.addFailedRecord(recCount++, record, causeDuplicateName, infoSkipped);
    }

    private void addIntoSkippedRecord(FileImportResult fileImportResult,
            String record, int recCount) {
        fileImportResult.increamentFailedRecords();
        String causeBlankRecord = APIMessageUtil.getMessageFromResourceBundle(resourceHandler, APIErrorCodes.BLANK_RECORD);
        String infoSkipped = APIMessageUtil.getMessageFromResourceBundle(resourceHandler, APIErrorCodes.SKIPPED_RECORD);
        fileImportResult.addFailedRecord(recCount++, record, causeBlankRecord, infoSkipped);
    }

    /**
     * This function returns a map of custom fields to customFieldDisplayLabel(Header in CSV)
     * map by looking up into app_throne_custom_fields table
     * 
     * @return Map<String,String> 
     */
    private Map<String, String> getCustomFieldsMap(int id) {
        
        List<CustomFields> list = customFieldRepository.findByCompanyId(id);
        
        if (list == null || list.isEmpty()) {
            return null;
        }
        
        Map<String, String> customFieldsToHeaderMap = new LinkedHashMap<String, String>();
        for (CustomFields customField : list) {
            String customFieldName = "custom" + customField.getCustomFieldColumnName();
            customFieldsToHeaderMap.put(customFieldName, customField.getCustomFieldDisplayLabel());
        }
        return customFieldsToHeaderMap;
    }
    
    /**
     * Get a map of Company columns
     * 
     * @param companyId
     * @return
     */
    private Map<String, String> getCompanyColumnsHeaderMap(int companyId) {
        
        Map<String, String> companyColumnHeaderMap = FileUploadEnum.COMPANY.prepareColumnHeaderMap();
        
        Map<String, String> customColumnHeaderMap = getCustomFieldsMap(companyId);//customColumnsLookUpId - gets custom fields from database
        
        companyColumnHeaderMap.putAll(customColumnHeaderMap);
        
        return companyColumnHeaderMap;
    }
    
}
