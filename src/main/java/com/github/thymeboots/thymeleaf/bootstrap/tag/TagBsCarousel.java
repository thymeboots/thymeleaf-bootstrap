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

/**
 * <p>Represents an bootstrap <code>carousel</code> element
 * <p><strong>Attributes</strong> <br>
 * <strong>startAnimation  </strong> start animation: [false, true], true:default value <br>
 * <strong>showIndicators  </strong> show indicators: [false, true], true:default value <br>
 * 
 * <p><strong>Examples</strong> <br> 
 * &lt;tb:carousel active-index="0" pills="true" tab-position="bottom" &gt;<br>
 * &nbsp;&nbsp;&nbsp;&lt;tb:carouselItem id="tab0" title="Header0"&gtTab0 text &lt;/tb:carouselItem&gt;<br>
 * &nbsp;&nbsp;&nbsp;&lt;tb:carouselItem id="tab1" title="Header1"&gtTab1 text &lt;/tb:carouselItem&gt;<br> 
 * &lt;tb:/carousel&gt;<br/> 
 * 
 * @author Rifat Yilmaz
 *
 * @since 3.4.0
 *
 */
public class TagBsCarousel extends com.github.thymeboots.thymeleaf.bootstrap.comp.UIOutput {
	private static final String TAG_NAME       = "carousel";
	private static final String TAG_HTML       = "div";
	private static final String TAG_BOOTSCLASS = "carousel";
	private static final String TAG_CHILD      = "carouselItem";
	private static final int    PRECEDENCE = 1000;
	  
    public TagBsCarousel(final String dialectPrefix, String    tagVersion) {
        super(tagVersion,TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }
    
	protected enum PropertyKeys {		
		 showIndicators("show-indicators")
		,startAnimation("start-animation");	
	 
       private String toString;
       PropertyKeys(String toString) { this.toString = toString; }
       PropertyKeys() { }
       public String toString() {
           return ((toString != null) ? toString : super.toString());
       }			
	}	
    
    public String getShowIndicators() {
    	return this.getAttributeValue(PropertyKeys.showIndicators.toString());
    }        
    public String getStartAnimation() {
    	return this.getAttributeValue(PropertyKeys.startAnimation.toString() );
    }    
    		
    @Override
    public String getHtmlTagStyleClass() {    	    	
    	String ret= TAG_BOOTSCLASS;
    	String animation="slide";
    	
    	String startAnimation=this.nvl(this.getStartAnimation(),"").trim();
    	if ("false".equals(startAnimation)) {
    		animation="";
    	}
    	ret=(ret+" "+animation).trim();
    	return ret;
    }
    @Override
    public String getHtmlTag() {
    	return TAG_HTML;
    }     
        
	@Override
	protected void preProcess(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler) {
		super.preProcess(context, model, structureHandler);
		
		//Base container icin id uretilir.
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
						
		tag=findBaseTag(model);
		String activeindex=this.nvl(tag.getAttributeValue("active-index"),"").trim();
		if (activeindex.isBlank()) {
			activeindex="0";
		}		
				
    	//attributes.put("id", id);
    	//Modeldeki child nesneleri icin page-active ve gerekiyorsa id bilgisi set edilir.
		int cardindex  =-1;
	    for (int i = 0; i < model.size(); i++) {
	    	ITemplateEvent event=model.get(i);	  
	    	if (i>0 && event instanceof IProcessableElementTag) {
	    		IProcessableElementTag tagorj= (IProcessableElementTag) event;
	    		String elementname=tagorj.getElementCompleteName();
	    		if (elementname.indexOf(":"+TAG_CHILD)>-1 ) {
	    			cardindex=cardindex+1;
	    			
	    			
	    			String pageActive="";
	    			if (activeindex.equals(cardindex+"")) {
	    				pageActive="active";
	    			}
		    		Map<String,String> addprops=new HashMap<String,String>();
		    		addprops.put("page-active"  ,pageActive );
		    		addprops.put("data-parent"  ,id );
	    			
		    		String tabid=this.nvl(tagorj.getAttributeValue("id"),"");
	    			if (tabid.isBlank()) {
	    				tabid=id+"_"+"tab"+cardindex;
	    				addprops.put("id"  ,tabid );
	    			}	    
	    			
	    			IProcessableElementTag tagnew= cloneTag(context, tagorj, addprops);
		    		model.replace(i, tagnew);	    			
	    		}
	    	}	    	
	    }
	    		
	}	
	
	@Override
	public void encodeBegin(ITemplateContext context, IModel model, Map<String,String> attributes )  {
		attributes.put("data-ride", TAG_BOOTSCLASS);		
	    super.encodeBegin(context, model, attributes);
	}	
	@Override
    public void encodeEnd(ITemplateContext context, IModel model,  Map<String,String> attributes)  {
		super.encodeEnd(context, model, attributes);    	
    }
	
	
	@Override
    public void encodeChildren(ITemplateContext context, IModel model,  Map<String,String> attributes)  {		
		IModelFactory modelFactory = context.getModelFactory();
		
		createNavTabModelForUp(context, model);
		
    	String             cardBodyTag ="div";
		Map<String,String> cardBodyAttr=new HashMap<String,String>();
		cardBodyAttr.put("class", "carousel-inner"	);  
		model.add(  modelFactory.createOpenElementTag(cardBodyTag, cardBodyAttr, null, false)  );		
    	  super.encodeChildren(context, model, attributes);  
    	model.add(  modelFactory.createCloseElementTag(cardBodyTag)  );
    	
    	//Create indicator
    	model.addModel( createNavIndicator(context) );
    	
    }
	
	private IModel createNavIndicator(ITemplateContext context) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel model=modelFactory.createModel();
		
		String caroselId=this.getId();
		String indicatorPrev=
			 "\n<a class=\"carousel-control-prev\" href=\"#"+caroselId+"\" data-slide=\"prev\">"+
		     "<span class=\"carousel-control-prev-icon\"></span>"+
		     "</a>";
		model.add(modelFactory.createText(indicatorPrev));

		String indicatorNext=
			 "\n<a class=\"carousel-control-next\" href=\"#"+caroselId+"\" data-slide=\"next\">"+
		     "<span class=\"carousel-control-next-icon\"></span>"+
		     "</a>";
		model.add(modelFactory.createText(indicatorNext));
		
		return model;
	}
	private void createNavTabModelForUp(ITemplateContext context, IModel model) {
		model.addModel( createNavTabModel(context) );
	}
	
	private IModel createNavTabModel(ITemplateContext context) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel model=modelFactory.createModel();
		
    	String             cardNavTag ="ul";
		Map<String,String> cardNavAttr=new HashMap<String,String>();
		cardNavAttr.put("class", "carousel-indicators");  
		model.add(  modelFactory.createOpenElementTag(cardNavTag, cardNavAttr, null, false)  );
		model.addModel( createNavLinkModel(context) );
    	model.add(  modelFactory.createCloseElementTag(cardNavTag)  );

    	return model;
		
	}
	private IModel createNavLinkModel(ITemplateContext context) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel model=modelFactory.createModel();
		
		List<ITemplateEvent> childs=this.getChilds();
		int cardindex=-1;
		if (childs!=null) {
		  for (int i = 0; i < childs.size(); i++) {
		    	ITemplateEvent event=childs.get(i);	  
		    	if (i>0 && event instanceof IProcessableElementTag) {
		    		IProcessableElementTag tag= (IProcessableElementTag) event;
		    		String elementname=tag.getElementCompleteName();
		    		if (elementname.indexOf(":"+TAG_CHILD)>-1 ) {		    			
		    			cardindex++;
		    			String active   = this.nvl(tag.getAttributeValue("page-active"),"").trim();
		    			if (active.equals("active")) {
		    				active="active";
		    			}
		    			else {
		    				active="";
		    			}		
		    			String linkid   = tag.getAttributeValue("data-parent");
		    			String textmodel=
		    					"\n<li data-target=\"#"+linkid+"\" data-slide-to=\""+cardindex+"\" class=\""+active+"\"></li>";
		    			
		    			model.add(  modelFactory.createText(textmodel));
		    		}		    		
		    	}
		  }
		}
		return model;
				
	}
	
   
}

