package model

import play.api.libs.json._

case class MemberInChatRoom (
                  username: Option[String],
                  chatroom: Option[String],
                  link: Option[Link]
)

object MemberInChatRoom {
implicit val format: Format[MemberInChatRoom] = Json.format
}

