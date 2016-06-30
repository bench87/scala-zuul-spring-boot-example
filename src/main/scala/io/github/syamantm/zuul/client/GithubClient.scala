package io.github.syamantm.zuul.client


import com.twitter.finagle.Http
import com.twitter.finagle.http.{Fields, RequestBuilder}
import com.twitter.util.Future
import org.slf4j.LoggerFactory

/**
  * @author syamantak.
  */
class GithubClient {

  val logger = LoggerFactory.getLogger(classOf[GithubClient])

  val service = Http.client
    .withTlsWithoutValidation
    .newClient("api.github.com:443")
    .toService


  def getUser(username: String): Future[String] = {
    logger.info("retrieving user info for : {}", username)
    val request = RequestBuilder()
      .url(s"https://api.github.com/users/$username")
      .addHeader(Fields.UserAgent, "curl/7.9.8 (i686-pc-linux-gnu) libcurl 7.9.8 (OpenSSL 0.9.6b) (ipv6 enabled)")
      .buildGet()

    service(request) map { resp =>
      val responseBody = resp.contentString
      logger.info("received response : {}", responseBody)
      responseBody
    }
  }

}
