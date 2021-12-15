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
 * <p>Represents an bootstrap <code>input</code> element
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:input&gt;&lt;tb:/input&gt;
 * 
 */
public class TagBsInput extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIInput {
	private static final String TAG_NAME       = "input";
	private static final String TAG_HTML       = "input";
	private static final String TAG_BOOTSCLASS = "form-control";
	private static final int    PRECEDENCE = 1000;
	
	
    public TagBsInput(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
    public TagBsInput(
    		String             tagVersion,
            final TemplateMode templateMode, final String dialectPrefix,
            final String elementName, final boolean prefixElementName,
            final String attributeName, final boolean prefixAttributeName,
            final int precedence) {
        super(tagVersion,templateMode, dialectPrefix, elementName, prefixElementName,attributeName, prefixAttributeName, precedence);                
    }   
	protected enum PropertyKeys {
		custom 
		,label
		,labelAlign("label-align")
		,layout
		,size
		,type; 
			 
       private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	
    
    public boolean isCustom() {
    	boolean ret=true;
    	String  custom= this.nvl(this.getAttributeValue(PropertyKeys.custom.toString()), "true").trim();
    	if (custom.equals("false") || custom.equals("no") ) {
    		ret=false;
    	}
    	return ret;
    } 
	
    public String getLabel() {
    	return this.getAttributeValue(PropertyKeys.label.toString());
    } 
    public String getLayout() {
    	String ret= this.nvl( this.getAttributeValue(PropertyKeys.layout.toString()) , "").trim();
    	if (ret.equals("line")) {
    		ret="inline";
    	}
    	return ret;
    }      
    
    public String getLabelAlign() {
    	String ret= this.nvl(this.getAttributeValue(PropertyKeys.labelAlign.toString()),"").trim();
    	if (ret.equals("right") || ret.equals("next") || ret.equals("after") || ret.equals("append")) {
    		ret="append";
    	}
    	return ret;
    }      
	
    public String getSize() {
    	return this.getAttributeValue(PropertyKeys.size.toString());
    }      
    public String getType() {
    	String ret= this.nvl(this.getAttributeValue(PropertyKeys.type.toString()),"");
    	if (ret.isBlank()) { ret="text";}
    	return ret;
    }      
    
    
    @Override
    public String getHtmlTagStyleClass() {
    	String ret=TAG_BOOTSCLASS;
    	
    	if (this.isCustom() &&  this.getType().equals("range")
    	) {
    		ret=ret+" "+"custom-range";
    	}
    	return ret;
    }    
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    }     
    
    @Override
	public void encodeBegin(ITemplateContext context, IModel model, Map<String,String> attributes )  {		
    	IModelFactory modelFactory = context.getModelFactory();
    	
    	Map<String, IModel> facets=createFacetsModel(context, this.getTagName());
    	IModel         facetPrepend=facets.get(FacetNamesEnum.prepend.toString());
    	IModel         facetAppend =facets.get(FacetNamesEnum.append.toString());
    	String             inputGrupTag="";
    	String             inputGrupCls="";
    	Map<String,String> inputGrupAttr=new HashMap<String,String>();
    	
    	//Read label  
    	String label=this.nvl(this.getLabel(),"");
    	String labelAlign=this.getLabelAlign();    	
    	String labelFor="";
    	String labelPrepend=null;
    	String labelAppend=null;    	
    	String labelClass="";
    	//Read type
    	String type  =this.nvl( this.getType() , "" );
    	String layout=this.nvl( this.getLayout(),"" );
    	//create input type as checkbox
    	if (type.equals("switch") ) {
    		attributes.put("type", "checkbox");
    	}
    	
    			
    			
    	String             containerTag="";
    	String             containerClss="";
    	Map<String,String> containerAttr=null;
    	if (
    		type.equals("radio")    ||
    		type.equals("checkbox") || 
    		type.equals("switch")   ||
    		type.equals("file")
    	) {
    		containerTag="div";
    		containerClss="custom-control";
    		
    		labelAlign="append";
    		labelClass="custom-control-label";    		
    		if (type.equals("radio") ) {
    			containerClss+=" custom-radio";
    		}
    		else if (type.equals("checkbox")){
    			containerClss+=" custom-checkbox";
    		}
    		else if (type.equals("switch")){
    			containerClss+=" custom-switch";
    		}
    		else if (type.equals("file")){
    			containerClss+=" custom-file";
    			labelClass="custom-file-label";  
    		}
			if (this.isCustom()==false) {
				containerClss="";
				labelClass="";
				if (type.equals("file")) {
					labelAlign="";
				}
			}

    		
    		if (layout.equals("inline")) {
    			if (this.isCustom()) {
    				containerClss+=" "+"custom-control-inline";
    			}    			
    		}
    		containerAttr=new HashMap<String,String>();
    		containerAttr.put("class", containerClss);
    		
    		
    		
    		String clzz=this.nvl(attributes.get("class"),""); 
    		if (this.isCustom()) {
    			clzz=clzz+" "+"control-input custom-control-input";
    		}
    		else {
    			clzz=clzz+" "+"control-input";
    		}
    		
    		attributes.put("class", clzz);    		    		
    	}    	
    	
    	if (!containerTag.isBlank()) {
  		   model.add(   modelFactory.createOpenElementTag (containerTag, containerAttr, null, false)  );    		   
    	}

    	if (!label.isEmpty()) {
    		//Label olusturulacak demektir.
    		labelPrepend =label;
    		if (labelAlign.equals("append")) {
    			labelAppend=label;
    			labelPrepend=null;
    		}
    		String id= this.nvl(attributes.get("id"),"").trim();
    		if (id.isBlank()) {
    			id=this.randomId();
    			attributes.put("id", id);
    			labelFor=id;
    		}
    	}
    	
    	
    	//Create previous label
    	if (labelPrepend!=null) {
    		createlabel(context,model, labelPrepend, labelFor, labelClass);
    	}
    	
	    if (facetPrepend!=null || facetAppend!=null ) {
	    		String size=this.nvl(this.getSize(),"").trim();
	    		inputGrupTag="div";
	    		inputGrupCls="input-group";
	    		if (!size.isBlank()) {
	    			inputGrupCls=inputGrupCls+" "+"input-group-"+size;
	    		}
	    		inputGrupAttr.put("class", " "+" "+inputGrupCls); 
	    		model.add(   modelFactory.createOpenElementTag(inputGrupTag, inputGrupAttr, null, false)  );    		
	    }
    	   //create prepend tag
    	   addPrependModel(context,model, facetPrepend);    	
    	   //input tag yaratiliyor
    	   super.encodeBegin(context, model, attributes);
    	   //create append tag
		   addAppendModel(context,model, facetAppend);
		if (!inputGrupTag.isBlank()) {
			model.add(   modelFactory.createCloseElementTag(inputGrupTag) );
		}

    	
    	//Create append label
    	if (labelAppend!=null) {
    		createlabel(context,model, labelAppend, labelFor, labelClass);
    	}
		
    	
    	if (!containerTag.isBlank()) {
   		   model.add(   modelFactory.createCloseElementTag(containerTag) );    		   
     	}


    }	
    @Override
    public void encodeChildren(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	super.encodeChildren(context, model, attributes);  	
    }
    @Override
    public void encodeEnd(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	super.encodeEnd(context, model, attributes);
    }
    
    public void createlabel(ITemplateContext context,IModel model, String label, String labelFor, String labelClass) {
   	   if (label!=null && !label.isEmpty()) {
   		   labelFor=this.nvl(labelFor, "").trim();
   		   IModelFactory modelFactory = context.getModelFactory();
  		   String             tag = "label";
  		   Map<String,String> tagAttr= new HashMap<String,String>();
  		   if (!labelFor.isBlank()) {tagAttr.put("for", labelFor);  } 	
  		  tagAttr.put("class", labelClass);
  		   model.add(   modelFactory.createOpenElementTag (tag, tagAttr, null, false)  );
  		   model.add(   modelFactory.createText(label)  );
  		   model.add(   modelFactory.createCloseElementTag(tag) );    		   
   	   }    	
      }
     
     public void addPrependModel(ITemplateContext context,IModel model, IModel facetPrepend) {
  	   //create prepend tag
  	   if (facetPrepend!=null) {
  		  IModelFactory modelFactory = context.getModelFactory();
 		   String             prependTag = "div";
 		   String             prependCls = "input-group-prepend";
 		   Map<String,String> prependAttr= new HashMap<String,String>();
 		   prependAttr.put("class", prependCls);
 		   model.add(   modelFactory.createOpenElementTag (prependTag, prependAttr, null, false)  );
 		   model.addModel(facetPrepend);
 		   model.add(   modelFactory.createCloseElementTag(prependTag) );    		   
  	   }    	
     }
     public void addAppendModel(ITemplateContext context,IModel model, IModel facetAppend) {
   	   //create tag
   	   if (facetAppend!=null) {
   		  IModelFactory modelFactory = context.getModelFactory();
   		   String             appendTag = "div";
   		   String             appendCls = "input-group-append";
   		   Map<String,String> appendAttr= new HashMap<String,String>();
   		   appendAttr.put("class", appendCls);
   		   model.add(   modelFactory.createOpenElementTag (appendTag, appendAttr, null, false)  );
   		   model.addModel(facetAppend);
   		   model.add(   modelFactory.createCloseElementTag(appendTag) );    		   
   	   }    	
      }
     
    
    /*
     
    <div class="form-group">
  <label for="sel1">Select list:</label>
  <select class="form-control" id="sel1">
    <option>1</option>
    <option>2</option>
    <option>3</option>
    <option>4</option>
  </select>
  
  <select class="custom-select custom-select-sm">
  <option selected>Open this select menu</option>
  <option value="1">One</option>
  <option value="2">Two</option>
  <option value="3">Three</option>
</select>

<select class="custom-select" size="3">
  <option selected>Open this select menu</option>
  <option value="1">One</option>
  <option value="2">Two</option>
  <option value="3">Three</option>
</select>

<select class="custom-select" multiple>
  <option selected>Open this select menu</option>
  <option value="1">One</option>
  <option value="2">Two</option>
  <option value="3">Three</option>
</select>

</div>


<input type="text" class="form-control form-control-sm">
<input type="text" class="form-control form-control">
<input type="text" class="form-control form-control-lg">


<input type="range" class="form-control-range">
<input type="file" class="form-control-file border">
    */
    
}
