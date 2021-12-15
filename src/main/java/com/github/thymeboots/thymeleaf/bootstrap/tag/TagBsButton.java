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

/**
 * <p>
 *   Represents an HTML <code>button</code> element
 *   for a button of type <code>button</code> <code>submit</code> or <code>reset</code>.
 *   The label text is specified by the component value.
 * </p>
 * <p>
 *   <strong>Attributes</strong> <br>
 *   <strong>data-dismis</strong> Dismiss attribute: "alert" <br>
 *   <strong>look       </strong> Button styles look attribute: [link, outline-primary,primary , info , success , warning , danger] <br>
 *   <strong>size       </strong> Button size attribute: [sm , md , lg , xl] <br>
 *   <strong>type       </strong> Button type attribute: "button , submit , reset" <br>
 *   <strong>value      </strong> Button label attribute<br>
 * </p>
 * <p>
 *   <strong>Examples</strong> <br> 
 *   &lt;tb:button type="button" look="primary" size="lg" value="Large button" icon="info-circle" icon-align="right" icon-spin="true" &gt;  &lt;/tb:button&gt;<br> 
 *   &lt;tb:button type="submit" look="link" size="lg" value="Large button"&gt;  &lt;/tb:button&gt; 
 * </p>
 * @author Rifat Yilmaz
 * 
 * @since 3.4.0
 *  
 */
public class TagBsButton extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "button";
	private static final String TAG_HTML       = "button";
	private static final String TAG_BOOTSCLASS = "btn";
	private static final int    PRECEDENCE = 1000;
	
    public TagBsButton(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
    public TagBsButton(
    		String             tagVersion,
            final TemplateMode templateMode, final String dialectPrefix,
            final String elementName, final boolean prefixElementName,
            final String attributeName, final boolean prefixAttributeName,
            final int precedence) {
        super(tagVersion,templateMode, dialectPrefix, elementName, prefixElementName,attributeName, prefixAttributeName, precedence);        
    }    
	
	protected enum PropertyKeys {
		dataDismiss("data-dismiss")
		,icon
		,iconAlign("icon-align")
		,iconSpin("icon-spin")
		,look		
		,size
		,type
		,target		
		,value;
				
       private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	    
        
    public String getDataDismiss() {
    	return this.getAttributeValue(PropertyKeys.dataDismiss.toString());
    } 
    public String getIcon() {
    	return this.getAttributeValue(PropertyKeys.icon.toString());
    }  

    public String getLook() {
    	String ret=this.getAttributeValue(PropertyKeys.look.toString());
    	if ("error".equals(ret)) {ret="danger";}
    	return ret;
    } 
    public String getIconAlign() {
    	String ret= this.nvl(this.getAttributeValue(PropertyKeys.iconAlign.toString()) ,"").trim();
    	if (ret.equals("right")) {
    		ret="right";    	
    	}
    	else {
    		ret="left";	
    	}
    	return ret;
    }          
    
    public String getIconSpin() {
    	String ret= this.nvl( this.getAttributeValue(PropertyKeys.iconSpin.toString()) ,"").trim();
    	if ("yes".equals(ret)) {
    		ret="true";
    	}
    	return ret;
    }          
    
    
    public String getSize() {
    	return this.getAttributeValue(PropertyKeys.size.toString());
    }  
    public String getTarget() {
    	return this.getAttributeValue(PropertyKeys.target.toString());
    }      
    
    public String getType() {
    	String val=this.getAttributeValue(PropertyKeys.type.toString());
    	if (val==null) {
    		val="";
    	}
    	if (val.isBlank()) {
    		val=TAG_NAME;
    	}
    	return val;
    };
        		
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
    	attributes.put(PropertyKeys.type.toString(), this.getType());
	    super.encodeBegin(context, model, attributes);
	}	
    @Override
    public void encodeChildren(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	IModel modelIcon=null;
    	String iconname =this.nvl( this.getIcon() ,"").trim();
    	String iconspin =this.nvl( this.getIconSpin() ,"").trim();
    	String iconalign=this.nvl( this.getIconAlign() ,"").trim();
    	if (!iconname.isBlank()) {
    		modelIcon=TagBsIcon.createTag(context, iconname, iconspin,null);
    	}
    	
    	//left icin model iconu
    	if (modelIcon!=null && iconalign.equals("left")) {
    		model.addModel(modelIcon);
    	}
    	
    	IModelFactory modelFactory = context.getModelFactory();		
    	String value=this.getValue();
    	if (value!=null) {
    	    model.add(modelFactory.createText(value));		    		
    	}
    	
    	//right icin model iconu
    	if (modelIcon!=null && iconalign.equals("right")) {
    		model.addModel(modelIcon);
    	}
    	
    	super.encodeChildren(context, model, attributes);  	
    }
    
    public static IModel createModel(
    			ITemplateContext context, 
    			String _value, 
    			String _clazz, 
    			String _dataDismiss, 
    			String _look, 
    			String _size,
    			List<ITemplateEvent> _childs,
    			boolean _addBootsClass
    			) {
    	if (_clazz==null) {
    		_clazz="";
    	}
    	if (_addBootsClass) {
    		_clazz=_clazz+" "+TAG_BOOTSCLASS;
    	}
    	if (_look!=null && !_look.isBlank()) {
    		_clazz=_clazz+" "+TAG_BOOTSCLASS+"-"+_look.trim();
    	}
    	if (_size!=null && !_size.isBlank()) {
    		_clazz=_clazz+" "+TAG_BOOTSCLASS+"-"+_size.trim();
    	}
    	    	
    	String             htmlTag  ="button";
    	Map<String,String> props    = new HashMap<String,String>();    	
    	props.put("class", _clazz);
    	props.put("type" , "button");
    	props.put("data-dismiss" , _dataDismiss);
    	
    	IModelFactory modelFactory=context.getModelFactory();
    	IModel model = modelFactory.createModel();
	    model.add(modelFactory.createOpenElementTag(htmlTag, props, null, false));
	    model.add(modelFactory.createText( _value) );
	    if (_childs!=null && _childs.size()>0) {
		    for (int i = 0; i < _childs.size(); i++) {
		    	ITemplateEvent event=_childs.get(i);
		    	model.add(event);
		    }	    	
	    }
	    model.add(modelFactory.createCloseElementTag(htmlTag));
    	return model;
    }    
    
    
}
