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

package com.github.thymeboots.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * <p>
 *   Default ThymeBoots controller.
 * </p>
 * <p>
 *   Note a class with this name existed since 3.4.0
 * </p>
 *
 * @author Rifat Yilmaz
 *
 * @since 3.4.0
 *
 */

@Controller
public class ThymeBootsController {	
	@RequestMapping(value= "/thymeboots")
	public String thymeboots(org.springframework.ui.Model model) {		
		return "thymeboots/index";
	}
	
	@RequestMapping(value= "/thymeboots/test")
	public String test(org.springframework.ui.Model model) {		
		return "thymeboots/test";
	}
	
}
