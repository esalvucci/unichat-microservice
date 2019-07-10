package api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import play.api.libs.json._
import com.lightbend.lagom.scaladsl.api.deser.PathParamSerializer

import model.ListOfMemberInChatRoom
import model.MemberInChatRoom

trait MemberInChatRoomApi extends Service {


  final override def descriptor = {
    import Service._
    named("MemberInChatRoomApi").withCalls(
      restCall(Method.PUT, "/rooms/:chatRoomName/username", addUserInChatRoom _), 
      restCall(Method.DELETE, "/rooms/:chatRoomName/:username", removeUserFromChatRoom _)
    ).withAutoAcl(true)
  }


  /**
    * Add a user to a particular chat room
    * Add a user to a particular chat room. 
    *  
    * @param chatRoomName The chat room name  
    * @return ListOfMemberInChatRoom Body Parameter  The username to be added.  
    */
  def addUserInChatRoom(chatRoomName: String): ServiceCall[MemberInChatRoom ,ListOfMemberInChatRoom]
  
  /**
    * Delete a particular user of a particular chat room
    * Delete a particular member of a particular chat room. 
    *  
    * @param chatRoomName The chat room name  
    * @param username The chat room user&#39;s identifier (i.e. the identifier of the corresponding user) 
    * @return void
    */
  def removeUserFromChatRoom(chatRoomName: String, username: String): ServiceCall[NotUsed ,Done]
  

  }

