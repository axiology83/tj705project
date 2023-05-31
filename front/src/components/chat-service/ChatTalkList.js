import React, { useEffect, useRef } from "react";
import ChatTalk from "./ChatTalk";

function ChatTalkList(props) {
  const chatRef = useRef(null);

  useEffect(() => {
    if (chatRef.current) {
      chatRef.current.scrollTop = chatRef.current.scrollHeight;
    }
  }, [props.messageList]);

  return (<>
    <h1>채팅방</h1>
    <div className='chatMessageBox' ref={chatRef}>
      
      <div>
        {props.messageList.length > 0 && props.messageList.map((messageObject, index) => (
          <ChatTalk key={index} object={messageObject} />
        ))}
      </div>
    </div>
    </>
  );
}

export default ChatTalkList;
