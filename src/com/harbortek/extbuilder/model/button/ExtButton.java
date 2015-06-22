package com.harbortek.extbuilder.model.button;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.harbortek.extbuilder.code.CodeContext;
import com.harbortek.extbuilder.xmodel.ExtElement;
import com.harbortek.extbuilder.xmodel.ExtScript;

public class ExtButton extends ExtElement {
	private static final long serialVersionUID = 1L;

	public static final String TYPE = "type";

	public static final String TOOLTIP_TYPE = "tooltipType";

	public static final String TOOLTIP = "tooltip";

	public static final String TOGGLE_GROUP = "toggleGroup";

	public static final String TEXT = "text";

	public static final String TAB_INDEX = "tabIndex";

	public static final String PRESSED = "pressed";

	public static final String MIN_WIDTH = "minWidth";

	public static final String MENU_ALIGN = "menuAlign";

	public static final String ICON_CLS = "iconCls";

	public static final String BUTTON_ICON = "buttonIcon";

	public static final String HIDDEN = "hidden";

	public static final String HANDLE_MOUSE_EVENTS = "handleMouseEvents";

	public static final String ENABLE_TOGGLE = "enableToggle";

	public static final String DISABLED = "disabled";

	public static final String CLICK_EVENT = "clickEvent";

	private String clickEvent = "click";

	private Boolean disabled = Boolean.FALSE;

	private Boolean enableToggle = Boolean.FALSE;

	private Boolean handleMouseEvents = Boolean.TRUE;

	private Boolean hidden = Boolean.FALSE;

	private String buttonIcon = "";

	private String iconCls = "";

	private String menuAlign = "tl-bl?";

	private Integer minWidth = new Integer(0);

	private Boolean pressed = Boolean.FALSE;

	private Integer tabIndex = new Integer(0);

	private String text = "";

	private String toggleGroup = "";

	private String tooltip = "";

	private String tooltipType = "qtip";

	private String type = "button";

	private String handler = "";

	private String scope = "this";

	public ExtButton() {
		setUseXtype(true);
	}

	public String getClickEvent() {
		return clickEvent;
	}

	public void setClickEvent(String clickEvent) {
		this.clickEvent = clickEvent;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getEnableToggle() {
		return enableToggle;
	}

	public void setEnableToggle(Boolean enableToggle) {
		this.enableToggle = enableToggle;
	}

	public Boolean getHandleMouseEvents() {
		return handleMouseEvents;
	}

	public void setHandleMouseEvents(Boolean handleMouseEvents) {
		this.handleMouseEvents = handleMouseEvents;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public String getButtonIcon() {
		return buttonIcon;
	}

	public void setButtonIcon(String buttonIcon) {
		this.buttonIcon = buttonIcon;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getMenuAlign() {
		return menuAlign;
	}

	public void setMenuAlign(String menuAlign) {
		this.menuAlign = menuAlign;
	}

	public Integer getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(Integer minWidth) {
		this.minWidth = minWidth;
	}

	public Boolean getPressed() {
		return pressed;
	}

	public void setPressed(Boolean pressed) {
		this.pressed = pressed;
	}

	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getToggleGroup() {
		return toggleGroup;
	}

	public void setToggleGroup(String toggleGroup) {
		this.toggleGroup = toggleGroup;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getTooltipType() {
		return tooltipType;
	}

	public void setTooltipType(String tooltipType) {
		this.tooltipType = tooltipType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Object translate(CodeContext codeContext) {
		Map result = new HashMap();

		if (!"click".equals(getClickEvent())) {
			result.put(CLICK_EVENT, getClickEvent());
		}
		if (getDisabled().booleanValue()) {
			result.put(DISABLED, getDisabled());
		}
		if (getEnableToggle().booleanValue()) {
			result.put(ENABLE_TOGGLE, getEnableToggle());
		}
		if (!getHandleMouseEvents().booleanValue()) {
			result.put(HANDLE_MOUSE_EVENTS, getHandleMouseEvents());
		}
		if (getHidden().booleanValue()) {
			result.put(HIDDEN, getHidden());
		}
		if (StringUtils.isNotEmpty(getButtonIcon())) {
			result.put(BUTTON_ICON, getButtonIcon());
		}
		if (StringUtils.isNotEmpty(getIconCls())) {
			result.put(ICON_CLS, getIconCls());
		}
		if (!"tl-bl?".equals(getMenuAlign())) {
			result.put(MENU_ALIGN, getMenuAlign());
		}
		if (getMinWidth().intValue() > 0) {
			result.put(MIN_WIDTH, getMinWidth());
		}
		if (getPressed().booleanValue()) {
			result.put(PRESSED, getPressed());
		}
		if (getTabIndex().intValue() > 0) {
			result.put(TAB_INDEX, getTabIndex());
		}
		if (StringUtils.isNotEmpty(getText())) {
			result.put(TEXT, getText());
		}
		if (StringUtils.isNotEmpty(getToggleGroup())) {
			result.put(TOGGLE_GROUP, getToggleGroup());
		}
		if (StringUtils.isNotEmpty(getTooltip())) {
			result.put(TOOLTIP, getTooltip());
		}
		if (!"qtip".equals(getTooltipType())) {
			result.put(TOOLTIP_TYPE, getTooltipType());
		}
		if (!"button".equals(getXtype())) {
			result.put("xtype", getXtype());
		}

		if (StringUtils.isNotEmpty(getHandler())) {
			StringBuffer sb = new StringBuffer();
			sb.append("function(button,event");
			sb.append("){").append("\n");
			if (codeContext.isForPreview()) {
				sb.append("alert('").append(getText()).append(" clicked!')").append("\n}");
			} else {
				sb.append("this.").append(getHandler()).append("(button,event");
				sb.append(");").append("\n");
				sb.append("}.createDelegate(this)");
			}
			result.put("handler", new ExtScript(sb.toString()));
		}

		return result;
	}

	public String getExtClassName() {
		return "Ext.Button";
	}

	public String getXtype() {
		return "button";
	}

}
