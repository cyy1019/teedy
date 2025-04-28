package com.sismics.docs.core.dao;

import com.sismics.docs.core.dao.dto.UserRequestDto;
import com.sismics.docs.core.model.jpa.UserRequest;
import com.sismics.docs.core.dao.dto.UserRequestDto;
import com.sismics.util.context.ThreadLocalContext;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class UserRequestDao {

    // Create or update UserRequest
    @Transactional
    public UserRequest createOrUpdate(UserRequest userRequest) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        if (userRequest.getId() == null) {
            em.persist(userRequest);  // Save a new UserRequest
        } else {
            em.merge(userRequest);   // Update existing UserRequest
        }
        return userRequest;
    }

    // Find UserRequest by ID
    public UserRequest findById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        return em.find(UserRequest.class, id);
    }

    // Find all UserRequests
    public List<UserRequestDto> findAll() {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        List<UserRequest> userRequests = em.createQuery("SELECT u FROM UserRequest u", UserRequest.class)
                .getResultList();
        return userRequests.stream()
                .map(userRequest -> new UserRequestDto(
                        userRequest.getId(),
                        userRequest.getUserName(),
                        userRequest.getType(),
                        userRequest.getPassword(),
                        userRequest.getEmail(),
                        userRequest.getCreateDate()))
                .collect(Collectors.toList());
    }

    // Delete UserRequest by ID
    @Transactional
    public void deleteById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        UserRequest userRequest = findById(id);
        if (userRequest != null) {
            em.remove(userRequest);
        }
    }

    public UserRequest findByUserName(String userName) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        try {
            return em.createQuery("SELECT u FROM UserRequest u WHERE u.userName = :userName", UserRequest.class)
                    .setParameter("userName", userName)
                    .getSingleResult();
        } catch (Exception e) {
            return null;  // Return null if no UserRequest found with this username
        }
    }

    @Transactional
    public String create(UserRequest request) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(request);
        return request.getId();
    }
}
