/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.hibernate3.dao;

import java.math.BigDecimal;
import java.util.List;

import org.seasar.hibernate3.dto.EmployeeSalDto;
import org.seasar.hibernate3.dto.EmployeeSearchDto;
import org.seasar.hibernate3.dto.EmployeeSimpleDto;
import org.seasar.hibernate3.entity.Employee;

public interface EmployeeAutoDao {

    public Class BEAN = Employee.class;

    // �ǉ��A�폜�A�X�V�A�ۑ�or�X�V������ꍇ
    public void save(Employee employee);

    public void delete(Employee employee);

    public void update(Employee employee);

    public void saveOrUpdate(Employee employee);

    // �I�u�W�F�N�g�̎擾
    public Employee load(Integer empno);

    // �I�u�W�F�N�g�����b�N�������Ď擾
    public String loadLock_LOCK = "UPGRADE";

    public Employee loadLock(Integer empno);

    // HQL���w�肵�Ȃ��Ŏ��s����ꍇ
    public String getEmployeeByEmpNo_ARGS = "empno";

    public Employee getEmployeeByEmpNo(Integer empNo);

    public String getEmployeeByJobDeptno_ARGS = "job,deptno";

    public List getEmployeeByJobDeptno(String job, int deptno);

    // HQL���w�肵�Ď��s����ꍇ
    public String getHQLAllEmployee_HQL = "from Employee emp order by emp.empno";

    public List getHQLAllEmployee();

    // �����w�肵�Ă��Ȃ��ꍇ
    public List getAllEmployee();

    // firstResult,maxResults���w�肷��ꍇ
    public String getEmployeeList_ARGS = "firstResult,maxResults";
    public String getEmployeeList_HQL = "from Employee emp order by emp.empno";

    public List getEmployeeList(int firstResult, int MaxResults);

    // �߂�l��int�̏ꍇ
    public String getEmployeeCount_HQL = "select count(emp) from Employee emp";

    public int getEmployeeCount();

    // �߂�l��String�̏ꍇ
    public String getEmployeeNameById_HQL = "select emp.ename from Employee emp where empno = :employeeId ";
    public String getEmployeeNameById_ARGS = "employeeId";

    public String getEmployeeNameById(Integer employeeId);

    // NamedQuery�Ăяo�����g���ꍇ
    // (Employee.hbm.xml��"org.seasar.hibernate3.dao.EmployeeAutoDao_getEmployeeByJob"�ɑΉ�)
    public String getEmployeeByJob_ARGS = "job";

    public List getEmployeeByJob(String job);

    // NamedQuery�Ŗ߂�l��String�ɂȂ�悤��SQL�����g���ꍇ
    // (Employee.hbm.xml��"org.seasar.hibernate3.dao.EmployeeAutoDao_getNamedQueryEmployeeNameById"�ɑΉ�)
    public String getNamedQueryEmployeeNameById_ARGS = "employeeId";

    public String getNamedQueryEmployeeNameById(Integer employeeId);

    // NamedQuery�Ŗ߂�l��Long�ɂȂ�悤��SQL�����g���ꍇ
    // (Employee.hbm.xml��"getNamedQueryEmployeeIdByName"NamedQuery�����s)
    public String getNamedQueryEmployeeIdByName_ARGS = "employeeName";

    public Integer getNamedQueryEmployeeIdByName(String employeeName);

    // ������List�����Ă�����ꍇ
    public String getEmployeeByIdList_HQL = "from Employee emp where emp.empno in (:empnoList)";
    public String getEmployeeByIdList_ARGS = "empnoList";

    public List getEmployeeByIdList(List empnoList);

    // OrderBy�̃t�B�[���h���w�肷��ꍇ
    public String getEmployeeOrderByField_ARGS = "orderBy";

    public List getEmployeeOrderByField(String orderBy);

    // ARGS�A�m�e�[�V�����ɔ�r�I�y���[�^�[�i>�j���w�肵���ꍇ
    public String getEmployeeByGtSal_ARGS = "sal >";

    public List getEmployeeByGtSal(BigDecimal sal);

    // ARGS�A�m�e�[�V�����ɔ�r�I�y���[�^�[�i<�j���w�肵���ꍇ
    public String getEmployeeByLtSal_ARGS = "sal <";

    public List getEmployeeByLtSal(BigDecimal sal);

    // ARGS�A�m�e�[�V�����ɔ�r�I�y���[�^�[�i>,<�j���w�肵���ꍇ
    // �w�肵���t�B�[���h���w�肵���͈͂̒l�̃I�u�W�F�N�g���擾�������Ƃ�
    public String getEmployeeByGtLtSal_ARGS = "sal >,sal <";

    public List getEmployeeByGtLtSal(BigDecimal BigDecimal, BigDecimal toSal);

    // ARGS�A�m�e�[�V�����ɔ�r�I�y���[�^�[�i=�j���w�肵���ꍇ
    public String getEmployeeByJobDeptnoEq_ARGS = "job =,deptno =";

    public List getEmployeeByJobDeptnoEq(String job, int deptno);

    // ARGS�A�m�e�[�V�����ɔ�r�I�y���[�^�[�ilike�j���w�肵���ꍇ
    public String getEmployeeByLikeEmane_ARGS = "ename like";

    public List getEmployeeByLikeEmane(String ename);

    // ARGS�A�m�e�[�V�����ɔ�r�I�y���[�^�[�iin�j���w�肵���ꍇ
    public String getEmployeeByInIdList_ARGS = "empno in";

    public List getEmployeeByInIdList(List empnoList);

    // ARGS�A�m�e�[�V�����ɔ�r�I�y���[�^�[�i>=,<=�j���w�肵���ꍇ�iEmployeeSearchDto�j
    // �w�肵���t�B�[���h���w�肵���͈͂̒l�̃I�u�W�F�N�g���擾�������Ƃ�
    public String getEmployeeByDto_PROPERTY = "empno,ename,job,mgr,deptno,"
            + "hiredate >= fromHiredate,hiredate <= toHiredate,sal >= fromSal,sal <= toSal";

    public List getEmployeeByDto(EmployeeSearchDto dto);

    // dto�Ƃ���Employee���w�肵�ď�������ꍇ
    public List getEmployeeByEmployeeDtoAuto(Employee dto);

    // dto�Ƃ���EmployeeSimpleDto���w�肵�ď�������ꍇ
    public List getEmployeeByEmployeeSimpleDtoAuto(EmployeeSimpleDto dto);

    // Dto�̒l��HQL�ɓn���Ď��s����ꍇ

    public String getEmployeeBySalDto_PROPERTY = "fromSal,toSal";

    public List getEmployeeBySalDto(EmployeeSalDto dto);

}
