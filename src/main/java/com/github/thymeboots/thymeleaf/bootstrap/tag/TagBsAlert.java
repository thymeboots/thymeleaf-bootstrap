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

import java.util.Map;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * <p>Represents an bootstrap <code>alert</code> element
 * <p><strong>Attributes</strong> <br/>
 * <strong>look  or severity  </strong> styles look attribute: [primary , secondary, info , success , warning , danger , dark , light] <br>
 * <strong>closable</strong> is closable : true <br>
 * <strong>header  </strong> alert title<br/>
 * <strong>value   </strong> alert text message<br>
 * 
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:alert severity="warning" header="Warning" closable="true" value="Alert message" &gt;  &lt;/tb:alert&gt; <br>
 * &lt;tb:alert look="warning" value="Alert message" &gt;  &lt;/tb:alert&gt; <br> 
 * 
 */
public class TagBsAlert extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "alert";
	private static final String TAG_BOOTSCLASS = "alert";
	private static final int    PRECEDENCE = 1000;
	
    public TagBsAlert(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
    
	protected enum PropertyKeys {
		/**closable [true, false]*/
		closable
		/**title */
		,header		
		/**look or severity-[success,info,warning,danger]*/
		,look	
		/**look or severity-[success,info,warning,danger]*/
		,severity				
		/**value*/
		,value; 
			 
       private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	
    
    
    public String getClosable() {
    	return this.getAttributeValue(PropertyKeys.closable.toString());
    }      
    public String getHeader() {
    	return this.getAttributeValue(PropertyKeys.header.toString());
    }      
    public String getLook() {
    	String ret=this.nvl(this.getAttributeValue(PropertyKeys.look.toString()),"");
    	if (ret.isBlank()) { ret="light";}
    	
    	String severity=this.nvl(this.getAttributeValue(PropertyKeys.severity.toString()),"");
    	if (!severity.isBlank()) { ret=severity;}
    	
    	if ("error".equals(ret)) {ret="danger";}
    	return ret;
    }      
    
    public String getValue() {
    	return this.getAttributeValue(PropertyKeys.value.toString());
    }      
    
    private String lookClass() {
    	String ret="";
    	String val =getLook();
    	if (val!=null) {
			if (!val.isBlank()) {
				if (val.equals("error")) { val="danger";}
				val=val.trim();
				ret=TAG_BOOTSCLASS+"-"+val;
			}    		
    	}    	
    	return ret;
    }    
    
    @Override
    public String getHtmlTagStyleClass() {
    	String ret=TAG_BOOTSCLASS;
    	
    	String severity=lookClass() ;
    	ret=(ret+" "+severity).trim();
    	    	
    	return ret;
    }
    
    protected String getIconClass(String icon) {
    	String ret=TagBsIcon.getIconClass(icon);
    	return ret;
    }
    @Override
    public void encodeChildren(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	String closable=this.nvl(this.getClosable(), "");
    	if ("true".equals(closable)) {
    		model.addModel(TagBsButton.createModel(context, "x", "close", "alert", null, null , null,false));
    	}
    	
    	String title=this.getHeader();
    	if (title!=null) {
    		model.addModel(this.createModel(context, "strong", title));
    		model.addModel(this.createModelText(context, "<br/>"));
    	}
    	
    	String severity=this.nvl(this.getLook(),"");
    	if (!severity.isBlank()) {
    		String severtyClass="";
    		if (severity.equals("info")) {
    			severtyClass= this.getIconClass("info-circle");
    		}
    		else if (severity.equals("warning")) {
    			severtyClass=this.getIconClass("exclamation-circle");
    		}
    		else if (severity.equals("success")) {
    			severtyClass=this.getIconClass("check-circle");
    		}
    		else if (severity.equals("danger") || severity.equals("error")) {
    			severtyClass=this.getIconClass("times-circle");
    		} 
    		else {
    			severtyClass=this.getIconClass("exclamation");    			
    		}
    		model.addModel(this.createModel(context, "span", "", severtyClass));
    	}
						
		//print message
		String value=this.getValue();
		if (value!=null) {
			model.addModel(this.createModelText(context, " "+value));
		}
		
    	
    	super.encodeChildren(context, model, attributes);
    }
    
   
}
