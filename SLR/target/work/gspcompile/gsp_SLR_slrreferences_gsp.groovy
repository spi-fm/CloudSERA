import es.uca.pfc.User
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_SLR_slrreferences_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/slr/references.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('render','g',8,['template':("headMeta"),'contextPath':("/")],-1)
printHtmlPart(2)
createTagBody(2, {->
createClosureForHtmlPart(3, 3)
invokeTag('captureTitle','sitemesh',10,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',10,[:],2)
printHtmlPart(4)
invokeTag('render','g',14,['template':("css"),'contextPath':("/")],-1)
printHtmlPart(5)
})
invokeTag('captureHead','sitemesh',14,[:],1)
printHtmlPart(5)
createTagBody(1, {->
printHtmlPart(6)
invokeTag('render','g',23,['template':("head"),'contextPath':("/")],-1)
printHtmlPart(7)
expressionOut.print(slrInstance.title)
printHtmlPart(8)
createClosureForHtmlPart(9, 2)
invokeTag('link','g',31,['controller':("index"),'action':("menu")],2)
printHtmlPart(10)
createClosureForHtmlPart(11, 2)
invokeTag('link','g',32,['controller':("slr"),'action':("myList")],2)
printHtmlPart(10)
createTagBody(2, {->
expressionOut.print(slrBreadCrumb)
})
invokeTag('link','g',33,['controller':("slr"),'action':("show"),'params':([guidSlr: "${slrInstance.guid}"])],2)
printHtmlPart(12)
invokeTag('render','g',41,['template':("referencesSearchResult"),'contextPath':("/slr")],-1)
printHtmlPart(13)
invokeTag('render','g',50,['template':("javascript"),'contextPath':("/")],-1)
printHtmlPart(14)
expressionOut.print(minYear)
printHtmlPart(15)
expressionOut.print(maxYear)
printHtmlPart(16)
expressionOut.print(minYear)
printHtmlPart(17)
expressionOut.print(maxYear)
printHtmlPart(18)
})
invokeTag('captureBody','sitemesh',63,[:],1)
printHtmlPart(19)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1523464426000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'none'
public static final String TAGLIB_CODEC = 'none'
}
