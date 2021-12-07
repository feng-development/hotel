/*
 *  Copyright 1999-2019 Evision Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.feng.hotel.utils.tree.handler;


import com.feng.hotel.base.Constants;
import com.feng.hotel.base.OrderType;
import com.feng.hotel.utils.tree.AbstractTreeNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 树节点建造处理器
 *
 * @author asheng
 * @since 2019/11/14
 */
public final class TreeBuilderHandler<T extends AbstractTreeNode<T>> {

    /**
     * 类字段处理器
     */
    private final FieldResolveHandler fieldResolveHandler;

    /**
     * 输入数组列表
     */
    private final List<T> targetList;

    /**
     * 树节点map，归类统一级别的节点
     */
    private final Map<Object, List<T>> treeNodeMap;

    /**
     * 是否完成了构造
     */
    private volatile boolean hasBuild = false;

    public TreeBuilderHandler(Collection<T> targetList) {
        if (targetList == null || targetList.isEmpty()) {
            throw new RuntimeException("target list is null or empty.");
        }

        this.treeNodeMap = new HashMap<>();
        this.targetList = new ArrayList<>(targetList);

        fieldResolveHandler = FieldResolveHandler.getInstance(this.targetList.get(0).getClass());
    }

    /**
     * 对树节点进行构造
     * <p>
     * 1.把所有节点按照级别放入map中
     * 2.比较好各个节点对排序
     * 3.依次取出，构建好树结构
     */
    private synchronized void build() {
        if (hasBuild) {
            return;
        }

        for (T t : targetList) {
            Object parentId = fieldResolveHandler.getParentId(t);

            List<T> levelTreeNode = treeNodeMap.computeIfAbsent(parentId, k -> new ArrayList<>());
            levelTreeNode.add(t);

            if (fieldResolveHandler.presentOrder()) {
                OrderType orderType = fieldResolveHandler.getOrderType();
                levelTreeNode.sort((o1, o2) -> {
                    Comparable<Object> orderVal1 = fieldResolveHandler.getOrder(o1);
                    Comparable<Object> orderVal2 = fieldResolveHandler.getOrder(o2);

                    if (orderVal1 == null || orderVal2 == null) {
                        throw new RuntimeException("sort field value can not be null.");
                    }

                    return OrderType.ASC.equals(orderType) ? orderVal1.compareTo(orderVal2) : orderVal2.compareTo(orderVal1);
                });
            }
        }

        Set<Map.Entry<Object, List<T>>> entries = treeNodeMap.entrySet();
        for (Map.Entry<Object, List<T>> entry : entries) {
            List<T> value = entry.getValue();
            value.forEach(n -> {
                List<T> treeNodes = treeNodeMap.get(fieldResolveHandler.getId(n));
                n.addChildren(treeNodes);
            });
        }

        hasBuild = true;
    }

    /**
     * 获取构建完成的整个树
     * {@link this#buildTree(Object)} 主要区别在于此方法的最顶层父节点为0
     *
     * @return 获取构建完成的整个树
     */
    public List<T> buildTree() {
        build();
        return treeNodeMap.get(Constants.DEFAULT_ROOT_ID);
    }

    /**
     * 获取构建完成的整个树
     * {@link this#buildTree()} 主要区别在于此方法的最顶层父节点为用户定义的内容
     *
     * @param rootId 根节点id （此处有坑，传入的根id应与树对象中的根节点 类型相同 ，eg：同为Long）
     * @return 获取构建完成的整个树
     */
    public List<T> buildTree(Object rootId) {
        build();
        return treeNodeMap.get(rootId);
    }

}
