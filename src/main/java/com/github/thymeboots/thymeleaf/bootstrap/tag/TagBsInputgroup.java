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

import com.github.thymeboots.thymeleaf.bootstrap.comp.FacetNamesEnum;

/**
 * <p>Represents an bootstrap <code>inputgroup</code> element
 * <p><strong>Attributes</strong> <br>
 * <strong>append     </strong> append text. <br>
 * <strong>prepend    </strong> prepend text. <br>
 * <strong>look       </strong> text style. [none,default,text] none:no styles , default and text:input-group-text<br>
 * <strong>size       </strong> size. [sm,md,lg,xl] <br> 
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:inputgroup size="sm" prepend="$" append="0.00" look="default" &gt;<br>
 * &nbsp;&nbsp;&nbsp;&lt;input type="text" class="form-control"&gt; <br>
 * &lt;/tb:inputgroup&gt;
 * 
 */
public class TagBsInputgroup extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "inputgroup";
	private static final String TAG_HTML       = "div";
	private static final String TAG_BOOTSCLASS = "input-group";
	private static final int    PRECEDENCE = 1000;
	
	
    public TagBsInputgroup(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }     
    
	protected enum PropertyKeys {
		append
		,look
		,prepend				
		,size; 
			 
       private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	

	public String getAppend() {
    	return this.getAttributeValue(PropertyKeys.append.toString());
    }      
	public String getLook() {
    	return this.getAttributeValue(PropertyKeys.look.toString());
    }          
    public String getPrepend() {
    	return this.getAttributeValue(PropertyKeys.prepend.toString());
    }         
    public String getSize() {
    	return this.getAttributeValue(PropertyKeys.size.toString());
    }      
    
    @Override
    public String getHtmlTagStyleClass() {
    	String ret= TAG_BOOTSCLASS;
    	
    	String size=this.nvl(this.getSize(), "").trim();
    	if (!size.isBlank()) {
    		ret=ret+" "+TAG_BOOTSCLASS+"-"+size;
    	}
    	return ret;
    }    
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    }     
    
    
    @Override
    public void encodeChildren(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	Map<String, IModel> facets=createFacetsModel(context, this.getTagName());
    	IModel         facetPrepend=facets.get(FacetNamesEnum.prepend.toString());
    	IModel         facetAppend =facets.get(FacetNamesEnum.append.toString());
    	
    	//addPrependModel
    	addPrependModel(context,model, facetPrepend, this.getPrepend(), this.getLook());
    	//adding child to ne model
    	super.encodeChildren(context, model, attributes);  
    	//addAppendModel
    	addAppendModel(context,model, facetAppend, this.getAppend(), this.getLook());
    	
    }
    
    public String lookClass(String look) {
    	String ret=" ";
    	look=this.nvl(look, "").trim();
    	if (look.equals("default") || look.equals("text") ) {
    		ret=TAG_BOOTSCLASS+"-"+"text";
    	}
    	else if (look.equals("none")) {
    		ret=" ";
    	}
    	else if (!look.isBlank() ) {
    		ret=" "+look+" ";
    	}
    	
    	return ret;
    }
    public void addPrependModel(ITemplateContext context,IModel model, IModel facetPrepend, String text, String look) {
    	text=this.nvl(text, "").trim();
  	   //create prepend tag
  	   if (facetPrepend!=null || !text.isBlank()) {
  		  IModelFactory modelFactory = context.getModelFactory();
 		   String             prependTag = "div";
 		   String             prependCls = "input-group-prepend";
 		   Map<String,String> prependAttr= new HashMap<String,String>();
 		   prependAttr.put("class", prependCls);
 		   model.add(   modelFactory.createOpenElementTag (prependTag, prependAttr, null, false)  );
 		   
 		   if (facetPrepend!=null) {
 			  model.addModel(facetPrepend);   
 		   }
 		   else if (!text.isBlank()) {
 			   String textclass=lookClass(look);
 			   String textmodel="<span class=\""+textclass+"\">"+text+"</span>";
 			   model.add( modelFactory.createText(textmodel) );
 		   } 		   
 		   
 		   model.add(   modelFactory.createCloseElementTag(prependTag) );    		   
  	   }    	
     }
    
    public void addAppendModel(ITemplateContext context,IModel model, IModel facetAppend, String text, String look) {
    	text=this.nvl(text, "").trim();
    	if (facetAppend!=null || !text.isBlank() ) {
   		  IModelFactory modelFactory = context.getModelFactory();
   		   String             appendTag = "div";
   		   String             appendCls = "input-group-append";
   		   Map<String,String> appendAttr= new HashMap<String,String>();
   		   appendAttr.put("class", appendCls);
   		   model.add(   modelFactory.createOpenElementTag (appendTag, appendAttr, null, false)  );
   		   
 		   if (facetAppend!=null) {
 			  model.addModel(facetAppend);   
 		   }
 		   else if (!text.isBlank()) {
 			   String textclass=lookClass(look);
 			   String textmodel="<span class=\""+textclass+"\">"+text+"</span>";
 			   model.add( modelFactory.createText(textmodel) );
 		   }
 		   
   		   model.add(   modelFactory.createCloseElementTag(appendTag) );    		   
   	    }    	
    }
    
    
}
