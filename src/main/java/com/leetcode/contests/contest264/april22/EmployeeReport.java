package com.leetcode.contests.contest264.april22;

import io.strati.libs.jackson.core.JsonGenerationException;
import io.strati.libs.jackson.databind.JsonMappingException;
import io.strati.libs.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

public class EmployeeReport {


    String[] solution(String jsonDump) {
        String[] res = {};
        //System.out.println(jsonDump);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, List<Object>> map = objectMapper.readValue(jsonDump, Map.class);
            List<Employee> employees = createEmployeeList(map.get("employees"));
            List<Department> departments = createDepartmentList(map.get("departments"));

            Map<Integer, Employee> empIdMap = new HashMap<>();
            for(Employee emp: employees) {
                empIdMap.put(emp.id, emp);
            }
            Employee ceo = null;
            Map<Integer, Set<Integer>> empSubOrdinateMap = new HashMap<>();
            for(Employee emp: employees) {
                if(emp.managerId == null) {
                    ceo = emp;
                    continue;
                }
                emp.manager = empIdMap.get(emp.managerId);
                empSubOrdinateMap.computeIfAbsent(emp.managerId, (e) -> new HashSet<>()).add(emp.id);
            }
            Department corporate = new Department();
            corporate.id = -1;
            corporate.name = "Corporate";
            corporate.headId = ceo.id;
            departments.add(corporate);
            Map<Integer, Integer> deptHeadDeptMap = new HashMap<>();
            Map<Integer, Department> deptMap = new HashMap<>();
            for(Department dept: departments) {
                deptHeadDeptMap.put(dept.headId, dept.id);
                deptMap.put(dept.id, dept);
                empIdMap.get(dept.headId).deptHeadId = dept.headId;
                empIdMap.get(dept.headId).department = dept;
            }
            //deptHeadDeptMap.put(ceo.id, -1);
            ceo.deptHeadId = ceo.id;
            populateDeptHead(ceo.id, ceo.deptHeadId, deptHeadDeptMap, empSubOrdinateMap, empIdMap);
            for(Employee emp: employees) {
                Integer headsDeptId = deptHeadDeptMap.get(emp.deptHeadId);
                //System.out.printf("Emp: %d, Head: %d, Head Dept : %d\n", emp.id, emp.deptHeadId, headsDeptId);
                Department dept = deptMap.get(headsDeptId);
                emp.department = dept;
                System.out.printf("Emp :[%s]\n", emp.toString());
            }
        /*Collections.sort(employees,
                Comparator.comparing((Employee e) -> e.department.name)
                        .thenComparing((Employee e) -> deptHeadDeptMap.getOrDefault(e.id, employees.size()+1))
                        .thenComparing((Employee e) -> e.name));*/
            res = new String[employees.size()+1];
            res[0] = "id,name,department,manager";
        } catch (JsonMappingException ex) {
            ex.printStackTrace();
        } catch (JsonGenerationException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return res;
    }
    private void populateDeptHead(
            Integer empId,
            Integer deptHeadId,
            Map<Integer, Integer> deptHeadDeptMap,
            Map<Integer, Set<Integer>> empSubOrdinateMap,
            Map<Integer, Employee> empIdMap) {
        Set<Integer> subOrdinates = empSubOrdinateMap.getOrDefault(empId, Collections.emptySet());
        for(Integer sub : subOrdinates) {
            Employee subEmp = empIdMap.get(sub);
            //System.out.printf("Emp Id: %d Is Head: %b\n", sub, deptHeadDeptMap.containsKey(sub));
            if(deptHeadDeptMap.containsKey(sub)) {
                //System.out.printf("Emp Id: %d Is Head: %d\n", sub, deptHeadDeptMap.get(sub));
                subEmp.deptHeadId = sub;
            } else {
                subEmp.deptHeadId = empId;
            }
            //System.out.printf("Emp Id: %d Dept  Head: %d\n", sub, subEmp.deptHeadId);
            populateDeptHead(sub, subEmp.deptHeadId, deptHeadDeptMap, empSubOrdinateMap, empIdMap);
        }
    }
    private List<Employee> createEmployeeList(List<Object> objects) {
        List<Employee>  employees = new ArrayList<>();
        for(Object obj: objects) {
            LinkedHashMap lim = (LinkedHashMap)obj;
            Employee emp = new Employee();
            emp.id = (Integer) lim.get("id");
            emp.name = (String) lim.get("name");
            emp.managerId = (Integer) lim.get("manager_id");
            employees.add(emp);
        }
        return employees;
    }
    private List<Department> createDepartmentList(List<Object> objects) {
        List<Department>  departments = new ArrayList<>();
        for(Object obj: objects) {
            LinkedHashMap lim = (LinkedHashMap)obj;
            Department dept = new Department();
            dept.id = (Integer) lim.get("id");
            dept.name = (String) lim.get("name");
            dept.headId = (Integer) lim.get("department_head_id");
            departments.add(dept);
        }
        return departments;
    }
    public static class Employee  {
        Integer id;
        String name;
        Integer managerId;
        Employee manager;
        Integer deptHeadId;
        Department department;
        public Employee() {

        }
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ID: ");
            sb.append(id);
            sb.append(", Name: ");
            sb.append(name);
            sb.append(", Manager ID: ");
            sb.append(managerId);
            sb.append(", Manager name: ");
            sb.append(manager == null ? "NULL" :  manager.name);
            sb.append(", Department Head Id: ");
            sb.append(deptHeadId);
            sb.append(", Department Name: ");
            sb.append(department == null ? "NULL" : department.name);
            return sb.toString();
        }
        public String asOpFormatString() {
            StringBuilder sb = new StringBuilder();
            sb.append(id);
            sb.append(',');
            sb.append(name);
            sb.append(',');
            sb.append(department == null ? "NULL" :  department.name);
            sb.append(',');
            sb.append(manager == null ? "" : manager.name);
            return sb.toString();
        }
    }
    public static class Department {
        Integer id;
        String name;
        Integer headId;
        public Department() {

        }
    }
    public static class EmployeeNode {
        Integer empId;
        Integer deptId;
        Set<Integer> subIds;

        public EmployeeNode(Integer empId) {
            this.empId = empId;
            this.subIds = new HashSet<>();
        }
    }
}
