/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

    // 追加、削除、更新、保存or更新をする場合
    public void save(Employee employee);

    public void delete(Employee employee);

    public void update(Employee employee);

    public void saveOrUpdate(Employee employee);

    // オブジェクトの取得
    public Employee load(Integer empno);

    // オブジェクトをロックをかけて取得
    public String loadLock_LOCK = "UPGRADE";

    public Employee loadLock(Integer empno);

    // HQLを指定しないで実行する場合
    public String getEmployeeByEmpNo_ARGS = "empno";

    public Employee getEmployeeByEmpNo(Integer empNo);

    public String getEmployeeByJobDeptno_ARGS = "job,deptno";

    public List getEmployeeByJobDeptno(String job, int deptno);

    // HQLを指定して実行する場合
    public String getHQLAllEmployee_HQL = "from Employee emp order by emp.empno";

    public List getHQLAllEmployee();

    // 何も指定していない場合
    public List getAllEmployee();

    // firstResult,maxResultsを指定する場合
    public String getEmployeeList_ARGS = "firstResult,maxResults";
    public String getEmployeeList_HQL = "from Employee emp order by emp.empno";

    public List getEmployeeList(int firstResult, int MaxResults);

    // 戻り値がintの場合
    public String getEmployeeCount_HQL = "select count(emp) from Employee emp";

    public int getEmployeeCount();

    // 戻り値がStringの場合
    public String getEmployeeNameById_HQL = "select emp.ename from Employee emp where empno = :employeeId ";
    public String getEmployeeNameById_ARGS = "employeeId";

    public String getEmployeeNameById(Integer employeeId);

    // NamedQuery呼び出しを使う場合
    // (Employee.hbm.xmlの"org.seasar.hibernate3.dao.EmployeeAutoDao_getEmployeeByJob"に対応)
    public String getEmployeeByJob_ARGS = "job";

    public List getEmployeeByJob(String job);

    // NamedQueryで戻り値がStringになるようなSQL文を使う場合
    // (Employee.hbm.xmlの"org.seasar.hibernate3.dao.EmployeeAutoDao_getNamedQueryEmployeeNameById"に対応)
    public String getNamedQueryEmployeeNameById_ARGS = "employeeId";

    public String getNamedQueryEmployeeNameById(Integer employeeId);

    // NamedQueryで戻り値がLongになるようなSQL文を使う場合
    // (Employee.hbm.xmlの"getNamedQueryEmployeeIdByName"NamedQueryを実行)
    public String getNamedQueryEmployeeIdByName_ARGS = "employeeName";

    public Integer getNamedQueryEmployeeIdByName(String employeeName);

    // 引数にListをしていする場合
    public String getEmployeeByIdList_HQL = "from Employee emp where emp.empno in (:empnoList)";
    public String getEmployeeByIdList_ARGS = "empnoList";

    public List getEmployeeByIdList(List empnoList);

    // OrderByのフィールドを指定する場合
    public String getEmployeeOrderByField_ARGS = "orderBy";

    public List getEmployeeOrderByField(String orderBy);

    // ARGSアノテーションに比較オペレーター（>）を指定した場合
    public String getEmployeeByGtSal_ARGS = "sal >";

    public List getEmployeeByGtSal(BigDecimal sal);

    // ARGSアノテーションに比較オペレーター（<）を指定した場合
    public String getEmployeeByLtSal_ARGS = "sal <";

    public List getEmployeeByLtSal(BigDecimal sal);

    // ARGSアノテーションに比較オペレーター（>,<）を指定した場合
    // 指定したフィールドが指定した範囲の値のオブジェクトを取得したいとき
    public String getEmployeeByGtLtSal_ARGS = "sal >,sal <";

    public List getEmployeeByGtLtSal(BigDecimal BigDecimal, BigDecimal toSal);

    // ARGSアノテーションに比較オペレーター（=）を指定した場合
    public String getEmployeeByJobDeptnoEq_ARGS = "job =,deptno =";

    public List getEmployeeByJobDeptnoEq(String job, int deptno);

    // ARGSアノテーションに比較オペレーター（like）を指定した場合
    public String getEmployeeByLikeEmane_ARGS = "ename like";

    public List getEmployeeByLikeEmane(String ename);

    // ARGSアノテーションに比較オペレーター（in）を指定した場合
    public String getEmployeeByInIdList_ARGS = "empno in";

    public List getEmployeeByInIdList(List empnoList);

    // ARGSアノテーションに比較オペレーター（>=,<=）を指定した場合（EmployeeSearchDto）
    // 指定したフィールドが指定した範囲の値のオブジェクトを取得したいとき
    public String getEmployeeByDto_PROPERTY = "empno,ename,job,mgr,deptno,"
            + "hiredate >= fromHiredate,hiredate <= toHiredate,sal >= fromSal,sal <= toSal";

    public List getEmployeeByDto(EmployeeSearchDto dto);

    // dtoとしてEmployeeを指定して処理する場合
    public List getEmployeeByEmployeeDtoAuto(Employee dto);

    // dtoとしてEmployeeSimpleDtoを指定して処理する場合
    public List getEmployeeByEmployeeSimpleDtoAuto(EmployeeSimpleDto dto);

    // Dtoの値をHQLに渡して実行する場合

    public String getEmployeeBySalDto_PROPERTY = "fromSal,toSal";

    public List getEmployeeBySalDto(EmployeeSalDto dto);

}
