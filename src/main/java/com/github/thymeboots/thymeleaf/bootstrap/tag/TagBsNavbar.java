
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
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * <p>Represents an HTML <code>navBar</code> element 
 * <p><strong>Attributes</strong> <br>
 * <strong>brand       </strong> Brand label <br>
 * <strong>brand-href  </strong> Brand href link<br>
 * <strong>fluid       </strong> Brand is fluid<br>
 * 
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:navBar brand="Brand" brand-href="#" fluid="true"&gt; <br>
 * &lt;tb:/navBar&gt;
 *
  * @author Rifat Yilmaz
 *
 * @since 3.4.0
 *
 */
public class TagBsNavbar extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "navbar";
	private static final String TAG_HTML       = "nav";
	private static final String TAG_BOOTSCLASS = "navbar";
	private static final int    PRECEDENCE = 1000;
	
	
	protected enum PropertyKeys {
	    brand
	   ,brandHref("brand-href")
	   ,fixed
	   ,fluid
	   ,look
	   ,sticky;
				
	   private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	
    public TagBsNavbar(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }    
        
        
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    } 
    @Override
    public String getHtmlTagStyleClass() {
    	String ret=TAG_BOOTSCLASS;    
    	
    	ret=(ret+" "+lookClass()).trim();
    	
    	ret=(ret+" "+positionClass()).trim();
    	
    	return ret;
    }
    @Override
    protected boolean isAddIdAttribute() {
		return true;
	}    
    @Override
	public boolean isAutoEncodeChild() {
		return false; //childs added into createNavbarInner
	}    
	@Override
	protected void preProcess(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler) {
		super.preProcess(context, model, structureHandler);				
	}
    
    @Override
	public void encodeBegin(ITemplateContext context, IModel model, Map<String,String> attributes )  {		
	    super.encodeBegin(context, model, attributes);
	    model.addModel (createNavbarModel(context, attributes));
	}	
    @Override
    public void encodeEnd(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	super.encodeEnd(context, model, attributes);
    }    
    @Override
	protected Map<String,String> addattributes() {	
		Map<String,String> ret=super.addattributes();
		ret.put("role", "navigation");
		return ret;
	}	
    
    
	private IModel createNavbarModel(ITemplateContext context, Map<String,String> attributes )  {		
	    IModelFactory modelFactory = context.getModelFactory();
	    IModel model=modelFactory.createModel();
	    
	    String containerTag="div";
	    String containerCls="container";
	    if (this.isFluid()) {
	    	containerCls=containerCls+"-fluid";
	    }
	    model.add( modelFactory.createOpenElementTag (containerTag, "class", containerCls) );
	      model.addModel(createNavbarHeader(context,attributes ));
	    model.add( modelFactory.createCloseElementTag(containerTag ));
	    	    
	    return model;
	}	
	private IModel createNavbarHeader(ITemplateContext context, Map<String,String> attributes )  {		
	    IModelFactory modelFactory = context.getModelFactory();
	    IModel model=modelFactory.createModel();
	    
	    String id=this.getId();
	    
	    String containerTag="div";
	    String containerCls="navbar-header";
	    model.add( modelFactory.createOpenElementTag (containerTag, "class", containerCls) );
	    	if (this.getBrand()!=null && !this.getBrand().isEmpty()) {
		     	//Brand
		    	String brand="<a class=\"navbar-brand\" href=\""+getBrandHref()+"\">"+this.getBrand()+"</a>";
		    	model.add( modelFactory.createText( brand  ));	    		
	    	}
	    	//buttonBar
	    	String buttonBar=
	    			"<button type=\"button\" class=\"navbar-toggler\" data-toggle=\"collapse\" "+
	    	          " data-target=\"#"+id+"_inner\" "+
	    	          " aria-controls=\""+id+"_inner\" "+
	    			  " aria-expanded=\"false\""+
	    			  " aria-label=\"Toggle navigation\">"+
	    	          "  <span class=\"navbar-toggler-icon\"></span>"+
	    			"</button>";
	    	model.add( modelFactory.createText( buttonBar  ));
	    model.add( modelFactory.createCloseElementTag(containerTag ));
	    
	    model.addModel(createNavbarInner(context, attributes ));
	    	    
	    return model;
	}	
	private IModel createNavbarInner(ITemplateContext context, Map<String,String> attributes )  {		
	    IModelFactory modelFactory = context.getModelFactory();
	    IModel model=modelFactory.createModel();
	    
	    
	    String             containerTag="div";
	    String             containerCls="collapse navbar-collapse";
	    String             containerId=this.getId()+"_inner";
	    Map<String,String> containerAtr= new  HashMap<String,String>();
	    containerAtr.put("class", containerCls);
	    containerAtr.put("id"   , containerId);	    
	    model.add( modelFactory.createOpenElementTag(containerTag, containerAtr, null, false) );
	      addChilds(model,  true);
	    model.add( modelFactory.createCloseElementTag(containerTag ));
	    	    
	    return model;
	}	
    
	
	
    public String getBrand() {
    	String ret=this.getAttributeValue(PropertyKeys.brand.toString());
    	if (ret==null) { ret="";}
    	return  ret;
    }      
    public String getBrandHref() {
    	String ret=this.nvl(this.getAttributeValue(PropertyKeys.brandHref.toString()),"").trim();
    	if (ret.isBlank()) { ret="#";}
    	return  ret;
    }      
    public String getFixed() {
    	String ret=this.nvl(this.getAttributeValue(PropertyKeys.fixed.toString()),"").trim();
    	return  ret;
    }      
    
    public boolean isFluid() {
    	boolean ret= false;
    	String  val= this.nvl(this.getAttributeValue(PropertyKeys.fluid.toString()),"").trim();
    	if (val.equals("yes") || val.equals("true")) {
    		ret=true;
    	}
    	return  ret;
    }      

    public boolean isSticky() {
    	boolean ret= false;
    	String  val= this.nvl(this.getAttributeValue(PropertyKeys.sticky.toString()),"").trim();
    	if (val.equals("yes") || val.equals("true")) {
    		ret=true;
    	}
    	return  ret;
    }      
    
    
    
    public String getLook() {
    	return this.nvl(this.getAttributeValue(PropertyKeys.look.toString()),"");
    }      
    
    private String lookClass() {
    	String ret="";
    	String val= this.nvl(this.getLook(),"").trim();
    	if (val.isBlank()) {
    		val="light";
    	}
    	ret= "navbar-expand-lg"+" " + "navbar-"+val+" "+"bg-"+val;
    	
    	return ret;
    }      

    private String positionClass() {
    	String ret="";
    	String val= this.nvl(this.getFixed(),"").trim();
    	if (!val.isBlank()) {
    		ret="fixed-"+val;
    		if (this.isSticky()) {
    			ret="sticky-"+val;
    		}
    	}
    	
    	return ret;
    }      

    
}
