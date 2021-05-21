package com.dynamicform.app.base;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;

import com.dynamicform.app.util.AuthCommonFunctions;
import com.dynamicform.app.util.Response;

public class AuthBaseRepository extends BaseRepository implements AuthCommonFunctions {

	@Autowired
	private EntityManager entityManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Response baseSingleObject(CriteriaQuery criteria) {
		Response response = new Response();
		Object obj = null;
		try {
			obj = entityManager.createQuery(criteria).getSingleResult();
			response.setObj(obj);
			return getSuccessResponse("find data Successfully", response);
		} catch (Exception e) {
			// TODO: handle exception
			return getErrorResponse("Data not Found !!");
		}
	}

}
