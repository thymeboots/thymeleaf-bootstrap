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

import com.github.thymeboots.thymeleaf.bootstrap.comp.FacetNamesEnum;

/**
 * <p>Represents an bootstrap <code>modal</code> element 
 *
 * <p><strong>Attributes</strong> <br>
 * <strong>header      </strong> modal title <br>
 * <strong>value       </strong> modal body text <br>
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:modal header="Title" value="Text metin"&gt;&lt;/tb:modal&gt;<br>
 * &lt;tb:modal header="Title"&gt;Text metin&lt;/tb:modal&gt;<br>
 * &lt;tb:modal header="Title" footer="footer text" value="body text" look="primary"  closable="false" centered="true" backdrop="true" draggable="true" resizable="true" &gt;&lt;/tb:modal&gt;<br> 
 */
public class TagBsModal extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "modal";
	private static final String TAG_HTML       = "div";
	private static final String TAG_BOOTSCLASS = "modal";
	private static final int    PRECEDENCE = 1000;
	
	// data-backdrop="static" data-keyboard="false"
	//closable="false"  close-on-escape="false" backdrop="false"
    public TagBsModal(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
        
	protected enum PropertyKeys {
		backdrop
		,centered
		,closable
		,contentClass("content-class")
		,draggable
		,footer
		,header
		,headerClass("header-class")
		,look
		,resizable
		,scrollable
		,value; 
			 
       private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	

    public String getBackdrop() {
    	String ret= this.getAttributeValue(PropertyKeys.backdrop.toString());
    	ret=this.nvl(ret, "").trim();
    	if (ret.equals("false") || ret.equals("no") || ret.equals("static") ) {
    		ret="false";
    	}
    	else {
    		ret="true";
    	}
    	return ret;
    	
    } 
    public String getCentered() {
    	String ret= this.getAttributeValue(PropertyKeys.centered.toString());
    	ret=this.nvl(ret, "").trim();
    	if (ret.equals("true") || ret.equals("yes") || ret.equals("center") || ret.equals("centered") ) {
    		ret="true";
    	}
    	else {
    		ret="false";
    	}
    	return ret;
    }   
    
    
    public String getClosable() {
    	String ret= this.getAttributeValue(PropertyKeys.closable.toString());
    	ret=this.nvl(ret, "").trim();
    	if (ret.equals("false") || ret.equals("no")) {
    		ret="false";
    	}
    	else {
    		ret="true";
    	}
    	return ret;
    }   
    public String getContentClass() {
    	return this.getAttributeValue(PropertyKeys.contentClass.toString());
    } 
    public String getDraggable() {
    	String ret= this.getAttributeValue(PropertyKeys.draggable.toString());
    	ret=this.nvl(ret, "").trim();
    	if (ret.equals("true") || ret.equals("yes") || ret.equals("draggable") ) {
    		ret="true";
    	}
    	else {
    		ret="false";
    	}
    	return ret;

    }          
    
    public String getHeader() {
    	return this.getAttributeValue(PropertyKeys.header.toString());
    }   
    public String getHeaderClass() {
    	return this.getAttributeValue(PropertyKeys.headerClass.toString());
    }      
    
    
    public String getFooter() {
    	return this.getAttributeValue(PropertyKeys.footer.toString());
    }      
    public String getLook() {
    	return this.getAttributeValue(PropertyKeys.look.toString());
    }      
    
    public String getScrollable() {
    	String ret= this.getAttributeValue(PropertyKeys.scrollable.toString());
    	ret=this.nvl(ret, "").trim();
    	if (ret.equals("true") || ret.equals("yes") || ret.equals("scrollable") ) {
    		ret="true";
    	}
    	else {
    		ret="false";
    	}
    	return ret;
    }      	    

    public String getResizable() {
    	String ret= this.getAttributeValue(PropertyKeys.resizable.toString());
    	ret=this.nvl(ret, "").trim();
    	if (ret.equals("true") || ret.equals("yes") || ret.equals("resizable") ) {
    		ret="true";
    	}
    	else {
    		ret="false";
    	}
    	return ret;
    }      	    
    
    
    
    public String getValue() {
    	return this.getAttributeValue(PropertyKeys.value.toString());
    }      
	
    @Override
    public String getHtmlTagStyleClass() {
    	String ret= TAG_BOOTSCLASS;
    	
    	String drag= this.nvl(this.getDraggable(),"").trim();
    	if (drag.equals("true")) {
    		ret=ret+" "+"modal-drag";
    	}
    	String resize= this.nvl(this.getResizable() ,"").trim();
    	if (resize.equals("true")) {
    		ret=ret+" "+"modal-resize";
    	}
    	
    	 
    	
    	return ret;
    }    
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    }     
    @Override	
	public boolean isAutoEncodeChild() {
		return false;
	}
    
    @Override
	protected Map<String,String> addattributes() {	
    	Map<String,String> ret=super.addattributes();
    	
    	ret.put("role", "dialog");
    	
    	String backdrop= this.nvl(this.getBackdrop(),"").trim();
    	if (backdrop.equals("false") ) {
    		ret.put("data-backdrop", "static");
    	}
    	
    	return ret;
	}	
	    
    @Override
    public void encodeChildren(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	super.encodeChildren(context, model, attributes);
    	//Child bilgisi burada manuel olusturulacak
    	model.addModel(createModalDialog(context));
    	
    }
    
    private IModel createModalDialog(ITemplateContext context) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel model=modelFactory.createModel();
		
		String             tagHtml="div";
		String             tagClss="modal-dialog";
		String             tmpClss=this.nvl( this.getScrollable()  , "").trim();
		if ("true".equals(tmpClss)) {
			tagClss=tagClss+" "+"modal-dialog-"+"scrollable";
		}		

		tmpClss=this.nvl( this.getCentered()  , "").trim();
		if ("true".equals(tmpClss)) {
			tagClss=tagClss+" "+"modal-dialog-"+"centered";
		}		
		
		Map<String,String> tagAttr= new HashMap<String,String>();
		tagAttr.put("class", tagClss);
		tagAttr.put("role" , "document");
		model.add(   modelFactory.createOpenElementTag(tagHtml, tagAttr, null, false)  );
	    	model.addModel(createModalContent(context));
		model.add(   modelFactory.createCloseElementTag(tagHtml)  );
		
		return model;
    }
    
    private IModel createModalContent(ITemplateContext context) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel model=modelFactory.createModel();
		
		String             tagHtml="div";
		String             tagClss="modal-content";
		String             tmpClss=this.nvl(this.getContentClass(),"").trim();
		if (!tmpClss.isBlank()) {
			tagClss=tagClss+" "+tmpClss;
		}
		Map<String,String> tagAttr= new HashMap<String,String>();
		tagAttr.put("class", tagClss);
		model.add(   modelFactory.createOpenElementTag(tagHtml, tagAttr, null, false)  );
	    	model.addModel(createModalHeader(context));
	    	model.addModel(createModalBody(context));
	    	model.addModel(createModalFooter(context));		
		model.add(   modelFactory.createCloseElementTag(tagHtml)  );
		
		return model;
    }
	private IModel createModalHeader(ITemplateContext context) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel model=modelFactory.createModel();
		
    	Map<String, IModel> facets=createFacetsModel(context, this.getTagName());
    	IModel         facetHeader=facets.get(FacetNamesEnum.header.toString());
    	
		
		
		String             title= this.nvl( this.getHeader() , "").trim();		
		String             headerTag="div";
		String             headerClss="modal-header";
		String             tmpClss=this.nvl(this.getContentClass(),"").trim();
		if (!tmpClss.isBlank()) {
			headerClss=headerClss+" "+tmpClss;
		}
		tmpClss=this.nvl(this.getLook(),"").trim();
		if (!tmpClss.isBlank()) {
			headerClss=headerClss+" "+"alert-"+tmpClss;
		}		

		tmpClss=this.nvl(this.getDraggable(),"").trim();
		if (tmpClss.equals("true")) {
			headerClss=headerClss+" "+"modal-drag-handle";
		}		
		
		Map<String,String> headerAttr= new HashMap<String,String>();
		headerAttr.put("class", headerClss);
		model.add(   modelFactory.createOpenElementTag(headerTag, headerAttr, null, false)  );
		
		if (facetHeader!=null) {
			model.addModel(facetHeader);
		}
		else {
			if (!title.isBlank()) {
				model.add(   modelFactory.createText("\n<h4 class=\"modal-title\">"+title+"</h4>")  );			
			}
			String closable=this.getClosable();
			if (closable.equals("true")) {
				model.add(   modelFactory.createText("\n<button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>")  );
			}			
		}
		
		model.add(   modelFactory.createCloseElementTag(headerTag)  );
		
		return model;
	}
	private IModel createModalBody(ITemplateContext context) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel model=modelFactory.createModel();
		
		String             value= this.nvl( this.getAttributeValue(PropertyKeys.value.toString()) , "").trim();
		
		String             bodyTag="div";
		String             bodyClss="modal-body";
		Map<String,String> bodyAttr= new HashMap<String,String>();
		bodyAttr.put("class", bodyClss);
		model.add(   modelFactory.createOpenElementTag(bodyTag, bodyAttr, null, false)  );
		if (!value.isBlank()) {
			model.add(   modelFactory.createText(value)  );			
		}
    	List<ITemplateEvent> childs=getChilds();
	    //Child eventler eklenir
	    if (childs!=null && childs.size()>0) {
		    for (int i = 0; i < childs.size(); i++) {
		    	ITemplateEvent event=childs.get(i);
		    	model.add(event);
		    }	    	
	    }    		
		//model kapatilir
		model.add(   modelFactory.createCloseElementTag(bodyTag)  );
		
		return model;
	}
	
	private IModel createModalFooter(ITemplateContext context) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel model=modelFactory.createModel();		
		           
    	Map<String, IModel> facets=createFacetsModel(context, this.getTagName());
    	IModel         facetFooter=facets.get(FacetNamesEnum.footer.toString());
    	String         footer=this.nvl( this.getFooter() , "").trim();
    	
    	if (facetFooter!=null || !footer.isBlank()) {
			String             headerTag="div";
			String             headerClss="modal-footer";
			Map<String,String> headerAttr= new HashMap<String,String>();
			headerAttr.put("class", headerClss);
			model.add(   modelFactory.createOpenElementTag(headerTag, headerAttr, null, false)  );

			if (facetFooter!=null) {
				model.addModel(facetFooter);
			}
			else {
				if (!footer.isBlank()) {
					model.add(   modelFactory.createText(footer)  );			
				}
				//model.add(   modelFactory.createText("\n<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">x</button>")  );
			}			
			
			model.add(   modelFactory.createCloseElementTag(headerTag)  );			
		}
				
		
		return model;
	}
	
	

    
}
