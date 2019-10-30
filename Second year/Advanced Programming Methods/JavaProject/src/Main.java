import Domain.*;
import Runners.DelayTaskRunner;
import Runners.PrinterTaskRunner;
import Runners.StrategyTaskRunner;
import Runners.TaskRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class Main {

    private static Task[] createTasks(){
        Task[] tasks=new Task[5];
        tasks[0]=new MessageTask("1","feedback","ai luat 9","teacher","student", LocalDateTime.now());
        tasks[1]=new MessageTask("2","feedback","ai luat 10","teacher","student", LocalDateTime.now());
        tasks[2]=new MessageTask("3","feedback","ai luat 7","teacher","student", LocalDateTime.now());
        tasks[3]=new MessageTask("4","feedback","ai luat 5","teacher","student", LocalDateTime.now());
        tasks[4]=new MessageTask("5","feedback","ai luat 8","teacher","student", LocalDateTime.now());

        return tasks;

    }

    private static void testSorting(){
        ArrayList<Integer> vector = new ArrayList<Integer>();
        SortingTask sortingTask=new SortingTask("1","description",vector);
        vector.add(1);
        vector.add(5);
        vector.add(3);
        vector.add(0);
        System.out.print("Original vector: ");
        for(Integer i:vector){
            System.out.print(i+" ");
        }
        System.out.println(" ");
        sortingTask.execute();
        System.out.print("Sorted vector: ");
        for(Integer i:vector){
            System.out.print(i+" ");
        }
        System.out.println(" ");
    }

    private static void addTaskInRunner(TaskRunner runner){
        runner.addTask(new MessageTask("1","Feedback MAP","Ai nota 10","Prof","student",LocalDateTime.now()));
        runner.addTask(new MessageTask("2","Feedback BD","Ai nota 7","Prof","student",LocalDateTime.now()));
        runner.addTask(new MessageTask("3","Feedback PS","Ai nota 8","Prof","student",LocalDateTime.now()));
        runner.addTask(new MessageTask("4","Feedback PLF","Ai nota 9","Prof","student",LocalDateTime.now()));
        runner.addTask(new MessageTask("5","Feedback RC","Ai nota 10","Prof","student",LocalDateTime.now()));
    }

    private static void testRunner10(Strategy str){
        StrategyTaskRunner runner=new StrategyTaskRunner(str);
        addTaskInRunner(runner);
        runner.executeAll();
    }

    private static void testRunner13(Strategy str){
        StrategyTaskRunner runner=new StrategyTaskRunner(str);
        addTaskInRunner(runner);
        runner.executeAll();
        TaskRunner decorator=new PrinterTaskRunner(runner);
        addTaskInRunner(decorator);
        decorator.executeAll();
    }

    private static void testRunner14(Strategy str){
        StrategyTaskRunner runner=new StrategyTaskRunner(str);
        addTaskInRunner(runner);
        runner.executeAll();
        TaskRunner decorator=new PrinterTaskRunner(runner);
        addTaskInRunner(decorator);
        System.out.println("Printer: ");
        decorator.executeAll();
        decorator =new DelayTaskRunner(decorator);
        addTaskInRunner(decorator);
        System.out.println("Delay: ");
        decorator.executeAll();
    }

    public static void main(String[] args) {
        Task[] tasks=createTasks();
        for (Task t:tasks) {
            System.out.println(t.toString());
        }
        System.out.println("Test Ex 3");
        testSorting();
        System.out.println("Test Ex 10");
        testRunner10(Strategy.valueOf(args[0]));
        System.out.println("Test Ex 13");
        testRunner13(Strategy.valueOf(args[0]));
        System.out.println("Test Ex 14");
        testRunner14(Strategy.valueOf(args[0]));
    }


}
