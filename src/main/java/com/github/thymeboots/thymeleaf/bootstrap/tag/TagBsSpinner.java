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
 * <p>Represents an bootstrap <code>spinner</code> element
 * <p><strong>Attributes</strong> <br>
 * <strong>look     </strong> spinner look: [info,warning,success,danger,primary,secondary ] <br>
 * <strong>size     </strong> spinner size: [sm,md,lg,xl,xxl] <br> 
 * <strong>type     </strong> spinner type: [border,grow ] <br>
 * <strong>value    </strong> spinner text: [loading..n ...] <br>
 * 
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:spinner type="border" value="loading" size="sm" look="info"&gt;  &lt;tb:/spinner&gt; <br>
 * &lt;tb:spinner type="grow"   value="loading" size="md" look="danger"&gt;  &lt;tb:/spinner&gt; <br>
 * 
 */

public class TagBsSpinner extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "spinner";
	private static final String TAG_HTML       = "span";
	private static final String TAG_BOOTSCLASS = "spinner";	
	private static final String SPIN_TYPE_BORDER = "border";
	private static final int    PRECEDENCE = 1000;
	
    public TagBsSpinner(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
	
	protected enum PropertyKeys {
		look
		,size
		,type		
		,value; 
			 
	   private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	
    
    
    public String getLook() {
    	String ret =  this.nvl(this.getAttributeValue(PropertyKeys.look.toString()),"");
    	if (ret.isBlank()) { ret="muted";}
    	
    	if (ret.equals("error") ) { ret="danger";}
    	return ret;
    }      
    public String getSize() {
    	return this.getAttributeValue(PropertyKeys.size.toString());
    }          
    public String getType() {
    	String ret = this.nvl(getAttributeValue(PropertyKeys.type.toString()),"");
    	if (ret.isBlank()) { ret=SPIN_TYPE_BORDER;}
    	return ret;
    }          
    public String getValue() {
    	return this.getAttributeValue(PropertyKeys.value.toString());
    }          
    
        
    @Override
    public String getHtmlTagStyleClass() {    	
    	String ret=TAG_BOOTSCLASS+"-"+this.getType();
    	
    	String size=this.nvl(getSize(),"");
    	if (!size.isBlank()) {ret=ret+" "+ret+"-"+size;}    	
    	
    	ret=ret+" "+"text-"+this.getLook();
    	return ret;
    }
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    }  
    @Override
	public void encodeBegin(ITemplateContext context, IModel model, Map<String,String> attributes )  {
    	//Create Wrapper Tag (<span>)  
    	String              wrapperTag="span";
    	Map<String,String> wrapperAttr=null;
    	IModelFactory modelFactory = context.getModelFactory();
    	model.add(modelFactory.createOpenElementTag(wrapperTag, wrapperAttr, null, false));    	
    	super.encodeBegin(context, model, attributes);
	}	
    @Override
    public void encodeEnd(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	super.encodeEnd(context, model, attributes);
    	
    	//Write value text and Close Wrapper Tag (<span>) 
    	IModelFactory modelFactory = context.getModelFactory();    	
		String value=this.nvl(this.getValue(),"");
		if (!value.isBlank()) {model.add(modelFactory.createText(value));}
    	//Close wrap
    	String              wrapperTag="span";
    	model.add(modelFactory.createCloseElementTag(wrapperTag));	
    	    	        	
    }    
          
}
