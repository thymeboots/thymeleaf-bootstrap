/*
 * =============================================================================
 *
 *   Copyright (c) 2021, The Rifat Yilmaz  (rifyilmaz.github.com)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */

package com.github.thymeboots.thymeleaf.bootstrap.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.ITemplateEvent;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * <p>Represents an bootstrap <code>icon</code> element
 * <p><strong>Attributes</strong> <br>
 * <strong>type     </strong> icon type: [fa , glyphicon ] <br>
 * <strong>value    </strong> icon name: [info-circle, check-circle , ok-sign ...] <br>
 * 
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:icon type="fa" value="info-circle" spin="true" &gt;  &lt;tb:/icon&gt; <br>
 * &lt;tb:icon type="glyphicon" value="ok-sign" &gt;  &lt;tb:/icon&gt; <br>
 * 
 * @author Rifat Yilmaz
 *
 * @since 3.4.0
 *
 */

public class TagBsIcon extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "icon";
	private static final String TAG_HTML       = "span";
	private static final String TAG_BOOTSCLASS = "fa";	
	private static final String SPIN_CLASS      = "spinner";
	private static final String SPIN_TYPE_BORDER= "border";
	private static final int    PRECEDENCE = 1000;
	
    public TagBsIcon(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
	
    public static String getIconClass(String picon) {
    	String icon=picon;
    	if (icon==null) {icon="";}
    	String ret=TAG_BOOTSCLASS+" "+TAG_BOOTSCLASS+"-"+icon.trim();
    	return ret;
    }
    public static String getSpinClass(String psin) {
    	String ret="";
    	String spin=psin;
    	if (spin==null) {spin="";}
    	if (spin.equals("true") ||  spin.equals("yes")) {
    		ret=SPIN_CLASS+"-"+SPIN_TYPE_BORDER;
    	}    	
    	return ret;
    }
    
	protected enum PropertyKeys {
		spin
		,type		
		,value; 
			 
	   private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	
    
    
    public String getSpin() {
    	String ret= this.nvl( this.getAttributeValue(PropertyKeys.spin.toString())  ,"").trim();
    	if ("yes".equals(ret)) { ret="true"; }
    	return ret;
    }          
    
    public String getValue() {
    	return this.getAttributeValue(PropertyKeys.value.toString());
    }          
    public String getType() {
    	String ret= this.nvl(this.getAttributeValue(PropertyKeys.type.toString()),"").trim();
    	if (ret.isBlank()) { ret=TAG_BOOTSCLASS;}    	
    	return ret;
    }      
        
    @Override
    public String getHtmlTagStyleClass() {
    	
    	String type= this.nvl(this.getType(),"");

    	String ret=type;
    	String value=this.nvl(this.getValue(),"");
    	if (!type.isBlank()) {
    		ret=ret+" "+type+"-"+value;
    	}
    	
    	//spin class eklenir
    	ret=ret+" "+getSpinClass(this.getSpin());
    	
    	ret=ret.trim();
    	return ret;
    }
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    }     
    
    public static IModel createTag(
			ITemplateContext context, 
			String icon, 
			String spin,
			List<ITemplateEvent> _childs
			) {  
    	
		String             htmlTag  = TAG_HTML;
		String             clazz    = getIconClass(icon)+" "+getSpinClass(spin); 				
		Map<String,String> props    = new HashMap<String,String>();    	
		props.put("class", clazz);
		
		IModelFactory modelFactory=context.getModelFactory();
		IModel model = modelFactory.createModel();
	    model.add(modelFactory.createOpenElementTag(htmlTag, props, null, false));
	    //model.add(modelFactory.createText( _value) );
	    if (_childs!=null && _childs.size()>0) {
		    for (int i = 0; i < _childs.size(); i++) {
		    	ITemplateEvent event=_childs.get(i);
		    	model.add(event);
		    }	    	
	    }
	    model.add(modelFactory.createCloseElementTag(htmlTag));
		return model;
	}   
    
          
}
