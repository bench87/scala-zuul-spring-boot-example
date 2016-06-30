package io.github.syamantm.zuul.filters.route

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import com.twitter.util.{Duration, Await, Future}
import io.github.syamantm.zuul.client.GithubClient
import org.slf4j.LoggerFactory

/**
  * @author syamantak.
  */
class GithubFilter() extends ZuulFilter {
  val logger = LoggerFactory.getLogger(classOf[GithubFilter])

  override def filterType(): String = "route"

  override def filterOrder(): Int = 2

  override def shouldFilter(): Boolean = true

  val githubClient: GithubClient = new GithubClient

  override def run(): AnyRef = {
    val ctx = RequestContext.getCurrentContext
    logger.info("GithubFilter : run")
    val request = ctx.getRequest
    if (request.getRequestURI.startsWith("/github/")) {
      logger.info("requesting GitHub, URL : {}", request.getRequestURI)
      val username = request.getRequestURI.takeRight(8).split("/").head

      val respFuture = githubClient.getUser(username) map { response =>
        ctx.setResponseBody(response)
      } rescue { case t: Throwable =>
        logger.error(t.getMessage, t)
        Future.value(t.getMessage)
      }
      Await.ready(respFuture, Duration.fromSeconds(10))
    }
    None
  }
}
