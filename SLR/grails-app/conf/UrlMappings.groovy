class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
		
		"/register" {
			controller="register"
			action="index"
		}
		
		"/register/registerUser" {
			controller="register"
			action="registerUser"
		}
		
		"/indexMendeley"{
			controller="index"
			action="indexMendeley"
		}

        //"/"(view:"/index")
        "/"(controller:"index", action: "index")
        //"500"(view:'/error')
		"500"(controller:"error", action: "error500")
		"404"(controller:"error", action: "error404")
		"503"(controller:"error", action: "error503")
		
		"/menu"(controller: "index", action: "menu")
		
		"/about"(controller: "index", action: "about")
		
		"/loggers"(controller: "index", action: "loggers")
		
		"/faqs"(controller: "index", action: "faqs")
		
		"/user/" {
			controller="user"
			action = "index"
		}
		
		"/user/$guid" {
			controller="user"
			action = "show"
		}
		
		"/user/$guid/friends" {
			controller="user"
			action = "friends"
		}
		
		"/user/list" {
			controller="user"
			action="list"
		}
		
		"/user/$guid/synchronizeUserProfile" {
			controller="user"
			action="synchronizeUserProfile"
		}
		
		"/slr/syncronizeListSlrMendeley" {
			controller="slr"
			action="syncronizeListSlrMendeley"
		}
		
		"/slr/syncronizeSlrMendeley/$guidSlr" {
			controller="slr"
			action="syncronizeSlrMendeley"
		}
		
		"/$guid/searchs" {
			controller="slr"
			action="searchs"
		}
		
		"/$guid/references" {
			controller="slr"
			action="references"
		}
		
		"/$guid/criterions" {
			controller="slr"
			action="criterions"
		}
		
		"/$guid/specAttributes" {
			controller="slr"
			action="specAttributes"
		}
		
		"/$guid/researchQuestions" {
			controller="slr"
			action="researchQuestions"
		}
				
		"/$guid/charts" {
			controller="slr"
			action="graphs"
		}
		
		"/reference/$idmend/exportReferenceToBibTex" {
			controller="reference"
			action="exportReferenceToBibTex"
		}
		
		"/reference/$idmend" {
			controller="reference"
			action="show"
		}
		
		"/reference/$idmend/sychronizeReferenceMend" {
			controller="reference"
			action="sychronizeReferenceMend"
		}
		
		"/$guidSlr/search/create" {
			controller="search"
			action="create"
		}
		
		"/$guidSlr/show" {
			controller="slr"
			action="show"
		}
		
		"/engineSearch/" {
			controller="engineSearch"
			action="index"
		}
		
		"/searcher/" {
			controller="searcher"
			action="index"
		}
		
		"/taskSearchs" {
			controller="index"
			action="taskSearchs"
		}
		
		"/taskSearch/$guid/details" {
			controller="taskSearch"
			action="details"
		}
		
		"/search/errorsSearchs" {
			controller="search"
			action="errorsSearchs"
		}
		
		"/wizardSlr/createSLR" {
			controller="wizardSlr"
			action="createSLR"
		}
		
		"/wizardSlr/createResQuestions" {
			controller="wizardSlr"
			action="createResQuestions"
		}
		
		"/wizardSlr/createResCriterions" {
			controller="wizardSlr"
			action="createResCriterions"
		}
	}
}
