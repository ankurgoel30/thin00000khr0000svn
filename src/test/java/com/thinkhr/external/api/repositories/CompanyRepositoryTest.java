package com.thinkhr.external.api.repositories;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.thinkhr.external.api.db.entities.Company;
import com.thinkhr.external.api.utils.ApiTestDataUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Junit to verify methods of CompanyRepository with use of H2 database
 * @author Surabhi Bhawsar
 * @since 2017-11-06
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED)
public class CompanyRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CompanyRepository companyRepository;

	/**
	 * To test findAll method
	 */
	@Test
	public void testFindAll() {
		   //TODO: Add implementation	
	}

	/**
	 * To test findOne method
	 */
	@Test
	public void testFindOne() {
		   //TODO: Add implementation	
		Company company1 = ApiTestDataUtil.createCompany(null, "Pepcus", "Software", "PEP");
		company1.setSearchHelp("Test");
		company1.setCompanySince(new Date());
		company1.setSpecialNote("111");
		Company entity = companyRepository.save(company1);
		Company foundEntity = companyRepository.findOne(entity.getCompanyId());
		  
        assertNotNull(foundEntity);
        assertEquals(entity.getCompanyName(), foundEntity.getCompanyName());
	}
	
	/**
	 * To test save method
	 */
	@Test
	public void testSave() {
	   //TODO: Add implementation	
	}
	
	/**
	 * To test delete method
	 */
	@Test
	public void testDelete() {
		//TODO: Add implementation
	}
}