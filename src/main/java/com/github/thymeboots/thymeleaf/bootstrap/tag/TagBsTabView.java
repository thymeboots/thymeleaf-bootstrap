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
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.ITemplateEvent;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import org.thymeleaf.model.ICloseElementTag;
import org.thymeleaf.model.IOpenElementTag;


/**
 * <p>Represents an bootstrap <code>tabView</code> element
 * <p><strong>Attributes</strong> <br/>
 * <strong>active-index   </strong> active tab index: [0,1,..]      <br>
 * <strong>pills          </strong> pills active: [true,false]      <br>
 * <strong>tab-position   </strong> tab position: [vertical,bottom] <br>
 *
 * <p><strong>Examples</strong> <br/> 
 * &lt;tb:tabView active-index="0" pills="true" tab-position="bottom" &gt;<br>
 * &nbsp;&nbsp;&nbsp;&lt;tb:tab id="tab0" title="Header0"&gtTab0 text &lt;/tb:tab&gt;<br>
 * &nbsp;&nbsp;&nbsp;&lt;tb:tab id="tab1" title="Header1"&gtTab1 text &lt;/tb:tab&gt;<br>
 * &nbsp;&nbsp;&nbsp;&lt;tb:tab id="tab2" title="Header2"&gtTab2 text &lt;/tb:tab&gt;<br> 
 * &lt;tb:/tabView&gt;<br/> 
 * 
 */
public class TagBsTabView extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "tabView";
	private static final String TAG_HTML       = "div";
	private static final String TAG_BOOTSCLASS = "tabs";
	private static final String TAG_CHILD_TAB  = "tab";
	//private static final String TAG_CHILD_CARD  = "card";
	//private static final String TAG_CHILD_ACCORDION  = "accordion";
	private static final int    PRECEDENCE = 1000;
	  
    public TagBsTabView(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
	
	protected enum PropertyKeys 	{
	   activeIndex("active-index")
	   ,dataParent("data-parent")
	   ,dataParentType("data-parenttype")				
	   ,dataShow("data-show")
	   ,pageActive("page-active")
	   ,pills		
	   ,tabPosition("tab-position")
	   ;
	 
       private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	
			    
    public String getActiveIndex() {
    	return this.getAttributeValue(PropertyKeys.activeIndex.toString());
    }        
    public String getPills() {
    	return this.getAttributeValue(PropertyKeys.pills.toString());
    }
    public String getTabPosition() {
    	return this.getAttributeValue(PropertyKeys.tabPosition.toString());
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
		
		//Create id for base tag 
		String id="";
		IProcessableElementTag    tag=findBaseTag(model);
		Map<String,String> tagattr=tag.getAttributeMap();
		if (tagattr!=null) {
			id= this.nvl(tagattr.get("id"),"").trim();
			if (id.isBlank()) {
				id=this.randomId();
				Map<String,String> addprops= new HashMap<String,String>();
				addprops.put("id", id);
				IProcessableElementTag tagnew= cloneTag(context, tag, addprops);
				model.replace(0, tagnew);
			}
		}		
		
		//Decide the active tab
		tag=findBaseTag(model);
		String activeindex=this.nvl(tag.getAttributeValue(PropertyKeys.activeIndex.toString()),"").trim();
		if (activeindex.isBlank()) {
			activeindex="0";
		}
		
    	//Set id for tab childs and set page-active=true for active index page
		int cardindex  =-1;
		int tabseviye  =0; 
	    for (int i = 0; i < model.size(); i++) {
	    	ITemplateEvent event=model.get(i);	 
	    	
	    	if (i>0 && event instanceof IOpenElementTag) {
	    		IOpenElementTag tagorj= (IOpenElementTag) event;
	    		String elementname=tagorj.getElementCompleteName();
	    		if (elementname.indexOf(":"+TAG_NAME)>-1 ) {
    				tabseviye++;
	    		}		    		
	    		
	    		if (tabseviye==0 && (elementname.indexOf(":"+TAG_CHILD_TAB)>-1 )) {
	    		//if (tabseviye==0 && elementname.indexOf(":"+TAG_CHILD_TAB)>-1 ) {
	    			cardindex=cardindex+1;
	    			String pageActive="";
	    			if (activeindex.equals(cardindex+"")) {
	    				pageActive="active";
	    			}
		    		Map<String,String> addprops=new HashMap<String,String>();
		    		addprops.put(PropertyKeys.pageActive.toString()    ,pageActive );
		    		addprops.put(PropertyKeys.dataShow.toString()      ,pageActive );
		    		addprops.put(PropertyKeys.dataParent.toString()    ,id );
		    		addprops.put(PropertyKeys.dataParentType.toString(),TAG_NAME );
		    		
	    			
		    		String tabid=this.nvl(tagorj.getAttributeValue("id"),"");
	    			if (tabid.isBlank()) {
	    				tabid=id+"_"+"tab"+cardindex;
	    				addprops.put("id"  ,tabid );
	    			}	    
	    			
	    			IProcessableElementTag tagnew= cloneTag(context, tagorj, addprops);
		    		model.replace(i, tagnew);	    			
	    		}
	    	}	
	    	
	    	else if (i>0 && event instanceof ICloseElementTag) {
	    		ICloseElementTag tagorj= (ICloseElementTag) event;
	    		String elementname=tagorj.getElementCompleteName();
	    		if (elementname.indexOf(":"+TAG_NAME)>-1  ) {
    				tabseviye--;
	    		}	
	    	}
	    }
	    		
	}	
	
	@Override
	public void encodeBegin(ITemplateContext context, IModel model, Map<String,String> attributes )  {		
	    super.encodeBegin(context, model, attributes);
	    
	    //Container cerate
	    IModelFactory modelFactory = context.getModelFactory();		
	    String             containerTag = "div";
	    Map<String,String> containerAttr=new HashMap<String,String>();
	    containerAttr.put("class", "container-fluid");
	    model.add(   modelFactory.createOpenElementTag(containerTag, containerAttr, null, false)  );
		    //row create		    	 
		    String             rowTag = "div";
		    String             rowClas= "row";
		    Map<String,String> rowAttr=new HashMap<String,String>();
		    rowAttr.put("class", rowClas);
		    model.add(   modelFactory.createOpenElementTag(rowTag, rowAttr, null, false)  );	    
	}	
	@Override
    public void encodeEnd(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
		IModelFactory modelFactory = context.getModelFactory();
		String             rowTag = "div";		
		String             containerTag = "div";
		
			model.add(modelFactory.createCloseElementTag(rowTag));	    	        					
    	model.add(modelFactory.createCloseElementTag(containerTag));	    	        	

		super.encodeEnd(context, model, attributes);
    	
    }
	
	
	@Override
    public void encodeChildren(ITemplateContext context, IModel model,  Map<String,String> attributes)  {		
		IModelFactory modelFactory = context.getModelFactory();		
		
		createNavTabModelForUp(context, model);
		
		//Create tab container <div class="tab-content">
		String             columnClas="col-tabcontent";
    	String             columnTag ="div";
		String position= this.nvl(this.getTabPosition()  ,"").trim();
		if (!"vertical".equals(position) || position.isBlank()) {
			columnClas=columnClas+" "+"container";
		}				
    	
		Map<String,String> columnAttr=new HashMap<String,String>();
		columnAttr.put("class", columnClas	);  
		model.add(  modelFactory.createOpenElementTag(columnTag, columnAttr, null, false)  );		
			//Create tab container <div class="tab-content">
	    	String             cardBodyTag ="div";
			Map<String,String> cardBodyAttr=new HashMap<String,String>();
			cardBodyAttr.put("class", "tab-content"	);  
			model.add(  modelFactory.createOpenElementTag(cardBodyTag, cardBodyAttr, null, false)  );		
	    	  super.encodeChildren(context, model, attributes);  
	    	model.add(  modelFactory.createCloseElementTag(cardBodyTag)  );
	    model.add(  modelFactory.createCloseElementTag(columnTag)  );
    	
    	createNavTabModelForDown(context, model); 
    }
	
	private void createNavTabModelForUp(ITemplateContext context, IModel model) {
		String position= this.nvl(this.getTabPosition()  ,"").trim();
		if ("vertical".equals(position) || position.isBlank()) {
			model.addModel( createNavTabModel(context) );
		}				
	}
	private void createNavTabModelForDown(ITemplateContext context, IModel model) {
		String position= this.nvl(this.getTabPosition()  ,"").trim();
		if ("bottom".equals(position)  ) {
			model.addModel( createNavTabModel(context) );
		}				
	}
	
	private IModel createNavTabModel(ITemplateContext context) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel model=modelFactory.createModel();
		
		//Create nav-tabs <div class="tab-content">
		//Create tab container <div class="tab-content">
		String navtype="tabs";
		String pills=this.nvl(this.getPills(), "").trim();
		if (pills.equals("true")) {
			navtype="pills";
		}
		
		String position= this.nvl(this.getTabPosition()  ,"").trim();
		if ("vertical".equals(position)) {
			navtype=navtype+" "+"nav-stacked flex-column";
		}
		
		String             columnClas="col-tabnavlink";
    	String             columnTag ="div";
		Map<String,String> columnAttr=new HashMap<String,String>();
		columnAttr.put("class", columnClas	);  
		model.add(  modelFactory.createOpenElementTag(columnTag, columnAttr, null, false)  );				
	    	String             cardNavTag ="ul";
			Map<String,String> cardNavAttr=new HashMap<String,String>();
			cardNavAttr.put("class", "nav nav-"+navtype);  
			model.add(  modelFactory.createOpenElementTag(cardNavTag, cardNavAttr, null, false)  );
			model.addModel( createNavLinkModel(context) );
	    	model.add(  modelFactory.createCloseElementTag(cardNavTag)  );
	    model.add(  modelFactory.createCloseElementTag(columnTag)  );
    	return model;
		
	}
	private IModel createNavLinkModel(ITemplateContext context) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel model=modelFactory.createModel();
		
		List<ITemplateEvent> childs=this.getChilds();
		int tabseviye=0;
		if (childs!=null) {
		  for (int i = 0; i < childs.size(); i++) {
		    	ITemplateEvent event=childs.get(i);		    	
		    	if (i>0 && event instanceof IOpenElementTag) {
		    		IOpenElementTag tag= (IOpenElementTag) event;
		    		String elementname=tag.getElementCompleteName();
		    		if (elementname.indexOf(":"+TAG_NAME)>-1 ) {
	    				tabseviye++;
		    		}		    		
		    		
		    		if (tabseviye==0 && (elementname.indexOf(":"+TAG_CHILD_TAB)>-1  )) {
		    			String active   = this.nvl(tag.getAttributeValue(PropertyKeys.pageActive.toString()),"").trim();
		    			if (active.equals("active")||active.equals("true")) {
		    				active="active";
		    			}
		    			else {
		    				active="fade";
		    			}
		    			
		    			String linkid   = tag.getAttributeValue("id");
		    			String linklabel= tag.getAttributeValue("header");
		    			String textmodel=
		    			"\n<li class=\"nav-item\">"+
		    		    "<a class=\"nav-link show "+active+"\" data-toggle=\"tab\" href=\"#"+linkid+"\">"+linklabel+"</a>"+
		    		    "</li>";
		    			model.add(  modelFactory.createText(textmodel));
		    			
		    		}		    		
		    	}
		    	else if (event instanceof ICloseElementTag) {
		    		ICloseElementTag tag= (ICloseElementTag) event;
		    		String elementname=tag.getElementCompleteName();
		    		if (elementname.indexOf(":"+TAG_NAME)>-1  
		    		) {
	    				tabseviye--;
		    		}		    		
		    	}
		  }
		}
		return model;
				
	}
	

   
}

