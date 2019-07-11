import akka.Done
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, PersistentEntity}
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import model.{ListOfMemberInChatRoom, MemberInChatRoom}
import play.api.libs.json.{Format, Json}

import scala.collection.immutable.Seq

class MemberInChatRoomEntity extends PersistentEntity {
  override type Command = MemberInChatRoomCommand
  override type Event = ChatRoomEvent
  override type State = ChatRoomState

  override def initialState: ChatRoomState = ChatRoomState(List.empty)

  override def behavior: Behavior = {
    case ChatRoomState(members) => Actions()
      .onCommand[AddMemberInChatRoomCommand, ListOfMemberInChatRoom] {
      case (AddMemberInChatRoomCommand(member), context, _) =>
        context.thenPersist(AddedMemberInChatRoomEvent(member)) {event =>
          context.reply(ListOfMemberInChatRoom(Some(members)))
        }
    }.onEvent {
      case (AddedMemberInChatRoomEvent(member), state) => ChatRoomState(member :: state.members)
    }
  }

  case class ChatRoomState(members: List[MemberInChatRoom])
  object ChatRoomState {
    implicit val format: Format[ChatRoomState] = Json.format[ChatRoomState]
  }

  sealed trait MemberInChatRoomCommand extends ReplyType[ListOfMemberInChatRoom]
  final case class AddMemberInChatRoomCommand(member: MemberInChatRoom) extends MemberInChatRoomCommand

  object MemberInChatRoomSerializerRegistry extends JsonSerializerRegistry {
    override def serializers: Seq[JsonSerializer[_]] = Seq(JsonSerializer[ChatRoomState])
  }

  sealed trait ChatRoomEvent extends AggregateEvent[ChatRoomEvent] {
    def aggregateTag: AggregateEventTag[ChatRoomEvent] = ChatRoomEvent.Tag
  }
  object ChatRoomEvent {
    val Tag: AggregateEventTag[ChatRoomEvent] = AggregateEventTag[ChatRoomEvent]
  }

  final case class AddedMemberInChatRoomEvent(member: MemberInChatRoom) extends ChatRoomEvent
  final case class MemberInChatRoomEvent(member: MemberInChatRoom) extends ChatRoomEvent

}
