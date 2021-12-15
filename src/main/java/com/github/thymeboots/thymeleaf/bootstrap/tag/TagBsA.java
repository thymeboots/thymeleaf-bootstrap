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
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * <p>
 *   Represents an HTML <code>a</code> element <br/>
 *   The label text is specified by the component value.
 * </p>
 * 
 * <p>
 *   <strong>Attributes</strong> <br/>
 *   <strong>look       </strong> Button styles look attribute: [outline-primary,primary , info , success , warning , danger , link] <br>
 *   <strong>value      </strong> Button label attribute<br>
 * </p>
 * 
 * <p>
 *   <strong>Examples</strong> <br> 
 *   &lt;tb:a href="#" value="Link name" look="primary" size="lg" &gt;  &lt;/tb:a&gt;
 * </p>
 * 
 * @author Rifat Yilmaz
 * 
 * @since 3.4.0
 */
public class TagBsA extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "a";
	private static final String TAG_HTML       = "a";
	private static final String TAG_BOOTSCLASS = "";
	private static final int    PRECEDENCE = 1000;
	
    public TagBsA(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
	
	protected enum PropertyKeys {
		href
		,look
		,size
		,value;
				
       private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	    
        
    public String getHref() {
    	return this.getAttributeValue(PropertyKeys.href.toString());
    }      
    
    public String getLook() {
    	String ret= this.getAttributeValue(PropertyKeys.look.toString());    	
    	if ("error".equals(ret)) {ret="danger";}
    	return ret;
    } 
    public String getSize() {
    	return this.getAttributeValue(PropertyKeys.size.toString());
    }      
            		
    public String getValue() {
    	return this.getAttributeValue(PropertyKeys.value.toString());
    }      
    
    
    private String lookClass() {
    	String ret="";
    	String val =getLook();
    	if (val!=null) {
			if (!val.isBlank()) {
				val=val.trim();
				ret=TAG_BOOTSCLASS+"-"+val;
			}    		
    	}    	
    	return ret;
    }    
    
    private String sizeClass() {
    	String ret="";
    	String val =getSize();
    	if (val!=null) {
			if (!val.isBlank()) {
				val=val.trim();
				ret=TAG_BOOTSCLASS+"-"+val;
			}    		
    	}    	
    	return ret;
    }    
        
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    } 
    @Override
    public String getHtmlTagStyleClass() {
    	String ret=TAG_BOOTSCLASS;
    	
    	String look=lookClass();
    	ret=(ret+" "+look).trim();
    	    	
    	String size=sizeClass();
    	ret=(ret+" "+size).trim();
    	
    	return ret;
    }
    @Override
	public void encodeBegin(ITemplateContext context, IModel model, Map<String,String> attributes )  {
	    super.encodeBegin(context, model, attributes);
	}	
    @Override
    public void encodeChildren(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	IModelFactory modelFactory = context.getModelFactory();		
    	String value=this.getValue();
    	if (value!=null) {
    	    model.add(modelFactory.createText(value));		    		
    	}
    	super.encodeChildren(context, model, attributes);  	
    }
    
    
}
