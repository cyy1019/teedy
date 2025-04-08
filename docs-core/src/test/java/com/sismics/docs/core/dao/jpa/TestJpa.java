package com.sismics.docs.core.dao.jpa;

import com.sismics.docs.BaseTransactionalTest;
import com.sismics.docs.core.dao.DocumentDao;
import com.sismics.docs.core.dao.TagDao;
import com.sismics.docs.core.dao.UserDao;
import com.sismics.docs.core.dao.criteria.TagCriteria;
import com.sismics.docs.core.dao.dto.TagDto;
import com.sismics.docs.core.model.jpa.Document;
import com.sismics.docs.core.model.jpa.User;
import com.sismics.docs.core.util.TransactionUtil;
import com.sismics.docs.core.util.authentication.InternalAuthenticationHandler;
import com.sismics.docs.core.util.jpa.SortCriteria;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Tests the persistance layer.
 * 
 * @author jtremeaux
 */
public class TestJpa extends BaseTransactionalTest {
    @Test
    public void testJpa() throws Exception {
        // Create a user
        UserDao userDao = new UserDao();
        User user = createUser("testJpa");

        TransactionUtil.commit();

        // Search a user by his ID
        user = userDao.getById(user.getId());
        Assert.assertNotNull(user);
        Assert.assertEquals("toto@docs.com", user.getEmail());

        // Authenticate using the database
        Assert.assertNotNull(new InternalAuthenticationHandler().authenticate("testJpa", "12345678"));

        // Delete the created user
        userDao.delete("testJpa", user.getId());
        TransactionUtil.commit();
    }

    @Test
    public void testCreateDocument() throws Exception {
        // Arrange
        DocumentDao documentDao = new DocumentDao();

        Document document = new Document();
        document.setTitle("Test Title");
        document.setDescription("Test Description");
        document.setCreateDate(new Date());
        document.setUserId("test-user-id");

        // Act
        String documentId = documentDao.create(document, "test-user-id");


        TransactionUtil.commit();

        // Assert
        Assert.assertNotNull("Document ID should not be null", documentId);
        Assert.assertEquals("Returned ID should match document.getId()", documentId, document.getId());


        Document persistedDocument = documentDao.getById(documentId);
        Assert.assertNotNull("Persisted document should be retrievable", persistedDocument);
        Assert.assertEquals("Document title should match", "Test Title", persistedDocument.getTitle());
    }


    @Test
    public void testFindByCriteria_onlyIdSet() {
        TagDao tagDao = new TagDao();

        TagCriteria criteria = new TagCriteria();
        criteria.setId("tag-1");
        criteria.setDocumentId(null);
        criteria.setTargetIdList(null);  // 不会触发 ACL join

        List<TagDto> result = tagDao.findByCriteria(criteria, null);
        Assert.assertNotNull(result);
    }

    @Test
    public void testFindByCriteria_onlyDoc_IdSet() {
        TagDao tagDao = new TagDao();

        TagCriteria criteria = new TagCriteria();
        criteria.setId(null);
        criteria.setDocumentId("test-doc-1");
        criteria.setTargetIdList(null);  // 不会触发 ACL join

        List<TagDto> result = tagDao.findByCriteria(criteria, null);
        Assert.assertNotNull(result);
    }

    @Test
    public void testFindByCriteria_noCriteriaSet() {
        TagDao tagDao = new TagDao();

        TagCriteria criteria = new TagCriteria();
        criteria.setId(null);
        criteria.setDocumentId(null);
        criteria.setTargetIdList(null);

        List<TagDto> result = tagDao.findByCriteria(criteria, null);
        Assert.assertNotNull(result);
    }



}
