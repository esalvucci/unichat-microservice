package model

import play.api.libs.json._

case class Link (
                  link: Option[String]
)

object Link {
implicit val format: Format[Link] = Json.format
}

