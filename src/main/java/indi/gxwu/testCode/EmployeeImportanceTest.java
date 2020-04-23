package indi.gxwu.testCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: gx.wu
 * @Date: 2020/1/6 16:14
 * @Description: 员工重要度之和
 */
public class EmployeeImportanceTest {


    public static class Employee {
        // It's the unique id of each node;
        // unique id of this employee
        public int id;
        // the importance value of this employee
        public int importance;
        // the id of direct subordinates
        public List<Integer> subordinates;
    };

    /**
     * @param employees:
     * @param id:
     * @return: the total importance value
     */
    public int getImportance(List<Employee> employees, int id) {
        // Write your code here.

        Map<Integer,Employee> map = new HashMap<Integer, Employee>();
        for(int i=0;i<employees.size();i++){
            Employee e = employees.get(i);
            map.put(e.id, e);
        }
        return getImportance(map, id);

    }

    public int getImportance(Map<Integer,Employee> map, int id){
        if(!map.containsKey(id)){
            return 0;
        }
        int result = map.get(id).importance;
        List<Integer> subs = map.get(id).subordinates;
        for(Integer sid : subs){
            result += getImportance(map, sid);
        }
        return result;
    }
}
