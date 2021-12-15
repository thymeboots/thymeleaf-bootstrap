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
 * <p>Represents an bootstrap <code>buttonDrop</code> element
 * <p><strong>Attributes</strong> <br>
 * <strong>look       </strong> styles look attribute: [link, outline-primary,primary , info , success , warning , danger ] <br>
 * <strong>size     </strong> item size: [sm,md,lg] <br>
 * 
 * <p><strong>Examples</strong> <br/> 
 * &lt;tb:buttonDrop look="link" size="sm" value="drop label" &gt; <br>
 * &nbsp;&nbsp;&nbsp;&lt;tb:navLink type="link" href="#" value="Drop Link1"&gt;&lt;/tb:navLink&gt; <br>
 * &nbsp;&nbsp;&nbsp;&lt;tb:navLink type="link" href="#" value="Drop Link2"&gt;&lt;/tb:navLink&gt; <br>
 * &lt;/tb:buttonDrop&gt;<br>

 * &lt;tb:buttonDrop look="primary" size="sm" value="drop label" &gt; <br>
 * &nbsp;&nbsp;&nbsp;&lt;tb:navLink href="#" value="Drop Item1"&gt;&lt;/tb:navLink&gt; <br>
 * &nbsp;&nbsp;&nbsp;&lt;tb:navLink href="#" value="Drop Item2"&gt;&lt;/tb:navLink&gt; <br>
 * &lt;/tb:buttonDrop&gt;

 */
public class TagBsButtonDrop extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "buttonDrop";
	private static final String TAG_HTML       = "div";
	private static final String TAG_BOOTSCLASS = "btn-group";
	private static final int    PRECEDENCE = 1000;
	
	protected enum PropertyKeys {
		 look
		,size
		,value; 		
	 
	   private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	
    public TagBsButtonDrop(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
    
    public String getLook() {
    	String ret= this.nvl(this.getAttributeValue(PropertyKeys.look.toString()),"").trim();   
    	if ("error".equals(ret)) {ret="danger";}
    	return ret;
    }     
    
    public String getSize() {
    	return this.getAttributeValue(PropertyKeys.size.toString());
    } 
    public String getValue() {
    	return this.getAttributeValue(PropertyKeys.value.toString());
    } 
    
    @Override
    public String getHtmlTagStyleClass() {
    	String ret=TAG_BOOTSCLASS;
    	
    	String size=this.nvl(this.getSize(),"").trim();
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
	public void encodeBegin(ITemplateContext context, IModel model, Map<String,String> attributes )  {		
    	super.encodeBegin(context, model, attributes);
    	
    	String look=this.nvl(this.getLook(), "");
    	String size=this.nvl(this.getSize(), "");
    	
    	IModelFactory modelFactory = context.getModelFactory();		
	    //String caretStr=" <span class=\"caret\"></span>";	    
    	String caretStr="";
	    String buttonLook="btn";	    
	    if (!look.isBlank()) {
	    	buttonLook="btn btn-"+this.getLook();
	    }
	    if (!size.isBlank()) {
	    	buttonLook=buttonLook+" "+"btn-"+size;
	    }
	    
	    String buttonValue= this.nvl(this.getValue()," ");
	    String buttonStr="\n<button type=\"button\" class=\""+buttonLook+" dropdown-toggle\" data-toggle=\"dropdown\">"+buttonValue+caretStr+"</button>";
	    
	    model.add(modelFactory.createText(buttonStr) );	
	    
	    
	}	
    @Override
    public void encodeChildren(ITemplateContext context, IModel model,  Map<String,String> attributes)  {    			
	    String             dropTag = "div";
	    String             dropClas= "dropdown-menu";
	    Map<String,String> dropAttr= new HashMap<String,String>();
	    dropAttr.put("class", dropClas);
	    model.add(   this.getModelFactory().createOpenElementTag(dropTag, dropAttr, null, false)  );		
		super.encodeChildren(context, model, attributes);
    	model.add(this.getModelFactory().createCloseElementTag(dropTag));	    	        	

    }
    @Override
    public void encodeEnd(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	super.encodeEnd(context, model, attributes);
    }
    
    
}
