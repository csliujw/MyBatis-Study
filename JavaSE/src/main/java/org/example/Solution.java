package org.example;

import java.util.Collections;
import java.util.LinkedList;

/**
 * 旅行售货员问题--优先队列式分支限界法
 */
public class Solution {
    float[][] graph;//图G的邻接矩阵

    // 初始化邻接矩阵
    public Solution(float[][] graph) {
        this.graph = graph;
    }

    public static class HeapNode implements Comparable {
        float lcost;//子树费用的下界 即代价函数（极小化问题的代价函数是下界） 也是优先队列的优先级
        float currentCost;//当前费用  计算最终的值时需要用到上一结点的值
        float rcost;//x[s:n-1]中顶点最小出边费用和
        int s;//根节点到当前节点的路径为x[0:s]
        int[] x;//需要进一步搜索的顶点是x[s+1:n-1]

        public HeapNode(float lcost, float currentCost, float rcost, int s, int[] x) {
            this.lcost = lcost;
            this.currentCost = currentCost;
            this.rcost = rcost;
            this.s = s;
            this.x = x;
        }

        /**
         * 排序规则  小顶堆，lcost小的在顶部
         */
        public int compareTo(Object x) {
            float xlc = ((HeapNode) x).lcost;
            if (lcost < xlc) return -1;
            if (lcost == xlc) return 0;
            return 1;
        }
    }


    public float bbTsp(int[] v) {
        int n = v.length - 1;//节点数
        LinkedList<HeapNode> heap = new LinkedList<HeapNode>();

        //minOut[i]=i的最小出边费用 A-->B  A为出边
        float[] minOut = new float[n + 1];  // 预先计算每个点的最小出边，好计算代价函数
        float minSum = 0;//最小出边费用和
        for (int i = 1; i <= n; i++) {//针对每个节点，找到最小出边
            //计算minOut[i]和minSum
            float min = Float.MAX_VALUE;
            for (int j = 1; j <= n; j++) {
                if (graph[i][j] != 0 && graph[i][j] < Float.MAX_VALUE && graph[i][j] < min)
                    min = graph[i][j];
            }
            if (min == Float.MAX_VALUE)
                return Float.MAX_VALUE;
            minOut[i] = min;
            minSum += min;
        }

        //初始化
        int[] x = new int[n];
        // 顶点都是从索引为1处开始的
        for (int i = 0; i < n; i++)
            x[i] = i + 1;
        // 以最小出边费用和 为优先级 进行排序  开始点 没有连接任何边，所以minSum
        HeapNode enode = new HeapNode(0, 0, minSum, 0, x);
        // 定义界
        float bestc = Float.MAX_VALUE;

        //搜索排列空间树
        while (enode != null && enode.s < n - 1) {
            //非叶节点
            x = enode.x;
            if (enode.s == n - 2) {
                //当前扩展结点是叶节点的父节点
                //再加两条边构成回路
                //所构成回路是否优于当前最优解
                if (graph[x[n - 2]][x[n - 1]] != 0 && graph[x[n - 1]][1] != 0 && (enode.currentCost + graph[x[n - 2]][x[n - 1]] + graph[x[n - 1]][1] < bestc)) {
                    //找到费用更小的回路
                    bestc = enode.currentCost + graph[x[n - 2]][x[n - 1]] + graph[x[n - 1]][1];
                    enode.currentCost = bestc;
                    enode.lcost = bestc;
                    enode.s++;
                    heap.add(enode);
                    Collections.sort(heap);
                }
            } else {//内部结点
                //产生当前扩展结点的儿子结点 可能的儿子结点的个数为  s+1 ~ n-1
                for (int i = enode.s + 1; i < n; i++) {
                    if (graph[x[enode.s]][x[i]] != 0) {
                        //可行儿子结点
                        float cc = enode.currentCost + graph[x[enode.s]][x[i]];
                        float rcost = enode.rcost = minOut[x[enode.s]];
                        float b = cc + rcost;//下界
                        // 代价函数与界的比较
                        if (b < bestc) {
                            //子树可能含有最优解，结点插入最小堆
                            int[] xx = new int[n];
                            for (int j = 0; j < n; j++)
                                xx[j] = x[j];
                            xx[enode.s + 1] = x[i];
                            xx[i] = x[enode.s + 1];
                            HeapNode node = new HeapNode(b, cc, rcost, enode.s + 1, xx);
                            heap.add(node);
                            Collections.sort(heap);
                        }
                    }
                }
            }
            //取下一个扩展结点
            enode = heap.poll();
        }
        //将最优解复制到v[1...n]
        for (int i = 0; i < n; i++)
            v[i + 1] = x[i];
        return bestc;
    }

    public static void main(String[] args) {
//        int n = 5;
//        float[][] weight = {
//                {0, 0, 0, 0, 0, 0},
//                {0, 0, 5, 0, 7, 9},
//                {0, 5, 0, 10, 3, 6},
//                {0, 0, 10, 0, 8, 0},
//                {0, 7, 3, 8, 0, 4},
//                {0, 9, 6, 0, 4, 0}};
//        int n = 4;
//        float[][] weight = {
//                {0, 0, 0, 0, 0},
//                {0, 0, 30, 6, 4},
//                {0, 30, 0, 5, 10},
//                {0, 6, 5, 0, 20},
//                {0, 4, 10, 20, 0}
//        };//a下标从1开始，0用来凑数；
        int n = 4;
        float[][] weight = {
                {0, 0, 0, 0, 0},
                {0, 0, 5, 9, 4},
                {0, 5, 0, 13, 2},
                {0, 9, 13, 0, 7},
                {0, 4, 2, 7, 0},

        };
        Solution b = new Solution(weight);
        int[] v = new int[n + 1];
        System.err.println("最短回路长为：" + b.bbTsp(v));
        System.err.print("最短回路为：");
        for (int i = 1; i <= n; i++) {
            System.err.print(v[i] + " ");
        }
    }
}


