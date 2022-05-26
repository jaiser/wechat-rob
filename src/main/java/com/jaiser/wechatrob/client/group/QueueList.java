package com.jaiser.wechatrob.client.group;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 自定义队列
 *
 * @author Jaiser on 2022/5/26
 */
public class QueueList<T> {

    private int maxSize;   // 队列最多容纳数量
    private List<T> queueArray;
    private int front;   // 队头
    private int rear;	// 队尾

    public QueueList(int length) {
        maxSize = length;
        queueArray = new ArrayList<>(maxSize);
        front = 0;
        rear = -1;
    }

    /**
     * 入队,队满则去掉第一个
     */
    public void enQueue(T t){
        if (isFull()) {
            deQueue();
        }
        queueArray.add(t);
    }

    /**
     * 出队, 队空则返回null
     */
    @SuppressWarnings("unchecked")
    public T deQueue(){

        if(isEmpty()){   // 出队之前先检查队列是否为空， 为空则出null
            return null;
        }

        T t = (T) queueArray.get(0);  // 先获取第一个对象返回出去
        if (queueArray.remove(0) != null) {
            return t;
        }else {
            throw new RuntimeException("出队异常");
        }
    }

    /**查看对头数据项
     */
    @SuppressWarnings("unchecked")
    public T peek(){
        if(isEmpty()){   // 查看队头时，判断是否为空， 为空则出null
            return null;
        }
        return (T) queueArray.get(0);
    }

    /**
     * 队列是否为空
     */
    public boolean isEmpty(){
        return queueArray.isEmpty();
    }

    /**
     * 队列是否为满
     */
    public boolean isFull(){
        return queueArray.size() == maxSize;
    }

    /**
     * 获取队列的大小
     */
    public int queueSize(){
        return queueArray.size();
    }

    /**
     * 是否全等
     */
    public Boolean isCongruent() {
        if (isEmpty()) {
            return false;
        }
        for (int i = 0; i < queueSize() - 1; i++) {
            if (!Objects.equals(queueArray.get(i), queueArray.get(i + 1))) {
                return false;
            }
        }
        return true;
    }


//    public static void main(String[] args) {
//        QueueList<String> queue = new QueueList<>(5);
//        System.out.print("是否全等：" + queue.isCongruent());
//        queue.enQueue("a");
//        queue.enQueue("a");
//
//
//        queue.enQueue("b");
//        queue.enQueue("d");
//        queue.enQueue("e");
//        queue.enQueue("f");
//        queue.enQueue("1");
////        queue.deQueue();
////        queue.deQueue();
//
//        System.out.println("队列是否为空： " + queue.isEmpty() + "  队列是否满： " + queue.isFull());
//        System.out.println("队列大小：" + queue.queueSize());
//        System.out.print("是否全等：" + queue.isCongruent());
//        int size = queue.queueSize();
//        for(int i = 0; i < size; i++){
//            String str = queue.deQueue();
//            System.out.print(str + " ");
//        }
//
//    }
}