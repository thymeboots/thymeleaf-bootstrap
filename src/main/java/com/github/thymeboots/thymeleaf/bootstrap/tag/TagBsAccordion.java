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
import org.thymeleaf.model.ICloseElementTag;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.ITemplateEvent;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * <p>Represents an bootstrap <code>accordion</code> element
 * <p><strong>Attributes</strong> <br/>
 * <strong>expanded-index   </strong> expanded card index: [0,1,..] <br>
 * 
 * <p><strong>Examples</strong> <br> 
 * expanded-index="0,2" expanded card0 and card2 <br>
 * &lt;tb:accordion expanded-index="0,2"&gt;<br>
 * &nbsp;&nbsp;&nbsp;&lt;tb:tab id="card0" header="Header0"&gtCard0 text &lt;/tb:tab&gt;<br>
 * &nbsp;&nbsp;&nbsp;&lt;tb:tab id="card1" header="Header1"&gtCard1 text &lt;/tb:tab&gt;<br>
 * &nbsp;&nbsp;&nbsp;&lt;tb:tab id="card2" header="Header2"&gtCard2 text &lt;/tb:tab&gt;<br>
 * &nbsp;&nbsp;&nbsp;&lt;tb:tab id="card3" header="Header3"&gtCard3 text &lt;/tb:tab&gt;<br> 
 * &lt;/tb:accordion&gt;<br> 
 * 
 */
public class TagBsAccordion extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "accordion";
	private static final String TAG_HTML       = "div";
	private static final String TAG_BOOTSCLASS = "accordion";
	private static final int    PRECEDENCE = 1000;
	  
    public TagBsAccordion(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
	
	protected enum PropertyKeys {	
		dataParent("data-parent")
		,dataParentType("data-parenttype")
		,dataShow("data-show")	
		,dataPageActive("page-active")
		,expandedIndex("expanded-index");	
	 
       private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	
	
    
        
    @Override
    public String getHtmlTagStyleClass() {    	    	
    	return TAG_BOOTSCLASS;
    }
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    }     
        
	

    
	
	@Override
	protected void preProcess(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler) {
		super.preProcess(context, model, structureHandler);
		
		String id="";
		ITemplateEvent firsevent=model.get(0);
		if (firsevent instanceof IProcessableElementTag) {
			IProcessableElementTag    tagorj= (IProcessableElementTag) firsevent;
    		Map<String,String> tagattr=tagorj.getAttributeMap();
    		if (tagattr!=null) {
    			id= this.nvl(tagattr.get("id"),"").trim();
    			if (id.isBlank()) {
    				id=this.randomId();
    				Map<String,String> addprops= new HashMap<String,String>();
    				addprops.put("id", id);
    				IProcessableElementTag tagnew= cloneTag(context, tagorj, addprops);
    				model.replace(0, tagnew);
    			}
    		}
		}
		
		IProcessableElementTag tag=findBaseTag(model);
		String expandindex=","+this.nvl(tag.getAttributeValue(PropertyKeys.expandedIndex.toString()),"").trim()+",";
		expandindex=expandindex.replace(" ", "");		 
		int cardindex  =-1;
		
    	//attributes.put("id", id);
    	//Modeldeki child nesneleri icin 
		int tabseviye=0;
	    for (int i = 0; i < model.size(); i++) {
	    	ITemplateEvent event=model.get(i);	
	    	if (i>0 && event instanceof IOpenElementTag) {
	    		IOpenElementTag tagorj= (IOpenElementTag) event;
	    		String elementname=tagorj.getElementCompleteName();
	    		if (elementname.indexOf(":"+TAG_NAME)>-1 ) {
    				tabseviye++;
	    		}		    			    		
	    		//if (tabseviye==0 && (elementname.indexOf(":card")>-1 || elementname.indexOf(":tab")>-1 )) {
	    		if (tabseviye==0 && (elementname.indexOf(":tab")>-1 )) {
	    			cardindex=cardindex+1;
	    			String dataParent=id;
	    			String dataShow="";
	    			String pageActive="";
	    			if (expandindex.indexOf(","+cardindex+",")>-1) {
	    				dataShow="show";
	    				pageActive="true";
	    			}
		    		Map<String,String> addprops=new HashMap<String,String>();
		    		addprops.put(PropertyKeys.dataParent.toString()     ,dataParent );
		    		addprops.put(PropertyKeys.dataParentType.toString() ,TAG_NAME );
		    		addprops.put(PropertyKeys.dataShow.toString()       ,dataShow );
		    		addprops.put(PropertyKeys.dataPageActive.toString() ,pageActive );
		    		
		    		IProcessableElementTag tagnew= cloneTag(context, tagorj, addprops);
		    		model.replace(i, tagnew);	    			
	    		}
	    	}
	    	else if (i>0 && event instanceof ICloseElementTag) {
	    		ICloseElementTag tagorj= (ICloseElementTag) event;
	    		String elementname=tagorj.getElementCompleteName();
	    		if (elementname.indexOf(":"+TAG_NAME)>-1 ) {
    				tabseviye--;
	    		}		    			    		    	
	    	}		
	    }		
	}
        
   
}

