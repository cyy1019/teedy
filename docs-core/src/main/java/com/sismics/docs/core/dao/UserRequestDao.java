package com.sismics.docs.core.dao;

import com.sismics.docs.core.dao.criteria.UserRequestCriteria;
import com.sismics.docs.core.dao.dto.UserRequestDto;
import com.sismics.docs.core.model.jpa.UserRequest;
import com.sismics.docs.core.util.jpa.QueryParam;
import com.sismics.docs.core.util.jpa.QueryUtil;
import com.sismics.docs.core.util.jpa.SortCriteria;
import com.sismics.util.context.ThreadLocalContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.sql.Timestamp;
import java.util.*;

public class UserRequestDao {

    /**
     * 创建请求
     */
    public String create(UserRequest request) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        request.setId(UUID.randomUUID().toString());
        request.setCreateDate(new Date());
        em.persist(request);
        return request.getId();
    }

    /**
     * 查询请求详情
     */
    public UserRequest getById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        return em.find(UserRequest.class, id);
    }

    /**
     * 查询用户的所有请求
     */
    public List<UserRequest> getByUserId(String userId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select r from UserRequest r where r.userId = :userId and r.deleteDate is null");
        q.setParameter("userId", userId);
        return q.getResultList();
    }

    /**
     * 逻辑删除请求
     */
    public void delete(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        UserRequest request = em.find(UserRequest.class, id);
        if (request != null) {
            request.setDeleteDate(new Date());
        }
    }

    /**
     * 查询条件筛选（分页 + 模糊等）
     */
    public List<UserRequestDto> findByCriteria(UserRequestCriteria criteria, SortCriteria sortCriteria) {
        Map<String, Object> parameterMap = new HashMap<>();
        List<String> whereList = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        sb.append("select r.USRREQ_ID_C as c0, r.USRREQ_IDUSER_C as c1, r.USRREQ_TYPE_C as c2, ")
                .append("r.USRREQ_CREATEDATE_D as c3, r.USRREQ_DATA_C as c4 ")
                .append("from T_USER_REQUEST r ");

        // 构建 where 条件
        if (criteria.getUserId() != null) {
            whereList.add("r.USRREQ_IDUSER_C = :userId");
            parameterMap.put("userId", criteria.getUserId());
        }
        if (criteria.getType() != null) {
            whereList.add("r.USRREQ_TYPE_C = :type");
            parameterMap.put("type", criteria.getType());
        }
        if (criteria.getFromDate() != null) {
            whereList.add("r.USRREQ_CREATEDATE_D >= :fromDate");
            parameterMap.put("fromDate", criteria.getFromDate());
        }
        if (criteria.getToDate() != null) {
            whereList.add("r.USRREQ_CREATEDATE_D <= :toDate");
            parameterMap.put("toDate", criteria.getToDate());
        }
        if (criteria.getSearch() != null) {
            whereList.add("lower(r.USRREQ_DATA_C) like :search");
            parameterMap.put("search", "%" + criteria.getSearch().toLowerCase() + "%");
        }

        // 拼接 where 子句
        if (!whereList.isEmpty()) {
            sb.append(" where ").append(String.join(" and ", whereList));
        }

        // 加入排序条件（可选）
        QueryParam queryParam = QueryUtil.getSortedQueryParam(new QueryParam(sb.toString(), parameterMap), sortCriteria);

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = QueryUtil.getNativeQuery(queryParam).getResultList();

        List<UserRequestDto> dtoList = new ArrayList<>();
        for (Object[] row : resultList) {
            int i = 0;
            UserRequestDto dto = new UserRequestDto();
            dto.setId((String) row[i++]);
            dto.setUserId((String) row[i++]);
            dto.setType((String) row[i++]);
            dto.setCreateDate(row[i++] != null ? new Date(((Timestamp) row[i - 1]).getTime()) : null);
            dto.setData((String) row[i++]);
            dtoList.add(dto);
        }

        return dtoList;
    }

}
