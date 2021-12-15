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
import java.util.Map;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * <p>Represents an bootstrap <code>collapse</code> element
 * <p><strong>Attributes</strong> <br>
 * <strong>header </strong> collapse link title<br>
 * <strong>look   </strong> styles look attribute: [primary , secondary, info , success , warning , danger , dark , light] <br>
 * 
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:collapse header="press me to see" look="primary" &gt; Collapsed text  &lt;tb:/collapse&gt; <br>
 * &lt;tb:collapse header="press me to see" look="info"    &gt; Collapsed text  &lt;tb:/collapse&gt; <br> 
 * 
 */
public class TagBsCollapse extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "collapse";
	private static final String TAG_HTML       = "div";
	private static final String TAG_BOOTSCLASS = "collapse";
	private static final int    PRECEDENCE = 1000;
	 
    public TagBsCollapse(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
	
	
	protected enum PropertyKeys {				
		header
		,look;				
	 
	   private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	
    
    public String getHeader() {
    	return this.getAttributeValue(PropertyKeys.header.toString());
    }      
	public String getLook() {
    	String ret=this.nvl(this.getAttributeValue(PropertyKeys.look.toString()),"");
    	if (ret.isBlank()) { ret="light";}
    	    	
    	if ("error".equals(ret)) {ret="danger";}
    	return ret;
    }      
    
    
        
    @Override
    public String getHtmlTagStyleClass() {    	    	
    	return super.getHtmlTagStyleClass();
    }
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    }     
        
    @Override
    public void encodeChildren(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	IModelFactory modelFactory = context.getModelFactory();
    	String id=this.nvl(this.getId(),"");
    	
    	
		String             containerTag = "div";
		Map<String,String> containerAttr= new HashMap<String,String>();
		String             containerId  = id+"_clps";
		if (id.isBlank()) {containerId=this.randomId();}
		containerAttr.put("class", TAG_BOOTSCLASS);
		containerAttr.put("id", containerId);
		
		//create link model		
		String             linkTag = "a";
		String             linkVal = this.getHeader();
		Map<String,String> linkAttr= new HashMap<String,String>();
		linkAttr.put("class", "link link-"+this.getLook());
		linkAttr.put("href", "#"+containerId);		
		linkAttr.put("data-toggle", TAG_BOOTSCLASS);
		 
		model.add( modelFactory.createOpenElementTag(linkTag, linkAttr, null, false)  );
		model.add( modelFactory.createText(linkVal));
		model.add( modelFactory.createCloseElementTag(linkTag));
		//create container model
	    model.add( modelFactory.createOpenElementTag(containerTag, containerAttr, null, false)  );		
	    super.encodeChildren(context, model, attributes);
	    model.add( modelFactory.createCloseElementTag(containerTag)  );	    	        			    	
    }


    
   
}
