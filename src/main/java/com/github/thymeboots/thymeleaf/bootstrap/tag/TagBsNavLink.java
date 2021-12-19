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
 * <p>Represents an HTML <code>navLink</code> element <br>
 * The label text is specified by the component value.
 * <p><strong>Attributes</strong> <br>
 * <strong>href       </strong> Link href <br>
 * <strong>type       </strong> Link type attribute. [link,dropitem, menuitem] <br>
 * <strong>value      </strong> Link label attribute<br>
 * 
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:navLink href="#" type="dropitem" value="Link name" &gt; &lt;tb:/navLink&gt;
 *
  * @author Rifat Yilmaz
 *
 * @since 3.4.0
 *
 */
public class TagBsNavLink extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "navLink";
	private static final String TAG_HTML       = "a";
	private static final String TAG_BOOTSCLASS_NAVLINK = "nav-link";
	private static final String TAG_BOOTSCLASS_DROPITEM= "dropdown-item";
	private static final String TAG_BOOTSCLASS_MENUITEM= "dropdown-item";
	
	private static final int    PRECEDENCE = 1000;
	
	private static final String TAG_HTML_CONTAINER = "li";
	private static final String TAG_BOOTSCLASS_CONTAINER = "nav-item";
	
	
	
	protected enum PropertyKeys {
	    href
	   ,type
	   ,value;
				
	   private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	
    public TagBsNavLink(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }    
    public TagBsNavLink(
    		String             tagVersion,
            final TemplateMode templateMode, final String dialectPrefix,
            final String elementName, final boolean prefixElementName,
            final String attributeName, final boolean prefixAttributeName,
            final int precedence) {
        super(tagVersion,templateMode, dialectPrefix, elementName, prefixElementName,attributeName, prefixAttributeName, precedence);        
    }
        
    public String getHref() {
    	return this.getAttributeValue(PropertyKeys.href.toString());
    }          
    public String getType() {
    	return this.nvl(this.getAttributeValue(PropertyKeys.type.toString()),"");
    }      
    public String getValue() {
    	return this.getAttributeValue(PropertyKeys.value.toString());
    }          

    /**
     * if value==null and href==null means this component divider
     * @return true:means divider
     */
    protected boolean isDividerLink() {
    	boolean ret=false;    	
    	String value=this.getAttributeValue(PropertyKeys.value.toString());
    	String href =this.getAttributeValue(PropertyKeys.href.toString());
    	if (value==null && href==null) {
    		ret=true;
    	}
    	return ret;
    }          
        
    @Override
    public String getHtmlTag() {
    	String ret= TAG_HTML;
    	if (isDividerLink()) {
    		ret="li";
    	}
    	return ret;
    } 
    @Override
    public String getHtmlTagStyleClass() {
    	String ret=TAG_BOOTSCLASS_NAVLINK;    
    	String type=this.getType();
    	if ("dropitem".equals(type)) {
    		ret=TAG_BOOTSCLASS_DROPITEM;
    	}
    	else if ("menuitem".equals(type)) {
    		ret=TAG_BOOTSCLASS_MENUITEM;
    	}
    	
    	if (isDividerLink()) {
    		ret="dropdown-divider divider";
    	}
    	
    	return ret;
    }
    @Override
	public void encodeBegin(ITemplateContext context, IModel model, Map<String,String> attributes )  {		
	    IModelFactory modelFactory = context.getModelFactory();
	    //Open container tag
	    Map<String,String> containerAttr= new HashMap<String,String>();
	    containerAttr.put("class", TAG_BOOTSCLASS_CONTAINER);
	    model.add(  modelFactory.createOpenElementTag(TAG_HTML_CONTAINER, containerAttr, null, false)   );
	    
	    //Open main tag
	    super.encodeBegin(context, model, attributes);
	}	
    @Override
    public void encodeEnd(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	IModelFactory modelFactory = context.getModelFactory();
    	
    	//Value degeri print edilir
    	String value=this.nvl(this.getValue(),"");
    	model.add(  modelFactory.createText(value)   );
    	//Close tag
    	super.encodeEnd(context, model, attributes);
    	
    	//Close container tag
    	model.add(  modelFactory.createCloseElementTag(TAG_HTML_CONTAINER)  );	    	        	
    }
    
    
}
