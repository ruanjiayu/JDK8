package com.xian.jdk.until;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentZSet<K, V> {
    private SortedSet<ScoredValue<K, V>> sortedSet;
    private ConcurrentMap<K, ScoredValue<K, V>> valueMap;

    public static void main(String[] args) {
        ConcurrentZSet<String, String> zSet = new ConcurrentZSet<>();
        zSet.add(1.5, "key1", "value1");
        zSet.add(2.0, "key2", "value2");
        zSet.add(0.5, "key3", "value3");

        zSet.addBig(1.2, "key2", "value4"); // 插入相同的 key，进行覆盖操作

        SortedSet<String> range = zSet.rangeByScore(1.0, 2.0);
        System.out.println(range); // 输出: [value1, value4]
    }

    public ConcurrentZSet() {
        sortedSet = new ConcurrentSkipListSet<>();
        valueMap = new ConcurrentHashMap<>();
    }

    public void add(double score, K key, V value) {
        ScoredValue<K, V> scoredValue = new ScoredValue<>(score, key, value);
        ScoredValue<K, V> existingValue = valueMap.put(key, scoredValue);
        if (existingValue != null) {
            sortedSet.remove(existingValue);
        }
        sortedSet.add(scoredValue);
    }

    /**
     * 只增加元素大的
     *
     * @param score
     * @param key
     * @param value
     */
    public void addBig(double score, K key, V value) {
        ScoredValue<K, V> scoredValue = new ScoredValue<>(score, key, value);
        ScoredValue<K, V> existingValue = valueMap.put(key, scoredValue);
        if (existingValue != null) {
            // 分数大的才存下来
            if (existingValue.getScore() > score) {
                return;
            }
            sortedSet.remove(existingValue);
        }
        sortedSet.add(scoredValue);
    }

    public void remove(K key) {
        ScoredValue<K, V> removedValue = valueMap.remove(key);
        if (removedValue != null) {
            sortedSet.remove(removedValue);
        }
    }

    public SortedSet<V> rangeByScore(double minScore, double maxScore) {
        SortedSet<V> result = new ConcurrentSkipListSet<>();
        for (ScoredValue<K, V> sv : sortedSet) {
            if (sv.getScore() >= minScore && sv.getScore() <= maxScore) {
                result.add(sv.getValue());
            }
        }
        return result;
    }

    /**
     * 弹出小于这个值值
     * @param minScore
     * @return
     */
    public SortedSet<V> popByScore(double minScore) {
        SortedSet<V> result = new ConcurrentSkipListSet<>();
        Iterator<ScoredValue<K, V>> iterator = sortedSet.iterator();
        while (iterator.hasNext()) {
            ScoredValue<K, V> sv = iterator.next();
            if (sv.getScore() < minScore) {
                iterator.remove();
                valueMap.remove(sv.getKey());

                result.add(sv.getValue());
            } else {
                break; // 因为 sortedSet 是有序的，一旦遇到第一个不小于 minScore 的元素，后续的元素都不小于 minScore
            }
        }
        return result;
    }



    private static class ScoredValue<K, V> implements Comparable<ScoredValue<K, V>> {
        private double score;
        private K key;
        private V value;

        public ScoredValue(double score, K key, V value) {
            this.score = score;
            this.key = key;
            this.value = value;
        }

        public double getScore() {
            return score;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public int compareTo(ScoredValue<K, V> o) {
            int scoreComparison = Double.compare(score, o.score);
            if (scoreComparison != 0) {
                return scoreComparison;
            }
            return key.toString().compareTo(o.key.toString());
        }
    }
}
