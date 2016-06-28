package io.github.syamantm.zuul.filters.route

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.slf4j.LoggerFactory

/**
  * @author syamantak.
  */
class ForwardingFilter extends ZuulFilter {
  val logger = LoggerFactory.getLogger(classOf[ForwardingFilter])

  override def filterType(): String = "route"

  override def filterOrder(): Int = 1

  override def shouldFilter(): Boolean = true

  override def run(): AnyRef = {
    val ctx = RequestContext.getCurrentContext
    val request = ctx.getRequest
    logger.info("URI : {}", request.getRequestURI)
    //TODO: Use a http client and forward the call.
    ctx.setResponseBody("hello world")
    "hello world"
  }
}
