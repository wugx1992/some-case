package indi.gxwu.flow;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author: gx.wu
 * @Date: 2021/1/18 16:49
 * @Description: code something to describe this module what it is
 */
public class HolidayRequest {

    public static void main(String[] args) {
        flowableTest();
    }


    public static ProcessEngine initDatabase (){
        //1、创建ProcessEngineConfiguration实例,该实例可以配置与调整流程引擎的设置
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                //2、通常采用xml配置文件创建ProcessEngineConfiguration，这里直接采用代码的方式
                //3、配置数据库相关参数
                .setJdbcUrl("jdbc:mysql://localhost:3306/flowable_demo?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2b8&nullCatalogMeansCurrent=true")
                .setJdbcUsername("root")
                .setJdbcPassword("credittone")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //4、初始化ProcessEngine流程引擎实例
        ProcessEngine processEngine = cfg.buildProcessEngine();
        return processEngine;
    }


    public static void flowableTest(){
        ProcessEngine processEngine = initDatabase();

        // 设计好的流程部署到流程引擎中（数据库）
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("holiday-request.bpmn20.xml")
                .deploy();

        // 根据流程ID查找已经部署的流程
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();
        System.out.println(" 发现定义的流程 : " + processDefinition.getName());

        // 模拟请假流程，输入相关的参数
        Scanner scanner = new Scanner(System.in);

        // 请假人
        System.out.println("你是谁?");
        String employee = scanner.nextLine();

        // 请假天数
        System.out.println("你要请几天假?");
        Integer nrOfHolidays = Integer.valueOf(scanner.nextLine());

        // 请假理由
        System.out.println("请假理由?");
        String description = scanner.nextLine();

        // 流程引擎服务
        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", employee);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);

        // 构建流程运行参数，启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday-request", variables);

        // 获取待办任务列表
        TaskService taskService = processEngine.getTaskService();
//        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("").list();
        System.out.println("你有 " + tasks.size() + " 个待办任务:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ") " + tasks.get(i).getName());
        }

        // 任务选择
        System.out.println("请选择你要处理的任务ID?");
        int taskIndex = Integer.valueOf(scanner.nextLine());
        Task task = tasks.get(taskIndex - 1);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());

        // 流程审批
        System.out.println(processVariables.get("employee") + " 要请 " + processVariables.get("nrOfHolidays")
                + " 天假. 是否同意? 同意回复 y ");

        boolean approved = scanner.nextLine().toLowerCase().equals("y");
        variables = new HashMap<>();
        variables.put("approved", approved);
        taskService.complete(task.getId(), variables);

        // 审批历史数据
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstance.getId()).finished().orderByHistoricActivityInstanceEndTime().asc()
                .list();

        for (HistoricActivityInstance activity : activities) {
            System.out.println(activity.getActivityId() + " took " + activity.getDurationInMillis() + " milliseconds");
        }
    }
}
