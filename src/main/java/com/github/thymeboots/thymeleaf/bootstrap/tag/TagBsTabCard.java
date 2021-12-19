
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
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import com.github.thymeboots.thymeleaf.bootstrap.comp.FacetNamesEnum;

/**
 * <p>Represents an bootstrap <code>tab or card</code> element. Default tab element
 * <p><strong>Attributes</strong> <br>
 *  
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:tab header="tab header" &gt;&lt;tb:/tab&gt;
 *  
 * @author Rifat Yilmaz
 *
 * @since 3.4.0
 *
 */
public class TagBsTabCard extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME_CARD  = "card";
	private static final String TAG_NAME_TAB   = "tab";	
	private static final String TAG_NAME       = TAG_NAME_TAB;
	private static final String TAG_HTML                 = "div";
	private static final String TAG_BOOTSCLASS           = "card";
	private static final String TAG_BOOTSCLASS_TABPANE   = "tab-pane";
	private static final String TAG_BOOTSCLASS_CONTAINER = "container";
	private static final int    PRECEDENCE = 1000;
	
    public TagBsTabCard(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
    public TagBsTabCard(
    		String             tagVersion,
            final TemplateMode templateMode, final String dialectPrefix,
            final String elementName, final boolean prefixElementName,
            final String attributeName, final boolean prefixAttributeName,
            final int precedence) {
        super(tagVersion,templateMode, dialectPrefix, elementName, prefixElementName,attributeName, prefixAttributeName, precedence);                
    }
    
	
	protected enum PropertyKeys {	
		
		ariaLabelledby("aria-labelledby")
		,dataParent("data-parent")
		,dataParentType("data-parenttype")
		,dataShow("data-show")
		/**Card header */
		,footer
		,header	
		/**Card look or severity-[success,info,warning,danger]*/
		,look			
		/**Card title */
		,cardTitle("card-title")	
		/**Card text */
		,cardText("card-text")	
		,collapsible
		/**Card body text message*/
		,value
		; 
		
	 
	   private  String toString;
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
    	String ret= TAG_BOOTSCLASS;
    	
    	if ("tab".equals(this.getTagName() ) && "tabView".equals(this.getDataParentType())) {
    		ret=TAG_BOOTSCLASS_TABPANE+" "+TAG_BOOTSCLASS_CONTAINER;
    		if ("show".equals(getDataShow()) || "active".equals(getDataShow()) ) {
    			ret=ret+" active";
    		}
    	}
    	return ret;
    }
    
    
    public String getLook() {
    	String ret=this.nvl(this.getAttributeValue(PropertyKeys.look.toString()),"");
    	    	
    	if ("error".equals(ret)) {ret="danger";}
    	return ret;
    }      
    
    
    public String getDataParent() {
    	return this.getAttributeValue(PropertyKeys.dataParent.toString());
    }          
    public String getDataParentType() {
    	return this.getAttributeValue(PropertyKeys.dataParentType.toString());
    }          
    public String getDataShow() {
    	return this.getAttributeValue(PropertyKeys.dataShow.toString());
    }          
    public String getFooter() {
    	return this.getAttributeValue(PropertyKeys.footer.toString());
    }          
    public String getHeader() {
    	return this.getAttributeValue(PropertyKeys.header.toString());
    }          
    public String getValue() {
    	return this.getAttributeValue(PropertyKeys.value.toString());
    } 
    public String getCardTitle() {
    	return this.getAttributeValue(PropertyKeys.cardTitle.toString());
    }      
    public String getCardText() {
    	return this.getAttributeValue(PropertyKeys.cardText.toString());
    }  
    public String getCollapsible() {
    	String ret= this.nvl(this.getAttributeValue(PropertyKeys.collapsible.toString()),"").trim();
    	if (ret.equals("true") || ret.equals("yes") ) {
    		ret="true";
    	}
    	else {
    		ret="false";
    	}
    	return ret;
    }      
        
    
    private String lookClass() {
    	String ret="";
    	String val =getLook();
    	if (val!=null) {
			if (!val.isBlank()) {
				if (val.equals("error")) { val="danger";}
				val=val.trim();
				ret="bg-"+val;
				if (!"light".equals(val)) {
					ret=ret+" "+"text-white";
				}
			}    		
    	}    	
    	return ret;
    }    
    
    
    protected String getIconClass(String icon) {
    	String ret=TagBsIcon.getIconClass(icon);
    	return ret;
    }
    
	@Override
	protected void doProcess(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler) {
		super.doProcess(context, model, structureHandler);
	}
    
    @Override
    public void encodeChildren(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
    	String dataParent    =this.nvl(attributes.get(PropertyKeys.dataParent.toString() ),"").trim();
    	String dataParentType=this.nvl(attributes.get(PropertyKeys.dataParentType.toString() ),"").trim();
    	String dataShow    ="";
    	if (dataParent.isBlank()) {
    		dataShow    ="show";
    	}
    	else {
    		//Accordion parenti var ise set edilmiş değer kullanılacak
    		dataShow    =this.nvl(attributes.get(PropertyKeys.dataShow.toString() ),"show").trim();
    	}
 
    	List<ITemplateEvent> childs=null;
    	IModelFactory modelFactory = context.getModelFactory();    	    	
    	//Read attribute
    	String value =this.getValue();
    	String header=this.getHeader();
    	String title =this.getCardTitle();
    	String text  =this.getCardText();
    	String             clpsBodyCls ="collapse";
    	if (dataParentType.equals("tabView")) {
    		clpsBodyCls=TAG_BOOTSCLASS_TABPANE;
    		header=null; //tabViw ise header bilgisi nav-liste eklenyor zaten 
    	}
    	
    	String cardId=this.nvl( attributes.get("id"),"").trim();
    	if (cardId.isBlank()) {
    		cardId=this.randomId();
    	}
    	
    	String  clpsBodyId  = cardId+"_clps";    	
    	String  clpsShow        =dataShow;
    	String  clpsAriaExpanded="false";
    	String  clpsCollapsed   ="collapsed";
    	if (clpsShow.equals("show") || clpsShow.equals("active") ) { 
    		clpsAriaExpanded="true";
    		clpsCollapsed="";
    	}
    	
    	//Create Panel-Header
    	String headerId=cardId+"_hdr";
    	if (header!=null) {
    		//Create header
    		String             collapsible=this.getCollapsible();
    		String             headerTag ="div";
        	String             headerCls =(TAG_BOOTSCLASS+"-header"+" "+lookClass()).trim();
    		Map<String,String> headerAttr=new HashMap<String,String>();    		                       		
    		headerAttr.put("id", headerId);
    		headerAttr.put("class", headerCls);
    		model.add(  modelFactory.createOpenElementTag(headerTag, headerAttr, null, false)  );
    		
    		String textmodel=header;
    		if (!dataParent.isBlank() || collapsible.equals("true")) {
    			String  btnClasLink="btn btn-link"+" "+"accordion-toggle"+" "+clpsCollapsed; 
    			textmodel=
    			"\n<button class=\""+btnClasLink+"\" data-toggle=\"collapse\" data-target=\"#"+clpsBodyId+"\" "+
    			         " aria-expanded=\""+clpsAriaExpanded+"\""+
    					 " aria-controls=\""+clpsBodyId+"\" "+">"+
    		    "\n"+header+
    		    "\n</button>";
    		}
    		model.add(  modelFactory.createText(textmodel)  );
    		model.add(  modelFactory.createCloseElementTag(headerTag)  );
    	}
    	
    	
    	String             clpsBodyTag ="div";
		Map<String,String> clpsBodyAttr=new HashMap<String,String>();		
		String             clpsBodyShow= clpsShow;
		clpsBodyAttr.put("id", clpsBodyId);
		clpsBodyAttr.put("class", clpsBodyCls+" "+clpsBodyShow);				
		clpsBodyAttr.put( PropertyKeys.ariaLabelledby.toString() , headerId);
		if (!dataParent.isBlank()) {
			clpsBodyAttr.put(PropertyKeys.dataParent.toString(), "#"+dataParent);
		}		
		model.add(  modelFactory.createOpenElementTag(clpsBodyTag, clpsBodyAttr, null, false)  );    	
			
			//Create card body
	    	String             cardBodyTag ="div";
	    	String             cardBodyClas=TAG_BOOTSCLASS+"-body";
	    	if (dataParentType.equals("tabView")) {
	    		cardBodyClas="";
	    	}	    	
			Map<String,String> cardBodyAttr=new HashMap<String,String>();
			cardBodyAttr.put("class", cardBodyClas );
			model.add(  modelFactory.createOpenElementTag(cardBodyTag, cardBodyAttr, null, false)  );
			    //print value message	to body	
			    if (value!=null) {model.addModel(this.createModelText(context, " "+value));}		
			    if (title!=null) {model.addModel(this.createModel(context,"h4",title, TAG_BOOTSCLASS+"-title" , childs)   );  }
			    if (text !=null) {model.addModel(this.createModel(context,"p" ,text , TAG_BOOTSCLASS+"-text" , childs)    );  }
	    	    super.encodeChildren(context, model, attributes);    	    	
	    	model.add(  modelFactory.createCloseElementTag(cardBodyTag)  );	    	
		    //Create Footer
		    model.addModel(createFooter(context));

	    model.add(  modelFactory.createCloseElementTag(clpsBodyTag)  );
	    
	         	
    }
    

    
	private IModel createFooter(ITemplateContext context) {
		
		IModelFactory modelFactory = context.getModelFactory();
		IModel model=modelFactory.createModel();		
		if (this.getTagName().equals(TAG_NAME_CARD)) {	           
	    	Map<String, IModel> facets=createFacetsModel(context, this.getTagName());
	    	IModel         facetFooter=facets.get(FacetNamesEnum.footer.toString());
	    	String         footer=this.nvl( this.getFooter() , "").trim();
	    	
	    	if (facetFooter!=null || !footer.isBlank()) {
				String             headerTag="div";
				String             headerClss="card-footer";
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
				}						
				model.add(   modelFactory.createCloseElementTag(headerTag)  );			
			}			
		}
				
		
		return model;
	}
    
   
}
