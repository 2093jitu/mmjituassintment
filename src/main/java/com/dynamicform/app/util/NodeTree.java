package com.dynamicform.app.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammad Lockman
 *
 */



public class NodeTree {

    private Long childId;
    private Long parentId;

    private String displayValue;
    private String pageLink;
    private String iconName;
    private NodeTree parent;

    private List<NodeTree> children;
    
    
    public NodeTree() {
        super();
        this.children = new ArrayList<>();
    }
    

    public NodeTree(String displayVal,String pageLink, String iconName, Long childId, Long parentId) {
        this.displayValue = displayVal;
        this.pageLink = pageLink;
        this.iconName = iconName;
        this.childId = childId;
        this.parentId = parentId;
        this.children = new ArrayList<>();
    }

	public Long getChildId() {
		return childId;
	}

	public void setChildId(Long childId) {
		this.childId = childId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getPageLink() {
		return pageLink;
	}

	public void setPageLink(String pageLink) {
		this.pageLink = pageLink;
	}

	public NodeTree getParent() {
		return parent;
	}

	public void setParent(NodeTree parent) {
		this.parent = parent;
	}

	public List<NodeTree> getChildren() {
		return children;
	}

	public void setChildren(List<NodeTree> children) {
		this.children = children;
	}
	  

    public String getIconName() {
		return iconName;
	}


	public void setIconName(String iconName) {
		this.iconName = iconName;
	}


	public void addChild(NodeTree child) {
        if (!this.children.contains(child) && child != null)
            this.children.add(child);
    }
    
    

}
