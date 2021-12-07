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
package com.feng.hotel.utils.tree;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.feng.hotel.utils.ProtoStuffUtils;
import com.feng.hotel.utils.tree.handler.FieldResolveHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 抽象树节点
 *
 * @author asheng
 * @since 2019/11/14
 */
@ApiModel(value = "抽象树节点")
public abstract class AbstractTreeNode<T extends AbstractTreeNode<T>> implements Serializable {

    /**
     * 用此SerialVersionUID唯一标识抽象树
     */
    private static final long serialVersionUID = -10086L;

    /**
     * 父亲节点，在json序列化的时候对属性进行忽略
     * 1.存入redis的时候，忽略属性
     * 2.返回前端的时候，忽略属性
     */
    @ApiModelProperty(value = "父亲节点")
    @JSONField(serialize = false, deserialize = false)
    @JsonIgnore
    private transient T parent;

    /**
     * 孩子节点
     */
    @ApiModelProperty(value = "孩子节点列表")
    private final List<T> children;

    protected AbstractTreeNode() {
        this.parent = null;
        this.children = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractTreeNode<?> that = (AbstractTreeNode<?>) o;
        return Objects.equals(this.resolveNodeId(), that.resolveNodeId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.resolveNodeId());
    }

    /**
     * 获取当前节点的节点id
     *
     * @return 当前节点的节点id
     */
    public Object resolveNodeId() {
        FieldResolveHandler handler = FieldResolveHandler.getInstance(this.getClass());
        Object nodeId = handler.getId(this);
        if (Objects.isNull(nodeId)) {
            throw new RuntimeException("node id is null. class: '" + this.getClass().getName() + "'");
        }
        return nodeId;
    }

    /**
     * 添加多个孩子节点
     *
     * @param children 孩子节点列表
     */
    public void addChildren(List<T> children) {
        if (children == null || children.isEmpty()) {
            return;
        }
        children.forEach(this::addChild);
    }

    /**
     * 添加单个孩子节点
     *
     * @param child 单一子节点
     */
    @SuppressWarnings("unchecked")
    public void addChild(T child) {
        if (child == null) {
            return;
        }
        this.children.add(child);
        child.parent((T) this);
    }

    /**
     * 设置children的时候使用，用于反序列化
     *
     * @param children 孩子节点列表
     */
    public void setChildren(List<T> children) {
        if (children == null || children.isEmpty()) {
            return;
        }
        children.forEach(this::addChild);
    }

    /**
     * 获取该节点下的孩子节点
     *
     * @return 该节点下的孩子节点
     */
    public List<T> getChildren() {
        return this.children;
    }

    /**
     * 降该节点下的孩子节点置空
     * 也可以使用{@link this#getChildren()#clear()}方法进行
     */
    public void clear() {
        this.children.clear();
    }

    public T parent() {
        return parent;
    }

    public void parent(T parent) {
        this.parent = parent;
    }

    /**
     * 进行深拷贝处理，即将当前节点与孩子节点都进行深拷贝
     * 避免对象引用到同一对象导致脏对象问题
     *
     * @return 拷贝后的节点
     */
    public T copy() {
        //noinspection unchecked
        return (T) ProtoStuffUtils.clone(this);
    }

}
