package com.feng.hotel.utils.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author feng
 * @since 2021/12/8
 */
public class TreeUtils {
    /**
     * 递归把整个树的数据结构放入List返回，返回对象不为树结构
     * Note:返回列表将不会是树结构！！！
     *
     * @param tree 输入的list
     * @param <T>  抽象树节点
     * @return 抽象树节点 {@link AbstractTreeNode}
     */
    public static <T extends AbstractTreeNode<T>> List<T> resolveAll(List<T> tree) {
        if (tree == null || tree.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> result = new ArrayList<>();
        for (T t : tree) {
            if (t == null) {
                continue;
            }
            T node = t.copy();
            result.add(node);
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                List<T> sonList = resolveAll(node.getChildren());
                if (!sonList.isEmpty()) {
                    result.addAll(sonList);
                    sonList.forEach(AbstractTreeNode::clear);
                }
            }
            node.clear();
        }
        return result;
    }
}
