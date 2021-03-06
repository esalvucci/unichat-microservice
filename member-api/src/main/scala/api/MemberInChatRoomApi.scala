/**
 * Unichat
 * - The Unichat application consists of an HTTP service functioning a central hub for multiple clients chatting by means of __chat rooms__  - Clients can register themselves using unique usernames, addresses, and passwords     + Upon registration, they are endowed with an [Universally unique identifier](https://it.wikipedia.org/wiki/Universally_unique_identifier) (UUID)     + Clients can either be `admin`s or `user`s     + Unlogged clients can chat too, but they have no explicit identity or role     + A logged client can be referenced by means of its username, its address, or its UUID  - Clients can create chat rooms -- thus becoming their __owners__ --, whereas other clients may __join__ -- thus becoming __members__ --, or leave such chat rooms     + Chat rooms can be referenced by means of their name, which is assumed to be unique     + The members of a chat room can see the whole sequence of messages published within that chat room or just a sub-sequence     + The members of a chat room can see the whole set of members of the chat room  - Owners can create their chat rooms with three different access levels:     + __public__ chat rooms can be read and written by anyone, there including unlogged users: membership is unimportant here     + __open__ chat rooms can be read and written only by their members, but any logged user can join them     + conversely, in __private__ chat rooms only the owner can assign memberships  - `admin`s can inspect and manage the list of registered users     - a registered `user` can be removed either by an `admin` or by him/her self  - The list of currently existing chat rooms is publicly available  - Here we consider a _very trivial and **insecure**_ authentication and authorization schema:     * upon registration, clients are assumed to provide an identifier and a password (i.e., their __credentials__), which are stored on the server side         + the server should prevent the same identifier from being registered twice         + clients are assumed to be registered as `user`s         + `admin`s are __hardcoded__ into the provided implementation stub      * when performing an HTTP request, clients are assumed to provide their credentials as a __JSON string__ contained within the __`Authorization`__ header of the request.     The JSON string, should have one of the following forms:         + `{ `__`\"id\"`__`: \"uuid here\", `__`\"password\"`__`: \"password here\" }`         + or `{ `__`\"username\"`__`: \"username here\", `__`\"password\"`__`: \"password here\" }`         + or `{ `__`\"email\"`__`: \"email here\", `__`\"password\"`__`: \"password here\" }`      * when performing an HTTP request, clients are considered __authenticated__ if they credentials match the ones which are stored on the server side      * when performing an HTTP request, clients are considered __authorized__ if they are authenticated _and_ they their role is enabled to perform the requested operation
 *
 * OpenAPI spec version: 1.0.0
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json._
import com.lightbend.lagom.scaladsl.api.deser.PathParamSerializer
import model.MemberInChatRoom

trait MemberInChatRoomApi extends Service {

  final override def descriptor: Descriptor = {
    import Service._
    named("MemberInChatRoomApi").withCalls(
      restCall(Method.PUT, "/rooms/:chatRoomName/user", addUserInChatRoom _), 
      restCall(Method.DELETE, "/rooms/:chatRoomName/:username", removeUserFromChatRoom _)
    ).withAutoAcl(true)
  }

  /**
    * Add a user to a particular chat room
    * Add a user to a particular chat room. 
    *  
    * @param chatRoomName The chat room name  
    * @return Seq[MemberInChatRoom] Body Parameter  The username to be added.  
    */
  def addUserInChatRoom(chatRoomName: String): ServiceCall[MemberInChatRoom ,Seq[MemberInChatRoom]]
  
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

