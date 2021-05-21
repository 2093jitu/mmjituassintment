package com.dynamicform.app.grade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dynamicform.app.base.AuthBaseRepository;
import com.dynamicform.app.util.Response;

@Repository
@Transactional
public class GradeRepository extends AuthBaseRepository {

	private final Long ADD_AMOUNT = 50000l;
	private final Long HOUSE_RENT = 20l;
	private final Long MEDICAL_ALLOWANCE = 15l;

	public Response save(String reqObj) {
		GradeEntity GradeEntity = objectMapperReadValue(reqObj, GradeEntity.class);
		return baseOnlySave(GradeEntity);
	}

	public GradeEntity updateGrade(GradeEntity gradeEntity) {
		GradeEntity obj = findById(gradeEntity.getId());
		Response response = new Response();

		if (obj != null) {
			obj.setBasic(gradeEntity.getBasic());
			response = baseUpdate(obj);
			return gradeEntity;
		}

		return null;

	}

	public Response update(String reqObj) {
		
		GradeEntity gradeEntity = objectMapperReadValue(reqObj, GradeEntity.class);
		GradeEntity lastSavedGrade = new GradeEntity();

		Response listRes = getAll();
		List<GradeEntity> grades = (List<GradeEntity>) getValueFromObject(listRes.getItems(), GradeEntity.class);

		List<Object> bachList = new ArrayList<Object>();

		if (grades.get(0).getId() == gradeEntity.getId()) {
			for (int i = 0; i < grades.size(); i++) {
				GradeEntity dbGrade = grades.get(i);
				Double basicSalary = i == 0 ? gradeEntity.getBasic() : lastSavedGrade.getBasic() + ADD_AMOUNT;

				dbGrade.setBasic(basicSalary);

				Double houseRent = (basicSalary / 100) * HOUSE_RENT;
				Double medical = (basicSalary / 100) * MEDICAL_ALLOWANCE;
				Double salary = basicSalary + houseRent + medical;

				dbGrade.setHouseRent(houseRent);
				dbGrade.setMedical(medical);
				dbGrade.setSalary(salary);
				bachList.add(dbGrade);
				lastSavedGrade = (GradeEntity) bachList.get(i);
			}
		} else {
			GradeEntity bdObj = findById(gradeEntity.getId());
			return baseUpdate(bdObj);
		}

		if (bachList != null && !CollectionUtils.isEmpty(bachList)) {
			return baseBatchSaveOrUpdate(bachList);			
		}
		return getErrorResponse("Update Fail !!");
	}

	public Response delete(long reqId) {
		return null;
	}

	public Response list(String reqObj) {
		GradeEntity billsBToBLabPolicyObj = null;
		if (null != reqObj) {
			billsBToBLabPolicyObj = objectMapperReadValue(reqObj, GradeEntity.class);
		}
		return baseList(criteriaQuery(billsBToBLabPolicyObj));
	}

	public Response getAll() {
		GradeEntity billsBToBLabPolicyObj = new GradeEntity();
		return baseList(criteriaQuery(billsBToBLabPolicyObj));
	}

	public Response detele(Long id) {

		GradeEntity billsBToBLabPolicyObj = findById(id);
		if (billsBToBLabPolicyObj == null) {
			return getErrorResponse("Record not found!");
		}
		return baseDelete(billsBToBLabPolicyObj);

	}

	public GradeEntity findById(Long id) {
		GradeEntity GradeEntity = new GradeEntity();
		GradeEntity.setId(id);
		Response response = baseFindById(criteriaQuery(GradeEntity));
		if (response.isSuccess()) {
			return getValueFromObject(response.getObj(), GradeEntity.class);
		}
		return null;
	}

	private Response duplicateChecEmployeeId(GradeEntity GradeEntity) {
		Response responce = new Response();

		if (GradeEntity == null) {
			return getErrorResponse("Search Id not found!");
		}

		Long totalCount = totalCountWithDConjunction(GradeEntity);

		if (totalCount.longValue() > 0) {
			responce.setValid(false);
			return responce;
		} else {
			responce.setValid(true);
			return responce;
		}

	}

	private Long totalCountWithDConjunction(GradeEntity filter) {

		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = longCriteriaQuery(builder);
		Root<GradeEntity> root = from(GradeEntity.class, criteriaQuery);

		return totalCount(builder, criteriaQuery, root, criteriaCondition(filter, builder, root));
	}

	// Non API
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private CriteriaQuery criteriaQuery(GradeEntity filter) {
		init();

		List<Predicate> p = new ArrayList<Predicate>();
		p = criteriaCondition(filter, null, null);

		if (!CollectionUtils.isEmpty(p)) {
			Predicate[] pArray = p.toArray(new Predicate[] {});
			Predicate predicate = builder.and(pArray);
			criteria.where(predicate);
		}
		return criteria;
	}

	@SuppressWarnings({ "unchecked" })
	private List<Predicate> criteriaCondition(GradeEntity filter, CriteriaBuilder builder, Root<GradeEntity> root) {
		if (builder == null) {
			builder = super.builder;
		}
		if (root == null) {
			root = super.root;
		}
		List<Predicate> p = new ArrayList<Predicate>();
		if (filter != null) {

			if (filter.getId() != null && filter.getId() > 0) {
				Predicate condition = builder.equal(root.get("id"), filter.getId());
				p.add(condition);
			}

		}
		return p;
	}

	@SuppressWarnings("rawtypes")
	private void init() {
		initEntityManagerBuilderCriteriaQueryRoot(GradeEntity.class);
		@SuppressWarnings("unused")
		CriteriaBuilder builder = super.builder;
		@SuppressWarnings("unused")
		CriteriaQuery criteria = super.criteria;
		@SuppressWarnings("unused")
		Root root = super.root;
	}

}
