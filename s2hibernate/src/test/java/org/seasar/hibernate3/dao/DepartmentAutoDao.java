/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.util.List;

import org.seasar.hibernate3.entity.Department;

public interface DepartmentAutoDao {

    public Class BEAN = Department.class;

    // load
    public Department load(short empno);

    // ���List�ⵂĂ�����ꍇ
    public String getDepartmentByDeptno_ARGS = "deptno";
    public String getDepartmentByDeptno_EAGER = "employee";

    public Department getDepartmentByDeptno(int deptno);

    public String findAll_EAGER = "employee";

    public List findAll();

    // public String getDept_HQL = "from Department as d where d.deptno =
    // :deptno";
    // public String getDept_HQL = "select d from Department as d where d.deptno
    // = :deptno";
    public String getDept_HQL = "from Department as d where d.deptno = :deptno";
    public String getDept_ARGS = "deptno";

    public Department getDept(int deptno);

    public String getDeptName_HQL = "select d.dname from Department as d where d.deptno = :deptno";
    public String getDeptName_ARGS = "deptno";

    public String getDeptName(int deptno);

}
