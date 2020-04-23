package indi.gxwu.testCode;

import java.util.*;

/**
 * @Author: gx.wu
 * @Date: 2020/1/20 12:10
 * @Description: 给定一个字符串，表示CPU需要执行的任务。 这个字符串由大写字母A到Z构成，不同的字母代表不同的任务。完成任务不需要按照给定的顺序。
 * 每项任务都可以在一个单位时间内被完成。 在每个单位时间，CPU可以选择完成一个任务或是不工作。
 * 但是，题目会给定一个非负的冷却时间“n”，表示在执行两个“相同的任务”之间，必须至少有n个单位时间，此时CPU不能执行该任务，只能执行其他任务或者不工作。
 * 您需要返回CPU完成所有给定任务所需的最少单位时间数。
 * 输入: tasks = ['A','A','A','B','B','B'], n = 2
 * 输出: 8
 * 解释:
 * A -> B -> idle -> A -> B -> idle -> A -> B.
 *
 * https://www.lintcode.com/problem/task-scheduler/description?_from=ladder&&fromId=139
 */
public class LeastIntervalTest {

    public static void main(String[] args) {
        LeastIntervalTest t = new LeastIntervalTest();
        char[] tasks;
        int n;

//        tasks = new char[]{'A','A','A','B','B','B'};
//        n = 2;
//        System.out.println(t.leastInterval(tasks, n));

//        tasks = new char[]{'A','A','A','B','B','B'};
//        n = 1;
//        System.out.println(t.leastInterval(tasks, n));

        String taskStr = "BFJJCHICEGCEJFGJBIBBCBGAJHCGDEHEHAHIAJCGBGHGH";
        n = 15;
        System.out.println(taskStr);
        System.out.println(t.leastInterval(taskStr.toCharArray(), n));
        System.out.println(t.leastInterval2(taskStr.toCharArray(), n));
        System.out.println(t.leastInterval2(taskStr.toCharArray(), 5));

    }

    /**
     * @param tasks: the given char array representing tasks CPU need to do
     * @param n: the non-negative cooling interval
     * @return: the least number of intervals the CPU will take to finish all the given tasks
     */
    public int leastInterval(char[] tasks, int n) {
        // write your code here
        Map<Character,Integer> map = new HashMap<Character, Integer>();
        int currentIndex = 0;
        int tryBeginIndex = Integer.MAX_VALUE;
        int taskIndex = 0;
        boolean hadUnHandle = false;
        while (true){
            Character character = getNextTask(tasks, taskIndex);
            if(character == null) {
                if(hadUnHandle) {
                    currentIndex++;
                    taskIndex = tryBeginIndex;
                    tryBeginIndex = Integer.MAX_VALUE;
                    hadUnHandle = false;
                    System.out.printf("idle->");
                }else {
                    break;
                }
            }else if(character == '0'){
                taskIndex++;
                continue;
            } else {
                int availableIndex = getAvailableIndex(character, map, currentIndex);
                if (availableIndex > currentIndex) {
                    if(taskIndex<tryBeginIndex){
                        tryBeginIndex = taskIndex;
                    }
                    hadUnHandle = true;
                    taskIndex++;
                    continue;
                }
                int nextAvailableIndex = availableIndex + n + 1;
                tasks[taskIndex] = '0';
                taskIndex++;
                currentIndex++;
                map.put(character, nextAvailableIndex);
                System.out.printf(character + "->");
                if(hadUnHandle){
                    taskIndex = tryBeginIndex;
                    hadUnHandle = false;
                }
            }
        }
        System.out.println();
        return currentIndex;
    }

    private Character getNextTask(char[] tasks, int index){
        if(index<tasks.length){
            return tasks[index];
        }else{
            return null;
        }
    }

    private int getAvailableIndex(Character character, Map<Character,Integer> availableMap, int currentIndex){
        int availableIndex;
        if(availableMap.containsKey(character)){
            availableIndex = availableMap.get(character);
            availableIndex = availableIndex<=currentIndex?currentIndex:availableIndex;
        }else{
            availableIndex = currentIndex;
        }
        return availableIndex;
    }

    public int leastInterval2(char[] tasks, int n) {
        List<CharacterTask> tl = new ArrayList<CharacterTask>();
        Map<Character,StringBuffer> map = new HashMap<Character, StringBuffer>();
        for(int i=0;i<tasks.length;i++){
            Character character = tasks[i];
            if(map.containsKey(character)){
                map.get(character).append(character);
            }else{
                StringBuffer buffer = new StringBuffer();
                buffer.append(character);
                map.put(character, buffer);
            }
        }
        for(Map.Entry<Character,StringBuffer> entry:map.entrySet()){
            tl.add(new CharacterTask(entry.getValue()));
        }
        Map<Character,Integer> indexMap = new HashMap<Character, Integer>();
        int currentIndex = 0;
        boolean hadFind;
        while (true){
            hadFind = false;
            Collections.sort(tl);
            for(int i=0;i<tl.size();i++){
                StringBuffer buffer = tl.get(i).taskBuffer;
                Character character = buffer.charAt(0);
                int availableIndex = getAvailableIndex(character, indexMap, currentIndex);
                if (availableIndex > currentIndex) {
                    continue;
                }
                int nextAvailableIndex = availableIndex + n + 1;
                currentIndex++;
                buffer.deleteCharAt(0);
                indexMap.put(character, nextAvailableIndex);
                System.out.printf(character + "->");
                hadFind = true;
                if(buffer.length()==0){
                    tl.remove(i);
                }
                break;
            }
            if(!hadFind){
                currentIndex++;
                System.out.printf("idle->");
            }
            if(tl.size()==0){
                System.out.println();
                break;
            }
        }
        return currentIndex;
    }


    class CharacterTask implements Comparable<CharacterTask>{
        public StringBuffer taskBuffer;
        public CharacterTask(){
            taskBuffer = new StringBuffer();
        }
        public CharacterTask(StringBuffer buffer){
            taskBuffer = buffer;
        }

        @Override
        public int compareTo(CharacterTask characterTask){
            return characterTask.taskBuffer.length() - this.taskBuffer.length();
        }
    }
}
