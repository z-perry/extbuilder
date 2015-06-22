package com.harbortek.extbuilder.constant;

import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.harbortek.extbuilder.model.grid.ExtColumnModel;
import com.harbortek.extbuilder.utils.properties.BooleanPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.IntegerPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.MultiLineStringPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.ObjectChooserPropertyDescriptor;
import com.harbortek.extbuilder.utils.properties.StandardComboBoxPropertyDescriptor;


public class PropertyConstant implements PropertyLabelConstant{
	

	public static final PropertyDescriptor PROP_AUTOHEIGHT =  new TextPropertyDescriptor(AUTOHEIGHT, AUTOHEIGHT);
	public static final PropertyDescriptor PROP_AUTOWIDTH =  new TextPropertyDescriptor(AUTOWIDTH, AUTOWIDTH);
	public static final PropertyDescriptor PROP_BODYBORDER =  new BooleanPropertyDescriptor(BODYBORDER, BODYBORDER);
	public static final PropertyDescriptor PROP_FRAME =  new BooleanPropertyDescriptor(FRAME, FRAME);
	public static final PropertyDescriptor PROP_HEADER =  new BooleanPropertyDescriptor(HEADER, HEADER);
	public static final PropertyDescriptor PROP_HEADERASTEXT =  new BooleanPropertyDescriptor(HEADERASTEXT, HEADERASTEXT);
	public static final PropertyDescriptor PROP_HEIGHT =  new TextPropertyDescriptor(HEIGHT, HEIGHT);
	public static final PropertyDescriptor PROP_HIDEBORDERS =  new BooleanPropertyDescriptor(HIDEBORDERS, HIDEBORDERS);
	public static final PropertyDescriptor PROP_HIDECOLLAPSETOOL =  new BooleanPropertyDescriptor(HIDECOLLAPSETOOL, HIDECOLLAPSETOOL);
	public static final PropertyDescriptor PROP_ID =  new TextPropertyDescriptor(ID, ID);
	public static final PropertyDescriptor PROP_LAYOUT =  new StandardComboBoxPropertyDescriptor(LAYOUT, LAYOUT,LayoutConstant.LAYOUT_VALUES,LayoutConstant.LAYOUT_VALUES);
	public static final PropertyDescriptor PROP_MASKDISABLED =  new BooleanPropertyDescriptor(MASKDISABLED, MASKDISABLED);
	public static final PropertyDescriptor PROP_MINBUTTONWIDTH =  new TextPropertyDescriptor(MINBUTTONWIDTH, MINBUTTONWIDTH);
	public static final PropertyDescriptor PROP_MONITORRESIZE =  new BooleanPropertyDescriptor(MONITORRESIZE, MONITORRESIZE);
	public static final PropertyDescriptor PROP_SHIM =  new BooleanPropertyDescriptor(SHIM, SHIM);
	public static final PropertyDescriptor PROP_TITLE =  new TextPropertyDescriptor(TITLE, TITLE);
	public static final PropertyDescriptor PROP_TITLECOLLAPSE =  new BooleanPropertyDescriptor(TITLECOLLAPSE, TITLECOLLAPSE);
	public static final PropertyDescriptor PROP_WIDTH =  new TextPropertyDescriptor(WIDTH, WIDTH);
	public static final PropertyDescriptor PROP_REGION =  new TextPropertyDescriptor(REGION, REGION);
	public static final PropertyDescriptor PROP_LABEL = new TextPropertyDescriptor(LABEL, LABEL);
	public static final PropertyDescriptor PROP_LABELWIDTH = new IntegerPropertyDescriptor(LABELWIDTH,LABELWIDTH);
	public static final PropertyDescriptor PROP_ACTIVETAB = new IntegerPropertyDescriptor(ACTIVETAB,ACTIVETAB);
	public static final PropertyDescriptor PROP_URL =  new TextPropertyDescriptor(URL, URL);
	public static final PropertyDescriptor PROP_BASEPARAMS =  new MultiLineStringPropertyDescriptor(BASEPARAMS, BASEPARAMS);
	//public static final PropertyDescriptor PROP_STORE =  new DataStorePropertyDescriptor(STORE, STORE);
	public static final PropertyDescriptor PROP_COLUMNMODEL =  new ObjectChooserPropertyDescriptor(COLUMNMODEL, COLUMNMODEL,ExtColumnModel.class);
	public static final PropertyDescriptor PROP_COMPONENTNAME =  new TextPropertyDescriptor(COMPONENTNAME, COMPONENTNAME);
	public static final PropertyDescriptor PROP_NAME =  new TextPropertyDescriptor(NAME, NAME);
	public static final PropertyDescriptor PROP_PACKAGENAME = new TextPropertyDescriptor(PACKAGENAME, PACKAGENAME);
	public static final PropertyDescriptor PROP_CLASSNAME = new TextPropertyDescriptor(CLASSNAME, CLASSNAME);
	public static final PropertyDescriptor PROP_CHECKBOXNAME = new TextPropertyDescriptor(CHECKBOXNAME, CHECKBOXNAME);
	public static final PropertyDescriptor PROP_CHECKBOXTOGGLE = new BooleanPropertyDescriptor(CHECKBOXTOGGLE, CHECKBOXTOGGLE);
	
}
