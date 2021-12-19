
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
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

/**
 * <p>
 *   Represents an bootstrap <code>progress</code> element.<br>
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:progress&gt;&lt;tb:/progress&gt;
 * 
 * @author Rifat Yilmaz
 *
 * @since 3.4.0
 *
 */
public class TagBsProgress extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput  {
	private static final String TAG_NAME       = "progress";
	private static final String TAG_HTML       = "div";
	private static final String TAG_BOOTSCLASS = "progress";
	private static final int    PRECEDENCE = 1000;
		
    public TagBsProgress(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
	    
	protected enum PropertyKeys {
		animated
		,label
		,labelShow("label-show")
		,look
		,striped
		,max
		,min
		,value;
				
       private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	    
     
	
	
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    }         
    @Override
    public String getHtmlTagStyleClass() {
    	String ret=TAG_BOOTSCLASS+" border";    
    	return ret;
    }   
    
    @Override
    protected void encodeBegin(ITemplateContext context, IModel model, Map<String,String> attributes )  {
    	if (isAddTooltipAttributes()==false) {
    		attributes.remove("tooltip");
    	}
    	super.encodeBegin(context, model, attributes);
    }
    @Override
    protected void encodeEnd(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	long valuenow=this.getValuenow();
    	long valuemin=this.getValuemin();
    	long valuemax=this.getValuemax();
    	long valueyzd=(valuemax-valuemin);
    	if (valueyzd<1L) {
    		valueyzd=0;
    	}
    	if (valueyzd>0) {
    		valueyzd=(valuenow*100)/(valuemax - valuemin);
    	}
    	String barlabel="";
		String label= this.nvl( this.getLabel() , "").trim();
		if (!label.isBlank()) {
			barlabel=label;
		}
		else {
	    	if (this.isLabelShow()) {
	    		barlabel=""+valueyzd+"%";
	    	}			
		}
    	
		String bartooltip= "";
		String tooltip   = this.nvl(this.getTooltip() , "").trim();
		if (!tooltip.isBlank()) {
		    String tooltipPosition=this.nvl( this.getTooltipPosition() ,"").trim();
		    if (tooltipPosition.isBlank()) {
		    	tooltipPosition="auto";
		    }			
			bartooltip= "data-toggle=\"tooltip\"  data-placement=\""+tooltipPosition+"\" title=\""+tooltip+"\"";
		}
    	String bar=
    		"<div class=\""+TAG_BOOTSCLASS+"-bar  "+lookClass()+" "+stripedClass()+" "+animatedClass()+ "\" role=\""+TAG_BOOTSCLASS+"bar\""+
    			  " style=\"width:  "+valueyzd+"%\" "+
    	          " aria-valuenow=\""+valuenow+"\" "+
    	          " aria-valuemin=\""+valuemin+"\" "+
    	          " aria-valuemax=\""+valuemax+"\" "+bartooltip+">"+barlabel+"</div>";
    	model.add(  context.getModelFactory().createText( HtmlEscape.unescapeHtml(bar) ) );    		
    	super.encodeEnd(context, model, attributes);    
    		    	        	
    }
    
    @Override
    protected boolean isAddTooltipAttributes() {
    	return false;
    }
    

    public String lookClass() {
    	String str= "bg-"+this.getLook();
    	return str;
    }      
    public String stripedClass() {
    	String str= "";
    	if (this.isStriped()) {
    		str=" progress-bar-striped ";
    	}    	
    	return str;
    }      
    public String animatedClass() {
    	String str= "";
    	if (this.isAnimated() ) {
    		str=" progress-bar-striped progress-bar-animated ";
    	}    	
    	return str;
    }      
    
    
    
        

    public String getLook() {
    	String str= this.nvl( this.getAttributeValue(PropertyKeys.look.toString()) , "").trim();
    	if (str.isBlank() ) {
    		str="primary";
    	}
    	return str;
    }      

    public String getLabel() {
    	String ret= this.nvl( this.getAttributeValue(PropertyKeys.label.toString()) , "").trim();
    	return ret;
    }
    
    public boolean isLabelShow() {
    	boolean ret=true;
    	String str= this.nvl( this.getAttributeValue(PropertyKeys.labelShow.toString()) , "").trim();
    	if (str.equals("false") || str.equals("no") ) {
    		ret=false;
    	}
    	return ret;
    }      

    public boolean isStriped() {
    	boolean ret=false;
    	String str= this.nvl( this.getAttributeValue(PropertyKeys.striped.toString()) , "").trim();
    	if (str.equals("true") || str.equals("yes") ) {
    		ret=true;
    	}
    	return ret;
    }      

    public boolean isAnimated() {
    	boolean ret=false;
    	String str= this.nvl( this.getAttributeValue(PropertyKeys.animated.toString()) , "").trim();
    	if (str.equals("true") || str.equals("yes") ) {
    		ret=true;
    	}
    	return ret;
    }      
    
    public long getValuemax() {
    	long lval=0L;
    	String str= this.nvl( this.getAttributeValue(PropertyKeys.max.toString()) , "").trim();
    	if (str.isBlank() ) {
    		str="100";
    	}
    	try {
    		lval=Long.valueOf(str).longValue();
    	}
    	catch (Throwable e){
    		lval=0L;
    	};
    	return lval;
    }      
    public long getValuemin() {
    	long lval=0L;
    	String str= this.nvl( this.getAttributeValue(PropertyKeys.min.toString()) , "").trim();
    	if (str.isBlank() ) {
    		str="0";
    	}
    	try {
    		lval=Long.valueOf(str).longValue();
    	}
    	catch (Throwable e){
    		lval=0L;
    	};
    	return lval;
    }      
    public long getValuenow() {
    	long lval=0L;
    	String str= this.nvl( this.getAttributeValue(PropertyKeys.value.toString()) , "").trim();
    	if (str.isBlank() ) {
    		str="0";
    	}
    	try {
    		lval=Long.valueOf(str).longValue();
    	}
    	catch (Throwable e){
    		lval=0L;
    	};
    	return lval;
    }      
    
}
